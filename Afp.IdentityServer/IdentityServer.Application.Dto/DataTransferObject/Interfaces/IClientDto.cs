using System.Collections.Generic;

namespace IdentityServer.Application.Dto.Interfaces
{
    public interface IClientDto
    {
        string ClientId { get; set; }

        string ClientName { get; set; }

        ICollection<string> AllowedGrantTypes { get; set; }

        ICollection<string> RedirectUris { get; set; }

        ICollection<string> ClientSecrets { get; set; }

        ICollection<string> PostLogoutRedirectUris { get; set; }

        ICollection<string> IdentityProviderRestrictions { get; set; }

        ICollection<string> AllowedScopes { get; set; }

        bool AllowOfflineAccess { get; set; }
    }
}
