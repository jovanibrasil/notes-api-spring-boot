package com.notes.repositories;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

import java.util.Optional;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import com.notes.models.User;

@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
@RunWith(SpringRunner.class)
@SpringBootTest
@ActiveProfiles("test")
public class UserRepositoryTest {

	@Autowired
	private UserRepository userRepository;	
	private User user;
	
	@Before
	public void setUp() {
		this.user = new User();
		this.user.setUserName("jovani");
	}
	
	@After
	public void tearDown() {
		this.userRepository.deleteAll();
	}
	
	// find user by name
	@Test
	public void testFindUserByName() {
		System.out.println(this.user);
		User savedUser = this.userRepository.save(this.user);
		this.user = this.userRepository.findUserByName("jovani");
		assertEquals(savedUser.getUserId(), this.user.getUserId());
		assertEquals(savedUser.getUsername(), this.user.getUsername());
	}

	// save user
	@Test
	public void testSaveUser() {
		User savedUser = userRepository.save(user);
		assertNotEquals(null, savedUser.getUsername());
	}
	
	// delete user
	@Test
	public void testDeleteUser() {
		String userName = userRepository.save(user).getUsername();
		userRepository.deleteById(userName);
		assertEquals(Optional.empty(), this.userRepository.findById(userName));
	}
	
	// update user
	@Test
	public void testUpdateUser() {
		user = userRepository.save(user);
		user.setUserName("newName");
		User savedUser = userRepository.save(user);
		assertEquals(user.getUserId(), savedUser.getUserId());
		assertEquals("newName", user.getUsername());
	}

}
