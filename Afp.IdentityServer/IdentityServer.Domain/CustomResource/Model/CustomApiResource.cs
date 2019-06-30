using Canducci.MongoDB.Repository.MongoAttribute;
using IdentityServer4.Models;
using MongoDB.Bson.Serialization.Attributes;
using System;
using System.Collections.Generic;
using System.Text;

namespace IdentityServer.Domain.CustomResource.Model
{
    [MongoCollectionName("apiresource")]
    [BsonIgnoreExtraElements]
    public class CustomApiResource : ApiResource
    {
        public CustomApiResource() : base()
        {

        }

        public CustomApiResource(string name, string displayName) : base(name, displayName)
        {

        }
    }
}
