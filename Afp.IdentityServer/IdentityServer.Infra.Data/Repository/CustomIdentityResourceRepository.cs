using Canducci.MongoDB.Repository.Connection;
using IdentityServer.Domain.CustomResource.Repository;
using IdentityServer.Infra.Data.Repository.Core;
using IdentityServer4.Models;
using MongoDB.Bson.Serialization;

namespace IdentityServer.Infra.Data.Repository
{
    public class CustomIdentityResourceRepository : BaseRepository<IdentityResource>, ICustomIdentityResourceRepository
    {
        public CustomIdentityResourceRepository(IConnect connect) : base(connect)
        {
        }
    }
}
