FROM microsoft/dotnet:2.2-aspnetcore-runtime AS base
WORKDIR /app
EXPOSE 80
EXPOSE 443

FROM microsoft/dotnet:2.2-sdk AS build
WORKDIR /src
COPY ["afp.gateway.services.csproj", "afp.gateway.services/"]
RUN dotnet restore "afp.gateway.services/afp.gateway.services.csproj"
COPY . .
WORKDIR "/src/afp.gateway.services"
RUN dotnet build "afp.gateway.services.csproj" -c Release -o /app

FROM build AS publish
RUN dotnet publish "afp.gateway.services.csproj" -c Release -o /app

FROM base AS final
WORKDIR /app
COPY --from=publish /app .
CMD ASPNETCORE_URLS=http://*:$PORT dotnet afp.gateway.services.dll
