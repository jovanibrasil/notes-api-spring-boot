package com.restful.controllers;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.restful.models.User;

@RestController
@CrossOrigin
public class PingController {
	
	@RequestMapping("/ping")
	public String login(HttpServletRequest request) {
		
		return request.getAttribute("userName") + ", seu ping realizado com sucesso";
	}
	
}
