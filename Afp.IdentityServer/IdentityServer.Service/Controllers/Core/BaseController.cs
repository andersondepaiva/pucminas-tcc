using IdentityServer.Infra.CrossCutting.BusinessNotification.Model;
using IdentityServer.Infra.CrossCutting.BusinessNotification.NotificationHandler;
using IdentityServer.Infra.CrossCutting.Mediator.Interface;
using MediatR;
using Microsoft.AspNetCore.Mvc;
using System.Collections.Generic;
using System.Linq;

namespace IdentityServer.Service.Controllers.Core
{
    public class BaseController : ControllerBase
    {
        private readonly BusinessNotificationHandler _notifications;
        private readonly IMediatorHandler _mediator;

        protected BaseController(INotificationHandler<BusinessNotification> notifications,
                                IMediatorHandler mediator)
        {
            _notifications = (BusinessNotificationHandler)notifications;
            _mediator = mediator;
        }

        protected IEnumerable<BusinessNotification> Notifications => _notifications.GetNotifications();

        protected bool IsValidOperation()
        {
            return (!_notifications.HasNotifications());
        }

        protected IActionResult CreatedResponse(object result = null)
        {
            if (IsValidOperation())
            {
                return Created(string.Empty, result);
            }

            return BusinessNotifications();
        }

        private IActionResult BusinessNotifications()
        {
            return BadRequest(new
            {
                success = false,
                errors = _notifications.GetNotifications().Select(n => n.Message)
            });
        }

        protected IActionResult OkResponse(object result = null)
        {
            if (IsValidOperation())
            {
                return BuildOkResponse(result);
            }

            return BusinessNotifications();
        }

        private IActionResult BuildOkResponse(object result = null)
        {
            if (result == null)
                return Ok();
            else
                return Ok(new { result });
        }
    }
}
