using AutoMapper;
using IdentityServer.Application.Core.Service;
using IdentityServer.Application.Dto;
using IdentityServer.Application.Dto.Interfaces;
using IdentityServer.Application.Service.Interface;
using IdentityServer.Domain.CustomClient.Command;
using IdentityServer.Domain.CustomClient.Model;
using IdentityServer.Domain.CustomClient.Repository;
using IdentityServer.Infra.CrossCutting.Mediator.Interface;
using IdentityServer4.Models;
using IdentityServer4.Stores;
using System.Collections.Generic;
using System.Threading.Tasks;

namespace IdentityServer.Application.Service
{
    public class CustomClientService : BaseService, ICustomClientService, IClientStore
    {
        private readonly ICustomClientRepository _customClientRepository;

        public CustomClientService(IMediatorHandler mediator, IMapper mapper, ICustomClientRepository customClientRepository) : base(mediator, mapper)
        {
            _customClientRepository = customClientRepository;
        }

        public async Task<Client> FindClientByIdAsync(string clientId)
        {
            var client = await _customClientRepository.FindAsync(x => x.ClientId == clientId);

            return client;
        }

        public async Task<CustomClient> GetById(string id)
        {

            if (string.IsNullOrWhiteSpace(id))
            {
                await _mediator.PublishBusinessNotification("Parameter Id is required");
                return null;
            }

            var result = await _customClientRepository.FindAsync(x => x.ClientId == id);

            return _mapper.Map<CustomClient>(result);
        }

        public async Task<IInsertClientResultDto> Insert(ClientDto dto)
        {
            var clientSecretsGenerated = await _mediator.SendCommand(_mapper.Map<InsertCustomClientCommand>(dto));

            return new ClientDto() { ClientId = dto.ClientId, ClientSecrets = new List<string>{ clientSecretsGenerated } };
        }
    }
}
