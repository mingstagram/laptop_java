package com.laptop.rfid_innotek2.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.laptop.rfid_innotek2.model.AdmAgent;
import com.laptop.rfid_innotek2.model.AdmSet;

public interface AdmSetRepository extends JpaRepository<AdmSet, Integer> {

	@Query(value = "SELECT * FROM admSet WHERE agentId=? LIMIT 1", nativeQuery = true)
	AdmSet admSetInfo(int agent);
	
//	@Query(value = "SELECT * FROM AdmSet WHERE agentId=? ORDER BY datetime DESC", nativeQuery = true)
	List<AdmSet> findByAdmAgentOrderByDatetimeDesc(AdmAgent admAgent);
	
}
