using System;
using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;

namespace IdentityServer.Service.Configuration
{
    public class ProvidersConfig
    {
        public IList<OpenIdConfig> OpenIdProviders { get; set; }

        public IList<OAuthConfig> OAuthProviders { get; set; }

        public IList<GoogleConfig> GoogleProviders { get; set; }

        public IList<FacebookConfig> FacebookProviders { get; set; }
    }

    public class OpenIdConfig
    {
        public string Code { get; set; }

        public string DisplayName { get; set; }

        public string AuthenticationScheme { get; set; }

        public string Authority { get; set; }

        public string ClientId { get; set; }

        public string ClientSecret { get; set; }

        public IList<string> Scopes { get; set; }

    }

    public class OAuthConfig
    {
        public string Code { get; set; }

        public string DisplayName { get; set; }

        public string AuthenticationScheme { get; set; }

        public string AuthorizationEndpoint { get; set; }

        public string TokenEndpoint { get; set; }

        public string ClientId { get; set; }

        public string ClientSecret { get; set; }

        public IList<string> Scopes { get; set; }

    }

    public class GoogleConfig
    {
        public string Code { get; set; }

        public string DisplayName { get; set; }

        public string AuthenticationScheme { get; set; }

        public string ClientId { get; set; }

        public string ClientSecret { get; set; }
    }

    public class FacebookConfig
    {
        public string Code { get; set; }

        public string DisplayName { get; set; }

        public string AuthenticationScheme { get; set; }

        public string ClientId { get; set; }

        public string ClientSecret { get; set; }
    }
}
