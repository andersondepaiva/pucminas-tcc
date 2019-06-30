using Canducci.MongoDB.Repository.MongoAttribute;
using IdentityServer4.Models;
using MongoDB.Bson.Serialization.Attributes;

namespace IdentityServer.Domain.CustomClient.Model
{
    [MongoCollectionName("client")]
    [BsonIgnoreExtraElements]
    public class CustomClient : Client
    {
    }
}
