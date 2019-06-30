using IdentityServer.Domain.CustomResource.Validation;
using MediatR;

namespace IdentityServer.Domain.CustomResource.Command
{
    public class InsertCustomApiResourceCommand : CustomApiResourceCommand, IRequest<string>
    {
        public override bool IsInvalid()
        {
            ValidationResult = new InsertCustomApiResourceValidation().Validate(this);
            return !(ValidationResult.IsValid);
        }
    }
}
