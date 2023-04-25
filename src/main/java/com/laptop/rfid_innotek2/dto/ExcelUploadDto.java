package com.laptop.rfid_innotek2.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ExcelUploadDto {

	private String 사번;
	private String 성명;
	private String 소속;
	private String PC자산번호;
	private String SerialNo;
	private String BARCODE;
	private String RFID;
	
}
