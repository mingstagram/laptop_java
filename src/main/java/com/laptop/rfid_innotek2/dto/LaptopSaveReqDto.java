package com.laptop.rfid_innotek2.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LaptopSaveReqDto {
 
	private int laptopId;
	private int userId;
	private String userNo;
	private String username;
	private String userPart;
	private String asset;
	private String serial;
	private String barcode;
	private String rfid; 
	private String userPos;
	
}
