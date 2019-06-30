using Canducci.MongoDB.Repository.Contracts;
using IdentityServer.Domain.Core.Base;
using System;
using System.Collections.Generic;
using System.Linq.Expressions;
using System.Threading.Tasks;

namespace IdentityServer.Domain.Core.Base
{
    public interface IBaseRepository<TModel> : IRepository<TModel> where TModel : class, new()
    {
    }
}
