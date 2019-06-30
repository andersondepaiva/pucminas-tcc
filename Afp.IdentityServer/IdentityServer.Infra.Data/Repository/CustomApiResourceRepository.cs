using Canducci.MongoDB.Repository.Connection;
using IdentityServer.Domain.CustomClient.Model;
using IdentityServer.Domain.CustomClient.Repository;
using IdentityServer.Domain.CustomResource.Model;
using IdentityServer.Domain.CustomResource.Repository;
using IdentityServer.Infra.Data.Repository.Core;

namespace IdentityServer.Infra.Data.Repository
{
    public class CustomApiResourceRepository : BaseRepository<CustomApiResource>, ICustomApiResourceRepository
    {
        public CustomApiResourceRepository(IConnect connect) : base(connect)
        {
        }
    }
}
