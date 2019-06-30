using MediatR;
using System.Threading.Tasks;

namespace IdentityServer.Infra.CrossCutting.Mediator.Interface
{
    public interface IMediatorHandler
    {
        Task<T> SendCommand<T>(IRequest<T> command);

        Task PublisNotification<T>(T notification) where T : INotification;

        Task PublishBusinessNotification(string message);
    }
}
