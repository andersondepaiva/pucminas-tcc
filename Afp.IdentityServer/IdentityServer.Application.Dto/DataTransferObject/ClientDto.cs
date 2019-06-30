using IdentityServer.Application.Dto.Interfaces;
using System;
using System.Collections.Generic;

namespace IdentityServer.Application.Dto
{
    public class ClientDto : IClientDto, IInsertClientDto, IInsertClientResultDto
    {
        public string ClientId { get; set; }

        public string ClientName { get; set; }

        public ICollection<string> AllowedGrantTypes { get; set; }

        public ICollection<string> RedirectUris { get; set; }

        public ICollection<string> ClientSecrets { get; set; }

        public ICollection<string> IdentityProviderRestrictions { get; set; }

        public ICollection<string> PostLogoutRedirectUris { get; set; }

        public ICollection<string> AllowedScopes { get; set; }

        public bool AllowOfflineAccess { get; set; }

        public CompanyDto Company { get; set; }

        public BrandDto Brand { get; set; }
    }
}
