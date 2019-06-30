using IdentityServer.Application.Core.Service.Interface;
using IdentityServer.Application.Dto;
using System.Threading.Tasks;

namespace IdentityServer.Application.Service.Interface
{
    public interface ICustomResourceService : IService
    {
        Task<ApiResourceDto> InsertCustomApiResource(ApiResourceDto dto);
        void InsertInitialIdentitiesResources();
    }
}
