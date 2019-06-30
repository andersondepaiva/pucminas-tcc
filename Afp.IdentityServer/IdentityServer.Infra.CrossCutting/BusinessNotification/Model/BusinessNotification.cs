using MediatR;
using System;

namespace IdentityServer.Infra.CrossCutting.BusinessNotification.Model
{
    public class BusinessNotification : INotification
    {
        public string IdNotification { get; set; }

        public string Message { get; set; }

        public BusinessNotification()
        {
            IdNotification = Guid.NewGuid().ToString();
        }
    }
}
