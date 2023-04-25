package com.laptop.rfid_innotek2.service;

import java.io.OutputStream;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.List;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.VerticalAlignment;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.laptop.rfid_innotek2.dto.LaptopSearchListInterface;
import com.laptop.rfid_innotek2.model.EventHistory;
import com.laptop.rfid_innotek2.model.LaptopInfo;

@Service
public class CommonService {

	@Autowired
	HttpServletRequest req;

	@Autowired
	HttpServletResponse res;

	private final Logger logger = LoggerFactory.getLogger(getClass());

	public String getCookie(String findName) {

		Cookie[] cookies = req.getCookies();
		String findValue = "";
		for (Cookie cookie : cookies) {
			if (cookie.getName().equals(findName)) {
				findValue = cookie.getValue();
				break;
			}
		}
		return findValue;
	}
	
	public void cookieChange(String cookieName, String cookieValue) {
		Cookie[] cookies = req.getCookies(); 
	    if (cookies != null && cookies.length > 0) { 
	        for (int i = 0 ; i < cookies.length ; i++) { 
	            if (cookies[i].getName().equals(cookieName)) { 
	                Cookie cookie = new Cookie(cookieName, cookieValue); 
	                res.addCookie(cookie); 
	            } 
	        } 
	    }
	}

	public void setCookie(String cookieName, String cookieValue) {
		
		Cookie cookie = new Cookie(cookieName, cookieValue);
		cookie.setPath("/");
		cookie.setMaxAge(60 * 60 * 24 * 7); // 쿠키 유효기간 설정 (7일로 설정할 경우)
		res.addCookie(cookie);
	}

	public void invalidateCookie() {

		Cookie[] cookies = req.getCookies();
		for (Cookie cookie : cookies) {
			cookie.setPath("/");
			cookie.setMaxAge(0);
			res.addCookie(cookie);
		}
	}

	public String getRemoteAddr(HttpServletRequest request) {
		return (null != request.getHeader("X-FORWARDED-FOR")) ? request.getHeader("X-FORWARDED-FOR")
				: request.getRemoteAddr();
	}

	public static String jsonEncode(final String val) {
		if ((val == null) || (val.length() == 0)) {
			return "\"\"";
		}

		StringBuilder sb = new StringBuilder();

		/*
		 * \n newline \t tab \b backspace \f form feed \r return \"   " (double quote)
		 * \\ \ (back slash)
		 */

		sb.append('"');

		for (int i = 0; i < val.length(); i++) {
			char ch = val.charAt(i);

			switch (ch) {
			case '\n':
				sb.append("\\n");
				break;

			case '\t':
				sb.append("\\t");
				break;

			case '\b':
				sb.append("\\b");
				break;

			case '\f':
				sb.append("\\f");
				break;

			case '\r':
				sb.append("\\r");
				break;

			case '"':
			case '/':
			case '\\':
				sb.append('\\');
				sb.append(ch);
				break;

			case ' ':
				sb.append(" ");
				break;

			default:
				if (Character.isISOControl(ch)) {
					String str = Integer.toHexString(ch);
					sb.append("\\u");
					sb.append("0000".substring(str.length() - 4));
					sb.append(str);
				} else {
					sb.append(ch);
				}
			}
		}

		sb.append('"');
		return sb.toString();
	}

	public String getBizDeptText(String bizDeptCd) {
		String bizDeptText = "";
		switch (bizDeptCd) {
		case "07":
			bizDeptText = "파주";
			break;
		case "03":
			bizDeptText = "안산";
			break;
		case "01":
			bizDeptText = "광주";
			break;
		case "02":
			bizDeptText = "구미";
			break;
		case "04":
			bizDeptText = "평택";
			break;
		case "18":
			bizDeptText = "마곡";
			break;
		default:
			bizDeptText = "미등록";
			break;
		}
		return bizDeptText;
	}

	public boolean nullCheck(Object obj) {
		return (obj != null && !obj.equals(""));
	}

