using AutoMapper;
using IdentityServer.Application.Dto;
using IdentityServer.Application.Mapping.Extension;
using IdentityServer.Domain.CustomClient.Command;
using IdentityServer.Domain.CustomClient.Model;
using MongoDB.Bson;
using System;
using System.Collections.Generic;
using System.Text;

namespace IdentityServer.Application.Mapping
{
    public class CustomClientMapping : Profile
    {
        public CustomClientMapping()
        {
            CreateMap<ClientDto, CustomClient>().IgnoreAllNonExisting();

            CreateMap<ClientDto, InsertCustomClientCommand>().IgnoreAllNonExisting();

            CreateMap<InsertCustomClientCommand, CustomClient>().IgnoreAllNonExisting();
        }
    }
}
