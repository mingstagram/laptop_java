package com.laptop.rfid_innotek2.controller.api;
  
import org.apache.poi.ss.usermodel.CellStyle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.laptop.rfid_innotek2.dto.AdmAgentSaveReqDto;
import com.laptop.rfid_innotek2.dto.ResponseDto;
import com.laptop.rfid_innotek2.model.AdmAgent;
import com.laptop.rfid_innotek2.service.AdmAgentService;
import com.laptop.rfid_innotek2.service.CommonService;
import com.laptop.rfid_innotek2.service.SystemInfoService;
 

@RestController
public class AdmAgentApiController {
	
	@Autowired
	AdmAgentService admAgentService;
	
	@Autowired
	SystemInfoService systemInfoService;
	
	@Autowired
	CommonService commonService;
	
	@PostMapping("/api/insertAdmAgent")
	public  ResponseDto<Integer> insertAdmAgent(@RequestBody AdmAgentSaveReqDto agentDto){
		AdmAgent agent = admAgentService.saveAgent(agentDto);
		systemInfoService.insert(agent); 
		int agent_id = agent.getId();
		return new ResponseDto<>(HttpStatus.OK, agent_id);
	}
	
	@PutMapping("/api/updateAdmAgent/{id}")
	public  ResponseDto<Integer> updateAdmAgent(@RequestBody AdmAgentSaveReqDto agentDto, @PathVariable int id){
		admAgentService.updateAgent(agentDto, id);  
		int agent_id = id;
		return new ResponseDto<>(HttpStatus.OK, agent_id);
	}
	
	@DeleteMapping("/api/deleteAdmAgent/{chk_Val}")
	public  ResponseDto<Integer> deleteAdmAgent(@PathVariable String chk_Val){
		String[] idValues = chk_Val.split(","); 
		for(String value : idValues) {
			int id = Integer.parseInt(value);
			admAgentService.deleteAgent(id);
		}
		return new ResponseDto<>(HttpStatus.OK, 1);
	} 

}