	public Workbook excelDownLoad(List<?> list, String kind) { 
		Workbook workbook = new XSSFWorkbook(); 

		Row row = null;
		Cell cell = null; 
		if (kind.equals("export_list")) { 
			Sheet sheet = workbook.createSheet(kind);
			String[] headerKey = { "사번", "이름", "직급", "바코드", "RFID", "사업장", "반출시간", "상태" };

			CellStyle headerStyle = CellStyleSetting(workbook, "header");

			CellStyle dataStyle = CellStyleSetting(workbook, "data");

			row = sheet.createRow(0);

			for (int i = 0; i < headerKey.length; i++) {
				cell = row.createCell(i);
				cell.setCellValue(headerKey[i]);
				cell.setCellStyle(headerStyle);
			}

			for (int i = 0; i < list.size(); i++) { // 데이터 구성
				row = sheet.createRow(i + 1);
				int cellIdx = 0;

				EventHistory vo = (EventHistory) list.get(i);
				
				cell = row.createCell(cellIdx++);
				cell.setCellValue(vo.getUserNo());
				cell.setCellStyle(dataStyle);

				cell = row.createCell(cellIdx++);
				cell.setCellValue(vo.getUsername());
				cell.setCellStyle(dataStyle);

				cell = row.createCell(cellIdx++);
				cell.setCellValue("");
				cell.setCellStyle(dataStyle);

				cell = row.createCell(cellIdx++);
				cell.setCellValue(vo.getBarcode());
				cell.setCellStyle(dataStyle);

				cell = row.createCell(cellIdx++);
				cell.setCellValue(vo.getRfid());
				cell.setCellStyle(dataStyle);

				cell = row.createCell(cellIdx++);
				cell.setCellValue(vo.getBizDeptCd());
				cell.setCellStyle(dataStyle);

				cell = row.createCell(cellIdx++);
				cell.setCellValue(vo.getDatetime().toString());
				cell.setCellStyle(dataStyle);

				cell = row.createCell(cellIdx++);
				if(vo.getResult().equals("Y") || vo.getResult().equals("S")) cell.setCellValue("승인");
				else if(vo.getResult().equals("N")) cell.setCellValue("미승인");
				else cell.setCellValue("인식불가");
				cell.setCellStyle(dataStyle);
			}

			// 셀 넓이 자동 조정
			for (int i = 0; i < headerKey.length; i++) {
				sheet.autoSizeColumn(i);
				sheet.setColumnWidth(i, sheet.getColumnWidth(i));
			}

		} else if (kind.equals("laptop_list")) { 
			Sheet sheet = workbook.createSheet(kind);
			String[] headerKey = { "사번", "성명", "PC자산번호", "SerialNo", "BARCODE", "소속", "RFID" };

			CellStyle headerStyle = CellStyleSetting(workbook, "header");

			CellStyle dataStyle = CellStyleSetting(workbook, "data");

			row = sheet.createRow(0);

			for (int i = 0; i < headerKey.length; i++) {
				cell = row.createCell(i);
				cell.setCellValue(headerKey[i]);
				cell.setCellStyle(headerStyle);
			}

			for (int i = 0; i < list.size(); i++) { // 데이터 구성
				row = sheet.createRow(i + 1);
				int cellIdx = 0;

				LaptopSearchListInterface vo = (LaptopSearchListInterface) list.get(i);

				cell = row.createCell(cellIdx++);
				cell.setCellValue(vo.getUserNo());
				cell.setCellStyle(dataStyle);

				cell = row.createCell(cellIdx++);
				cell.setCellValue(vo.getUsername());
				cell.setCellStyle(dataStyle);

				cell = row.createCell(cellIdx++);
				cell.setCellValue(vo.getAsset());
				cell.setCellStyle(dataStyle);

				cell = row.createCell(cellIdx++);
				cell.setCellValue(vo.getSerial());
				cell.setCellStyle(dataStyle);

				cell = row.createCell(cellIdx++);
				cell.setCellValue(vo.getBarcode());
				cell.setCellStyle(dataStyle);

				cell = row.createCell(cellIdx++);
				cell.setCellValue(vo.getUserPart());
				cell.setCellStyle(dataStyle);

				cell = row.createCell(cellIdx++);
				cell.setCellValue(vo.getRfid());
				cell.setCellStyle(dataStyle); 
			}

			// 셀 넓이 자동 조정
			for (int i = 0; i < headerKey.length; i++) {
				sheet.autoSizeColumn(i);
				sheet.setColumnWidth(i, sheet.getColumnWidth(i));
			}

		} else if (kind.equals("laptop_search_list")) { 
			Sheet sheet = workbook.createSheet(kind);
			String[] headerKey = { "사번", "성명", "PC자산번호", "SerialNo", "BARCODE", "소속", "RFID" };

			CellStyle headerStyle = CellStyleSetting(workbook, "header");

			CellStyle dataStyle = CellStyleSetting(workbook, "data");

			row = sheet.createRow(0);

			for (int i = 0; i < headerKey.length; i++) {
				cell = row.createCell(i);
				cell.setCellValue(headerKey[i]);
				cell.setCellStyle(headerStyle);
			}

			for (int i = 0; i < list.size(); i++) { // 데이터 구성
				row = sheet.createRow(i + 1);
				int cellIdx = 0;
 
				LaptopSearchListInterface vo = (LaptopSearchListInterface) list.get(i);

				cell = row.createCell(cellIdx++);
				cell.setCellValue(vo.getUserNo());
				cell.setCellStyle(dataStyle);

				cell = row.createCell(cellIdx++);
				cell.setCellValue(vo.getUsername());
				cell.setCellStyle(dataStyle);

				cell = row.createCell(cellIdx++);
				cell.setCellValue(vo.getAsset());
				cell.setCellStyle(dataStyle);

				cell = row.createCell(cellIdx++);
				cell.setCellValue(vo.getSerial());
				cell.setCellStyle(dataStyle);

				cell = row.createCell(cellIdx++);
				cell.setCellValue(vo.getBarcode());
				cell.setCellStyle(dataStyle);

				cell = row.createCell(cellIdx++);
				cell.setCellValue(vo.getUserPart());
				cell.setCellStyle(dataStyle);

				cell = row.createCell(cellIdx++);
				cell.setCellValue(vo.getRfid());
				cell.setCellStyle(dataStyle); 
			}

			// 셀 넓이 자동 조정
			for (int i = 0; i < headerKey.length; i++) {
				sheet.autoSizeColumn(i);
				sheet.setColumnWidth(i, sheet.getColumnWidth(i));
			}

		} 
		return workbook;

	}

