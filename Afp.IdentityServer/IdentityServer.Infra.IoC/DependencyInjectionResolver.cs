using Autofac;
using Canducci.MongoDB.Repository.Connection;
using Canducci.MongoDB.Repository.Contracts;
using IdentityServer.Application.Core.Service.Interface;
using IdentityServer.Infra.CrossCutting.Mediator;
using IdentityServer.Infra.CrossCutting.Mediator.Interface;
using MediatR;
using System;
using System.Linq;

namespace IdentityServer.Infra.IoC
{
    public class DependencyInjectionResolver : Module
    {
        protected const string assemblyNamePattern = "IdentityServer";

        protected System.Reflection.Assembly[] assembliesToScan;

        protected override void Load(ContainerBuilder builder)
        {
            RegisterConnection(builder);

            RegisterMediatorHandler(builder);

            LoadAssemblies();

            assembliesToScan = FilterProjectAssemblies();

            RegisterRepositories(builder, assembliesToScan);

            RegisterApplicationServices(builder, assembliesToScan);

            RegisterCommandHandlers(builder, assembliesToScan);

            RegisterNotificationHandlers(builder, assembliesToScan);
        }

        public virtual void RegisterConnection(ContainerBuilder builder)
        {
            builder.RegisterType<Config>().As<IConfig>();
            builder.RegisterType<Connect>().As<IConnect>();
        }

        public virtual void RegisterMediatorHandler(ContainerBuilder builder)
        {
            builder.RegisterType<MediatorHandler>().As<IMediatorHandler>();
        }

        public virtual void LoadAssemblies()
        {
            AppDomain.CurrentDomain.Load(string.Format(@"{0}{1}", assemblyNamePattern, ".Service"));
            AppDomain.CurrentDomain.Load(string.Format(@"{0}{1}", assemblyNamePattern, ".Application"));
            AppDomain.CurrentDomain.Load(string.Format(@"{0}{1}", assemblyNamePattern, ".Domain"));
            AppDomain.CurrentDomain.Load(string.Format(@"{0}{1}", assemblyNamePattern, ".Domain.Core"));
            AppDomain.CurrentDomain.Load(string.Format(@"{0}{1}", assemblyNamePattern, ".Infra.Data"));
        }

        public virtual System.Reflection.Assembly[] FilterProjectAssemblies()
        {
            return AppDomain.CurrentDomain.GetAssemblies().Where(t => t.FullName.Contains(assemblyNamePattern)).ToArray();
        }

        public virtual void RegisterRepositories(ContainerBuilder builder, System.Reflection.Assembly[] assembliesToScan)
        {
            builder.RegisterAssemblyTypes(assembliesToScan)
               .Where(t => t.Name.EndsWith("Repository"))
               .AsClosedTypesOf(typeof(IRepository<>))
               .InstancePerLifetimeScope()
               .AsSelf()
               .AsImplementedInterfaces();
        }

        public virtual void RegisterApplicationServices(ContainerBuilder builder, System.Reflection.Assembly[] assembliesToScan)
        {
            builder.RegisterAssemblyTypes(assembliesToScan.ToArray())
                .Where(t => t.Name.EndsWith("Service"))
                .As<IService>()
                .InstancePerLifetimeScope()
                .AsSelf()
                .AsImplementedInterfaces();
        }

        public virtual void RegisterCommandHandlers(ContainerBuilder builder, System.Reflection.Assembly[] assembliesToScan)
        {
            builder.RegisterAssemblyTypes(assembliesToScan.ToArray())
                .Where(t => t.Name.EndsWith("CommandHandler"))
                .AsClosedTypesOf(typeof(IRequestHandler<,>))
                .InstancePerLifetimeScope()
                .AsSelf()
                .AsImplementedInterfaces();
        }

        public virtual void RegisterNotificationHandlers(ContainerBuilder builder, System.Reflection.Assembly[] assembliesToScan)
        {
            builder.RegisterAssemblyTypes(assembliesToScan.ToArray())
                .Where(t => t.Name.EndsWith("NotificationHandler"))
                .AsClosedTypesOf(typeof(INotificationHandler<>))
                .InstancePerLifetimeScope()
                .AsSelf()
                .AsImplementedInterfaces();
        }
    }
}
