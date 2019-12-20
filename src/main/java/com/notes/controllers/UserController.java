package com.notes.controllers;

import java.util.Optional;

import javax.validation.Valid;

import com.notes.exceptions.CustomMessageSource;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.notes.dtos.UserDTO;
import com.notes.integrations.ErrorDetail;
import com.notes.integrations.Response;
import com.notes.models.User;
import com.notes.services.UserService;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/users")
@Slf4j
public class UserController {

	private UserService userService;	
	private CustomMessageSource msgSrc;

	public UserController(UserService userService, CustomMessageSource msgSrc) {
		this.userService = userService;
		this.msgSrc = msgSrc;
	}

	/**
	 * Retrieve a UserDTO by a given user name.
	 * 
	 * @param userName is the user name to retrieve the UserDTO.
	 * @return A response object with the UserDTO and empty error message on success, and data fiend empty and a list 
	 * of error messages on failure.  
	 */
	@GetMapping("/{userName}")
	public ResponseEntity<Response<UserDTO>> getUser(@PathVariable("userName") String userName){
		
		Response<UserDTO> response = new Response<>();
		Optional<User> optUser = this.userService.findByUserName(userName);
		
		if(!optUser.isPresent()) {
			log.error("It was not possible to find the specified user.");
			response.addError(new ErrorDetail(msgSrc.getMessage("error.user.find")));
			return ResponseEntity.badRequest().body(response);
		}
		
		response.setData(this.userToUserDTO(optUser.get()));
		return ResponseEntity.ok(response);
	}
	
	/**
	 * Delete an user by name.
	 * 
	 */
	@DeleteMapping("/{userName}")
	public ResponseEntity<Response<String>> deleteUser(@PathVariable("userName") String userName){
		log.info("Deleting user {}.", userName);
		Response<String> response = new Response<String>();
		this.userService.deleteByUserName(userName);
 		return ResponseEntity.ok(response);
	}
	
	/**
	 * 
	 * Saves a userDTO.
	 * 
	 * @param userDTO is an UserDTO.
	 * @param bindingResult is an object with the result of userDTO validation.
	 * @return An Response object with the saved UserDTO and empty error message list on success, and an Response object 
	 * with empty data and error messages on failure.  
	 */
	@PostMapping
	public ResponseEntity<Response<UserDTO>> saveUser(@Valid @RequestBody UserDTO userDTO, BindingResult bindingResult){
		Response<UserDTO> response = new Response<UserDTO>();
		try {
			log.info("Creating user {}", userDTO.getUserName());
			if(bindingResult.hasErrors()) {
				log.error("It was not possible to create the specified user. DTO binding error.");
				bindingResult.getAllErrors().forEach(err -> response.addError(new ErrorDetail(err.getDefaultMessage())));
				return ResponseEntity.badRequest().body(response);
			}
			
			this.validateUser(userDTO, bindingResult);
			if(bindingResult.hasErrors()) {
				log.error("It was not possible to create the specified user. Validation Error.");
				bindingResult.getAllErrors().forEach(err -> response.addError(new ErrorDetail(err.getDefaultMessage())));
				return ResponseEntity.badRequest().body(response);
			}
			
			User user = this.userDTOtoUser(userDTO);
			Optional<User> optUser = this.userService.save(user);
			if(!optUser.isPresent()) {
				log.error("It was not possible to create the specified user.");
				response.addError(new ErrorDetail(msgSrc.getMessage("error.user.create")));
				return ResponseEntity.badRequest().body(response);
			}
			log.info("Creating user {}", user.getUsername());
			userDTO = this.userToUserDTO(user);
			response.setData(userDTO);
			return ResponseEntity.status(HttpStatus.CREATED).body(response);
		} catch (Exception e) {
			log.error("It was not possible to create the specified user.");
			bindingResult.getAllErrors().forEach(err -> response.addError(new ErrorDetail(err.getDefaultMessage())));
			return ResponseEntity.badRequest().body(response);
		}
	}
		
	/**
	 * 
	 * Validates an UserDTO object accordingly the constraints.
	 * 
	 * 1) Verify if the user already exists.
	 *  
	 * 
	 * @param userDTO is the UserDto object that you wish to have validated.
	 * @param result bindingResult is an object with the result of userDTO validation.
	 */
	private void validateUser(UserDTO userDTO, BindingResult result) {
		userService.findByUserName(userDTO.getUserName()).ifPresent(u -> 
			result.addError(new ObjectError("User", msgSrc.getMessage("error.user.name.unique"))));
	}
	
	/**
	 * Convert an User object to an UserDTO object.  
	 * 
	 * @param user that you wish to have converted. 
	 * @return an UserDTO object.
	 */
	private UserDTO userToUserDTO(User user) {
		UserDTO userDTO = new UserDTO();
		userDTO.setUserName(user.getUsername());
		return userDTO;
	}
	
	/**
	 * Convert an UserDTO object to an User object.
	 * 
	 * @param userDTO is the object that you wish to have converted.
	 * @return an User object.
	 */
	private User userDTOtoUser(UserDTO userDTO) {
		User user = new User();
		user.setUserId(userDTO.getUserId());
		user.setUserName(userDTO.getUserName());
		return user;
	}
	
}
