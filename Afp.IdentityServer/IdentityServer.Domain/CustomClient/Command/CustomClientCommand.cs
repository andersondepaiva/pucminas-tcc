using IdentityServer.Domain.Core.Base;
using IdentityServer.Domain.CustomClient.Model;
using System;
using System.Collections.Generic;
using System.Text;

namespace IdentityServer.Domain.CustomClient.Command
{
    public abstract class CustomClientCommand : BaseCommand
    {
        public string ClientId { get; set; }

        public string ClientName { get; set; }

        public ICollection<string> AllowedGrantTypes { get; set; }

        public ICollection<string> RedirectUris { get; set; }

        public ICollection<string> PostLogoutRedirectUris { get; set; }

        public ICollection<string> AllowedScopes { get; set; }

        public bool AllowOfflineAccess { get; set; }
    }
}
