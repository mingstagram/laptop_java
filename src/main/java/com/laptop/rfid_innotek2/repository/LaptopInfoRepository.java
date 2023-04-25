package com.laptop.rfid_innotek2.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.laptop.rfid_innotek2.dto.LaptopSearchListInterface;
import com.laptop.rfid_innotek2.model.AdmAgent;
import com.laptop.rfid_innotek2.model.EventHistory;
import com.laptop.rfid_innotek2.model.LaptopInfo;

public interface LaptopInfoRepository extends JpaRepository<LaptopInfo, Integer> {

	@Query(value = "select * from laptopInfo where rfid=? order by datetime desc limit 1;", nativeQuery = true)
	LaptopInfo findByRfid(String tagName); 
	
	@Query(value = "select * from laptopInfo where userId=? order by datetime desc limit 1;", nativeQuery = true)
	LaptopInfo findByUserId(int userId); 
	
	@Query(value= "SELECT * FROM laptopInfo ORDER BY datetime DESC LIMIT ?, ?", nativeQuery = true)
	List<LaptopInfo> page(int startPage, int pageSize);
	
	@Query(value= "SELECT userId FROM laptopInfo WHERE id=? LIMIT 1", nativeQuery = true)
	int getUserId(int id);
	
	//바코드,사번,사원명,RFID
	@Query(value= "SELECT count(*) FROM laptopinfo l LEFT JOIN user u ON l.userId = u.id WHERE 1=1 AND (l.barcode LIKE %:keyword% OR u.userNo LIKE %:keyword% OR u.username LIKE %:keyword% OR l.rfid LIKE %:keyword%)", nativeQuery = true)
	Integer findBySearchCount(@Param("keyword") String keyword);
	
	@Query(value= "SELECT l.id, l.userId, u.userNo, u.userPart, u.username, l.asset, l.serial, l.barcode, l.rfid\r\n"
			+ "FROM laptopinfo l LEFT JOIN user u ON l.userId = u.id WHERE 1=1 \r\n"
			+ "AND (l.barcode LIKE %:keyword% OR u.userNo LIKE %:keyword% OR u.username LIKE %:keyword% OR l.rfid LIKE %:keyword%)\r\n"
			+ "ORDER BY l.datetime DESC LIMIT :start, :page", nativeQuery = true)
	List<LaptopSearchListInterface> findBySearch(@Param("keyword") String keyword, @Param("start") int startPage, @Param("page") int pageSize);
	
	@Query(value= "SELECT l.id, l.userId, u.userNo, u.userPart, u.username, l.asset, l.serial, l.barcode, l.rfid\r\n"
			+ "FROM laptopinfo l LEFT JOIN user u ON l.userId = u.id ORDER BY l.datetime DESC", nativeQuery = true)
	List<LaptopSearchListInterface> findAllByOrderByDatetimeDesc();
	
	@Query(value= "SELECT l.id, l.userId, u.userNo, u.userPart, u.username, l.asset, l.serial, l.barcode, l.rfid\r\n"
			+ "FROM laptopinfo l LEFT JOIN user u ON l.userId = u.id WHERE 1=1 \r\n"
			+ "AND (l.barcode LIKE %:keyword% OR u.userNo LIKE %:keyword% OR u.username LIKE %:keyword% OR l.rfid LIKE %:keyword%)\r\n"
			+ "ORDER BY l.datetime DESC", nativeQuery = true)
	List<LaptopSearchListInterface> findBySearch(@Param("keyword") String keyword);
	
}
