package com.laptop.rfid_innotek2.controller.api;
 
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import com.laptop.rfid_innotek2.dto.ResponseDto;
import com.laptop.rfid_innotek2.model.EventHistory;
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
	
}
