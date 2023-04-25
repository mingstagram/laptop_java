package com.laptop.rfid_innotek2.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.laptop.rfid_innotek2.model.EventHistory;

public interface EventHistoryRepository extends JpaRepository<EventHistory, Integer>, JpaSpecificationExecutor<EventHistory> {

	
	@Query(value = "SELECT * FROM eventhistory ORDER BY datetime DESC LIMIT 2", nativeQuery = true)
	List<EventHistory> historyTop2List();
	
	@Query(value = "SELECT * FROM eventhistory ORDER BY datetime DESC LIMIT 30", nativeQuery = true)
	List<EventHistory> historyTop30List();
	
	@Query(value = "SELECT * FROM eventhistory WHERE agent=? ORDER BY datetime DESC LIMIT 2", nativeQuery = true)
	List<EventHistory> historyTop2List(int agent);
	
	@Query(value = "SELECT * FROM eventhistory WHERE agent=? ORDER BY datetime DESC LIMIT 30", nativeQuery = true)
	List<EventHistory> historyTop30List(int agent); 
	 
	@Query(value= "SELECT * FROM eventHistory ORDER BY datetime DESC LIMIT ?, ?", nativeQuery = true)
	List<EventHistory> page(int startPage, int pageSize);
	
	@Query(value= "SELECT count(*) FROM eventHistory WHERE 1 = 1 AND (bizDeptCd = :bizDeptCd) AND result IN :result", nativeQuery = true)
	Integer searchCount(@Param("bizDeptCd") String bizDeptCd, 
											@Param("result") String result);
	
	List<EventHistory> findAllByOrderByDatetimeDesc();
	
//	Page<EventHistory> findAll(Specification<EventHistory> spec, Pageable pageable);
	
//	@Query(value = "", nativeQuery = true)
//	List<EventHistory> findBySearchList();
	
//	Page<EventHistory> findEventHistoryByUname(String keyword, Pageable pageable);
	
//	@Query(value = "SELECT \r\n"
//			+ "e.id,\r\n"
//			+ "a.id AS agent_id,\r\n"
//			+ "u.u_num,\r\n"
//			+ "u.u_name,\r\n"
//			+ "l.u_code,\r\n"
//			+ "l.u_rfid,\r\n"
//			+ "a.bizDeptCd,\r\n"
//			+ "e.datetime,\r\n"
//			+ "e.result\r\n"
//			+ "FROM eventhistory e\r\n"
//			+ "LEFT JOIN admagent a \r\n"
//			+ "ON e.agent_id = a.id\r\n"
//			+ "LEFT JOIN laptopinfo l\r\n"
//			+ "ON e.laptop_id = l.id\r\n"
//			+ "LEFT JOIN user u\r\n"
//			+ "ON l.user_id = u.id\r\n"
//			+ "WHERE 1=1\r\n"
//			+ "AND u.u_name LIKE %:name% \r\n"
//			+ "ORDER BY e.datetime DESC", nativeQuery = true)
//	Page<EventHistoryListInterface> historyList(@Param("name") String u_name, Pageable pageable);
}
