using System;
using System.Collections.Generic;
using System.Text;

namespace IdentityServer.Application.Dto.Interfaces
{
    public interface IInsertClientDto
    {
        string ClientId { get; set; }

        string ClientName { get; set; }

        ICollection<string> AllowedGrantTypes { get; set; }

        ICollection<string> RedirectUris { get; set; }

        ICollection<string> PostLogoutRedirectUris { get; set; }

        ICollection<string> IdentityProviderRestrictions { get; set; }

        ICollection<string> AllowedScopes { get; set; }

        bool AllowOfflineAccess { get; set; }

        CompanyDto Company { get; set; }

        BrandDto Brand { get; set; }
    }
}
