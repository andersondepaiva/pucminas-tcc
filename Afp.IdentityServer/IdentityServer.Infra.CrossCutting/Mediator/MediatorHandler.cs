using IdentityServer.Infra.CrossCutting.Mediator.Interface;
using MediatR;
using System.Threading.Tasks;

namespace IdentityServer.Infra.CrossCutting.Mediator
{
    public class MediatorHandler : IMediatorHandler
    {
        private readonly IMediator _mediator;

        public MediatorHandler(IMediator mediator)
        {
            _mediator = mediator;
        }

        public Task<T> SendCommand<T>(IRequest<T> command)
        {
            return _mediator.Send(command);
        }

        public Task PublisNotification<T>(T notification) where T : INotification
        {
            return _mediator.Publish(notification);
        }

        public Task PublishBusinessNotification(string message)
        {
            return PublisNotification(new BusinessNotification.Model.BusinessNotification() { Message = message });
        }
    }
}
