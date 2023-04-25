package com.laptop.rfid_innotek2.model;

import java.sql.Timestamp;
 
import javax.persistence.Entity; 
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id; 

import org.hibernate.annotations.CreationTimestamp;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
@Entity
public class EventHistory {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY) // auto_increment
	private int id;
	
	private String xray;
	
	private String result;
	
	private String antenaNo;
	
	private String errorCode;
	
	private String agent;
	
	private String username;
	
	private String userNo;
	
	private String barcode;
	
	private String rfid;
	
	private String bizDeptCd;
	
	private String bizDeptText;
	
	@CreationTimestamp
	private Timestamp datetime;

}
