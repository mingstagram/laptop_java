package com.laptop.rfid_innotek2.controller.api;

import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.ss.usermodel.Workbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.laptop.rfid_innotek2.dto.ExcelUploadDto;
import com.laptop.rfid_innotek2.dto.LaptopSaveReqDto;
import com.laptop.rfid_innotek2.dto.LaptopSearchListInterface;
import com.laptop.rfid_innotek2.dto.ResponseDto;
import com.laptop.rfid_innotek2.model.EventHistory;
import com.laptop.rfid_innotek2.model.LaptopInfo;
import com.laptop.rfid_innotek2.model.PageMaker;
import com.laptop.rfid_innotek2.service.CommonService;
import com.laptop.rfid_innotek2.service.EventHistoryService;
import com.laptop.rfid_innotek2.service.LaptopInfoService;
import com.laptop.rfid_innotek2.service.UserService;

@RestController
public class UtilApiController {
	
	private static final Logger logger = LoggerFactory.getLogger(UtilApiController.class); 


	
	@Autowired
	CommonService commonService; 
	
	@Autowired
	EventHistoryService eventHistoryService;
	
	@Autowired
	LaptopInfoService laptopInfoService;
	
	@Autowired
	UserService userService;
	
	@GetMapping("/api/util/excelDownload")
	public ResponseDto<Integer> excelDownload(HttpServletRequest request, HttpServletResponse response,
			String bizDeptCd,
			String result,
			String keyword,
			String sdate,
			String edate,
			String sort) throws Exception {
		  
		if(sort.equals("laptop")) { 
			if (keyword != null && !keyword.equals("")){  
				List<LaptopSearchListInterface> laptopList = laptopInfoService.laptopSearch(keyword); 
				commonService.reqExcelDownload(request, response, laptopList, "laptop_search_list"); 
			} else {  
				List<LaptopSearchListInterface> laptopList = laptopInfoService.laptopList(); 
				commonService.reqExcelDownload(request, response, laptopList, "laptop_list"); 
			} 
		} else if(sort.equals("history")) {
			if(bizDeptCd == null && result == null && keyword == null && sdate == null && edate == null ) {

				List<EventHistory> historyList = eventHistoryService.historyList(); 
				commonService.reqExcelDownload(request, response, historyList, "export_list"); 
			} else {   
				List<EventHistory> historyList =eventHistoryService.historySearchList(bizDeptCd, result, keyword, sdate, edate); 
				commonService.reqExcelDownload(request, response, historyList, "export_list"); 
			} 
		}
		
		
		return new ResponseDto<Integer>(HttpStatus.OK, 1);
	}
	
	@PostMapping("/api/util/excelUpload")
	public Object excelUpload(@RequestParam Map<String, Object> parameters) throws JsonMappingException, JsonProcessingException{
		int insert = 0;
		int update = 0;
		int total = 0;
		int failed = 0;
		
		int userCount = 0;
		String json = parameters.get("list").toString();
		ObjectMapper mapper = new ObjectMapper();
		List<Map<String, Object>> paramList = mapper.readValue(json, new TypeReference<ArrayList<Map<String, Object>>>(){});
		
		total = laptopInfoService.laptopCount();
		
		for (Map<String, Object> map : paramList) {
			userCount = userService.findUserCount(String.valueOf(map.get("사번"))); 
			System.out.println(">>> " + userCount);
			if(userCount == 0) { 
				LaptopSaveReqDto saveDto = new LaptopSaveReqDto();
				saveDto.setAsset(String.valueOf(map.get("PC자산번호")));
				saveDto.setUserNo(String.valueOf(map.get("사번")));
				saveDto.setUsername(String.valueOf(map.get("성명")));
				saveDto.setSerial(String.valueOf(map.get("SerialNo")));
				saveDto.setBarcode(String.valueOf(map.get("BARCODE")));
				saveDto.setUserPart(String.valueOf(map.get("소속")));
				saveDto.setRfid(String.valueOf(map.get("RFID")));
				laptopInfoService.saveLaptop(saveDto);

				insert++;
			} else {  
				LaptopSaveReqDto saveDto = new LaptopSaveReqDto();
				saveDto.setAsset(String.valueOf(map.get("PC자산번호")));
				saveDto.setUserNo(String.valueOf(map.get("사번")));
				saveDto.setUsername(String.valueOf(map.get("성명")));
				saveDto.setSerial(String.valueOf(map.get("SerialNo")));
				saveDto.setBarcode(String.valueOf(map.get("BARCODE")));
				saveDto.setUserPart(String.valueOf(map.get("소속")));
				saveDto.setRfid(String.valueOf(map.get("RFID")));
				laptopInfoService.updateLaptopByUserNo(saveDto, String.valueOf(map.get("사번")));
				
				update++;
			} 
		}
		Map<String, Integer> result = new HashMap<>();
		result.put("insert", insert);
		result.put("update", update);
		result.put("total", total);
		result.put("failed", failed); 
		
		
		return result;
	}


	
}
