using FluentValidation;
using IdentityServer.Domain.CustomClient.Command;
using System;
using System.Collections.Generic;
using System.Text;

namespace IdentityServer.Domain.CustomClient.Validation
{
    public abstract class CustomClientValidation<T> : AbstractValidator<T> where T : CustomClientCommand 
    {
        protected void ValidateClientId()
        {
            RuleFor(c => c.ClientId)
                .NotEmpty().WithMessage("ClientId is required");
        }

        protected void ValidateClientName()
        {
            RuleFor(c => c.ClientName)
                .NotEmpty().WithMessage("ClientName is required");
        }
    }
}
