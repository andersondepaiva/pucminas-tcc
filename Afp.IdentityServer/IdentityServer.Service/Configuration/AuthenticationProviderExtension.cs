using IdentityServer4;
using Microsoft.AspNetCore.Authentication;
using Microsoft.Extensions.DependencyInjection;
using Microsoft.IdentityModel.Tokens;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;

namespace IdentityServer.Service.Configuration
{
    public static class AuthenticationProviderExtension
    {
        public static AuthenticationBuilder AddProvidersByConfig(this AuthenticationBuilder authenticationBuilder, ProvidersConfig providersConfig)
        {
            if (providersConfig == null)
                return authenticationBuilder;

            if (providersConfig.OpenIdProviders != null)
                providersConfig.OpenIdProviders.ToList().ForEach(openIdConfig =>
                {
                    authenticationBuilder.AddOpenIdConnect(openIdConfig.AuthenticationScheme, openIdConfig.DisplayName, options =>
                    {
                        options.SignInScheme = IdentityServerConstants.ExternalCookieAuthenticationScheme;
                        options.SignOutScheme = IdentityServerConstants.SignoutScheme;
                        options.Authority = openIdConfig.Authority;
                        options.ClientId = openIdConfig.ClientId;
                        options.CallbackPath = $"/signin-{openIdConfig.Code.ToLower()}";
                        openIdConfig.ClientSecret = openIdConfig.ClientSecret;

                        openIdConfig.Scopes?.ToList().ForEach(scope =>
                        {
                            options.Scope.Add(scope);
                        });

                        options.TokenValidationParameters = new TokenValidationParameters
                        {
                            ValidateIssuer = false
                        };

                        options.GetClaimsFromUserInfoEndpoint = true;
                    });
                });

            if (providersConfig.OAuthProviders != null)
                providersConfig.OAuthProviders.ToList().ForEach(oauthConfig =>
                {
                    authenticationBuilder.AddOAuth(oauthConfig.AuthenticationScheme, options =>
                      {
                          options.SignInScheme = IdentityServerConstants.ExternalCookieAuthenticationScheme;
                          options.AuthorizationEndpoint = oauthConfig.AuthorizationEndpoint;
                          options.TokenEndpoint = oauthConfig.TokenEndpoint;
                          options.ClientId = oauthConfig.ClientId;
                          options.ClientSecret = oauthConfig.ClientSecret;
                          options.CallbackPath = $"/signin-{oauthConfig.Code.ToLower()}";
                          oauthConfig.Scopes?.ToList().ForEach(scope =>
                          {
                              options.Scope.Add(scope);
                          });
                      });
                });

            if (providersConfig.GoogleProviders != null)
                providersConfig.GoogleProviders.ToList().ForEach(googleConfig =>
                {
                    authenticationBuilder.AddGoogle(googleConfig.AuthenticationScheme, options =>
                    {
                        options.SignInScheme = IdentityServerConstants.ExternalCookieAuthenticationScheme;

                        options.ClientId = googleConfig.ClientId;
                        options.ClientSecret = googleConfig.ClientSecret;
                        options.CallbackPath = $"/signin-{googleConfig.Code.ToLower()}";
                    });
                });

            if (providersConfig.FacebookProviders != null)
                providersConfig.FacebookProviders.ToList().ForEach(facebookConfig =>
                {
                    authenticationBuilder.AddFacebook(facebookConfig.AuthenticationScheme, options =>
                    {
                        options.SignInScheme = IdentityServerConstants.ExternalCookieAuthenticationScheme;

                        options.ClientId = facebookConfig.ClientId;
                        options.ClientSecret = facebookConfig.ClientSecret;
                        options.CallbackPath = $"/signin-{facebookConfig.Code.ToLower()}";
                    });
                });

            return authenticationBuilder;
        }
    }
}
