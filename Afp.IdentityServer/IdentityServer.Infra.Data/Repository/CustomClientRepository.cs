using Canducci.MongoDB.Repository.Connection;
using IdentityServer.Domain.CustomClient.Model;
using IdentityServer.Domain.CustomClient.Repository;
using IdentityServer.Infra.Data.Repository.Core;

namespace IdentityServer.Infra.Data.Repository
{
    public class CustomClientRepository : BaseRepository<CustomClient>, ICustomClientRepository
    {
        public CustomClientRepository(IConnect connect) : base(connect)
        {
        }
    }
}
