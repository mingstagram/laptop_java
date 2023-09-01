package com.laptop.rfid_innotek2.controller;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.laptop.rfid_innotek2.util.UtilService;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class UtilController {
	
	private final UtilService utilService;
	
	@Value("${file.path}")
    private String filePath;
	
	@GetMapping("/util/excel")
	public String excel() { 
		return "page/excel";
	}
	
	@GetMapping("/util/errorView")
	public String errorView(Model model) throws IOException {
		// /home/mingook/nohup.out
		String errorLog = utilService.readFile(filePath);  
		if(errorLog != null) { 
			model.addAttribute("errorLog", errorLog);
		}
		
		return "page/error_view";
	}

}
