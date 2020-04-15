package com.notes.controllers;

import java.net.URI;

import javax.validation.Valid;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.notes.dtos.UserDTO;
import com.notes.mappers.UserMapper;
import com.notes.models.User;
import com.notes.services.UserService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {

	private final UserService userService;
	private final UserMapper userMapper;

	/**
	 * Busca um usuário pelo nome.
	 *
	 * @param userName é o nome do usuário que está se buscando.
	 * @return 
	 */
	@GetMapping("/{userName}")
	public ResponseEntity<UserDTO> getUser(@PathVariable("userName") String userName){
		User user = this.userService.findByUserName(userName);
		return ResponseEntity.ok(userMapper.userToUserDto(user));
	}

	/**
	 * Remove um usuário por nome.
	 *
	 */
	@DeleteMapping("/{userName}")
	public ResponseEntity<?> deleteUser(@PathVariable("userName") String userName){
		this.userService.deleteByUserName(userName);
 		return ResponseEntity.noContent().build();
	}

	/**
	 *
	 * Salva um usuário.
	 *
	 * @param userDTO.
	 * @return 
	 */
	@PostMapping
	public ResponseEntity<UserDTO> saveUser(@Valid @RequestBody UserDTO userDTO){
		log.info("Creating user {}", userDTO.getUserName());
		User user = userMapper.userDtoToUser(userDTO);
		user = this.userService.save(user);
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest()
				.path("/{userName}")
				.buildAndExpand(user.getUsername())
				.toUri();
		return ResponseEntity.created(uri).build();
	}

}
