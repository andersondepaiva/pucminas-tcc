using IdentityServer.Application.Dto;
using IdentityServer.Application.Core.Service.Interface;
using IdentityServer.Application.Dto.Interfaces;
using IdentityServer.Domain.CustomClient.Model;
using System.Threading.Tasks;

namespace IdentityServer.Application.Service.Interface
{
    public interface ICustomClientService : IService
    {
        Task<CustomClient> GetById(string id);
        Task<IInsertClientResultDto> Insert(ClientDto dto);
    }
}
