package com.laptop.rfid_innotek2.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.laptop.rfid_innotek2.model.AdmAgent;
import com.laptop.rfid_innotek2.model.Criteria;
import com.laptop.rfid_innotek2.model.EventHistory;
import com.laptop.rfid_innotek2.model.PageMaker;
import com.laptop.rfid_innotek2.service.AdmAgentService;
import com.laptop.rfid_innotek2.service.AdmMemberService;
import com.laptop.rfid_innotek2.service.CommonService;
import com.laptop.rfid_innotek2.service.EventHistoryService;

import lombok.extern.log4j.Log4j2;

@Log4j2
@Controller
public class EventHistoryController {

	@Autowired
	CommonService commonService;

	@Autowired
	AdmMemberService admMemberService;

	@Autowired
	EventHistoryService eventHistoryService;

	@Autowired
	AdmAgentService admAgentService;

	@GetMapping("/eventHistory/main")
	public String main(Model model, HttpServletResponse res) throws IOException { 
		String agent_id_str = commonService.getCookie("agent_id");

		if (commonService.nullCheck(agent_id_str)) {
			int agent_id = Integer.parseInt(agent_id_str);

			List<EventHistory> topHistoryList = new ArrayList<>();
			List<EventHistory> mainHistoryList = new ArrayList<>();

			String username = commonService.getCookie("username");
			if (username.equals("admin")) {
				// 슈퍼관리자 인경우 전체 보기
				topHistoryList = eventHistoryService.mainTopHistoryList();
				mainHistoryList = eventHistoryService.mainBottomHistoryList();
			} else {
				topHistoryList = eventHistoryService.mainTopHistoryList(agent_id);
				mainHistoryList = eventHistoryService.mainBottomHistoryList(agent_id);
			}

			model.addAttribute("topHistoryList", topHistoryList);
			model.addAttribute("mainHistoryList", mainHistoryList);
//			}

			return "page/main";
		} else {
			commonService.loginCheckLogic(res);
			return null;
		}

	}

	@GetMapping("/eventHistory/search1")
	public String search1(Model model,
			@PageableDefault(page = 0, size = 10, sort = "datetime", direction = Direction.DESC) Pageable pageable,
			String bizDeptCd, String result, String keyword, String sdate, String edate, Criteria cri,
			HttpServletResponse res) {

		String username = commonService.getCookie("username");
		String agent = commonService.getCookie("agent_id");
		if (commonService.nullCheck(agent)) {
			List<EventHistory> eventList = new ArrayList<>();
			int count = 0;
			if (bizDeptCd == null && result == null && keyword == null && sdate == null && edate == null) {
				PageMaker pageMaker = new PageMaker();
				pageMaker.setCri(cri);

				if (username.equals("admin")) {
					count = eventHistoryService.historyCount(null);
					eventList = eventHistoryService.historyList(cri, null);
				} else {
					count = eventHistoryService.historyCount(agent);
					eventList = eventHistoryService.historyList(cri, agent);
				}

				pageMaker.setTotalCount(count);

				model.addAttribute("searchYn", false);
				model.addAttribute("count", count);
				model.addAttribute("eventList", eventList);
				model.addAttribute("curPageNum", cri.getPage());
				model.addAttribute("pageMaker", pageMaker);
			} else {
				String queryString = "";
				if (bizDeptCd != null && bizDeptCd != "")
					queryString += "&bizDeptCd=" + bizDeptCd;
				if (result != null && result != "")
					queryString += "&result=" + result;
				if (keyword != null && keyword != "")
					queryString += "&keyword=" + keyword;
				if (sdate != null && sdate != "")
					queryString += "&sdate=" + sdate;
				if (edate != null && edate != "")
					queryString += "&edate=" + edate;

				PageMaker pageMaker = new PageMaker();
				pageMaker.setCri(cri);

				if (username.equals("admin")) {
					count = eventHistoryService.historySearchCount(bizDeptCd, result, keyword, sdate, edate, null);
					eventList = eventHistoryService.historySearchList(bizDeptCd, result, keyword, sdate, edate, cri,
							null);
				} else {
					count = eventHistoryService.historySearchCount(bizDeptCd, result, keyword, sdate, edate, agent);
					eventList = eventHistoryService.historySearchList(bizDeptCd, result, keyword, sdate, edate, cri,
							agent);
				}

				pageMaker.setTotalCount(count);
				model.addAttribute("searchYn", true);
				model.addAttribute("count", count);
				model.addAttribute("bizDeptCd", bizDeptCd);
				model.addAttribute("result", result);
				model.addAttribute("queryString", queryString);
				model.addAttribute("eventList", eventList);
				model.addAttribute("curPageNum", cri.getPage());
				model.addAttribute("pageMaker", pageMaker);

			}
			return "page/search1";
		} else {
			commonService.loginCheckLogic(res);
			return null;
		}

	}

	@GetMapping("/eventHistory/xrayContents")
	public String xrayContents(Model model, HttpServletResponse res) {
		String agent_id_str = commonService.getCookie("agent_id");
		if (commonService.nullCheck(agent_id_str)) {
			int agent_id = Integer.parseInt(agent_id_str);

			List<EventHistory> topHistoryList = new ArrayList<>();
			List<EventHistory> mainHistoryList = new ArrayList<>();

			String username = commonService.getCookie("username");
			if (username.equals("admin")) {
				// 슈퍼관리자 인경우 전체 보기
				topHistoryList = eventHistoryService.mainTopHistoryList();
				mainHistoryList = eventHistoryService.mainBottomHistoryList();
			} else {
				topHistoryList = eventHistoryService.mainTopHistoryList(agent_id);
				mainHistoryList = eventHistoryService.mainBottomHistoryList(agent_id);
			}
			model.addAttribute("topHistoryList", topHistoryList);
			model.addAttribute("mainHistoryList", mainHistoryList);
			return "page/xrayContents";
		} else {
			commonService.loginCheckLogic(res);
			return null;
		}

	}

	@GetMapping("/eventHistory/tableContents")
	public String tableContents(Model model, HttpServletResponse res) {
		String agent_id_str = commonService.getCookie("agent_id");
		if(commonService.nullCheck(agent_id_str)) {
			int agent_id = Integer.parseInt(agent_id_str);

			List<EventHistory> topHistoryList = new ArrayList<>();
			List<EventHistory> mainHistoryList = new ArrayList<>();

			String username = commonService.getCookie("username");
			if (username.equals("admin")) {
				// 슈퍼관리자 인경우 전체 보기
				topHistoryList = eventHistoryService.mainTopHistoryList();
				mainHistoryList = eventHistoryService.mainBottomHistoryList();
			} else {
				topHistoryList = eventHistoryService.mainTopHistoryList(agent_id);
				mainHistoryList = eventHistoryService.mainBottomHistoryList(agent_id);
			}
			model.addAttribute("topHistoryList", topHistoryList);
			model.addAttribute("mainHistoryList", mainHistoryList);
			return "page/tableContents";
		} else {
			commonService.loginCheckLogic(res);
			return null;
		}
		
	}

}
