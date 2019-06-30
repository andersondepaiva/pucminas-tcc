using FluentValidation.Results;

namespace IdentityServer.Domain.Core.Base
{
    public abstract class BaseCommand
    {
        public ValidationResult ValidationResult { get; set; }

        public virtual string Id { get; set; }

        public virtual bool IsActive { get; set; }

        public abstract bool IsInvalid();
    }
}
