package com.notes.service.impl;

import java.util.ArrayList;

import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import com.notes.controller.dto.UserDTO;
import com.notes.exception.ExceptionMessages;
import com.notes.exception.NotFoundException;
import com.notes.mapper.UserMapper;
import com.notes.model.ColorPallete;
import com.notes.model.User;
import com.notes.model.enums.ProfileTypeEnum;
import com.notes.repository.UserRepository;
import com.notes.service.ColorPalleteService;
import com.notes.service.UserService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RequiredArgsConstructor
@Slf4j
@Service
@Primary
public class UserServiceImpl implements UserService {

	private final UserRepository userRepository;
	private final ColorPalleteService palletService;
	private final UserMapper userMapper;

	/**
	 * Returns a user (if exists) with the specified name.
	 * 
	 */
	@Override
	public UserDTO findByUserName(String userName) {
		return userRepository.findUserByName(userName)
				.map(userMapper::userToUserDto)
				.orElseThrow(() -> new NotFoundException(ExceptionMessages.USER_NOT_FOUND));
	}

	/**
	 * Saves a new user.
	 * 
	 */
	@Override
	public UserDTO save(UserDTO userDTO) {
		User user = userMapper.userDtoToUser(userDTO);
		log.info("Saving new user. Name: {}", user.getUsername());
	
		// Verify if the user already exists.
		userRepository.findUserByName(user.getUsername()).ifPresent(savedUser -> {
			throw new IllegalArgumentException(ExceptionMessages.USER_NAME_UNIQUE); } );
		
		user.setProfileType(ProfileTypeEnum.ROLE_USER);
		user = userRepository.save(user);
		
		ColorPallete colorPallete = new ColorPallete();
		colorPallete.setUserName(user.getUsername());
		colorPallete.setColors(new ArrayList<>());
		palletService.saveColorPallete(colorPallete);
		
		return userMapper.userToUserDto(user);
	}

	/**
	 * Deletes a user (if exists) with the specified name.
	 * 
	 */
	@Override
	public void deleteByUserName(String userName) {
		log.info("The user {} will be deleted.", userName);
		userRepository.findUserByName(userName)
			.ifPresentOrElse(userRepository::delete,
			() -> new NotFoundException(ExceptionMessages.USER_NOT_FOUND));
	}
	
}