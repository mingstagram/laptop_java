package com.laptop.rfid_innotek2.dto;
 

import org.springframework.data.jpa.domain.Specification;

import com.laptop.rfid_innotek2.model.EventHistory;

public class EventHistorySpecification {
	public static Specification<EventHistory> equalsBizDeptCd(String bizDeptCd){
		return (root, query, CriteriaBuilder) -> CriteriaBuilder.equal(root.get("bizDeptCd"),bizDeptCd);
	}
	
	public static Specification<EventHistory> equalsResult(String result){
		return (root, query, CriteriaBuilder) -> CriteriaBuilder.equal(root.get("result"),result);
	}
	
	public static Specification<EventHistory> betweenDatetime(String s_datetime, String e_datetime) {
        return (root, query, CriteriaBuilder) -> CriteriaBuilder.between(root.get("datetime"),s_datetime, e_datetime);
    }
	
	public static Specification<EventHistory> equalsUsername(String keyword) {
        return (root, query, CriteriaBuilder) -> CriteriaBuilder.like(root.get("username"),"%" + keyword + "%");
    }
	
	public static Specification<EventHistory> equalsBarcode(String keyword) {
        return (root, query, CriteriaBuilder) -> CriteriaBuilder.like(root.get("barcode"),"%" + keyword + "%");
    }
	
	public static Specification<EventHistory> equalsUserNo(String keyword) {
        return (root, query, CriteriaBuilder) -> CriteriaBuilder.like(root.get("userNo"),"%" + keyword + "%");
    }

}
