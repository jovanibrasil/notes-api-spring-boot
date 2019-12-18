package com.notes.integrations;

import com.notes.ServiceProperties;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
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

@Component
@EnableConfigurationProperties(ServiceProperties.class)
public class AuthClient {

	@Value("${urls.auth.check-token}")
	private String checkTokenUri;

	@Value("${urls.auth.create-token}")
	private String createTokenUri;

	private static final Logger log = LoggerFactory.getLogger(AuthClient.class);

	private ServiceProperties serviceProperties;

	public AuthClient(ServiceProperties serviceProperties){
		this.serviceProperties = serviceProperties;
	}


	public TempUser checkUserToken(String token) {
		try {
			log.info("Checking received token");
			RestTemplate restTemplate = new RestTemplate();
			HttpHeaders headers = new HttpHeaders();
			headers.set("Authorization", token);
			HttpEntity<String> entity = new HttpEntity<>("parameters", headers);
			ResponseEntity<Response<TempUser>> responseEntity = restTemplate.exchange(checkTokenUri, HttpMethod.GET,
					entity, new ParameterizedTypeReference<Response<TempUser>>() {
					});
			log.info("Token successfully verified");
			return responseEntity.getBody().getData();
		} catch (Exception e) {
			log.info("It was not possible to validate the token. {}", e.getMessage());
			throw new MicroServiceIntegrationException("It was not posssible to validate the token. ", e);
		}
	}

	public String getServiceToken() {
		try {
			log.info("Getting service auth token ...");
			RestTemplate restTemplate = new RestTemplate();
			HttpHeaders headers = new HttpHeaders();
			headers = new HttpHeaders();
			headers.setContentType(MediaType.APPLICATION_JSON);
			JwtAuthenticationDto authDTO = new JwtAuthenticationDto();
			authDTO.setUserName(this.serviceProperties.getUsername());
			authDTO.setPassword(this.serviceProperties.getPassword());
			authDTO.setApplication(ApplicationType.NOTES_APP);
			HttpEntity<JwtAuthenticationDto> request = new HttpEntity<>(authDTO, headers);
			ResponseEntity<Response<TokenObj>> responseEntity = restTemplate.exchange(createTokenUri, 
					HttpMethod.POST, request, new ParameterizedTypeReference<Response<TokenObj>>() {
					});
			
			log.info("Response code: {}", responseEntity.getStatusCode());
			return responseEntity.getBody().getData().getToken();
		} catch (Exception e) {
			log.info("It was not posssible to get the service auth token.");
			throw new MicroServiceIntegrationException("It was not posssible to get the service auth token. " + e.getMessage(), e);
		}
	}

	

}
