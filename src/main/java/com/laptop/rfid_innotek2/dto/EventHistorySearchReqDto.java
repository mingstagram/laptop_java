package com.laptop.rfid_innotek2.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EventHistorySearchReqDto {

	private String bizDeptCd;
	private String sdate;
	private String edate;
	private String result_temp;
	private String keyword;
	
}
