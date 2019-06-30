using System;
using System.Collections.Generic;
using System.Text;

namespace IdentityServer.Application.Dto.DataTransferObject
{
    public class ConsentInputModel
    {
        public string Button { get; set; }
        public IEnumerable<string> ScopesConsented { get; set; }
        public bool RememberConsent { get; set; }
        public string ReturnUrl { get; set; }
    }
}
