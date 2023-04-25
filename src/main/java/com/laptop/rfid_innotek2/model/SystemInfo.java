package com.laptop.rfid_innotek2.model;

import java.sql.Timestamp;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

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
public class SystemInfo {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY) // auto_increment
	private int id;
	
	private String set1;
	
	private String set2;
	
	private String set3;
	
	private String set4;
	
	@CreationTimestamp
	private Timestamp datetime;
	
	@ManyToOne
	@JoinColumn(name = "agentId")
	private AdmAgent admAgent;

}
