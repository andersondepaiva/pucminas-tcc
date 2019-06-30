using AutoMapper;
using IdentityServer.Domain.Core.Base;
using IdentityServer.Domain.CustomResource.Command;
using IdentityServer.Domain.CustomResource.Model;
using IdentityServer.Domain.CustomResource.Repository;
using IdentityServer.Infra.CrossCutting.Mediator.Interface;
using IdentityServer4.Models;
using MediatR;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading;
using System.Threading.Tasks;

namespace IdentityServer.Domain.CustomResource.CommandHandler
{
    public class CustomApiResourceCommandHandler : BaseCommandHandler, IRequestHandler<InsertCustomApiResourceCommand, string>
    {
        private readonly ICustomApiResourceRepository _customApiResourceRepository;

        public CustomApiResourceCommandHandler(IMediatorHandler mediator, IMapper mapper, ICustomApiResourceRepository customApiResourceRepository) : base(mediator, mapper)
        {
            _customApiResourceRepository = customApiResourceRepository;
        }

        public async Task<string> Handle(InsertCustomApiResourceCommand request, CancellationToken cancellationToken)
        {
            if (request.IsInvalid())
            {
                NotifyValidationErrors(request);
                return null;
            }

            var clientsResult = await _customApiResourceRepository.AllAsync(x => x.Name == request.Name);

            if (clientsResult.Any())
            {
                await _mediator.PublishBusinessNotification("API Name already exists");
                return null;
            }

            var entityInserted = await _customApiResourceRepository.AddAsync(new CustomApiResource(request.Name, request.DisplayName));

            return entityInserted.Name;
        }
    }
}
