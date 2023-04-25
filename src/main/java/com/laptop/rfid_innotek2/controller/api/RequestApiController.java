package com.laptop.rfid_innotek2.controller.api;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.laptop.rfid_innotek2.dto.AgentReqDto;
import com.laptop.rfid_innotek2.dto.AgentResDto;
import com.laptop.rfid_innotek2.dto.ControlLampDto;
import com.laptop.rfid_innotek2.dto.RfidDeviceDto;
import com.laptop.rfid_innotek2.model.AdmAgent;
import com.laptop.rfid_innotek2.service.AdmAgentService;
import com.laptop.rfid_innotek2.service.AdmSetService;
import com.laptop.rfid_innotek2.service.CommonService;
import com.laptop.rfid_innotek2.service.EventHistoryService;
import com.laptop.rfid_innotek2.service.LaptopInfoService;
import com.laptop.rfid_innotek2.service.SystemInfoService;

@RestController
public class RequestApiController {
	
	@Autowired
	LaptopInfoService laptopInfoService; 
	
	@Autowired
	CommonService commonService; 
	
	@Autowired
	SystemInfoService systemInfoService;
	
	@Autowired
	AdmAgentService admAgentService;
	
	@Autowired
	AdmSetService admSetService; 
	
	@Autowired
	EventHistoryService eventHistoryService; 
	
//	@PostMapping("/api/control_lamp")
	@PostMapping("/rest/control_lamp.php")
	public String controlLamp(@RequestBody ControlLampDto lampDto) {
		int agent_id = Integer.parseInt(lampDto.getAgent());
		AdmAgent agent = 	admAgentService.findByAgent(agent_id);
		String agent_ip = agent.getAgentIp();
		String agent_port = agent.getAgentPort();
		RestTemplate rt = new RestTemplate();
		
		// HttpHeader 오브젝트 생성
		HttpHeaders headers = new HttpHeaders();
		headers.add("Content-type", "application/json");
		
		// HttpBody 오브젝트 생성
		Map<String, Object> params = new HashMap<>();
		params.put("xray_id", Integer.parseInt(lampDto.getXray_id()));
		params.put("lamp_type", Integer.parseInt(lampDto.getLamp_type()));
		params.put("warning_time", Integer.parseInt(lampDto.getWarning_time()));
		params.put("warning_type", Integer.parseInt(lampDto.getWarning_type()));
		params.put("sound_onoff", Integer.parseInt(lampDto.getSound_onoff())); 
		// HttpBody를 하나의 오브젝트에 담기
		HttpEntity<Map<String, Object>> rfidInfo = 
				new HttpEntity<>(params, headers);
		
		String url = "http://" + agent_ip+":" + agent_port + "/control_lamp";
		
		// Http 요청하기
		ResponseEntity<String> response = rt.exchange(
//					"http://165.186.83.46:8011/storage/storage/RetrieveStorageOutBarcodeCmd.dev",
					url,
					HttpMethod.POST,
					rfidInfo,
					String.class
				);

		return response.getBody();
	}
	
//	@PostMapping("/api/send_reset_device_info")
	@PostMapping("/rest/send_reset_device_info.php")
	public Object send_reset_device_info(@RequestBody AgentReqDto agentReqDto) {
		System.out.println(agentReqDto);
		int agent_id = agentReqDto.getXray_id();
		AdmAgent agent = 	admAgentService.findByAgent(agent_id);
		System.out.println(">D>D " + agent.getId());
		String agent_ip = agent.getAgentIp();
		String agent_port = agent.getAgentPort(); 
		
		String url = "http://" + agent_ip+":" + agent_port + "/reset_device_info";
		RestTemplate rt = new RestTemplate();
		
		// HttpHeader 오브젝트 생성
		HttpHeaders headers = new HttpHeaders();
		headers.add("Content-type", "application/json");
		
		// HttpBody 오브젝트 생성
		Map<String, Object> params = new HashMap<>();
		params.put("xray_id", agent_id); 
		// HttpBody를 하나의 오브젝트에 담기
		HttpEntity<Map<String, Object>> agentInfo = 
				new HttpEntity<>(params, headers);
		
		// Http 요청하기
		ResponseEntity<String> response = rt.exchange( 
					url,
					HttpMethod.POST,
					agentInfo,
					String.class
				);
		
		return response.getBody();
	}
	
//	@PostMapping("/api/get_device_state")
	@PostMapping("/rest/get_device_state.php")
	public Object get_device_state(@RequestBody AgentResDto agentResDto) throws Exception {
		String agent = agentResDto.getAgent();
		System.out.println(">>>> " + agent);
		if(agent != null && !agent.equals("")) {
			System.out.println("11111111111111");
			int agentId = Integer.parseInt(agent);
			
			AdmAgent admAgent = admAgentService.findTopByIdOrderByDatetimeDesc(agentId);
			String agent_ip = admAgent.getAgentIp();
			String agent_port = admAgent.getAgentPort();
			String url = "http://" + agent_ip+":" + agent_port + "/get_device_state";
			
			RestTemplate rt = new RestTemplate();
			
			// HttpHeader 오브젝트 생성
			HttpHeaders headers = new HttpHeaders();
			headers.add("Content-type", "application/json");
			
			// HttpBody 오브젝트 생성
			Map<String, Object> params = new HashMap<>();
			params.put("xray_id", 1);
			params.put("lamp_test", 1); 
			// HttpBody를 하나의 오브젝트에 담기
			HttpEntity<Map<String, Object>> agentInfo = 
					new HttpEntity<>(params, headers);
			
			// Http 요청하기
			ResponseEntity<String> response = rt.exchange( 
						url,
						HttpMethod.POST,
						agentInfo,
						String.class
					); 
			ObjectMapper objectMapper = new ObjectMapper();
			System.out.println(response.getBody());
			AgentResDto resDto = objectMapper.readValue(response.getBody(), AgentResDto.class);
//			RfidDeviceDto resDto1 = objectMapper.readValue(resDto.toString(), RfidDeviceDto.class);
			if(resDto.getRfid_device() != null) {
				System.out.println("22222222222");
				String device_address = (String) resDto.getRfid_device().get(0).get("device_address");
				int device_lamp_state = (int) resDto.getRfid_device().get(0).get("device_lamp_state");
				int device_state = (int) resDto.getRfid_device().get(0).get("device_state");
				System.out.println(">>> " + resDto.getRfid_device().get(0));
				Map<String, Object> result = new HashMap<>(); 
				if(device_lamp_state == 1 && resDto != null) {
					result.put("antena_result", "connected");
				} else {
					result.put("antena_result", "fail");
				}
				
				if(device_state == 1 && resDto != null) {
					result.put("alert_result", "connected");
				} else {
					result.put("alert_result", "fail");
				}
				return result;
			} else {
				System.out.println("3333333");
				Map<String, Object> result = new HashMap<>(); 
				result.put("antena_result", "fail");
				result.put("alert_result", "fail");
				return result;
			} 
		} else {
			return null;
		}
		
		
	}
	
	

}
