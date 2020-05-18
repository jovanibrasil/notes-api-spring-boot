package com.notes.controllers;

import java.net.URI;

import javax.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.notes.mappers.UserMapper;
import com.notes.model.User;
import com.notes.model.dto.UserDTO;
import com.notes.services.UserService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {

	private final UserService userService;
	private final UserMapper userMapper;

	@ResponseStatus(HttpStatus.OK)
	@GetMapping("/{userName}")
	public UserDTO getUser(@PathVariable("userName") String userName){
		User user = this.userService.findByUserName(userName);
		return userMapper.userToUserDto(user);
	}

	@ResponseStatus(HttpStatus.NO_CONTENT)
	@DeleteMapping("/{userName}")
	public void deleteUser(@PathVariable("userName") String userName){
		userService.deleteByUserName(userName);
	}

	@PostMapping
	public ResponseEntity<?> saveUser(@Valid @RequestBody UserDTO userDTO){
		log.info("Creating user {}", userDTO.getUserName());
		User user = this.userService.save(userMapper.userDtoToUser(userDTO));
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest()
				.path("/{userName}")
				.buildAndExpand(user.getUsername())
				.toUri();
		return ResponseEntity.created(uri).build();
	}

}
