package com.notes.services;

import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import com.notes.enums.ProfileTypeEnum;
import com.notes.models.User;
import com.notes.repositories.UserRepository;


@Service
@Primary
public class UserService {
    
	private static final Logger log = LoggerFactory.getLogger(UserService.class);
	
	@Autowired
	private UserRepository userRepository;
	
	//@Override
	public List<User> findAll() {
		return this.userRepository.findAll();
	}

	//@Override
	public Optional<User> findByUserName(String userName) {
		return Optional.ofNullable(this.userRepository.findUserByName(userName));
	}

	//@Override
	public Optional<User> save(User user) {
		user.setProfileType(ProfileTypeEnum.ROLE_USER);
		return Optional.ofNullable(this.userRepository.save(user));	
	}

	//@Override
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