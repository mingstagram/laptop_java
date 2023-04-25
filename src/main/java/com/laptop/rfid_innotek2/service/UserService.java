package com.laptop.rfid_innotek2.service;
 
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.laptop.rfid_innotek2.model.User;
import com.laptop.rfid_innotek2.repository.UserRepository; 

@Service
public class UserService {
	
	@Autowired
	UserRepository userRepository;
	
	@Transactional
	public int findUserCount(String userNo) {
		return userRepository.findByUserNoCount(userNo);
	} 
	
	@Transactional
	public User findByUserNo(String userNo) {
		return userRepository.findTopByUserNo(userNo);
	}

}
