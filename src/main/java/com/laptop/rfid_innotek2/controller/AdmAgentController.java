package com.laptop.rfid_innotek2.controller; 
 
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.laptop.rfid_innotek2.model.AdmAgent;
import com.laptop.rfid_innotek2.service.AdmAgentService; 

@Controller
public class AdmAgentController { 
	
	@Autowired
	AdmAgentService admAgentService;
	
	@GetMapping("/admAgent/admin4")
	public String admin4(Model model) { 
		List<AdmAgent> admAgentList = admAgentService.admAgentList();
		model.addAttribute(admAgentList);
		return "page/admin4";
	}

}
