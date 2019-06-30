package br.com.andersondepaiva.infra.filter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import com.google.common.base.Strings;
import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;

import br.com.andersondepaiva.gateway.model.ApiKey;
import br.com.andersondepaiva.gateway.model.AppProperties;

@Component
public class ApiKeyAuthenticationFilter extends ZuulFilter {

	@Autowired
	private AppProperties appProperties;

	@Override
	public Object run() throws ZuulException {

		RequestContext context = RequestContext.getCurrentContext();

		Boolean isApiKeyValid = validRequestWithApiKey(context);

		if (!isApiKeyValid) {
			context.setResponseStatusCode(HttpStatus.UNAUTHORIZED.value());
			context.setResponseBody(null);
			context.setSendZuulResponse(false);
		}

		return null;
	}

	@Override
	public String filterType() {
		return "pre";
	}

	@Override
	public int filterOrder() {
		return 1;
	}

	@Override
	public boolean shouldFilter() {
		return true;
	}

	private Boolean validRequestWithApiKey(RequestContext context) {
		boolean isApiKeyValid = false;
		
		if (context.getRequest().getMethod() == "OPTIONS")
			return true;

		for (ApiKey apiKey : appProperties.getApiKeys()) {

			if (isValidApiKey(apiKey.getKey(), context.getRequest().getHeader(apiKey.getHeader()))) {
				isApiKeyValid = true;
			}

			/**
			 * Feito dessa forma devido as práticas Object Calisthenics
			 * o princípio Only one level of indentation per method
			 */
			if (isApiKeyValid) {
				break;
			}
		}

		return isApiKeyValid;
	}

	private boolean isValidApiKey(String apiKey, String headerValue) {
		if (!Strings.isNullOrEmpty(headerValue) && headerValue.equals(apiKey)) {
			return true;
		}

		return false;
	}
}
