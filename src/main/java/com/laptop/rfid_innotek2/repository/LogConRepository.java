package com.laptop.rfid_innotek2.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.laptop.rfid_innotek2.model.AdmAgent;
import com.laptop.rfid_innotek2.model.LogCon;

public interface LogConRepository extends JpaRepository<LogCon, Integer> {

	@Query(value = "SELECT * FROM logcon WHERE agent=? ORDER BY DATETIME DESC;", nativeQuery = true)
	List<LogCon> LogConListByAgentId(int agent_id);
	
}
