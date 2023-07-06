package com.laptop.rfid_innotek2.controller; 
 
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.laptop.rfid_innotek2.model.AdmAgent;
import com.laptop.rfid_innotek2.service.AdmAgentService;
import com.laptop.rfid_innotek2.service.CommonService; 

@Controller
public class AdmAgentController { 
	
	@Autowired
	AdmAgentService admAgentService;
	
	@Autowired
	CommonService commonService;
	
	@GetMapping("/admAgent/admin4")
	public String admin4(Model model, HttpServletResponse res) { 
		String agent = commonService.getCookie("agent_id");
		if(commonService.nullCheck(agent)) {
			List<AdmAgent> admAgentList = admAgentService.admAgentList();
			model.addAttribute(admAgentList);
			return "page/admin4";
		} else {
			commonService.loginCheckLogic(res);
			return null;
		}
		
	}

}
