package com.laptop.rfid_innotek2.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping; 

import com.laptop.rfid_innotek2.model.SystemInfo;
import com.laptop.rfid_innotek2.service.CommonService;
import com.laptop.rfid_innotek2.service.SystemInfoService;

@Controller
public class SystemInfoController {
	
	@Autowired
	SystemInfoService systemInfoService;
	
	@Autowired
	CommonService commonService;
	
	// 시스템 설정
	@GetMapping("/systemInfo/admin2")
	public String admin2(Model model) {
		String agent_id_str = commonService.getCookie("agent_id");
		System.out.println(" >>>>>>>>>>> " + agent_id_str);
		int id = Integer.parseInt(agent_id_str);
		SystemInfo system = systemInfoService.systemInfo(id); 
		model.addAttribute("system", system);
		return "page/admin2";
	}

}
