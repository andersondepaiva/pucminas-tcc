using AutoMapper;
using IdentityServer.Application.Dto;
using IdentityServer.Application.Mapping.Extension;
using IdentityServer.Domain.CustomResource.Command;
using IdentityServer.Domain.CustomResource.Model;

namespace IdentityServer.Application.Mapping
{
    public class CustomApiResourceMapping : Profile
    {
        public CustomApiResourceMapping()
        {
            CreateMap<ApiResourceDto, CustomApiResource>();

            CreateMap<ApiResourceDto, InsertCustomApiResourceCommand>().IgnoreAllNonExisting();

            CreateMap<InsertCustomApiResourceCommand, CustomApiResource>().IgnoreAllNonExisting();
        }
    }
}
