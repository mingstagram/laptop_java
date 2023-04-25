package com.laptop.rfid_innotek2.test;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HttpControllerTest {
	
	@GetMapping("/http/lombok")
	public String lombokTest() {
		Member m = Member.builder().username("ssar").password("1234").email("ddd@naver.com").build();
		System.out.println(m.getId());
		m.setId(5000);
		System.out.println(m.getId());
		return "";
	}

	@GetMapping("/http/get")
	public String getTest(Member m) { 
		return "getTest 요청 : " + m.getEmail();
	}

	@PostMapping("/http/post")
	public String postTest(@RequestBody Member m) {
		return "postTest 요청  id : " + m.getId() + ", username : " + m.getUsername() + ", password : " + m.getPassword() + ", email : " + m.getEmail();
	}
	
	@PutMapping("/http/put")
	public String putTest() {
		return "putTest 요청";
	}
	
	@DeleteMapping("/http/delete")
	public String deleteTest() {
		return "deleteTest 요청";
	}
	
}
