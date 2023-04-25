package com.laptop.rfid_innotek2.dto;

import java.util.List;
import java.util.Map;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AgentResDto {
	 
	private int result_code; 
	private List<Map<String, Object>> rfid_device;
//	private String device_address;
//	private int device_lamp_state;
//	private int device_state;

//	public class rfid_device {
//		public String device_address;
//		public int device_lamp_state;
//		public int device_state;
//		public String getDevice_address() {
//			return device_address;
//		}
//		public void setDevice_address(String device_address) {
//			this.device_address = device_address;
//		}
//		public int getDevice_lamp_state() {
//			return device_lamp_state;
//		}
//		public void setDevice_lamp_state(int device_lamp_state) {
//			this.device_lamp_state = device_lamp_state;
//		}
//		public int getDevice_state() {
//			return device_state;
//		}
//		public void setDevice_state(int device_state) {
//			this.device_state = device_state;
//		}
//		 
//	}
	
	private String agent;

}
