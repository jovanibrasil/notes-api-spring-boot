package com.notes.integrations;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import com.notes.exceptions.MicroServiceIntegrationException;
import com.notes.security.TempUser;

@Component
public class AuthClient {

	@Value("${urls.auth.check-token}")
	private String uri;
	
	public TempUser checkToken(String token) {
		try {
			RestTemplate restTemplate = new RestTemplate();
			HttpHeaders headers = new HttpHeaders();
			headers.set("Authorization", token);
			HttpEntity<String> entity = new HttpEntity<>("parameters", headers);
			ResponseEntity<Response<TempUser>> responseEntity = restTemplate.exchange(uri, HttpMethod.GET, entity, 
					new ParameterizedTypeReference<Response<TempUser>>() {} );
			return responseEntity.getBody().getData();
		} catch (Exception e) {
			System.out.println(e.getMessage());
			throw new MicroServiceIntegrationException("It was not posssible to validate the user.", e);
		}
	}
	
}
