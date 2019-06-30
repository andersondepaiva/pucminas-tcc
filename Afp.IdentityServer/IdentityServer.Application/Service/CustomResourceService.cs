using AutoMapper;
using IdentityServer.Application.Dto;
using IdentityServer.Application.Core.Service;
using IdentityServer.Application.Service.Interface;
using IdentityServer.Domain.CustomClient.Model;
using IdentityServer.Domain.CustomClient.Repository;
using IdentityServer.Domain.CustomResource.Command;
using IdentityServer.Domain.CustomResource.Model;
using IdentityServer.Domain.CustomResource.Repository;
using IdentityServer.Infra.CrossCutting.Mediator.Interface;
using IdentityServer4.Models;
using IdentityServer4.Stores;
using MongoDB.Bson.Serialization;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;

namespace IdentityServer.Application.Service
{
    public class CustomResourceService : BaseService, ICustomResourceService, IResourceStore
    {
        private readonly ICustomApiResourceRepository _customApiResourceRepository;
        private readonly ICustomIdentityResourceRepository _customIdentityRepository;

        public CustomResourceService(IMediatorHandler mediator, IMapper mapper, ICustomApiResourceRepository customResourceRepository, ICustomIdentityResourceRepository customIdentityRepository) : base(mediator, mapper)
        {
            _customApiResourceRepository = customResourceRepository;
            _customIdentityRepository = customIdentityRepository;
        }

        public async Task<ApiResource> FindApiResourceAsync(string name)
        {
            if (string.IsNullOrEmpty(name)) throw new ArgumentNullException(nameof(name));

            return await _customApiResourceRepository.FindAsync(x => x.Name == name);
        }

        public async Task<IEnumerable<ApiResource>> FindApiResourcesByScopeAsync(IEnumerable<string> scopeNames)
        {
            var list = await _customApiResourceRepository.AllAsync(x => x.Scopes.Any(s => scopeNames.Contains(s.Name)));

            return list;
        }

        public async Task<IEnumerable<IdentityResource>> FindIdentityResourcesByScopeAsync(IEnumerable<string> scopeNames)
        {
            var list = await _customIdentityRepository.AllAsync(x => scopeNames.Contains(x.Name));

            return list;
        }

        public async Task<Resources> GetAllResourcesAsync()
        {
            var identityResources = await GetAllIdentityResources();
            var apiResources = await GetAllApiResources();

            return new Resources(identityResources, apiResources);
        }

        private async Task<IEnumerable<ApiResource>> GetAllApiResources()
        {
            return await _customApiResourceRepository.AllAsync();
        }

        private async Task<IEnumerable<IdentityResource>> GetAllIdentityResources()
        {
            return await _customIdentityRepository.AllAsync();
        }

        public async Task<ApiResourceDto> InsertCustomApiResource(ApiResourceDto dto)
        {
            var apiName = await _mediator.SendCommand(_mapper.Map<InsertCustomApiResourceCommand>(dto));

            return new ApiResourceDto() { Name = apiName };
        }

        public void InsertInitialIdentitiesResources()
        {
            if (_customIdentityRepository.Count() < 2)
            {
                new List<IdentityResource>
                {
                    new IdentityResources.OpenId(),
                    new IdentityResources.Profile(),
                }.ForEach(i => _customIdentityRepository.Add(i));
            }
        }
    }
}
