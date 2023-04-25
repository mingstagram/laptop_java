package com.laptop.rfid_innotek2.controller.api;
 
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import com.laptop.rfid_innotek2.dto.ResponseDto;
import com.laptop.rfid_innotek2.model.User;
import com.laptop.rfid_innotek2.service.UserService;

@RestController
public class UserApiController {
	
	@Autowired
	UserService userService;
	
	@PostMapping("/api/loadUserName")
	public ResponseDto<String> loadUserName(String u_num){
		System.out.println(">>> " + u_num);
		User user = userService.findByUserNo(u_num);
		String username = "";
		if(user != null) username = user.getUsername();
		
		System.out.println(">>>> " + username);
		return new ResponseDto<String>(HttpStatus.OK, username);
		
	}

}
