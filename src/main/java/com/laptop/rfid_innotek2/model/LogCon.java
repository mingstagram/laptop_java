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
public class LogCon {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY) // auto_increment
	private int id;
	
	private String agent;
	
	private String agentIp;
	
	private String agentPort;
	
	private String etc;

	@CreationTimestamp
	private Timestamp datetime; 
}
