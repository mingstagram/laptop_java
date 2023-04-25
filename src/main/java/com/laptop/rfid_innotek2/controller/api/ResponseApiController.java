package com.laptop.rfid_innotek2.controller.api;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.laptop.rfid_innotek2.dto.IsmsReqDto;
import com.laptop.rfid_innotek2.dto.IsmsResDto;
import com.laptop.rfid_innotek2.model.AdmAgent;
import com.laptop.rfid_innotek2.model.AdmSet;
import com.laptop.rfid_innotek2.model.EventHistory;
import com.laptop.rfid_innotek2.model.LaptopInfo;
import com.laptop.rfid_innotek2.model.LogCon;
import com.laptop.rfid_innotek2.model.SystemInfo;
import com.laptop.rfid_innotek2.model.User;
import com.laptop.rfid_innotek2.service.AdmAgentService;
import com.laptop.rfid_innotek2.service.AdmSetService;
import com.laptop.rfid_innotek2.service.CommonService;
import com.laptop.rfid_innotek2.service.EventHistoryService;
import com.laptop.rfid_innotek2.service.LaptopInfoService;
import com.laptop.rfid_innotek2.service.LogConService;
import com.laptop.rfid_innotek2.service.SystemInfoService; 

@RestController
public class ResponseApiController {
	
	@Autowired
	CommonService commonService;
	
	@Autowired
	AdmAgentService admAgentService;
	
	@Autowired
	SystemInfoService systemInfoService;
	
	@Autowired
	LogConService logConService;
	
	@Autowired
	AdmSetService admSetService;
	
	@Autowired
	EventHistoryService eventHistoryService; 
	
	@Autowired
	LaptopInfoService laptopInfoService; 
	
//	@PostMapping("/api/send_rfid_info")
	@PostMapping("/rest/send_rfid_info.php")
	public Map<String,Object> sendRfidInfo(@RequestBody IsmsResDto resData, HttpServletRequest req) throws Exception {
		LaptopInfo laptop = laptopInfoService.laptopByRfid(resData.getTag_name()); 
		System.out.println(laptop);
		System.out.println(resData.getTag_name());
		Map<String, Object> result = new HashMap<>();
		if(laptop != null) {
			RestTemplate rt = new RestTemplate();
			
			// HttpHeader 오브젝트 생성
			HttpHeaders headers = new HttpHeaders();
			headers.add("Content-type", "application/json");
			
			// HttpBody 오브젝트 생성
			MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
			params.add("barcode", null);
			params.add("enpNo", null);
			params.add("bizDeptCd", null); 
			// HttpBody를 하나의 오브젝트에 담기
			HttpEntity<MultiValueMap<String, String>> rfidInfo = 
					new HttpEntity<>(params, headers);
			
			// Http 요청하기
			ResponseEntity<String> response = rt.exchange(
//						"http://165.186.83.46:8011/storage/storage/RetrieveStorageOutBarcodeCmd.dev",
						"http://127.0.0.1:8000/api/receive",
						HttpMethod.POST,
						rfidInfo,
						String.class
					);
			
			// Gson, Json Simple, ObjectMapper // 응답받은 JSON값 Object로 변경해주는 템플릿
			ObjectMapper objectMapper = new ObjectMapper();
			

			String remoteAddr = commonService.getRemoteAddr(req);
			
			AdmAgent agent = admAgentService.findTopByAgentIp(remoteAddr); 
			int agent_id = agent.getId();
			String agent_id_str = String.valueOf(agent_id);
			String lamp_type = "0";
			String set3 = systemInfoService.systemInfo(agent_id).getSet3(); 
			
			AdmSet set = admSetService.admSetInfo(agent_id);
			 
			IsmsResDto resDto = objectMapper.readValue(response.getBody(), IsmsResDto.class);  
			if(resDto.getStorageOutFlag().equals("Y") || resDto.getStorageOutFlag().equals("S")) { 
				lamp_type = "2";
				set3 = "1";
			} else if(resDto.getStorageOutFlag().equals("N")) { 
				lamp_type = "1";
			} else { 
				lamp_type = "0";
			}
			
			String warning_type = "";
			String sound_onoff = "0";
			if(set.getAlertRed().equals("rb")) warning_type = "2";
			else warning_type = "1";
			
			if(set.getAlertBlue().equals("gb")) warning_type = "2";
			else warning_type = "1";
			
			if(set.getAlertRedSound().equals("sound") || set.getAlertBlueSound().equals("sound")) 
				sound_onoff = "1"; 
			
			HashMap<Object, Object> map = new HashMap<>();
			map.put("xray_id", 1);
			map.put("lamp_type", Integer.parseInt(lamp_type));
			map.put("warning_time", Integer.parseInt(set3));
			map.put("warning_type", Integer.parseInt(warning_type));
			map.put("sound_onoff", Integer.parseInt(sound_onoff));
			
			result.put("result_code", 0);
			result.put("result", map); 
			
			User user = laptop.getUser();
			String err_code = resDto.getErr_code();  
			if(user == null) err_code = "300"; 
			
			String bizDeptCd = agent.getBizDeptCd();
			
			String bizDeptText = commonService.getBizDeptText(bizDeptCd); 
			
			EventHistory history = EventHistory.builder()
					.agent(agent_id_str)
					.rfid(resData.getTag_name())
					.result(resDto.getStorageOutFlag())
					.xray("1")
					.antenaNo("1")
					.errorCode(err_code)
					.userNo(user.getUserNo())
					.username(user.getUsername())
					.barcode(laptop.getBarcode())
					.bizDeptCd(bizDeptCd)
					.bizDeptText(bizDeptText)  
					.build();
			
			eventHistoryService.saveHistory(history); 
			return result;
		} else {
			result.put("result_code", "1");
			return result;
		}
		 
	}
	
