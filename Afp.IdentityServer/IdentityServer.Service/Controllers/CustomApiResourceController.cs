using IdentityServer.Application.Dto;
using IdentityServer.Application.Dto.Interfaces;
using IdentityServer.Application.Service.Interface;
using IdentityServer.Infra.CrossCutting.BusinessNotification.Model;
using IdentityServer.Infra.CrossCutting.Mediator.Interface;
using IdentityServer.Service.Controllers.Core;
using MediatR;
using Microsoft.AspNetCore.Mvc;
using System.Threading.Tasks;

namespace IdentityServer.Service.Controllers
{
    [Route("api/[controller]")]
    [ApiController]
    [ApiVersion("1.0")]
    public class CustomApiResourceController : BaseController
    {
        private readonly ICustomResourceService _customResourceService;

        public CustomApiResourceController(ICustomResourceService customResourceService, INotificationHandler<BusinessNotification> notifications,
            IMediatorHandler mediator) : base(notifications, mediator)
        {
            _customResourceService = customResourceService;
        }

        [HttpPost]
        public async Task<IActionResult> Post([FromBody] ApiResourceDto dto)
        {
            var result = await _customResourceService.InsertCustomApiResource(dto);

            return CreatedResponse(result);
        }
    }
}
