package com.laptop.rfid_innotek2.service;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.laptop.rfid_innotek2.dto.AdmSetSaveReqDto;
import com.laptop.rfid_innotek2.model.AdmAgent;
import com.laptop.rfid_innotek2.model.AdmSet;
import com.laptop.rfid_innotek2.repository.AdmAgentRepository;
import com.laptop.rfid_innotek2.repository.AdmSetRepository;

@Service
public class AdmSetService {

	@Autowired
	AdmSetRepository admSetRepository;

	@Autowired
	AdmAgentRepository admAgentRepository;

	public List<AdmSet> admSetList() {
		return admSetRepository.findAll();
	}
	
	public List<AdmSet> admSetListByAgent(AdmAgent admAgent) {
		return admSetRepository.findByAdmAgentOrderByDatetimeDesc(admAgent);
	}

	public AdmSet admSetInfo(int agent_id) {
		return admSetRepository.admSetInfo(agent_id);
	}

	@Transactional
	public void saveAdmSet(AdmSetSaveReqDto setDto) { 
		AdmAgent agent = admAgentRepository.findById(setDto.getAgentId()).get();
		AdmSet set = AdmSet.builder().admAgent(agent).xray(setDto.getXray()).antenaIp(setDto.getAntenaIp())
				.antenaPort(setDto.getAntenaPort()).alertIp(setDto.getAlertIp()).alertPort(setDto.getAlertPort())
				.alertBlue(setDto.getAlertBlue()).alertBlueSound(setDto.getAlertBlueSound())
				.alertRed(setDto.getAlertRed()).alertRedSound(setDto.getAlertRedSound()).build();

		admSetRepository.save(set);
	}

	@Transactional
	public int update(AdmSetSaveReqDto setDto, int id) {
		int result = 0;
		AdmAgent agent = admAgentRepository.customFindById(setDto.getAgentId());
		System.out.println(">>> " +setDto.getAgentId());
		AdmSet set = admSetRepository.findById(id).get();
		if (set != null) {
			set.setAdmAgent(agent);
			set.setXray(setDto.getXray());
			set.setAntenaIp(setDto.getAntenaIp());
			set.setAntenaPort(setDto.getAntenaPort());
			set.setAlertIp(setDto.getAlertIp());
			set.setAlertPort(setDto.getAlertPort());
			set.setAlertBlue(setDto.getAlertBlue());
			set.setAlertBlueSound(setDto.getAlertBlueSound());
			set.setAlertRed(setDto.getAlertRed());
			set.setAlertRedSound(setDto.getAlertRedSound());
			result = 1;
		}
		return result;
	}

	@Transactional
	public void delete(int id) {
		admSetRepository.deleteById(id);
	}

}
