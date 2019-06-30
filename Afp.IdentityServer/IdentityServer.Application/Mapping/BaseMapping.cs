using AutoMapper;
using IdentityServer.Application.Dto.Core;
using IdentityServer.Application.Mapping.Extension;
using IdentityServer.Domain.Core.Base;
using MongoDB.Bson;

namespace IdentityServer.Application.Mapping
{
    public class BaseMapping : Profile
    {
        public BaseMapping()
        {
            CreateMap<BaseDto, BaseCommand>().Ignore(x => x.ValidationResult).IncludeAllDerived();

            CreateMap<BaseCommand, BaseModel>().AfterMap((src, dest) =>
            {
                if (!string.IsNullOrWhiteSpace(src.Id))
                    dest.Id = ObjectId.Parse(src.Id);
            }).IncludeAllDerived();

            CreateMap<BaseModel, BaseCommand>().AfterMap((src, dest) => dest.Id = src.Id.ToString()).IncludeAllDerived();
        }
    }
}
