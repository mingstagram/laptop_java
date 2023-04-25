package com.laptop.rfid_innotek2.dto;

import java.sql.Timestamp; 

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class HistoryList {

	private int num;
	private String agent;
	private String userNo;
	private String username;
	private String barcode;
	private String rfid;
	private String bizDeptName;
	private Timestamp datetime;
	private String resultTemp;
	
}
