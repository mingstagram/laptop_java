package com.laptop.rfid_innotek2.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.laptop.rfid_innotek2.model.AdmAgent;
import com.laptop.rfid_innotek2.model.AdmSet;

public interface AdmAgentRepository extends JpaRepository<AdmAgent, Integer> { 
	
	AdmAgent findTopByAgentIpOrderByDatetime(String agentIp); 
	
	AdmAgent findTopByIdOrderByDatetimeDesc(int agentId);
	
	@Query(value = "SELECT * FROM AdmAgent WHERE 1 = 1 AND id=?", nativeQuery = true)
	AdmAgent customFindById(int id);
	
}
