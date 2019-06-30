using IdentityServer.Application.Dto.DataTransferObject;
using IdentityServer4.Models;
using System;
using System.Collections.Generic;
using System.Text;
using System.Threading.Tasks;

namespace IdentityServer.Application.Service.Interface
{
    public interface ICustomConsentService
    {
        Task<ProcessConsentResult> ProcessConsent(ConsentInputModel model);

        Task<ConsentViewModel> BuildViewModelAsync(string returnUrl, ConsentInputModel model = null);

        ScopeViewModel CreateScopeViewModel(IdentityResource identity, bool check);

        ScopeViewModel CreateScopeViewModel(Scope scope, bool check);

    }
}
