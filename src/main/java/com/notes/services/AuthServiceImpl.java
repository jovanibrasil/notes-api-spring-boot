package com.notes.services;

import com.notes.config.ServiceProperties;
import com.notes.services.models.Response;
import com.notes.services.models.TokenObj;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import com.notes.dtos.JwtAuthenticationDto;
import com.notes.enums.ApplicationType;
import com.notes.exceptions.MicroServiceIntegrationException;
import com.notes.security.TempUser;

@Slf4j
@Component
public class AuthServiceImpl implements AuthService {

	@Value("${urls.auth.check-token}")
	private String checkTokenUri;

	@Value("${urls.auth.create-token}")
	private String createTokenUri;

	private final ServiceProperties serviceProperties;
	private final RestTemplate restTemplate;

	public AuthServiceImpl(@Qualifier("ServiceProperties") ServiceProperties serviceProperties, RestTemplate restTemplate){
		this.serviceProperties = serviceProperties;
		this.restTemplate = restTemplate;
	}

	/**
	 * Checks if a received token is valid. This validation check are done by the remote
	 * authentication service.
	 *
	 * @param token
	 * @return
	 */
	public TempUser checkUserToken(String token) {
		try {
			log.info("Checking received token");
			HttpHeaders headers = new HttpHeaders();
			HttpEntity<String> entity = new HttpEntity<>("parameters", headers);
			ResponseEntity<Response<TempUser>> responseEntity = this.restTemplate.exchange(checkTokenUri, HttpMethod.GET,
					entity, new ParameterizedTypeReference<Response<TempUser>>() {});
			log.info("Token successfully verified");
			return responseEntity.getBody().getData();
		} catch (Exception e) {
			log.info("It was not possible to validate the token: {}. {}", token, e.getMessage());
			throw new MicroServiceIntegrationException("It was not possible to validate the token. ", e);
		}
	}

	/**
	 * Retrieves a token using the service credentials. This authentication process are done by
	 * the remote authentication process.
	 *
	 * @return
	 */
	public String getServiceToken() {
		try {
			log.info("Getting service auth token ...");
			HttpHeaders headers = new HttpHeaders();
			headers.setContentType(MediaType.APPLICATION_JSON);
			JwtAuthenticationDto authDTO = new JwtAuthenticationDto();
			authDTO.setUserName(this.serviceProperties.getUsername());
			authDTO.setPassword(this.serviceProperties.getPassword());
			authDTO.setApplication(ApplicationType.NOTES_APP);
			HttpEntity<JwtAuthenticationDto> request = new HttpEntity<>(authDTO, headers);
			ResponseEntity<Response<TokenObj>> responseEntity = this.restTemplate.exchange(createTokenUri,
					HttpMethod.POST, request, new ParameterizedTypeReference<Response<TokenObj>>() {
					});
			
			log.info("Response code: {}", responseEntity.getStatusCode());
			return responseEntity.getBody().getData().getToken();
		} catch (Exception e) {
			log.info("It was not possible to get the service auth token.");
			throw new MicroServiceIntegrationException("It was not possible to get the service auth token. " + e.getMessage(), e);
		}
	}

}
