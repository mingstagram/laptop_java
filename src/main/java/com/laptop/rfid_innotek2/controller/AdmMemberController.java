package com.laptop.rfid_innotek2.controller;
 
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import com.laptop.rfid_innotek2.dto.AdmMemberSaveReqDto;
import com.laptop.rfid_innotek2.model.AdmAgent;
import com.laptop.rfid_innotek2.model.AdmMember;
import com.laptop.rfid_innotek2.service.AdmAgentService;
import com.laptop.rfid_innotek2.service.AdmMemberService;
import com.laptop.rfid_innotek2.service.CommonService;

@Controller
public class AdmMemberController {
	
	@Autowired
	AdmMemberService admMemberService;
	
	@Autowired
	AdmAgentService admAgentService;
	
	@Autowired
	CommonService commonService;
	
	@GetMapping("/")
	public String index() {
		admMemberService.createAdmin();
		return "index";
	}
	 
	@GetMapping("/admMember/admin3")
	public String admin3(Model model) { 
		List<AdmMember> admMemberList = admMemberService.admMemberList();
		List<AdmAgent> admAgentList = admAgentService.admAgentList();
		model.addAttribute(admMemberList);
		model.addAttribute(admAgentList);
		return "page/admin3";
	}
	
	@GetMapping("/admMember/logout")
	public String logout() {
		commonService.invalidateCookie();
		return "redirect:/";
	}
	
	@GetMapping("/admMember/infra")
	public String infra() { 
		return "page/infra";
	}
	
	@PostMapping("/admMember/saveInfra")
	public String saveInfra(AdmMemberSaveReqDto admMemberDto) {
		admMemberService.saveInfraAdmin(admMemberDto);
		return "redirect:/admMember/admin3";
	}

}
