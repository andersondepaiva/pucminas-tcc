using MongoDB.Bson;
using MongoDB.Bson.Serialization.Attributes;

namespace IdentityServer.Domain.Core.Base
{
    public class BaseModel
    {
        [BsonRequired()]
        [BsonId()]
        public ObjectId Id { get; set; }
    }
}
