package com.NeverStarve.member.model;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PageVo<T> {
	
	private int currentPage;
	private int totalPage;
	private List<T> data;

	
	
	


}