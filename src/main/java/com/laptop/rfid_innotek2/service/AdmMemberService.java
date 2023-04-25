package com.laptop.rfid_innotek2.service;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.laptop.rfid_innotek2.dto.AdmMemberSaveReqDto;
import com.laptop.rfid_innotek2.model.AdmAgent;
import com.laptop.rfid_innotek2.model.AdmMember;
import com.laptop.rfid_innotek2.repository.AdmAgentRepository;
import com.laptop.rfid_innotek2.repository.AdmMemberRepository;

@Service
public class AdmMemberService {
	
	@Autowired
	AdmMemberRepository admMemberRepository;
	
	@Autowired
	AdmAgentRepository admAgentRepository;
	
	public void createAdmin() {
		AdmMember admin = admMemberRepository.findByUsername("admin");
		if(admin == null) {
			admin = new AdmMember();
			admin.setUsername("admin");
			admin.setPassword("1111");
			admin.setProp("3");
			admin.setXrayNum("1");
			admMemberRepository.save(admin);
		}
	}
	
	public AdmMember login_check(AdmMember admMember) {
		String username = admMember.getUsername();
		String password = admMember.getPassword();
		
		AdmMember findAdmMember = admMemberRepository.findByUsernameAndPassword(username, password);

		return findAdmMember;
	}
	
	public AdmMember findByUsername(String username) {
		return admMemberRepository.findByUsername(username);
	}
	
	public List<AdmMember> admMemberList(){
		return admMemberRepository.findAll();
	}
	
	@Transactional
	public void saveAdmMember(AdmMemberSaveReqDto admMemberDto) {
		 
		Optional<AdmAgent> agent = admAgentRepository.findById(admMemberDto.getAgentId());
		AdmMember admMember = new AdmMember();
		admMember.setXrayNum("1");
		admMember.setUsername(admMemberDto.getUsername());
		admMember.setProp("3");
		admMember.setPassword(admMemberDto.getPassword());
		admMember.setAdmAgent(agent.get());
		
		admMemberRepository.save(admMember);
	}
	
	@Transactional
	public void saveInfraAdmin(AdmMemberSaveReqDto admMemberDto) {
		AdmMember admMember = new AdmMember();
		admMember.setXrayNum("1");
		admMember.setUsername(admMemberDto.getUsername());
		admMember.setProp("3");
		admMember.setPassword(admMemberDto.getPassword());
		admMember.setAdmAgent(null);
		
		admMemberRepository.save(admMember);
	}
	
	@Transactional
	public void deleteById(int id) {
		admMemberRepository.deleteById(id);
	}
	
	@Transactional
	public int update(AdmMemberSaveReqDto admMemberDto, int id) {
		
		int result = 0;
		Optional<AdmAgent> agent = admAgentRepository.findById(admMemberDto.getAgentId());
		AdmMember admMember =  admMemberRepository.findById(id).get(); 
		if(admMember != null) {
			admMember.setAdmAgent(agent.get());
			admMember.setUsername(admMemberDto.getUsername());
			admMember.setPassword(admMemberDto.getPassword()); 
			result = 1;
		} 
		return result;
		
	}
	
	@Transactional
	public int updatePropByUsername(String username, String prop) {
		int result = 0;
		
		AdmMember admMember = admMemberRepository.findByUsername(username);
		
		if(admMember != null) {
			admMember.setProp(prop);
			result = 1;
		}
		
		return result;
	}

}
