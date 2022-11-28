package com.shopme.admin.user;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.annotation.Rollback;

import com.shopme.common.entity.Role;
import com.shopme.common.entity.User;

@DataJpaTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
@Rollback(false)
public class UserRepositoryTest {
	@Autowired
	UserRepository repo;
	
	@Autowired
	private TestEntityManager entityManager;
	
	@Test
	public void testCreateUserWithOneRole() {
		Role roleShipper = entityManager.find(Role.class, 4);
		User userNur = new User("nurfitrianti@gmail.com", "password345", "Nur Fitrianti", "Fahrudin");
		userNur.addRole(roleShipper);
		
		User savedUser = repo.save(userNur);
		assertThat(savedUser.getId()).isGreaterThan(0);
	}
	
	@Test
	public void testCreateUserWithTwoRoles() {
		User userSofia = new User("sofia.umaroh@gmail.com", "password234", "Sofia", "Umaroh");
		Role roleEditor = new Role(3);
		Role roleAssistant = new Role(5);
		
		userSofia.addRole(roleEditor);
		userSofia.addRole(roleAssistant);
		
		User savedUser = repo.save(userSofia);
		assertThat(savedUser.getId()).isGreaterThan(0);
	}
	
	@Test
	public void testListAllUsers() {
		List<User> listUser = (List<User>) repo.findAll();
		listUser.forEach(user->System.out.println(user));
	}
	
	@Test
	public void testGetUserById() {
		User userPutra = repo.findById(1).get();
		System.out.println(userPutra);
		assertThat(userPutra).isNotNull();
	}
	
	@Test
	public void testUpdateUserDetails() {
		User userPutra = repo.findById(1).get();
		userPutra.setEnabled(true);
		userPutra.setEmail("kurniarp@gmail.com");
		
		repo.save(userPutra);
	}
	
	@Test
	public void testUpdateUserRoles() {
		User userSofia = repo.findById(2).get();
		Role roleEditor = new Role(3);
		Role roleSalesperson = new Role(2);
		userSofia.getRoles().remove(roleEditor);
		userSofia.addRole(roleSalesperson);
		
		repo.save(userSofia);
	}
	
	@Test
	public void testDeleteUserById() {
		Integer userId = repo.findById(3).get().getId();
		repo.deleteById(userId);
	}
}
