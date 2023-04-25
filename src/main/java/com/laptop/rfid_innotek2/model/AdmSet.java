package com.laptop.rfid_innotek2.model;

import java.sql.Timestamp;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.ConstraintMode;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ForeignKey;
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
public class AdmSet {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY) // auto_increment
	private int id;

	private String xray;
	 
	private String antenaIp;
	 
	private String antenaPort;
	 
	private String alertIp;
	 
	private String alertPort;
	 
	private String alertRed;
	 
	private String alertRedSound;
	 
	private String alertBlue;
	 
	private String alertBlueSound;
	
	@CreationTimestamp
	private Timestamp datetime;
	
	@ManyToOne(fetch = FetchType.LAZY,cascade = CascadeType.ALL)
	@JoinColumn(name = "agentId", nullable = false, foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT))
	private AdmAgent admAgent;
	
}
