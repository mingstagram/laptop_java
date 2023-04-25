package com.laptop.rfid_innotek2.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AdmSetSaveReqDto {
 
	private int agentId;
	private String xray;
	private String antenaIp;
	private String antenaPort;
	private String alertIp;
	private String alertPort;
	private String alertBlue;
	private String alertBlueSound;
	private String alertRed;
	private String alertRedSound; 
	
}
