package com.notes.services.impl;

import java.util.Optional;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import com.notes.enums.ProfileTypeEnum;
import com.notes.models.User;
import com.notes.repositories.UserRepository;
import com.notes.services.UserService;

@RequiredArgsConstructor
@Slf4j
@Service
@Primary
public class UserServiceImpl implements UserService {

	private final UserRepository userRepository;

	@Override
	public Optional<User> findByUserName(String userName) {
		return Optional.ofNullable(this.userRepository.findUserByName(userName));
	}

	@Override
	public Optional<User> save(User user) {
		user.setProfileType(ProfileTypeEnum.ROLE_USER);
		return Optional.ofNullable(this.userRepository.save(user));	
	}

	@Override
	public void deleteByUserName(String userName) {
		Optional<User> optUser = this.findByUserName(userName);
		
		if(optUser.isPresent()) {
			log.info("The user {} will be deleted.", userName);
			this.userRepository.delete(optUser.get());
		}else {
			log.info("Delete: User not found");
		}
	}
	
}