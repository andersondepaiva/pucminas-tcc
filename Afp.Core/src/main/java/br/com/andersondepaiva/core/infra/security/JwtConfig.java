package br.com.andersondepaiva.core.infra.security;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Component
public class JwtConfig {

	@Value("${security.jwt.uri-auth:/api/auth/**}")
	private String uriAuth;

	@Value("${security.jwt.header:Authorization}")
	private String header;

	@Value("${security.jwt.prefix:Bearer }")
	private String prefix;

	@Value("${security.jwt.expiration:#{24*60*60}}")
	private int expiration;

	@Value("${security.jwt.secret:TccPucMinas}")
	private String secret;
}