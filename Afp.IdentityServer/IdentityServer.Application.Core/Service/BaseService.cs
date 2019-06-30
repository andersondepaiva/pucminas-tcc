using AutoMapper;
using IdentityServer.Application.Core.Service.Interface;
using IdentityServer.Infra.CrossCutting.Mediator.Interface;

namespace IdentityServer.Application.Core.Service
{
    public abstract class BaseService : IService
    {
        protected readonly IMediatorHandler _mediator;
        protected readonly IMapper _mapper;

        public BaseService(IMediatorHandler mediator, IMapper mapper)
        {
            _mediator = mediator;
            _mapper = mapper;
        }
    }
}
