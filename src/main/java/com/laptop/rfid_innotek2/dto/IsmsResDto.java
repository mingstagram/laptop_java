package com.laptop.rfid_innotek2.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@JsonIgnoreProperties(ignoreUnknown = true)
public class IsmsResDto {
	
	private String err_code;
	private String result_code;
	private String storageOutFlag;
	
	private String tag_name;
	
	private String agent_id;

	private String status;
	private String empNm;
	private String empId;
	private String mediaLineId;
	

}
