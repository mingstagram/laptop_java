package com.laptop.rfid_innotek2.controller.api;
 
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.laptop.rfid_innotek2.dto.LaptopSaveReqDto;
import com.laptop.rfid_innotek2.dto.ResponseDto;
import com.laptop.rfid_innotek2.service.LaptopInfoService;
import com.laptop.rfid_innotek2.service.UserService;

@RestController
public class LaptopInfoApiController {

	@Autowired
	LaptopInfoService laptopInfoService;
	
	@Autowired
	UserService userService;
	
	@PostMapping("/api/insertLaptop")
	public ResponseDto<Integer> insertAdmSet(@RequestBody LaptopSaveReqDto laptopDto){ 
		int result = laptopInfoService.saveLaptop(laptopDto);
		return new ResponseDto<Integer>(HttpStatus.OK, result);
	}
	
	@PutMapping("/api/updateLaptop/{laptopId}")
	public ResponseDto<Integer> updateLaptop(@RequestBody LaptopSaveReqDto laptopDto, @PathVariable int laptopId){
		int result = laptopInfoService.updateLaptop(laptopDto, laptopId);
		return new ResponseDto<Integer>(HttpStatus.OK, result);
	}
	
	@DeleteMapping("/api/deleteLaptop/{chk_Val}")
	public ResponseDto<Integer> deleteLaptop(@PathVariable String chk_Val){
		String[] idValues = chk_Val.split(","); 
		for(String value : idValues) {
			int id = Integer.parseInt(value);
			laptopInfoService.deleteLaptop(id);
		}
		return new ResponseDto<Integer>(HttpStatus.OK, 1);
	}
	
}
