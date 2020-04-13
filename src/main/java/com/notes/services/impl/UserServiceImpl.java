package com.notes.services.impl;

import java.util.ArrayList;
import java.util.Optional;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import com.notes.enums.ProfileTypeEnum;
import com.notes.exceptions.NotFoundException;
import com.notes.models.ColorPallet;
import com.notes.models.User;
import com.notes.repositories.UserRepository;
import com.notes.services.ColorPalletService;
import com.notes.services.UserService;

@RequiredArgsConstructor
@Slf4j
@Service
@Primary
public class UserServiceImpl implements UserService {

	private final UserRepository userRepository;
	private final ColorPalletService palletService;

	@Override
	public User findByUserName(String userName) {
		Optional<User> optUser = userRepository.findUserByName(userName);
		if(optUser.isPresent()) {
			return optUser.get();
		}
		log.info("User not found. Name: {}", userName);
		throw new NotFoundException("error.user.find");
	}

	@Override
	public User save(User user) {
		log.info("Saving new user. Name: {}", user.getUsername());
		// Verify if the user already exists.
		Optional<User> optUser = userRepository.findUserByName(user.getUsername());
		if(!optUser.isPresent()) {
			
			user.setProfileType(ProfileTypeEnum.ROLE_USER);
			user = userRepository.save(user);
			
			ColorPallet colorPallet = new ColorPallet();
			colorPallet.setUserName(user.getUsername());
			colorPallet.setColors(new ArrayList<>());
			palletService.saveColorPallet(colorPallet);
			
			return user;
		}
		throw new IllegalArgumentException("error.user.name.unique");
	}

	@Override
	public void deleteByUserName(String userName) {
		User user = findByUserName(userName);	
		log.info("The user {} will be deleted.", userName);
		this.userRepository.delete(user);
	}
	
}