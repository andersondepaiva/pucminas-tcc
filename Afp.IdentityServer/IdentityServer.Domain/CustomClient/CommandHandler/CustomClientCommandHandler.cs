using AutoMapper;
using IdentityServer.Domain.Core.Base;
using IdentityServer.Domain.CustomClient.Command;
using IdentityServer.Domain.CustomClient.Repository;
using IdentityServer.Infra.CrossCutting.Mediator.Interface;
using IdentityServer4.Models;
using MediatR;
using MongoDB.Bson;
using MongoDB.Driver;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading;
using System.Threading.Tasks;

namespace IdentityServer.Domain.CustomClient.CommandHandler
{
    public class CustomClientCommandHandler : BaseCommandHandler, IRequestHandler<InsertCustomClientCommand, string>
    {
        private readonly ICustomClientRepository _customClientRepository;

        public CustomClientCommandHandler(IMediatorHandler mediator, IMapper mapper, ICustomClientRepository customClientRepository) : base(mediator, mapper)
        {
            _customClientRepository = customClientRepository;
        }

        public async Task<string> Handle(InsertCustomClientCommand request, CancellationToken cancellationToken)
        {
            if (request.IsInvalid())
            {
                NotifyValidationErrors(request);
                return null;
            }

            var entityToInsert = _mapper.Map<Model.CustomClient>(request);

            var clientsResult = await _customClientRepository.AllAsync(x => x.ClientId == entityToInsert.ClientId || x.ClientName == entityToInsert.ClientName);

            if (clientsResult.Any())
            {
                await _mediator.PublishBusinessNotification("Client Id already registred");
                return null;
            }

            var clientSecret = GenerateSecret();

            entityToInsert.ClientSecrets = new List<Secret> {
                new Secret(clientSecret.Sha256())
            };

            var entityInserted = await _customClientRepository.AddAsync(entityToInsert);

            return clientSecret;
        }

        private string GenerateSecret(int length = 24)
        {
            Random random = new Random();
            string characters = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";
            StringBuilder result = new StringBuilder(length);
            for (int i = 0; i < length; i++)
            {
                result.Append(characters[random.Next(characters.Length)]);
            }
            return result.ToString();
        }
    }
}
