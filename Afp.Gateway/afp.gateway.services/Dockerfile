FROM microsoft/dotnet:2.2-aspnetcore-runtime AS base
WORKDIR /app
COPY publish /app
CMD ASPNETCORE_URLS=http://*:$PORT dotnet afp.gateway.services.dll
