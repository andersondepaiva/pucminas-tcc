namespace afp.gateway.services.tracing
{
    using Butterfly.Client.AspNetCore;
    using Microsoft.Extensions.DependencyInjection;
    using Ocelot.DependencyInjection;
    using Ocelot.Logging;
    using Ocelot.Tracing.Butterfly;
    using System;

    public static class CustomOcelotBuilderExtensions
    {
        public static IOcelotBuilder AddButterfly(this IOcelotBuilder builder, Action<ButterflyOptions> settings)
        {
            builder.Services.AddSingleton<ITracer, CustomButterflyTracing>();
            builder.Services.AddButterfly(settings);
            return builder;
        }
    }
}
