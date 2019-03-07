package com.restful;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Collections;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

@CrossOrigin(origins = "http://localhost:4200")
public class HttpInterceptor extends HandlerInterceptorAdapter {

	private static Logger log = LoggerFactory.getLogger(HttpInterceptor.class);

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		
		// TODO if debug mode is enable, turn off the verification and set userName to an specific name
		String authorization = request.getHeader("Authorization");
		if(authorization == null) {
			log.error("Invalid token (without authentication field).");
		
			// solve CORS problems setting properly response header configuration
			response.setHeader("Access-Control-Allow-Origin", "*");
			response.setHeader("Access-Control-Allow-Methods", "DELETE, HEAD, GET, OPTIONS, POST, PUT");
			response.setHeader("Access-Control-Allow-Headers", "Content-Type, Accept, X-Requested-With, Authorization");
	        response.setHeader("Access-Control-Max-Age", "3600");
			
			return false;
		}
		
		try {
			log.info("Checking request authorization.");
			URL url = new URL("https://auth.jovanibrasil.com/auth-api/auth/checkToken");
			final HttpURLConnection connection = (HttpURLConnection) url.openConnection();
			// Set request header values
			connection.setRequestMethod("POST");
			Collections.list(request.getHeaderNames()).stream().forEach(name -> {
				connection.setRequestProperty(name, request.getHeader(name));
			});
			
			// Send post request 
			connection.setDoOutput(true);
			DataOutputStream wr = new DataOutputStream(connection.getOutputStream());
			wr.flush();
			wr.close();

			// Get response code
			int responseCode = connection.getResponseCode();
			
			if (responseCode == HttpURLConnection.HTTP_OK) { 
				// HTTP_OK (200) - indicates that the request has succeeded
				BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
				String inputLine;
				StringBuffer res = new StringBuffer();

				while ((inputLine = in.readLine()) != null) {
					res.append(inputLine);
				}
				in.close();
				// Transform string raw data to json data
				System.out.println(res.toString());
				JSONObject obj = new JSONObject(res.toString());
				obj =  new JSONObject(obj.getString("data").toString());
				String userName = obj.get("userName").toString();
				request.getSession().setAttribute("userName", userName);
				log.info("The request sended by " + request.getSession().getAttribute("userName") + " was accepted (HTTP_OK)");
				return true;
			} else if(responseCode == HttpURLConnection.HTTP_UNAUTHORIZED) {
				//HTTP_UNAUTHORIZED (401) - request has not been applied because it lacks valid authentication credentials
				log.info("The request was not accepted (HTTP_UNAUTHORIZED)");
				response.setStatus(HttpURLConnection.HTTP_UNAUTHORIZED);
			} else if(responseCode == HttpURLConnection.HTTP_FORBIDDEN) {
				// HTTP_FORBIDDEN (403) - is similar to 401, but the access is permanently forbidden and re-authenticating will make no difference
				log.info("The request was not accepted (HTTP_FORBIDDEN)");
				response.setStatus(HttpURLConnection.HTTP_FORBIDDEN);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

		log.info("Invalid request (HTTP_BAD_REQUEST)");
		response.setStatus(HttpURLConnection.HTTP_BAD_REQUEST);
		return false;

	}

}
