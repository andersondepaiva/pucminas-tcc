using FluentValidation;
using IdentityServer.Domain.CustomResource.Command;
using System;
using System.Collections.Generic;
using System.Text;

namespace IdentityServer.Domain.CustomResource.Validation
{
    public abstract class CustomApiResourceValidation<T> : AbstractValidator<T> where T : CustomApiResourceCommand
    {
        protected void ValidateName()
        {
            RuleFor(c => c.Name)
                .NotNull()
                .NotEmpty().WithMessage("Name is required");
        }

        protected void ValidateDisplayName()
        {
            RuleFor(c => c.DisplayName)
                .NotNull()
                .NotEmpty().WithMessage("DisplayName is required");
        }
    }
}
