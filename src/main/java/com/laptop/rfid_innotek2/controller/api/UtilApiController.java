package com.laptop.rfid_innotek2.controller.api;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.laptop.rfid_innotek2.dto.LaptopSaveReqDto;
import com.laptop.rfid_innotek2.dto.LaptopSearchListInterface;
import com.laptop.rfid_innotek2.dto.ResponseDto;
import com.laptop.rfid_innotek2.model.EventHistory;
import com.laptop.rfid_innotek2.service.CommonService;
import com.laptop.rfid_innotek2.service.EventHistoryService;
import com.laptop.rfid_innotek2.service.LaptopInfoService;
import com.laptop.rfid_innotek2.service.UserService;
import com.laptop.rfid_innotek2.util.UtilService;

import lombok.extern.log4j.Log4j2;

@Log4j2
@RestController
public class UtilApiController { 
 
	@Autowired
	CommonService commonService; 
	
	@Autowired
	EventHistoryService eventHistoryService;
	
	@Autowired
	LaptopInfoService laptopInfoService;
	
	@Autowired
	UserService userService;
	
	@Value("${file.path}")
    private String filePath;
	
	@GetMapping("/api/util/excelDownload")
	public ResponseDto<Integer> excelDownload(HttpServletRequest request, HttpServletResponse response,
			String bizDeptCd,
			String result,
			String keyword,
			String sdate,
			String edate,
			String sort) throws Exception {
		log.info("□□□□□□□□□□ 엑셀 다운로드 시작 □□□□□□□□□□"); 
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
		log.info("□□□□□□□□□□ 엑셀 다운로드 종료 □□□□□□□□□□"); 
		
		return new ResponseDto<Integer>(HttpStatus.OK, 1);
	}
	
	@PostMapping("/api/util/excelUpload")
	public Object excelUpload(@RequestParam Map<String, Object> parameters) throws JsonMappingException, JsonProcessingException{
		
		log.info("□□□□□□□□□□ 엑셀 업로드 시작 □□□□□□□□□□"); 
		
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
		
		log.info("□□□□□□□□□□ 엑셀 업로드 종료 □□□□□□□□□□");
		
		return result;
	}
	
	@GetMapping("/api/util/logDownload")
	public Object downloadFile(HttpServletResponse res) throws IOException {
		String currentDate = UtilService.currentDateFormat("yyyyMMdd");
		
        // 다운로드할 파일 경로
		// /home/mingook/nohup.out   
        if(UtilService.isFilePathValid(filePath)) {
        	InputStream inputStream = new FileInputStream(filePath);
            InputStreamResource resource = new InputStreamResource(inputStream);

            HttpHeaders headers = new HttpHeaders();
            headers.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=Log_" + currentDate + ".log");

            return ResponseEntity
                    .ok()
                    .headers(headers)
                    .contentType(MediaType.TEXT_PLAIN)
                    .body(resource);
        } else {
        	res.setContentType("text/html; charset=euc-kr");
        	PrintWriter out = res.getWriter();
			out.println("<script>");
			out.println("alert('파일이 존재하지 않습니다.')");
			out.println("history.back()");
			out.println("</script>");
			out.flush();
			return null;
        }
        
    }
}
