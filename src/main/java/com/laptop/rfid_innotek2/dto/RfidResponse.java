package com.laptop.rfid_innotek2.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class RfidResponse {

	private String storageOutFlag;
	private String result_code;
	
}
