package com.shopme.admin.user;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.Rollback;

import com.shopme.common.entity.Role;

@DataJpaTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
@Rollback(false)
public class RoleRepositoryTest {
	@Autowired
	private RoleRepository repo;
	
	@Test
	public void testCreateFirstRole() {
		Role roleAdmin = new Role("Admin", "Manage Everything");
		Role savedRole = repo.save(roleAdmin);
		assertThat(savedRole.getId()).isGreaterThan(0);
	}
	
	@Test
	public void testCreateRestRole() {
		Role roleSalesPerson = new Role("Salesperson", "Manage Product Price, "
				+ "Customers, Shipping, Orders, and Sales Report");
		
		Role roleEditor = new Role("Editor", "Manage Categories, "
				+ "Brands, Products, and Menus");
		
		Role roleShipper = new Role("Shipper", "View Products, "
				+ "View Orders, and Update Order Status");
		
		Role roleAssistant = new Role("Assistant", "Manage Questions and Reviews");
		
		List<Role> roles = new ArrayList<>();
		
		roles.add(roleSalesPerson);
		roles.add(roleEditor);
		roles.add(roleShipper);
		roles.add(roleAssistant);
		
		repo.saveAll(roles);
	}
}
