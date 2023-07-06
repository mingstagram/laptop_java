package com.laptop.rfid_innotek2.controller;

import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.laptop.rfid_innotek2.dto.LaptopSearchListInterface;
import com.laptop.rfid_innotek2.model.Criteria;
import com.laptop.rfid_innotek2.model.LaptopInfo;
import com.laptop.rfid_innotek2.model.PageMaker;
import com.laptop.rfid_innotek2.service.CommonService;
import com.laptop.rfid_innotek2.service.LaptopInfoService;
import com.laptop.rfid_innotek2.util.PageModule;

@Controller
public class LaptopInfoController {

	@Autowired
	LaptopInfoService laptopInfoService;

	@Autowired
	CommonService commonService;

	@GetMapping("/laptopInfo/search2")
	public String search2(Model model,
			@PageableDefault(page = 0, size = 10, sort = "datetime", direction = Direction.DESC) Pageable pageable,
			Criteria cri, String keyword, HttpServletResponse res) {
		String agent_id_str = commonService.getCookie("agent_id");
		if (commonService.nullCheck(agent_id_str)) {
			if (keyword != null && !keyword.equals("")) {
				PageMaker pageMaker = new PageMaker();
				int count = laptopInfoService.laptopSearchCount(keyword);
				pageMaker.setCri(cri);
				pageMaker.setTotalCount(count);
				List<LaptopSearchListInterface> laptopList = laptopInfoService.laptopSearch(keyword, cri);
				model.addAttribute("search", keyword);
				model.addAttribute("count", count);
				model.addAttribute("laptopList", laptopList);
				model.addAttribute("curPageNum", cri.getPage());
				model.addAttribute("pageMaker", pageMaker);
			} else {
				PageMaker pageMaker = new PageMaker();
				int count = laptopInfoService.laptopCount();
				pageMaker.setCri(cri);
				pageMaker.setTotalCount(count);
				List<LaptopInfo> laptopList = laptopInfoService.laptopList(cri);
				model.addAttribute("search", keyword);
				model.addAttribute("count", count);
				model.addAttribute("laptopList", laptopList);
				model.addAttribute("curPageNum", cri.getPage());
				model.addAttribute("pageMaker", pageMaker);
			}

			return "page/search2";
		} else {
			commonService.loginCheckLogic(res);
			return null;
		}

	}

}
