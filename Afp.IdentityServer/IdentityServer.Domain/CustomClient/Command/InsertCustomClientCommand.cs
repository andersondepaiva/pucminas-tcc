using IdentityServer.Domain.CustomClient.Validation;
using IdentityServer4.Models;
using MediatR;
using System;
using System.Collections.Generic;

namespace IdentityServer.Domain.CustomClient.Command
{
    public class InsertCustomClientCommand : CustomClientCommand, IRequest<string>
    {
        public override bool IsInvalid()
        {
            ValidationResult = new InsertCustomClientValidation().Validate(this);
            return !(ValidationResult.IsValid);
        }
    }
}
