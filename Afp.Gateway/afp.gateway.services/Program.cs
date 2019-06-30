using IdentityServer4.AccessTokenValidation;
using Microsoft.AspNetCore.Authentication.JwtBearer;
using Microsoft.AspNetCore.Builder;
using Microsoft.AspNetCore.Hosting;
using Microsoft.Extensions.Configuration;
using Microsoft.Extensions.DependencyInjection;
using Ocelot.DependencyInjection;
using Ocelot.Middleware;
using Ocelot.Provider.Eureka;
using System.IO;

namespace afp.gateway.services
{
    public class Program
    {
        private const string basePathOcelotConfig = "./ocelot-config/";
        private static IConfigurationRoot configuration;

        public static void Main(string[] args)
        {
            BuildWebHost(args).Run();
        }

        public static IWebHost BuildWebHost(string[] args)
        {
            return new WebHostBuilder()
            .UseKestrel()
            .UseContentRoot(Directory.GetCurrentDirectory())
            .ConfigureAppConfiguration((hostingContext, config) =>
            {
                config.SetBasePath(hostingContext.HostingEnvironment.ContentRootPath)
                .AddJsonFile($"appsettings.json", true, true)
                .AddJsonFile($"{basePathOcelotConfig}ocelot.{hostingContext.HostingEnvironment.EnvironmentName.ToLower()}.json", false, true)
                .AddEnvironmentVariables();

                configuration = config.Build();
            })
            .ConfigureServices(s =>
            {
                ConfigureAuthentication(s);
                s.AddOcelot()
                .AddEureka();
            })
            .UseIISIntegration()
            .Configure(app =>
            {
                app.UseOcelot().Wait();
            })
            .Build();
        }

        public static void ConfigureAuthentication(IServiceCollection services)
        {
            services.AddAuthentication(o =>
            {
                o.DefaultAuthenticateScheme = JwtBearerDefaults.AuthenticationScheme;
                o.DefaultChallengeScheme = JwtBearerDefaults.AuthenticationScheme;
            })
            // Identity
            .AddIdentityServerAuthentication("IdentityApiKey", x =>
            {
                x.Authority = "http://localhost:5000";
                x.SupportedTokens = SupportedTokens.Both;
                x.RequireHttpsMetadata = false;
            });
        }
    }
}
