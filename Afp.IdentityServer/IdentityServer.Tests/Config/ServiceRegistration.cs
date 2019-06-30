using Autofac;
using Autofac.Configuration;
using Autofac.Extensions.DependencyInjection;
using AutoMapper;
using IdentityServer.Application.Mapping;
using IdentityServer.Infra.IoC;
using MediatR;
using Microsoft.Extensions.Configuration;
using Microsoft.Extensions.DependencyInjection;
using Moq;
using System;
using System.IO;
using System.Linq;
using System.Reflection;
using System.Text;
using Xunit;
using Xunit.Abstractions;
using Xunit.Ioc.Autofac;
using Xunit.Sdk;

[assembly: TestFramework("IdentityServer.Tests.Config.ServiceRegistration", "IdentityServer.Tests")]
namespace IdentityServer.Tests.Config
{
    public class ServiceRegistration : AutofacTestFramework
    {
        public IConfiguration Configuration { get; }

        public ServiceRegistration(IMessageSink diagnosticMessageSink)
            : base(diagnosticMessageSink)
        {
            try
            {
                var builder = new ContainerBuilder();

                builder.RegisterAssemblyTypes(Assembly.GetExecutingAssembly())
                   .Where(t => t.Name.EndsWith("Test"));

                builder.Register(context => new TestOutputHelper())
                  .AsSelf()
                  .As<ITestOutputHelper>()
                  .InstancePerLifetimeScope();

                IServiceCollection serviceCollection = new ServiceCollection();
                serviceCollection.AddAutoMapper();
                AutoMapperConfig.RegisterMappings();
                serviceCollection.AddMediatR();
                serviceCollection.AddOptions();

                var configurationBuilder = new ConfigurationBuilder()
                .AddJsonFile("appsettings.json", optional: true, reloadOnChange: true);

                Configuration = configurationBuilder.Build();

                builder.RegisterModule(new ConfigurationModule(Configuration));

                builder.RegisterInstance(Configuration);

                builder.Populate(serviceCollection);
                builder.RegisterModule(new UnitTestDependencyInjectionResolver());

                Container = builder.Build();
            }
            catch (ReflectionTypeLoadException ex)
            {
                StringBuilder sb = new StringBuilder();
                foreach (Exception exSub in ex.LoaderExceptions)
                {
                    sb.AppendLine(exSub.Message);
                    FileNotFoundException exFileNotFound = exSub as FileNotFoundException;
                    if (exFileNotFound != null)
                    {
                        if (!string.IsNullOrEmpty(exFileNotFound.FusionLog))
                        {
                            sb.AppendLine("Fusion Log:");
                            sb.AppendLine(exFileNotFound.FusionLog);
                        }
                    }
                    sb.AppendLine();
                }

                string errorMessage = sb.ToString();
            }
        }
    }
}
