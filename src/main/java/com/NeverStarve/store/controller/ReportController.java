package com.NeverStarve.store.controller;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
//import java.util.Random;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cglib.core.Converter;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.NeverStarve.orders.model.OrderBean;
import com.NeverStarve.orders.model.OrderListBean;
import com.NeverStarve.store.model.StoreBean;
import com.NeverStarve.store.repository.ReportRepository;
import com.NeverStarve.store.service.StoreService;



@Controller
@RequestMapping("/Report")
public class ReportController {
	@Autowired ReportRepository reportRepository;
	
//	public class String2LocalDateConverter implements Converter<String, LocalDate> {
//	    @Override
//	    public LocalDate convert(String s) {
//	        DateTimeFormatter fmt = DateTimeFormatter.ofPattern("yyyy-MM-dd");
//	        return LocalDate.parse(s, fmt);
//	    }
//	}
	
//	private static final Random RANDOM = new Random(System.currentTimeMillis());
	@GetMapping("/testpiechart")
	public String test (Model model) {
		model.addAttribute("chartData", getChartData());
		
	return "report/piechart";
	}
	
	private List<List<Object>> getChartData() { 
	        return List.of(
	                List.of("滷肉飯",1),
	                List.of("Onions", 1),
	                List.of("Olives", 1),
	                List.of("Zucchini", 1),
	                List.of("Pepperoni", 1)
	        );
	}
	
	private Integer getpkStoreId(HttpSession session) {
		
		StoreBean mystorebean=(StoreBean) session.getAttribute("storeUser");
		return mystorebean.getPkStoreId();
	}
	
	@PostMapping("/quanty")
	@ResponseBody
	public List<OrderListBean> getQuanty(Integer pkStoreId,HttpSession session,LocalDate date){
		
		
		Integer pkStoreId=getpkStoreId(session); //addquantity
		
		mystorebean.get
		
		return reportRepository.getquantity(pkStoreId,date);
		
	}
	
}