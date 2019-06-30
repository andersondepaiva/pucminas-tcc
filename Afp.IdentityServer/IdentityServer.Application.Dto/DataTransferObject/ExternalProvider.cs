using System;
using System.Collections.Generic;
using System.Text;

namespace IdentityServer.Application.Dto.DataTransferObject
{
    public class ExternalProvider
    {
        public string DisplayName { get; set; }
        public string AuthenticationScheme { get; set; }
    }
}
