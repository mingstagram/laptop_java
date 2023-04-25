package com.laptop.rfid_innotek2.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
 
import com.laptop.rfid_innotek2.model.SystemInfo;

public interface SystemInfoRepository extends JpaRepository<SystemInfo, Integer> {

	@Query(value = "SELECT * FROM systeminfo WHERE agentId = ? ORDER BY DATETIME DESC LIMIT 1", nativeQuery = true)
	SystemInfo systemInfo(int id);
	
}
