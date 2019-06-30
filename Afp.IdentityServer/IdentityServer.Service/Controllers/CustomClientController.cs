using IdentityServer.Application.Dto;
using IdentityServer.Application.Dto.Interfaces;
using IdentityServer.Application.Service.Interface;
using IdentityServer.Infra.CrossCutting.BusinessNotification.Model;
using IdentityServer.Infra.CrossCutting.Mediator.Interface;
using IdentityServer.Service.Controllers.Core;
using MediatR;
using Microsoft.AspNetCore.Mvc;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;

namespace IdentityServer.Service.Controllers
{
    [Route("api/[controller]")]
    [ApiController]
    [ApiVersion("1.0")]
    public class CustomClientController : BaseController
    {
        private readonly ICustomClientService _customClientService;

        public CustomClientController(ICustomClientService customClientService, INotificationHandler<BusinessNotification> notifications,
            IMediatorHandler mediator) : base(notifications, mediator)
        {
            _customClientService = customClientService;
        }

        [HttpPost]
        public async Task<IActionResult> Post([FromBody] ClientDto dto)
        {
            var result = await _customClientService.Insert(dto);

            return CreatedResponse(result);
        }
    }
}
