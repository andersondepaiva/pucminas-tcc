using Canducci.MongoDB.Repository.Connection;
using Canducci.MongoDB.Repository.Contracts;
using IdentityServer.Domain.Core.Base;
using IdentityServer.Infra.CrossCutting.Extension;
using System.Linq;
using MongoDB.Driver;
using System;
using System.Collections.Generic;
using System.Linq.Expressions;
using System.Threading.Tasks;

namespace IdentityServer.Infra.Data.Repository.Core
{
    public class BaseModelRepository<TModel> : BaseRepository<TModel>, IBaseModelRepository<TModel> where TModel : BaseModel, new()
    {
        public BaseModelRepository(IConnect connect) : base(connect)
        { }

        public async Task<TModel> FindByIdAsync(string id)
        {
            return await FindAsync(x => x.Id.Equals(CreateObjectId(id)));
        }
    }
}
