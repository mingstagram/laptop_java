package com.laptop.rfid_innotek2.controller.api;
 
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.laptop.rfid_innotek2.dto.ResponseDto;
import com.laptop.rfid_innotek2.dto.SystemInfoSaveReqDto;
import com.laptop.rfid_innotek2.service.CommonService;
import com.laptop.rfid_innotek2.service.SystemInfoService;

@RestController
public class SystemInfoApiController {
	
	@Autowired
	SystemInfoService systemInfoService;
	
	@Autowired
	CommonService commonService;
	
	@PutMapping("/api/updateSystem/{id}")
	public ResponseDto<Integer> updateSystem(@RequestBody SystemInfoSaveReqDto systemDto, @PathVariable int id){
		systemInfoService.update(systemDto, id);
		String agent_id_str = commonService.getCookie("agent_id");
		int agent_id = Integer.parseInt(agent_id_str);
		return new ResponseDto<Integer>(HttpStatus.OK, agent_id);
	}

}
