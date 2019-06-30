using Canducci.MongoDB.Repository.Connection;
using Canducci.MongoDB.Repository.Contracts;
using IdentityServer.Domain.Core.Base;

namespace IdentityServer.Infra.Data.Repository.Core
{
    public class BaseRepository<TModel> : Repository<TModel>, IBaseRepository<TModel> where TModel : class, new()
    {
        public BaseRepository(IConnect connect) : base(connect)
        { }
    }
}
