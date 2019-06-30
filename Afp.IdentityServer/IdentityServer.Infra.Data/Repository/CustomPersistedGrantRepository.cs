using Canducci.MongoDB.Repository.Connection;
using IdentityServer.Domain.CustomPersistedGrant.Repository;
using IdentityServer.Infra.Data.Repository.Core;
using IdentityServer4.Models;

namespace IdentityServer.Infra.Data.Repository
{
    public class CustomPersistedGrantRepository : BaseRepository<PersistedGrant>, ICustomPersistedGrantRepository
    {
        public CustomPersistedGrantRepository(IConnect connect) : base(connect)
        {
        }
    }
}
