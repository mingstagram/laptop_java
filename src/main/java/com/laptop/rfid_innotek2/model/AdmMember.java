package com.laptop.rfid_innotek2.model;

import java.sql.Timestamp;

import javax.persistence.CascadeType;
import javax.persistence.ConstraintMode;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ForeignKey;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;

import org.hibernate.annotations.ColumnDefault;
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
public class AdmMember {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY) // auto_increment
	private int id;
	
	private String username;
	
	private String password; 
	
	@ColumnDefault("1")
	private String xrayNum;
	
	@ColumnDefault("3")
	private String prop;
	 
	@CreationTimestamp
	private Timestamp datetime;
	
	@ManyToOne 
	@JoinColumn(name = "agentId")
	private AdmAgent admAgent; 
}
