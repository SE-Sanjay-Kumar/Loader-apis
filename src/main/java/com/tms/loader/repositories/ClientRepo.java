package com.tms.loader.repositories;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.tms.loader.entities.Client;

import jakarta.transaction.Transactional;

public interface ClientRepo extends JpaRepository<Client, Long> {
	@Transactional
	@Modifying
	@Query("update Client u set u.userName = ?1, u.password = ?2, u.cnic = ?3, u.phoneNumber = ?4, u.companyName = ?5 where u.id = ?6")
	int setClientInfoById(String userName, String password, Long cnic, String phoneNumber, String companyName, Long id);
	Client findByuserName(String userName);
}
