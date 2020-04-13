package com.notes.controllers;

import javax.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.notes.dtos.UserDTO;
import com.notes.mappers.UserMapper;
import com.notes.models.User;
import com.notes.services.UserService;
import com.notes.services.models.Response;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/users")
@Slf4j
@RequiredArgsConstructor
public class UserController {

	private final UserService userService;
	private final UserMapper userMapper;

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
		User user = this.userService.findByUserName(userName);
		response.setData(userMapper.userToUserDto(user));
		return ResponseEntity.ok(response);
	}

	/**
	 * Delete an user by name.
	 *
	 */
	@DeleteMapping("/{userName}")
	public ResponseEntity<?> deleteUser(@PathVariable("userName") String userName){
		this.userService.deleteByUserName(userName);
 		return ResponseEntity.noContent().build();
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
	public ResponseEntity<Response<UserDTO>> saveUser(@Valid @RequestBody UserDTO userDTO){
		Response<UserDTO> response = new Response<>();
		log.info("Creating user {}", userDTO.getUserName());
		User user = userMapper.userDtoToUser(userDTO);
		user = this.userService.save(user);
		userDTO = userMapper.userToUserDto(user);
		response.setData(userDTO);
		return ResponseEntity.status(HttpStatus.CREATED).body(response);
		
	}

}
