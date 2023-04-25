package com.laptop.rfid_innotek2.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AdmMemberSaveReqDto {

	private String username;
	private String password;
	private int agentId;
	private String bizDeptCd;
	
}
