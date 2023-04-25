package com.laptop.rfid_innotek2.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.laptop.rfid_innotek2.model.LaptopInfo;
import com.laptop.rfid_innotek2.model.User;

public interface UserRepository extends JpaRepository<User, Integer> {

	@Query(value = "SELECT * FROM user WHERE userNo=? LIMIT 1", nativeQuery = true)
	public User findByUserNo(String userNo);
	
	@Query(value = "SELECT count(*) FROM user WHERE userNo=? LIMIT 1", nativeQuery = true)
	public int findByUserNoCount(String userNo);  
	
	public User findTopByUserNo(String userNo);
	
}
