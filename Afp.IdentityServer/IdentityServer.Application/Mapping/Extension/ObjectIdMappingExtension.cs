/*using AutoMapper;
using IdentityServer.Domain.Core.Base;
using MongoDB.Bson;
using System;
using System.Linq.Expressions;

namespace IdentityServer.Application.Mapping.Extension
{
    public static class ObjectIdMappingExtension
    {
        public static IMappingExpression<TSource, TDestination> ToObjectId<TSource, TDestination>(this IMappingExpression<TSource, TDestination> map) where TDestination : BaseModel where TSource : BaseCommand
        {
            map.AfterMap((src, dest) =>
            {
                dest.Id = new ObjectId(src.Id);
            });

            return map;
        }
    }
}*/