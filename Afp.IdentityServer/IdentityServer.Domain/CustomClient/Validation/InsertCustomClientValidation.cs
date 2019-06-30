using IdentityServer.Domain.CustomClient.Command;
using System;
using System.Collections.Generic;
using System.Text;

namespace IdentityServer.Domain.CustomClient.Validation
{
    public class InsertCustomClientValidation : CustomClientValidation<InsertCustomClientCommand>
    {
        public InsertCustomClientValidation()
        {
            ValidateClientName();
            ValidateClientId();
        }
    }
}
