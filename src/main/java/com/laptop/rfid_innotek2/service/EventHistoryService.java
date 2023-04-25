package com.laptop.rfid_innotek2.service;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import com.laptop.rfid_innotek2.dto.EventHistoryListInterface;
import com.laptop.rfid_innotek2.dto.EventHistorySearchReqDto;
import com.laptop.rfid_innotek2.dto.EventHistorySpecification;
import com.laptop.rfid_innotek2.model.Criteria;
import com.laptop.rfid_innotek2.model.EventHistory;
import com.laptop.rfid_innotek2.repository.EventHistoryRepository;

@Service
public class EventHistoryService {
	
	@Autowired
	EventHistoryRepository eventHistoryRepository;
	
	@PersistenceContext
	EntityManager em;
	
	public List<EventHistory> mainTopHistoryList(){
		return eventHistoryRepository.historyTop2List();
	}
	
	public List<EventHistory> mainBottomHistoryList(){
		return eventHistoryRepository.historyTop30List();
	}
	
	public List<EventHistory> mainTopHistoryList(int agent){
		return eventHistoryRepository.historyTop2List(agent);
	}
	
	public List<EventHistory> mainBottomHistoryList(int agent){
		return eventHistoryRepository.historyTop30List(agent);
	}
	
	public List<EventHistory> findAll(){
		return eventHistoryRepository.findAll();
	}
	
	public Page<EventHistory> findAll(Pageable pageable) {
		return eventHistoryRepository.findAll(pageable);
	} 
	
	public void saveHistory(EventHistory history) {
		eventHistoryRepository.save(history);
	}
	
	public int historyCount(String agent) {
		String Query = "SELECT e FROM EventHistory e WHERE 1 = 1";
		if(agent != null) Query += " AND agent=" + agent;
		
		return em.createQuery(Query).getResultList().size();
	}
	
	public List<EventHistory> historyList(Criteria cri, String agent){
		String Query = "SELECT e FROM EventHistory e WHERE 1 = 1";
		if(agent != null) Query += " AND agent=" + agent;
		TypedQuery<EventHistory> typeQuery = em.createQuery(Query, EventHistory.class); 
		List<EventHistory> resultList = typeQuery.setFirstResult(cri.getPageStart()).setMaxResults(cri.getPerPageNum()).getResultList();
		
		return resultList;
	}
	
	public List<EventHistory> historyList(){
		return eventHistoryRepository.findAllByOrderByDatetimeDesc();
	}
	 
	public int historySearchCount(String bizDeptCd, String result, String keyword, String s_datetime, String e_datetime, String agent) { 
		String Query = "SELECT id, agent, userNo, username, barcode, rfid, bizDeptCd, datetime, result FROM EventHistory WHERE 1 = 1";

		if(bizDeptCd != "" && bizDeptCd != null) Query += "AND bizDeptCd = "+bizDeptCd;
		if(result != "" && result != null) {
			if(result.equals("Y") || result.equals("S")) {
				Query += "AND (result = 'Y' OR result = 'S') ";
			} else {
				Query += "AND result = 'N' ";
			}
		}
		if(keyword != "" && keyword != null) Query += "AND (barcode like '%"+keyword+"%' OR userNo like '%"+keyword+"%' OR username like '%"+keyword+"%')";
		if(s_datetime != "" && s_datetime != null) {
			if(e_datetime != "" && e_datetime != null) {
				Query += "AND (DATE(datetime) BETWEEN '" + s_datetime + " 00:00:00' AND '" + e_datetime + " 23:59:59')";
			}
		}
		if(agent != null) Query += "AND agent=" + agent;
		
		return em.createQuery(Query).getResultList().size();
	} 
	
	public List<EventHistory> historySearchList(String bizDeptCd, String result, String keyword, String s_datetime, String e_datetime, Criteria cri, String agent) { 
		String Query = "SELECT e FROM EventHistory e WHERE 1 = 1";

		if(bizDeptCd != "" && bizDeptCd != null) Query += "AND bizDeptCd = "+bizDeptCd;
		if(result != "" && result != null) {
			if(result.equals("Y") || result.equals("S")) {
				Query += "AND (result = 'Y' OR result = 'S') ";
			} else {
				Query += "AND result = 'N' ";
			}
		}
		if(keyword != "" && keyword != null) Query += "AND (barcode like '%"+keyword+"%' OR userNo like '%"+keyword+"%' OR username like '%"+keyword+"%')";
		if(s_datetime != "" && s_datetime != null) {
			if(e_datetime != "" && e_datetime != null) {
				Query += "AND (DATE(datetime) BETWEEN '" + s_datetime + " 00:00:00' AND '" + e_datetime + " 23:59:59')";
			}
		}
		if(agent != null) Query += "AND agent=" + agent;
		Query += " ORDER BY datetime DESC"; 
		TypedQuery<EventHistory> typeQuery = em.createQuery(Query, EventHistory.class); 
		List<EventHistory> resultList = typeQuery.setFirstResult(cri.getPageStart()).setMaxResults(cri.getPerPageNum()).getResultList();

		return resultList;
	}
	
	public List<EventHistory> historySearchList(String bizDeptCd, String result, String keyword, String s_datetime, String e_datetime) { 
		String Query = "SELECT e FROM EventHistory e WHERE 1 = 1";

		if(bizDeptCd != "" && bizDeptCd != null) Query += "AND bizDeptCd = "+bizDeptCd;
		if(result != "" && result != null) {
			if(result.equals("Y") || result.equals("S")) {
				Query += "AND (result = 'Y' OR result = 'S') ";
			} else {
				Query += "AND result = 'N' ";
			}
		}
		if(keyword != "" && keyword != null) Query += "AND (barcode like '%"+keyword+"%' OR userNo like '%"+keyword+"%' OR username like '%"+keyword+"%')";
		if(s_datetime != "" && s_datetime != null) {
			if(e_datetime != "" && e_datetime != null) {
				Query += "AND (DATE(datetime) BETWEEN '" + s_datetime + " 00:00:00' AND '" + e_datetime + " 23:59:59')";
			}
		}
		Query += " ORDER BY datetime DESC"; 
		TypedQuery<EventHistory> typeQuery = em.createQuery(Query, EventHistory.class); 
		List<EventHistory> resultList = typeQuery.getResultList();

		return resultList;
	}

//	public Page<EventHistory> getHistorySearch(String bizDeptCd, 
//			String result_temp, String keyword, String s_datetime, 
//			String e_datetime, Pageable pageable) {
//		
//		Specification<EventHistory> spec = (root, query, criteriaBuilder) -> null;
//		
//		if(bizDeptCd != null || bizDeptCd != "") spec = spec.and(EventHistorySpecification.equalsBizDeptCd(bizDeptCd));
//		if(result_temp != null || result_temp != "") spec = spec.and(EventHistorySpecification.equalsResult(result_temp));
//		if(keyword != null || keyword != "") spec = spec.and(EventHistorySpecification.equalsBarcode(keyword));
//		if(keyword != null || keyword != "") spec = spec.and(EventHistorySpecification.equalsUsername(keyword));
//		if(keyword != null || keyword != "") spec = spec.and(EventHistorySpecification.equalsUserNo(keyword));
////		if(s_datetime != null && e_datetime != null || s_datetime != ""  && e_datetime != "") spec = spec.and(EventHistorySpecification.betweenDatetime(s_datetime, e_datetime));
//		
//		return eventHistoryRepository.findAll(spec, pageable);
//	}
	
//	public Page<EventHistory> historyList(String keyword, Pageable pageable){
//		return eventHistoryRepository.findEventHistoryByUname(keyword, pageable);
//	}

}
