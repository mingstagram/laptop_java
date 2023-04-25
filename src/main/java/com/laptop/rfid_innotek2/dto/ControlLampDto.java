package com.laptop.rfid_innotek2.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ControlLampDto {

	private String agent;
	private String xray_id;
	private String lamp_type;
	private String warning_time;
	private String warning_type;
	private String sound_onoff;
	
}
