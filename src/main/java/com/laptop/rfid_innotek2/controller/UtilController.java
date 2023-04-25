package com.laptop.rfid_innotek2.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class UtilController {
	
	@GetMapping("/util/excel")
	public String excel() {
		
		return "page/excel";
	}

}
