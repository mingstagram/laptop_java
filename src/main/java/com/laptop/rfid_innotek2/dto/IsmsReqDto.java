package com.laptop.rfid_innotek2.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class IsmsReqDto {
	
	private String barcode;
	private String enpNo;
	private String bizDeptCd; 

}
