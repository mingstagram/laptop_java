package com.laptop.rfid_innotek2.util; 

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.laptop.rfid_innotek2.model.EventHistory;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PageModule {
	
	private int nowPage; 
	private int endPage;
	private int startPage;
	private int totalPage;
	
	public void pageSetting(Page<?> list, int displayPage) { 
		this.nowPage = list.getPageable().getPageNumber()+1;
		this.endPage = (int) (Math.ceil(nowPage/(double) displayPage) * displayPage);
		this.startPage = (endPage - displayPage) + 1;
		this.totalPage = list.getTotalPages(); 
		
		if(this.endPage > this.totalPage) {
			this.endPage = this.totalPage;
		}
		
	} 

}
