package com.laptop.rfid_innotek2.model;

import java.sql.Timestamp;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;

import org.hibernate.annotations.CreationTimestamp;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
@Entity
public class AdmAgent {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY) // auto_increment
	private int id;
	
	private String agentIp;
	
	private String agentPort;
	
	private String agentNum;
	 
	@CreationTimestamp
	private Timestamp datetime;
	
	private String bizDeptCd;  
	 
	@OneToMany(mappedBy = "admAgent", cascade = CascadeType.REMOVE)
	@JsonIgnoreProperties({"admAgent"}) // 무한 참조 방지
	@OrderBy("datetime desc")
	private List<AdmSet> setList;

	@OneToMany(mappedBy = "admAgent", cascade = CascadeType.REMOVE)
	@JsonIgnoreProperties({"admAgent"}) // 무한 참조 방지
	@OrderBy("datetime desc")
	private List<AdmMember> memberList;
	
	@OneToMany(mappedBy = "admAgent", cascade = CascadeType.REMOVE)
	@JsonIgnoreProperties({"admAgent"}) // 무한 참조 방지
	@OrderBy("datetime desc")
	private List<SystemInfo> systemList;
}
