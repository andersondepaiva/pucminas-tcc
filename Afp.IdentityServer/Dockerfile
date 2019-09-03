FROM microsoft/dotnet:2.2-aspnetcore-runtime AS base
WORKDIR /app
EXPOSE 80
EXPOSE 443

FROM microsoft/dotnet:2.2-sdk AS build
WORKDIR /src
COPY ["IdentityServer.Service/IdentityServer.Service.csproj", "IdentityServer.Service/"]
COPY ["IdentityServer.Application/IdentityServer.Application.csproj", "IdentityServer.Application/"]
COPY ["IdentityServer.Application.Core/IdentityServer.Application.Core.csproj", "IdentityServer.Application.Core/"]
COPY ["IdentityServer.Application.Dto/IdentityServer.Application.Dto.csproj", "IdentityServer.Application.Dto/"]
COPY ["IdentityServer.Domain/IdentityServer.Domain.csproj", "IdentityServer.Domain/"]
COPY ["IdentityServer.Domain.Core/IdentityServer.Domain.Core.csproj", "IdentityServer.Domain.Core/"]
COPY ["IdentityServer.Infra.CrossCutting/IdentityServer.Infra.CrossCutting.csproj", "IdentityServer.Infra.CrossCutting/"]
COPY ["IdentityServer.Infra.Data/IdentityServer.Infra.Data.csproj", "IdentityServer.Infra.Data/"]
COPY ["IdentityServer.Infra.IoC/IdentityServer.Infra.IoC.csproj", "IdentityServer.Infra.IoC/"]
RUN dotnet restore "IdentityServer.Service/IdentityServer.Service.csproj"
COPY . .
WORKDIR "/src/IdentityServer.Service"
RUN dotnet build "IdentityServer.Service.csproj" -c Release -o /app

FROM build AS publish
RUN dotnet publish "IdentityServer.Service.csproj" -c Release -o /app

FROM base AS final
WORKDIR /app
COPY --from=publish /app .
CMD ASPNETCORE_URLS=http://*:$PORT dotnet IdentityServer.Service.dll
