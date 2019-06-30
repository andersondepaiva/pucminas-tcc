package br.com.andersondepaiva.gateway.security;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.google.common.base.Strings;

import br.com.andersondepaiva.core.infra.security.JwtConfig;
import br.com.andersondepaiva.gateway.model.AppProperties;
import br.com.andersondepaiva.gateway.model.Route;
import br.com.andersondepaiva.infra.filter.JwtTokenAuthenticationFilter;

@EnableWebSecurity
public class SecurityTokenConfig extends WebSecurityConfigurerAdapter {

	@Autowired
	private JwtConfig jwtConfig;

	@Autowired
	private AppProperties appProperties;

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.csrf().disable().sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).and()
				.exceptionHandling()
				.authenticationEntryPoint((req, rsp, e) -> rsp.setStatus(HttpServletResponse.SC_UNAUTHORIZED)).and()
				.cors().and()
				.addFilterAfter(new JwtTokenAuthenticationFilter(jwtConfig), UsernamePasswordAuthenticationFilter.class)
				.authorizeRequests().antMatchers(HttpMethod.POST, jwtConfig.getUriAuth()).permitAll();
		http.authorizeRequests().antMatchers(HttpMethod.OPTIONS, jwtConfig.getUriAuth()).permitAll();
		http.authorizeRequests().antMatchers(HttpMethod.OPTIONS, "/**").permitAll();

		if (appProperties.getRoutes() != null) {
			for (Route route : appProperties.getRoutes()) {
				if (!Strings.isNullOrEmpty(route.getMethods())) {
					for (String method : route.getMethods().split(";")) {
						if (route.getRoles() != null && !Strings.isNullOrEmpty(route.getRoles())) {
							http.authorizeRequests()
									.antMatchers(HttpMethod.valueOf(method.toUpperCase()), route.getUri())
									.hasAnyAuthority(route.getRoles().split(";"));
						} else {
							http.authorizeRequests()
									.antMatchers(HttpMethod.valueOf(method.toUpperCase()), route.getUri()).permitAll();
						}
					}
				} else {
					if (route.getRoles() != null && !Strings.isNullOrEmpty(route.getRoles())) {
						http.authorizeRequests().antMatchers(route.getUri()).hasAnyAuthority(route.getRoles().split(";"));
					} else {
						http.authorizeRequests().antMatchers(route.getUri()).permitAll();
					}
				}
			}
		}

		http.authorizeRequests().anyRequest().authenticated();
	}

}
