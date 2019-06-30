using IdentityServer.Domain.Core.Base;
using IdentityServer4.Models;
using System;
using System.Collections.Generic;
using System.Text;

namespace IdentityServer.Domain.CustomPersistedGrant.Repository
{
    public interface ICustomPersistedGrantRepository : IBaseRepository<PersistedGrant>
    {
    }
}
