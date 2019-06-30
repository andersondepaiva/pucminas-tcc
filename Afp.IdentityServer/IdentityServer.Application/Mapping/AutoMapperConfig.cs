using AutoMapper;
using System;
using System.Collections.Generic;
using System.Reflection;
using System.Linq;

namespace IdentityServer.Application.Mapping
{
    public class AutoMapperConfig
    {
        public static MapperConfiguration RegisterMappings()
        {
            return new MapperConfiguration(cfg =>
            {
                GetMappings().ToList().ForEach(mapping => cfg.AddProfile((Profile)Activator.CreateInstance(mapping)));
            });
        }

        private static IEnumerable<Type> GetMappings()
        {
            return from t in Assembly.GetExecutingAssembly().GetTypes()
                   where t.IsClass
                   && t.Namespace.Contains("Mapping")
                   && t.Name.EndsWith("Mapping")
                   && t.IsSubclassOf(typeof(Profile))
                   select t;
        }
    }
}
