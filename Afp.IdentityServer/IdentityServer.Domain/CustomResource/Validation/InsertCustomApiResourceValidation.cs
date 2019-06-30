using IdentityServer.Domain.CustomResource.Command;
using System;
using System.Collections.Generic;
using System.Text;

namespace IdentityServer.Domain.CustomResource.Validation
{
    public class InsertCustomApiResourceValidation : CustomApiResourceValidation<InsertCustomApiResourceCommand>
    {
        public InsertCustomApiResourceValidation()
        {
            ValidateDisplayName();
            ValidateName();
        }
    }
}
