package com.laptop.rfid_innotek2.controller;
 
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.laptop.rfid_innotek2.model.AdmAgent;
import com.laptop.rfid_innotek2.model.AdmSet;
import com.laptop.rfid_innotek2.service.AdmAgentService;
import com.laptop.rfid_innotek2.service.AdmSetService;
import com.laptop.rfid_innotek2.service.CommonService;

@Controller
public class AdmSetController {
	 
	@Autowired
	AdmSetService admSetService;
	
	@Autowired
	AdmAgentService admAgentService;
	
	@Autowired
	CommonService commonService;
	
	// 장비 설정
	@GetMapping("/admSet/admin1")
	public String admin1(Model model) { 
		List<AdmSet> admSetList = admSetService.admSetList();
		List<AdmAgent> admAgentList = admAgentService.admAgentList();
		model.addAttribute(admSetList);
		model.addAttribute(admAgentList);
		return "page/admin1";
	}
	 
	// 장비 확인
	@GetMapping("/admSet/admin6")
	public String admin6(Model model) { 
		String agent_id_str = commonService.getCookie("agent_id");
		int agent_id = Integer.parseInt(agent_id_str); 
		AdmSet admSet = admSetService.admSetInfo(agent_id);  
		model.addAttribute("admSet", admSet);
		return "page/admin6";
	}

}
