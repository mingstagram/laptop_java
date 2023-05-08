package com.laptop.rfid_innotek2.model;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity; 
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

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
@Table(name = "log")
public class Log {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY) // auto_increment
	private int id;
	 
	private String device;
	
	@Column(name = "ip_addr")
	private String ipAddress;
	 
	private String state;

	@CreationTimestamp
	private Timestamp datetime; 
}
