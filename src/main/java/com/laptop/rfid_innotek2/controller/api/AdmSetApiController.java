package com.laptop.rfid_innotek2.controller.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.laptop.rfid_innotek2.dto.AdmSetSaveReqDto;
import com.laptop.rfid_innotek2.dto.ResponseDto;
import com.laptop.rfid_innotek2.service.AdmSetService;
import com.laptop.rfid_innotek2.service.CommonService;

@RestController
public class AdmSetApiController {

	@Autowired
	AdmSetService admSetService;

	@Autowired
	CommonService commonService;

	@PostMapping("/api/insertAdmSet")
	public ResponseDto<Integer> insertAdmSet(@RequestBody AdmSetSaveReqDto setDto) {
		admSetService.saveAdmSet(setDto);
		int agent_id = setDto.getAgentId();
		return new ResponseDto<Integer>(HttpStatus.OK, agent_id);
	}

	@PutMapping("/api/updateAdmSet/{id}")
	public ResponseDto<Integer> updateAdmSet(@RequestBody AdmSetSaveReqDto setDto, @PathVariable int id) {
		System.out.println("setDto >>> " + setDto.toString());
		admSetService.update(setDto, id);
		int agent_id = setDto.getAgentId();
		return new ResponseDto<Integer>(HttpStatus.OK, agent_id);
	}

	@DeleteMapping("/api/deleteAdmSet/{id}")
	public ResponseDto<Integer> deleteAdmSet(@PathVariable int id) {
		admSetService.delete(id);
		return new ResponseDto<Integer>(HttpStatus.OK, 1);
	}

}
