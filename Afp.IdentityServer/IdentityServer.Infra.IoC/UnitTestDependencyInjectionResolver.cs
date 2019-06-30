using Autofac;

namespace IdentityServer.Infra.IoC
{
    public class UnitTestDependencyInjectionResolver : DependencyInjectionResolver
    {
        protected override void Load(ContainerBuilder builder)
        {
            base.Load(builder);

            RegisterControllers(builder, assembliesToScan);
        }

        public virtual void RegisterControllers(ContainerBuilder builder, System.Reflection.Assembly[] assembliesToScan)
        {
            builder.RegisterAssemblyTypes(assembliesToScan)
               .Where(t => t.Name.EndsWith("Controller"))
               .InstancePerLifetimeScope()
               .AsSelf()
               .AsImplementedInterfaces();
        }

    }
}