	public CellStyle CellStyleSetting(Workbook workbook, String kind) {
		CellStyle cellStyle = workbook.createCellStyle();

		cellStyle.setBorderTop(BorderStyle.THIN);
		cellStyle.setBorderBottom(BorderStyle.THIN);
		cellStyle.setBorderLeft(BorderStyle.THIN);
		cellStyle.setBorderRight(BorderStyle.THIN);

		if (kind.equals("header")) {
			// 배경색 회색
			cellStyle.setFillForegroundColor(IndexedColors.YELLOW.getIndex());
			cellStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
		}

		// 데이터는 가운데 정렬
		cellStyle.setAlignment(HorizontalAlignment.CENTER);
		cellStyle.setVerticalAlignment(VerticalAlignment.CENTER); // 중앙 정렬

		// 폰트 설정
		Font fontOfGothic = workbook.createFont();
		fontOfGothic.setFontName("맑은 고딕");
		cellStyle.setFont(fontOfGothic);

		return cellStyle;
	}

	// 엑셀 다운로드 요청
	public void reqExcelDownload(HttpServletRequest request, HttpServletResponse response, List<?> list, String kind)
			throws Exception {

		OutputStream outs = response.getOutputStream();

		String fileName = kind;
		if(fileName.equals("laptop_search_list")) fileName = "laptop_list";
		String encFileName = fileName;
		logger.info(">>> [" + fileName + "] 엑셀 다운로드 시작.");

		try {
			Workbook workbook = null;

			workbook = excelDownLoad(list, kind);
			response.reset();  
			response.setHeader("Content-Disposition", "attachment; filename=\"" + encFileName + ".xlsx\"");
			// 엑셀 출력
			workbook.write(outs);

		} catch (Exception e) {
			logger.error(">>> [" + fileName + "] 엑셀 다운로드 도중 오류가 발생했습니다. : {}", e);
			e.printStackTrace();

			response.reset();
			response.setContentType("text/html;charset=UTF-8");
			PrintWriter pout = response.getWriter();
			pout.println("<script type=\"text/javascript\">");
			pout.println("alert('[IOException]  [" + fileName + "] 엑셀 다운로드 도중 오류가 발생했습니다.\\n시스템 관리자에게 문의 바랍니다.');history.go(-1);");
			pout.println("</script>");
			pout.flush();
		} finally {
			logger.info(">>> [" + fileName + "] 엑셀 다운로드 종료.");
			if (outs != null)
				outs.close();
			response.getOutputStream().flush();
			response.getOutputStream().close();
		}

	}

}
