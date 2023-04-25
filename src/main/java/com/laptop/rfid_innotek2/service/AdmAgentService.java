package com.laptop.rfid_innotek2.service;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.laptop.rfid_innotek2.dto.AdmAgentSaveReqDto;
import com.laptop.rfid_innotek2.model.AdmAgent;
import com.laptop.rfid_innotek2.repository.AdmAgentRepository;

@Service
public class AdmAgentService {
	
	@Autowired
	AdmAgentRepository admAgentRepository;
	
	public List<AdmAgent> admAgentList(){
		return admAgentRepository.findAll();
	}
	
	public AdmAgent findTopByAgentIp(String agentIp) {
		return admAgentRepository.findTopByAgentIpOrderByDatetime(agentIp);
	}
	
	
	public AdmAgent findByAgent(int agent_id) {
		return admAgentRepository.customFindById(agent_id);
	}
	
	public AdmAgent findTopByIdOrderByDatetimeDesc(int agentId) {
		return admAgentRepository.findTopByIdOrderByDatetimeDesc(agentId);
	}
	
	
	@Transactional
	public AdmAgent saveAgent(AdmAgentSaveReqDto agentDto) {
		AdmAgent agent = new AdmAgent();
		agent.setAgentIp(agentDto.getAgentIp());
		agent.setAgentPort(agentDto.getAgentPort());
		agent.setAgentNum(agentDto.getAgentNum());
		agent.setBizDeptCd(agentDto.getBizDeptCd()); 
		admAgentRepository.save(agent);
		return agent;
	}
	
	@Transactional
	public void deleteAgent(int id) {
		admAgentRepository.deleteById(id);
	}
	
	@Transactional
	public int updateAgent(AdmAgentSaveReqDto agentDto, int id) {
		int result = 0;
		AdmAgent agent = admAgentRepository.findById(id).get();
		if(agent != null) {
			agent.setAgentIp(agentDto.getAgentIp());
			agent.setAgentPort(agentDto.getAgentPort());
			agent.setAgentNum(agentDto.getAgentNum());
			agent.setBizDeptCd(agentDto.getBizDeptCd());
			result = 1;
		}
		return result;
	}

}
