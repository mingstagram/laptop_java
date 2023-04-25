package com.laptop.rfid_innotek2.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class IsmsResDto {
	
	private String err_code;
	private String result_code;
	private String storageOutFlag;
	
	private String tag_name;
	
	private String agent_id;
	

}
