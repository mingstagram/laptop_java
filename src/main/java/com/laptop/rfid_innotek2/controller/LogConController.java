package com.laptop.rfid_innotek2.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.laptop.rfid_innotek2.model.LogCon;
import com.laptop.rfid_innotek2.service.CommonService;
import com.laptop.rfid_innotek2.service.LogConService;

@Controller
public class LogConController {
	
	@Autowired
	LogConService logConService;

	@Autowired
	CommonService commonService;
	
	@GetMapping("/logCon/equip_test")
	public String equip_test(Model model, HttpServletResponse res) {
		String agent_id = commonService.getCookie("agent_id"); 
		if(commonService.nullCheck(agent_id)) {
			String username = commonService.getCookie("username");
			List<LogCon> logConList = new ArrayList<>();
			if(username.equals("admin")) {
				logConList = logConService.logConList(null);
			} else {
				logConList = logConService.logConList(agent_id);
			}
			
			model.addAttribute("logConList", logConList);
			return "page/equip_test";
		} else {
			commonService.loginCheckLogic(res);
			return null;
		}
		
	}

}
