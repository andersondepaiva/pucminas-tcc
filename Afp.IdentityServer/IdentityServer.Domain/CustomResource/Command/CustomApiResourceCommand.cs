using IdentityServer.Domain.Core.Base;
using System;
using System.Collections.Generic;
using System.Text;

namespace IdentityServer.Domain.CustomResource.Command
{
    public abstract class CustomApiResourceCommand : BaseCommand
    {
        public string Name { get; set; }

        public string DisplayName { get; set; }
    }
}
