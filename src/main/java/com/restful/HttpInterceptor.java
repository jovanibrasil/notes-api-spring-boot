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
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

@Component
public class HttpInterceptor implements HandlerInterceptor {

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
		
		try {
			
			URL url = new URL("http://localhost:9000/auth/checkToken");
			final HttpURLConnection connection = (HttpURLConnection) url.openConnection();
			connection.setRequestMethod("POST");
			
			// Set request header values
			Collections.list(request.getHeaderNames())
				    .stream().forEach(name -> {
				    	connection.setRequestProperty(name, request.getHeader(name));
				    });
				    
		    // Send post request - For post only - START
			connection.setDoOutput(true);
			DataOutputStream wr = new DataOutputStream(connection.getOutputStream());
			//wr.write("userName=test".getBytes()); // append specific data
			wr.flush();
			wr.close();
			// For post only - END

			// Get response code
			int responseCode = connection.getResponseCode();
			System.out.println("Response code received : " + responseCode);
		    
			if(responseCode == HttpURLConnection.HTTP_OK) {
				
				System.out.println("requisição foi aceita");
				
				BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
				String inputLine;
				StringBuffer res = new StringBuffer();

				while ((inputLine = in.readLine()) != null) { res.append(inputLine); }
				in.close();

				// Transform string raw data to json data
				JSONObject obj = new JSONObject(res.toString());	
				String userName = new JSONObject(obj.getString("data")).getString("userName").toString();
				request.setAttribute("userName", userName);
				
				// Get response code
				// Response errors:
					// HTTP_UNAUTHORIZED (401) - request has not been applied because it lacks valid authentication credentials
					// HTTP_FORBIDDEN (403) - is similar to 401, but the access is permanently forbidden and re-authenticating will make no difference
					// HTTP_OK (200) - indicates that the request has succeeded
				return true;
			}
			
		} catch (Exception e) {
		    
			e.printStackTrace();
		}
		
		System.out.println("requisição foi negada");
		response.setStatus(HttpURLConnection.HTTP_UNAUTHORIZED);
		
	    return false;
	}

}
