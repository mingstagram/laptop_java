package com.laptop.rfid_innotek2.service;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.laptop.rfid_innotek2.model.LogCon;
import com.laptop.rfid_innotek2.repository.LogConRepository;

@Service
public class LogConService {
	
	@Autowired
	LogConRepository logConRepository;
	
	@PersistenceContext
	EntityManager em;
	
	public List<LogCon> logConList(String agent){
		String Query = "SELECT e FROM LogCon e WHERE 1 = 1";
		if(agent != null) Query += " AND agent=" + agent;
		Query += " ORDER BY DATETIME DESC";
		TypedQuery<LogCon> typeQuery = em.createQuery(Query, LogCon.class); 
		List<LogCon> resultList = typeQuery.getResultList();
		return resultList;
	}
	
	@Transactional
	public void save(LogCon log) {
		logConRepository.save(log);
	}

}
