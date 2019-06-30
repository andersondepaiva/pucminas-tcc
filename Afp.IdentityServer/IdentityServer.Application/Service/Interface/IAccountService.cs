using IdentityServer.Application.Dto.DataTransferObject;
using System.Threading.Tasks;

namespace IdentityServer.Application.Service.Interface
{
    public interface IAccountService
    {
        Task<LoginViewModel> BuildLoginViewModelAsync(string returnUrl);

        Task<LoginViewModel> BuildLoginViewModelAsync(LoginInputModel model);

        Task<LogoutViewModel> BuildLogoutViewModelAsync(string logoutId);

        Task<LoggedOutViewModel> BuildLoggedOutViewModelAsync(string logoutId);
    }
}
