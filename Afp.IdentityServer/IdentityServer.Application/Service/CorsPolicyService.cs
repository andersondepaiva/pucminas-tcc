using IdentityServer.Domain.CustomClient.Repository;
using IdentityServer4.Services;
using Microsoft.Extensions.Logging;
using System.Linq;
using System.Threading.Tasks;

namespace IdentityServer.Application.Service
{
    public class CorsPolicyService : ICorsPolicyService
    {
        private readonly ICustomClientRepository _customClientRepository;
        private readonly ILogger<CorsPolicyService> _logger;

        public CorsPolicyService(ILogger<CorsPolicyService> logger, ICustomClientRepository customClientRepository)
        {
            _customClientRepository = customClientRepository;
            _logger = logger;
        }

        public async Task<bool> IsOriginAllowedAsync(string origin)
        {
            var result = await _customClientRepository.AllAsync(x => x.Enabled && x.AllowedCorsOrigins != null && x.AllowedCorsOrigins.Contains(origin));

            _logger.LogDebug($"Origin {origin} is allowed: {result.Any()}");

            return result.Any();
        }
    }
}
