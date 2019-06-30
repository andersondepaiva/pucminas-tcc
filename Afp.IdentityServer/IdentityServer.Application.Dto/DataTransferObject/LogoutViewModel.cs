using System;
using System.Collections.Generic;
using System.Text;

namespace IdentityServer.Application.Dto.DataTransferObject
{
    public class LogoutViewModel : LogoutInputModel
    {
        public bool ShowLogoutPrompt { get; set; }
    }
}
