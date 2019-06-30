using System;
using System.Collections.Generic;
using System.Text;

namespace IdentityServer.Application.Dto
{
    public class EnableDisableClientDto
    {
        public IList<string> CompanyIds { get; set; }

        public IList<string> BrandIds { get; set; }
    }
}
