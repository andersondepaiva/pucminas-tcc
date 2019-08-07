using Autofac;
using Autofac.Extensions.DependencyInjection;
using AutoMapper;
using IdentityServer.Application.Mapping;
using IdentityServer.Application.Service.Interface;
using IdentityServer.Infra.IoC;
using IdentityServer.Infra.IoC.Extensions;
using IdentityServer.Service.Configuration;
using IdentityServer.Swagger;
using MediatR;
using Microsoft.AspNetCore.Builder;
using Microsoft.AspNetCore.Hosting;
using Microsoft.AspNetCore.Mvc;
using Microsoft.AspNetCore.Mvc.Versioning;
using Microsoft.Extensions.Configuration;
using Microsoft.Extensions.DependencyInjection;
using Swashbuckle.AspNetCore.Swagger;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Reflection;

namespace IdentityServer.Service
{
    public class Startup
    {
        private static HashSet<string> apiVersions;

        private const string apiName = "IdentityServer";

        public Startup(IHostingEnvironment env)
        {
            BuildApiVersions();

            var builder = new ConfigurationBuilder()
                .SetBasePath(env.ContentRootPath)
                .AddJsonFile("appsettings.json", optional: true, reloadOnChange: true)
                .AddJsonFile("providers.json", optional: true, reloadOnChange: true)
                .AddJsonFile($"appsettings.{env.EnvironmentName}.json", optional: true)
                .AddJsonFile($"providers.{env.EnvironmentName}.json", optional: true)
                .AddEnvironmentVariables();

            Configuration = builder.Build();
        }

        public IConfiguration Configuration { get; }

        // This method gets called by the runtime. Use this method to add services to the container.
        public IServiceProvider ConfigureServices(IServiceCollection services)
        {
            services.AddAutoMapper();
            AutoMapperConfig.RegisterMappings();

            services.AddMvc().AddJsonOptions(opt =>
            {
                opt.SerializerSettings.NullValueHandling = Newtonsoft.Json.NullValueHandling.Ignore;
            }).SetCompatibilityVersion(CompatibilityVersion.Version_2_2);

            services.AddMediatR();
            services.AddCors();
            services.AddIdentityServer()
                // Only Environments without certificate
                .AddDeveloperSigningCredential()
                //.LoadCertificate(Configuration.GetSection("ThumbPrint").Value)
                .AddMongoRepository()
                .AddClients()
                .AddIdentityApiResources()
                .AddPersistedGrants();

            var providersConfig = new ProvidersConfig();

            Configuration.Bind("Providers", providersConfig);

            services.AddAuthentication()
               .AddProvidersByConfig(providersConfig);

            services.AddApiVersioning(options =>
            {
                options.AssumeDefaultVersionWhenUnspecified = true;
                options.ReportApiVersions = true;
                options.ApiVersionReader = new MediaTypeApiVersionReader();
                options.DefaultApiVersion = new ApiVersion(1, 0);
                options.UseApiBehavior = false;
            }).AddSwaggerGen(c =>
            {
                // Mount Swagger API Spec
                apiVersions.ToList().ForEach(version => c.SwaggerDoc(version, new Info { Title = apiName, Version = version }));

                c.OperationFilter<ApiVersionOperationFilter>();
                c.DocInclusionPredicate((docName, apiDesc) =>
                {
                    var actionApiVersionModel = apiDesc.ActionDescriptor?.GetApiVersion();
                    // would mean this action is unversioned and should be included everywhere
                    if (actionApiVersionModel == null)
                    {
                        return true;
                    }
                    if (actionApiVersionModel.DeclaredApiVersions.Any())
                    {
                        return actionApiVersionModel.DeclaredApiVersions.Any(v => $"v{v.ToString()}" == docName);
                    }
                    return actionApiVersionModel.ImplementedApiVersions.Any(v => $"v{v.ToString()}" == docName);
                });
            });

            var builder = new ContainerBuilder();
            builder.Populate(services);
            builder.RegisterModule(new DependencyInjectionResolver());
            var container = builder.Build();

            // Create the IServiceProvider based on the container.
            return new AutofacServiceProvider(container);
        }

        private static void InitializeDatabase(IApplicationBuilder app)
        {
            var resource = app.ApplicationServices.GetService<ICustomResourceService>();
            resource.InsertInitialIdentitiesResources();
        }

        // This method gets called by the runtime. Use this method to configure the HTTP request pipeline.
        public void Configure(IApplicationBuilder app, IHostingEnvironment env)
        {
            app.UseCors(opt =>
            {
                opt.AllowAnyOrigin();
                opt.AllowAnyMethod();
                opt.AllowAnyHeader();
            });
            app.UseIdentityServer();
            app.UseAuthentication();

            // Enable middleware to serve generated Swagger as a JSON endpoint.
            app.UseSwagger();

            // Enable middleware to serve swagger-ui (HTML, JS, CSS, etc.), specifying the Swagger JSON endpoint.
            app.UseSwaggerUI(c =>
            {
                // Mount Swagger Endpoint Versions
                apiVersions.ToList().ForEach(version => c.SwaggerEndpoint(string.Format(@"/swagger/{0}/swagger.json", version), string.Format(@"{0} {1}", apiName, version)));
            });

            if (env.IsDevelopment())
            {
                app.UseDeveloperExceptionPage();
            }
            else
            {
                // The default HSTS value is 30 days. You may want to change this for production scenarios, see https://aka.ms/aspnetcore-hsts.
                app.UseHsts();
            }

            app.UseHttpsRedirection();
            app.UseMvc();
            app.UseMvcWithDefaultRoute();
            app.UseStaticFiles();
            InitializeDatabase(app);
        }

        private static void BuildApiVersions()
        {
            apiVersions = new HashSet<string>();

            var controllers = from t in Assembly.GetExecutingAssembly().GetTypes() where t.IsClass && t.Namespace == "IdentityServer.Service.Controllers" select t;

            controllers.ToList().ForEach(controller =>
            {
                var apiVersionAttributtes = (IEnumerable<ApiVersionAttribute>)controller.GetTypeInfo().GetCustomAttributes(typeof(ApiVersionAttribute));

                apiVersionAttributtes?.ToList()
                    .ForEach(apiVersion =>
                        apiVersion.Versions?.ToList()
                            .ForEach(version =>
                                apiVersions.Add(string.Format(@"v{0}.{1}", version.MajorVersion, version.MinorVersion))));
            });
        }
    }
}
