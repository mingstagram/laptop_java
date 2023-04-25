package com.laptop.rfid_innotek2.controller.api;
  
import javax.servlet.http.HttpServletResponse; 

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.laptop.rfid_innotek2.dto.AdmMemberSaveReqDto;
import com.laptop.rfid_innotek2.dto.ResponseDto;
import com.laptop.rfid_innotek2.model.AdmMember;
import com.laptop.rfid_innotek2.service.AdmMemberService;
import com.laptop.rfid_innotek2.service.CommonService;

@RestController
public class AdmMemberApiController {
	
	@Autowired
	CommonService commonService;
	
	@Autowired
	AdmMemberService admMemberService;  
	
	@Autowired
	HttpServletResponse res;
	
	@PostMapping("/api/login_check.do")
	public ResponseDto<Integer> login_check(@RequestBody AdmMember admMember){
		 String agent_id_str = "0";
		AdmMember findMember = admMemberService.login_check(admMember);
		
		if(findMember != null) { 
			int agent_id = 0;
			if(findMember.getAdmAgent() != null) {
				agent_id = findMember.getAdmAgent().getId();
				agent_id_str = String.valueOf(agent_id);
			} 
			commonService.setCookie("username", findMember.getUsername());
			commonService.setCookie("prop", findMember.getProp());
			commonService.setCookie("agent_id", agent_id_str); 
			
			if(findMember.getAdmAgent() == null) {
				return new ResponseDto<>(HttpStatus.OK, 2);
			} else { 
				return new ResponseDto<>(HttpStatus.OK, 1);
			} 
		} else {
			return new ResponseDto<>(HttpStatus.OK, 0);
		}
		
	}
	
	@PostMapping("/api/insertAdmMember")
	public ResponseDto<Integer> insertAdmMember(@RequestBody AdmMemberSaveReqDto admMemberDto){
		 System.out.println(">>>> "+ admMemberDto.toString());
		admMemberService.saveAdmMember(admMemberDto); 
		int agent_id = admMemberDto.getAgentId();
		return new ResponseDto<>(HttpStatus.OK, agent_id);
	}
	
	@PutMapping("/api/updateAdmMember/{id}")
	public ResponseDto<Integer> updateAdmMember(@RequestBody AdmMemberSaveReqDto admMemberDto, @PathVariable int id){
		
		admMemberService.update(admMemberDto, id);  
		int agent_id = admMemberDto.getAgentId();
		return new ResponseDto<>(HttpStatus.OK, agent_id);
	}
	
	@DeleteMapping("/api/deleteAdmMember/{chk_Val}")
	public ResponseDto<Integer> deleteAdmMember(@PathVariable String chk_Val){ 
		String[] idValues = chk_Val.split(","); 
		for(String value : idValues) {
			int id = Integer.parseInt(value);
			admMemberService.deleteById(id);
		}
		return new ResponseDto<>(HttpStatus.OK, 1);
	}
	
	@PostMapping("/api/updateProp")
	public ResponseDto<Integer> updateProp(String username, String prop){
		
		int result = admMemberService.updatePropByUsername(username, prop);
		commonService.setCookie("prop", prop);
		
		return new ResponseDto<Integer>(HttpStatus.OK, result);
	}

}