	@PostMapping("/api/receive")
//	@PostMapping("/pages/receive.php")
	public Object receive(IsmsReqDto result) {  
		
		int random = (int)((Math.random()*10000)%10);
		String flag = "Y"; 
		if(random%2 == 1) flag = "Y";
		else flag = "N";
		IsmsResDto resDto = new IsmsResDto(); 
		resDto.setErr_code(null);
		resDto.setResult_code("200");
		resDto.setStorageOutFlag(flag);
		
		return resDto;
	}
	
//	@PostMapping("/api/control_device_info")
	@PostMapping("/rest/control_device_info.php")
	public Object control_device_info(@RequestBody IsmsResDto resDto, HttpServletRequest req) { 
		String agent_id = resDto.getAgent_id();
		System.out.println(">>> " + agent_id);
		if(agent_id != null && !agent_id.equals("")) {

			String remoteAddr = commonService.getRemoteAddr(req); 
			AdmAgent agent = admAgentService.findTopByAgentIp(remoteAddr);
			if(agent != null) {
				int id = agent.getId();
				SystemInfo system = systemInfoService.systemInfo(id); 
				HashMap<Object, Object> result = new HashMap<>();
				
				HashMap<Object, Object> map = new HashMap<>();
				
				String agentNum = agent.getAgentNum();
				String port = String.valueOf(req.getServerPort());
				
				LogCon log = LogCon.builder()
						.agent(agent_id)
						.agentIp(remoteAddr)
						.agentPort(port)
						.etc(agentNum)
						.build();
				logConService.save(log);
				 
				HashMap<Object, Object> rfid_device = new HashMap<>();
				int[] antena_Arr = {1,2};  
				HashMap<Object, Object> warning_lamp_device = new HashMap<>(); 
				List<AdmSet> admSetList = admSetService.admSetListByAgent(agent); 
				for (AdmSet set : admSetList) {
					map.put("xray_id", Integer.parseInt(set.getXray()));
					rfid_device.put("device_address", set.getAntenaIp());
					rfid_device.put("device_port", Integer.parseInt(set.getAntenaPort()));
					rfid_device.put("antenna_list", antena_Arr);
					warning_lamp_device.put("device_address", set.getAlertIp());
					warning_lamp_device.put("device_port", Integer.parseInt(set.getAlertPort()));
					warning_lamp_device.put("warning_lamp_device", Integer.parseInt(set.getAlertPort()));
				} 
				List<Object> device_info = new ArrayList<>(); 
				HashMap<Object, Object> device_info_map = new HashMap<>(); 
				device_info_map.put("xray_id", 1);
				device_info_map.put("rfid_device", rfid_device);
				device_info_map.put("warning_lamp_device", warning_lamp_device);
				device_info.add(device_info_map);
				
				result.put("detect_count", Integer.parseInt(system.getSet1()));
				result.put("reselect_count", Integer.parseInt(system.getSet4()));
				result.put("warning_time", Integer.parseInt(system.getSet3()));
				result.put("rfid_clear_time", Integer.parseInt(system.getSet2()));
				result.put("device_info", device_info); 
				
				map.put("result_code", 0);
				map.put("result", result); 
				 
				return map;
			}
			return null;
			
		} else {
			return null;
		}
		
		
		
	}
	
	@GetMapping("/time/test")
	public Object timeTEst() {
		
		Date nowDate = new Date(); 
		
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"); 
		 
		return simpleDateFormat.format(nowDate);
	}

}
