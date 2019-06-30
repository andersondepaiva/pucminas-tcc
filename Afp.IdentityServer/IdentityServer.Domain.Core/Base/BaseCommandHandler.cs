using AutoMapper;
using IdentityServer.Infra.CrossCutting.Mediator.Interface;

namespace IdentityServer.Domain.Core.Base
{
    public abstract class BaseCommandHandler
    {
        protected readonly IMediatorHandler _mediator;
        protected readonly IMapper _mapper;

        public BaseCommandHandler(IMediatorHandler mediator, IMapper mapper)
        {
            _mediator = mediator;
            _mapper = mapper;
        }

        protected void NotifyValidationErrors(BaseCommand command)
        {
            foreach (var error in command.ValidationResult.Errors)
            {
                _mediator.PublishBusinessNotification(error.ErrorMessage);
            }
        }
    }
}
