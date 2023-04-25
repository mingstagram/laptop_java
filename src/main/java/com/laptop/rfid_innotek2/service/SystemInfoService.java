package com.laptop.rfid_innotek2.service;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.laptop.rfid_innotek2.dto.SystemInfoSaveReqDto;
import com.laptop.rfid_innotek2.model.AdmAgent;
import com.laptop.rfid_innotek2.model.SystemInfo;
import com.laptop.rfid_innotek2.repository.SystemInfoRepository;

@Service
public class SystemInfoService {
	
	@Autowired
	SystemInfoRepository systemInfoRepository;
	
	@Transactional
	public SystemInfo systemInfo(int id) {
		return systemInfoRepository.systemInfo(id);
		
	}
	
	@Transactional
	public void insert(AdmAgent agent) {
		SystemInfo system = SystemInfo.builder()
				.set1("3")
				.set2("30")
				.set3("3")
				.set4("30")
				.admAgent(agent)
				.build();
		
		systemInfoRepository.save(system);
	}
	
	@Transactional
	public int update(SystemInfoSaveReqDto systemDto, int id) {
		int result = 0;
		SystemInfo system = systemInfoRepository.findById(id).get();
		if(system != null) {
			system.setSet1(systemDto.getSet1());
			system.setSet2(systemDto.getSet2());
			system.setSet3(systemDto.getSet3());
			system.setSet4(systemDto.getSet4());
		}
		return result;
	}

}
