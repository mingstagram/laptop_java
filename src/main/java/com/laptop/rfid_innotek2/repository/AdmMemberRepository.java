package com.laptop.rfid_innotek2.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.laptop.rfid_innotek2.model.AdmAgent;
import com.laptop.rfid_innotek2.model.AdmMember;

public interface AdmMemberRepository extends JpaRepository<AdmMember, Integer> {
	
	@Query(value = "SELECT * FROM admmember WHERE username=? AND password=?", nativeQuery = true)
	AdmMember findByUsernameAndPassword(String username, String password);
	
	AdmMember findByUsername(String username); 

}
