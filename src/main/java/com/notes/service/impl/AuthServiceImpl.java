package com.notes.service.impl;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import com.notes.configuration.security.TempUser;
import com.notes.controller.dto.JwtAuthenticationDTO;
import com.notes.controller.dto.TokenDTO;
import com.notes.exception.MicroServiceIntegrationException;
import com.notes.model.enums.ApplicationType;
import com.notes.service.AuthService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

	@Value("${urls.auth.check-token}")
	private String checkTokenUri;

	@Value("${urls.auth.create-token}")
	private String createTokenUri;
	
	@Value("${notes-api.username}")
	private String serviceUsername;
	
	@Value("${notes-api.password}")
	private String servicePassword;
	
	private final RestTemplate restTemplate;
	
	/**
	 * Checks if a received token is valid. This validation check are done by the remote
	 * authentication service.
	 *
	 * @param token
	 * @return
	 */
	public TempUser checkUserToken(String token) {
		try {
			HttpHeaders headers = new HttpHeaders();
			headers.set("Authorization", token);
			HttpEntity<String> entity = new HttpEntity<>("parameters", headers);
			ResponseEntity<TempUser> responseEntity = this.restTemplate.exchange(checkTokenUri, HttpMethod.GET,
					entity, new ParameterizedTypeReference<TempUser>() {});
			return responseEntity.getBody();
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
			JwtAuthenticationDTO authDTO = new JwtAuthenticationDTO();
			authDTO.setUserName(serviceUsername);
			authDTO.setPassword(servicePassword);
			authDTO.setApplication(ApplicationType.NOTES_APP);
			HttpEntity<JwtAuthenticationDTO> request = new HttpEntity<>(authDTO, headers);
			ResponseEntity<TokenDTO> responseEntity = this.restTemplate.exchange(createTokenUri,
					HttpMethod.POST, request, new ParameterizedTypeReference<TokenDTO>() {});
			
			log.info("Response code: {}", responseEntity.getStatusCode());
			return responseEntity.getBody().getToken();
		} catch (Exception e) {
			log.info("It was not possible to get the service auth token.");
			throw new MicroServiceIntegrationException("It was not possible to get the service auth token. " + e.getMessage(), e);
		}
	}

}
