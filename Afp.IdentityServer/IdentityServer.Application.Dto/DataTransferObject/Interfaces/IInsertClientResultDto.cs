using System;
using System.Collections.Generic;
using System.Text;

namespace IdentityServer.Application.Dto.Interfaces
{
    public interface IInsertClientResultDto
    {
        string ClientId { get; set; }

        ICollection<string> ClientSecrets { get; set; }
    }
}
