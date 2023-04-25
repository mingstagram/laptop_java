package com.laptop.rfid_innotek2.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AdmAgentSaveReqDto {

	private String agentIp;
	private String agentPort;
	private String agentNum;
	private String bizDeptCd;
	
}
