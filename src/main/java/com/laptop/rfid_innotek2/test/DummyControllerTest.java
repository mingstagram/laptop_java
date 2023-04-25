package com.laptop.rfid_innotek2.test;

import java.util.List;
import java.util.function.Supplier;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.web.PageableDefault;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.laptop.rfid_innotek2.model.AdmAgent; 
import com.laptop.rfid_innotek2.repository.AdmAgentRepository;

@RestController
public class DummyControllerTest {
	
	@Autowired // 의존성 주입(DI)
	private AdmAgentRepository admAgentRepository;
	
	@DeleteMapping("/dummy/agent/{id}")
	public String deleteAgent(@PathVariable int id) {
		try {
			admAgentRepository.deleteById(id);
		} catch (EmptyResultDataAccessException e) {
			return "삭제 실패 : " + e.getMessage();
		} 
		return "삭제 완료";
	}
	
	@Transactional
	@PutMapping("/dummy/agent/{id}")
	public AdmAgent updateAgent(@PathVariable int id, @RequestBody AdmAgent requestAgent) { 
		AdmAgent agent = admAgentRepository.findById(id).orElseThrow(()->{
			return new IllegalArgumentException("수정 실패.");
		});
		 
		
		requestAgent.setId(id);
//		admAgentRepository.save(requestAgent);
		
		return agent;
	}
	
	@GetMapping("/dummy/agents")
	public List<AdmAgent> list(){
		return admAgentRepository.findAll();
	}
	
	@GetMapping("/dummy/agent")
	public List<AdmAgent> pageList(@PageableDefault(size = 2, sort = "id", direction = Sort.Direction.DESC) Pageable pageable){
		Page<AdmAgent> pagingAgent =  admAgentRepository.findAll(pageable);
		
		List<AdmAgent> agents = pagingAgent.getContent();
		
		return agents;
	}
	
	// {id} 주소를 파라미터로 전달 받을 수 있음.
	@GetMapping("/dummy/agent/{id}")
	public AdmAgent detail(@PathVariable int id) {
		AdmAgent agent = admAgentRepository.findById(id).orElseThrow(() -> {
			return new IllegalArgumentException("해당 유저는 없습니다. id : " + id);
		});
		return agent;
	}
	
	@PostMapping("/dummy/join")
	public String join(AdmAgent agent) {
		System.out.println(agent);
		admAgentRepository.save(agent);
		return "저장완료";
	}

}