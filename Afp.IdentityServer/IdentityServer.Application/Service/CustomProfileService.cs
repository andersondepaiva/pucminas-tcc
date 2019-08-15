using IdentityServer.Domain.CustomClient.Repository;
using IdentityServer4.Models;
using IdentityServer4.Services;
using System.Linq;
using System.Security.Claims;
using System.Threading.Tasks;

namespace IdentityServer.Application.Service
{
    public class CustomProfileService : IProfileService
    {
        private readonly ICustomClientRepository _customClientRepository;

        public CustomProfileService(ICustomClientRepository customClientRepository)
        {
            _customClientRepository = customClientRepository;
        }

        public async Task GetProfileDataAsync(ProfileDataRequestContext context)
        {
            var claimIdentity = GetClaimsFromUser(context.Subject);

            if (claimIdentity?.Name != null)
                context.IssuedClaims.Add(new Claim("user_name", claimIdentity.Name));
        }

        public async Task IsActiveAsync(IsActiveContext context)
        {
            var client = await _customClientRepository.FindAsync(x => x.ClientId == context.Client.ClientId);

            context.IsActive = client.Enabled;
        }

        private ClaimsIdentity GetClaimsFromUser(ClaimsPrincipal claimsPrincipal)
        {
            return claimsPrincipal.Claims?.Select(x => x.Subject).FirstOrDefault();
        }
    }
}