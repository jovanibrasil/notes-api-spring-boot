package com.notes.service.impl;

import java.util.ArrayList;

import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import com.notes.exception.NotFoundException;
import com.notes.model.ColorPallet;
import com.notes.model.User;
import com.notes.model.enums.ProfileTypeEnum;
import com.notes.repository.UserRepository;
import com.notes.service.ColorPalletService;
import com.notes.service.UserService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RequiredArgsConstructor
@Slf4j
@Service
@Primary
public class UserServiceImpl implements UserService {

	private final UserRepository userRepository;
	private final ColorPalletService palletService;

	/**
	 * Returns a user (if exists) with the specified name.
	 * 
	 */
	@Override
	public User findByUserName(String userName) {
		return userRepository.findUserByName(userName).orElseThrow(() -> new NotFoundException("error.user.find"));
	}

	/**
	 * Saves a new user.
	 * 
	 */
	@Override
	public User save(User user) {
		log.info("Saving new user. Name: {}", user.getUsername());
	
		// Verify if the user already exists.
		userRepository.findUserByName(user.getUsername()).ifPresent(savedUser -> {
			throw new IllegalArgumentException("error.user.name.unique"); } );
		
		user.setProfileType(ProfileTypeEnum.ROLE_USER);
		user = userRepository.save(user);
		
		ColorPallet colorPallet = new ColorPallet();
		colorPallet.setUserName(user.getUsername());
		colorPallet.setColors(new ArrayList<>());
		palletService.saveColorPallet(colorPallet);
		
		return user;
	}

	/**
	 * Deletes a user (if exists) with the specified name.
	 * 
	 */
	@Override
	public void deleteByUserName(String userName) {
		log.info("The user {} will be deleted.", userName);
		this.userRepository.delete(findByUserName(userName));
	}
	
}