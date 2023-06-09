package com.laptop.rfid_innotek2.controller.api;
 
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import com.laptop.rfid_innotek2.dto.ResponseDto;
import com.laptop.rfid_innotek2.service.CommonService;
import com.laptop.rfid_innotek2.service.EventHistoryService;

@RestController
public class EventHistoryApiController {
	
	@Autowired
	CommonService commonService;
	
	@Autowired
	EventHistoryService eventHistoryService;

	@PostMapping("/eventHistory/load")
	public ResponseDto<Integer> load(){ 
		return new ResponseDto<>(HttpStatus.OK, 1);
	} 
	
	@PostMapping("/eventHistory/limitDel")
	public ResponseDto<Integer> limitDel(){
		int result = 0;
		result = eventHistoryService.limitDelete();
		return new ResponseDto<>(HttpStatus.OK, result);
	}
	
//	@GetMapping("/eventHistory/xrayContents")
//	public String xrayContents(){ 
//		return "<h1>xrayContents</h1>";
//	} 
//	
//	@GetMapping("/eventHistory/tableContents")
//	public String tableContents(){ 
//		return "<h1>tableContents</h1>";
//	} 
	
}
