namespace afp.gateway.services.tracing
{
    using System;
    using System.Net.Http;
    using System.Threading;
    using System.Threading.Tasks;
    using Butterfly.Client.Tracing;
    using Butterfly.OpenTracing;
    using Microsoft.Extensions.DependencyInjection;
    using Ocelot.Tracing.Butterfly;

    public class CustomButterflyTracing : ButterflyTracer
    {
        private const string PrefixSpanId = "ot-spanId";
        private readonly IServiceTracer _tracer;

        public CustomButterflyTracing(IServiceProvider services) : base(services)
        {
            _tracer = services.GetService<IServiceTracer>();
        }

        protected override async Task<HttpResponseMessage> TracingSendAsync(
            ISpan span,
            HttpRequestMessage request,
            CancellationToken cancellationToken,
            Action<string> addTraceIdToRepo,
            Func<HttpRequestMessage, CancellationToken, Task<HttpResponseMessage>> baseSendAsync)
        {
            if (request.Headers.Contains(PrefixSpanId))
            {
                request.Headers.Remove(PrefixSpanId);
                request.Headers.TryAddWithoutValidation(PrefixSpanId, span.SpanContext.SpanId);
            }

            addTraceIdToRepo(span.SpanContext.TraceId);

            span.Log(LogField.CreateNew().ClientSend());

            var responseMessage = await baseSendAsync(request, cancellationToken);

            span.Log(LogField.CreateNew().ClientReceive());

            await Task.Run(() => SendSpan(span, request, responseMessage));

            return responseMessage;
        }

        private void SendSpan(ISpan span, HttpRequestMessage request, HttpResponseMessage response)
        {
            span.Tags.Client().Component("HttpClient")
                .HttpMethod(request.Method.Method)
                .HttpUrl(request.RequestUri.OriginalString)
                .HttpHost(request.RequestUri.Host)
                .HttpPath(request.RequestUri.PathAndQuery)
                .PeerAddress(request.RequestUri.OriginalString)
                .PeerHostName(request.RequestUri.Host)
                .Set("body.request", request.Content.ReadAsStringAsync().Result)
                .Set("body.response", response.Content.ReadAsStringAsync().Result)
                .PeerPort(request.RequestUri.Port);

            _tracer.Tracer.Inject(span.SpanContext, request.Headers, (c, k, v) =>
            {
                if (!c.Contains(k))
                {
                    c.Add(k, v);
                }
            });
        }
    }
}
