package com.laptop.rfid_innotek2.service;

import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.laptop.rfid_innotek2.dto.LaptopSaveReqDto;
import com.laptop.rfid_innotek2.dto.LaptopSearchListInterface;
import com.laptop.rfid_innotek2.model.Criteria;
import com.laptop.rfid_innotek2.model.LaptopInfo;
import com.laptop.rfid_innotek2.model.User;
import com.laptop.rfid_innotek2.repository.LaptopInfoRepository;
import com.laptop.rfid_innotek2.repository.UserRepository;

@Service
public class LaptopInfoService {
	
	@Autowired
	LaptopInfoRepository laptopInfoRepository;
	
	@Autowired
	UserRepository userRepository;
	
	public Page<LaptopInfo> laptopList(Pageable pageable){
		return laptopInfoRepository.findAll(pageable); 
	}
	
	public LaptopInfo laptopByRfid(String tagName) {
		return laptopInfoRepository.findByRfid(tagName);
	} 
	
	@Transactional
	public void deleteLaptop(int id) {
		int userId = laptopInfoRepository.getUserId(id);
		userRepository.deleteById(userId);
	} 
	
	@Transactional
	public int saveLaptop(LaptopSaveReqDto laptopDto) {
		int result = 0; 
		List<User> userList = new ArrayList<>();
		String userNo = laptopDto.getUserNo();
		String dbUserNo = "";
		User dbUser = userRepository.findByUserNo(userNo);
		if(dbUser != null) {
			dbUserNo = dbUser.getUserNo();
		}
		if(userNo.equals(dbUserNo)) {
			result = 0;
		} else {
			// 중복 되는 사번이 아닌경우
			User user = User.builder()
					.userNo(userNo)
					.username(laptopDto.getUsername())
					.userPart(laptopDto.getUserPart())
					.userPos(laptopDto.getUserPos())
					.build(); 
			userRepository.save(user);

			LaptopInfo laptop = LaptopInfo.builder()
					.asset(laptopDto.getAsset())
					.barcode(laptopDto.getBarcode())
					.rfid(laptopDto.getRfid())
					.serial(laptopDto.getSerial()) 
					.user(user)
					.build(); 
			laptopInfoRepository.save(laptop);
			result = 1;
		}
		
		return result;
	}
	
	@Transactional
	public int updateLaptop(LaptopSaveReqDto laptopDto, int laptopId) {
		int result = 0;
		LaptopInfo laptop = laptopInfoRepository.findById(laptopId).get();
		if(laptop != null) {
			User user = laptop.getUser();
			
			laptop.setAsset(laptopDto.getAsset());
			laptop.setBarcode(laptopDto.getBarcode());
			laptop.setRfid(laptopDto.getRfid());
			laptop.setSerial(laptopDto.getSerial());
			
			user.setUsername(laptopDto.getUsername());
			user.setUserPart(laptopDto.getUserPart());
			user.setUserPos(laptopDto.getUserPos());
			
			result = 1;
			
		}
		return result;
	}
	
	@Transactional
	public int updateLaptopByUserNo(LaptopSaveReqDto laptopDto, String userNo) {
		int result = 0;
		User user = userRepository.findByUserNo(userNo);
		
		if(user != null) {  
			int userId = user.getId();
			LaptopInfo laptop = laptopInfoRepository.findByUserId(userId);             
			laptop.setAsset(laptopDto.getAsset());
			laptop.setBarcode(laptopDto.getBarcode());
			laptop.setRfid(laptopDto.getRfid());
			laptop.setSerial(laptopDto.getSerial());
			
			user.setUsername(laptopDto.getUsername());
			user.setUserPart(laptopDto.getUserPart());
			user.setUserPos(laptopDto.getUserPos()); 
			result++;
			
		}
		return result;
	}
	
	@Transactional
	public int laptopCount() {
		return (int)laptopInfoRepository.count();
	}
	
	@Transactional
	public List<LaptopInfo> laptopList(Criteria cri){
		return laptopInfoRepository.page(cri.getPageStart(), cri.getPerPageNum());
	}
	
	@Transactional
	public List<LaptopSearchListInterface> laptopList(){
		return laptopInfoRepository.findAllByOrderByDatetimeDesc();
	}
	
	@Transactional
	public int laptopSearchCount(String keyword) {
		return (int)laptopInfoRepository.findBySearchCount(keyword);
	}
	
	@Transactional
	public List<LaptopSearchListInterface> laptopSearch(String keyword, Criteria cri){
		return laptopInfoRepository.findBySearch(keyword, cri.getPageStart(), cri.getPerPageNum());
	}
	
	@Transactional
	public List<LaptopSearchListInterface> laptopSearch(String keyword){
		return laptopInfoRepository.findBySearch(keyword);
	}

}
