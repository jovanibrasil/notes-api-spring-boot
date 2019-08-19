package com.notes.services;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.notes.enums.ProfileTypeEnum;
import com.notes.models.User;
import com.notes.repositories.UserRepository;


@Service
@Primary
public class UserService {
    
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
			this.userRepository.delete(optUser.get());
		}
	}

	
}