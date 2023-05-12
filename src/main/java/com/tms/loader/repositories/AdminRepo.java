package com.tms.loader.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.tms.loader.entities.Admin;

import jakarta.transaction.Transactional;

public interface AdminRepo extends JpaRepository<Admin, Long> {
	@Modifying
	@Transactional
	@Query(value = "CALL SINGLETON_TRIGGER()", nativeQuery = true)
	void singletonQueryTrigger();
	
	@Query(value = "SELECT *\r\n"
			+ "FROM admin LIMIT 1\r\n" , nativeQuery = true)
	Admin getAdmin();
	Admin findByuserName(String username);
}
