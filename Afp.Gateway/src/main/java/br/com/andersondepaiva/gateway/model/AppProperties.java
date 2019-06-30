package br.com.andersondepaiva.gateway.model;

import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Configuration
@ConfigurationProperties(prefix = "app")
@Getter
@Setter
@NoArgsConstructor
@EnableConfigurationProperties
@EnableAutoConfiguration
public class AppProperties {

	private List<ApiKey> apiKeys;
	
	private List<Route> routes;
	
	@Value("${app.cors.access-control-allow-origin:*}")
	private String accessControlAllowOrigin;
}
