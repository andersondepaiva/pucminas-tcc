using AutoMapper;
using IdentityServer.Application.Core.Service;
using IdentityServer.Application.Service.Interface;
using IdentityServer.Domain.CustomPersistedGrant.Repository;
using IdentityServer.Infra.CrossCutting.Mediator.Interface;
using IdentityServer4.Models;
using IdentityServer4.Stores;
using System.Collections.Generic;
using System.Threading.Tasks;

namespace IdentityServer.Application.Service
{
    public class CustomPersistedGrantService : BaseService, ICustomPersistedGrantService, IPersistedGrantStore
    {
        private readonly ICustomPersistedGrantRepository _customPersistedGrantRepository;

        public CustomPersistedGrantService(IMediatorHandler mediator, IMapper mapper, ICustomPersistedGrantRepository customPersistedGrantRepository) : base(mediator, mapper)
        {
            _customPersistedGrantRepository = customPersistedGrantRepository;
        }

        public async Task<IEnumerable<PersistedGrant>> GetAllAsync(string subjectId)
        {
            return await _customPersistedGrantRepository.AllAsync(x => x.SubjectId == subjectId);
        }

        public async Task<PersistedGrant> GetAsync(string key)
        {
            return await _customPersistedGrantRepository.FindAsync(x => x.Key == key);
        }

        public async Task RemoveAllAsync(string subjectId, string clientId)
        {
            await _customPersistedGrantRepository.DeleteAsync(i => i.SubjectId == subjectId && i.ClientId == clientId);
        }

        public async Task RemoveAllAsync(string subjectId, string clientId, string type)
        {
            await _customPersistedGrantRepository.DeleteAsync(i => i.SubjectId == subjectId && i.ClientId == clientId && i.Type == type);
        }

        public async Task RemoveAsync(string key)
        {
            await _customPersistedGrantRepository.DeleteAsync(i => i.Key == key);
        }

        public async Task StoreAsync(PersistedGrant grant)
        {
            await _customPersistedGrantRepository.AddAsync(grant);
        }
    }
}
