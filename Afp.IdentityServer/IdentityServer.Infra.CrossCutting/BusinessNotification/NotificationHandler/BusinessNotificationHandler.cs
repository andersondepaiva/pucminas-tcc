using MediatR;
using System.Collections.Generic;
using System.Linq;
using System.Threading;
using System.Threading.Tasks;

namespace IdentityServer.Infra.CrossCutting.BusinessNotification.NotificationHandler
{
    public class BusinessNotificationHandler : INotificationHandler<Model.BusinessNotification>
    {
        private List<Model.BusinessNotification> _notifications;

        public BusinessNotificationHandler()
        {
            _notifications = new List<Model.BusinessNotification>();
        }

        public Task Handle(Model.BusinessNotification notification, CancellationToken cancellationToken)
        {
            _notifications.Add(notification);

            return Task.CompletedTask;
        }

        public virtual List<Model.BusinessNotification> GetNotifications()
        {
            return _notifications;
        }

        public virtual bool HasNotifications()
        {
            return GetNotifications().Any();
        }

        public void Dispose()
        {
            _notifications = new List<Model.BusinessNotification>();
        }
    }
}
