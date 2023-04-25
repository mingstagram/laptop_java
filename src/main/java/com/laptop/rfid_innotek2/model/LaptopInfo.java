package com.laptop.rfid_innotek2.model;

import java.sql.Timestamp;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import org.hibernate.annotations.CreationTimestamp;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
@Entity
public class LaptopInfo {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY) // auto_increment
	private int id;
	
	private String barcode;
	
	private String asset;
	
	private String serial;
	
	private String rfid;
	
	@CreationTimestamp
	private Timestamp datetime;
	
//	@OneToMany(mappedBy = "laptopInfo", cascade = CascadeType.REMOVE)
//	@JsonIgnoreProperties({"laptopInfo"}) // 무한 참조 방지 
//	private List<EventHistory> historyList;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "userId")
	private User user;

}
