using Canducci.MongoDB.Repository.Contracts;
using System;
using System.Collections.Generic;
using System.Linq.Expressions;
using System.Threading.Tasks;

namespace IdentityServer.Domain.Core.Base
{
    public interface IBaseModelRepository<TModel> : IBaseRepository<TModel> where TModel : BaseModel, new()
    {
        Task<TModel> FindByIdAsync(string id);
    }
}
