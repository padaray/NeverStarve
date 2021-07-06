package com.NeverStarve.store.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.NeverStarve.store.service.ReportService;

 
@Controller
@RequestMapping("/store")
public class ReportController {
	@Autowired ReportService reportService;
	
//	public class String2LocalDateConverter implements Converter<String, LocalDate> {
//	    @Override
//	    public LocalDate convert(String s) {
//	        DateTimeFormatter fmt = DateTimeFormatter.ofPattern("yyyy-MM-dd");
//	        return LocalDate.parse(s, fmt);
//	    }
//	}
	
//	private static final Random RANDOM = new Random(System.currentTimeMillis());
	
	@GetMapping("/testpiechart")
	public String testpiechart (Model model) {
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
	
//	private StoreBean getpkStoreBean(HttpSession session) {//回傳對應的店家ID，從session拿到
//		
//		StoreBean mystorebean=(StoreBean) session.getAttribute("storeUser");
//		return mystorebean;
//	}
	
//	@GetMapping("/allquanty")
//	@ResponseBody
//	public Map<String,Integer> getOrderListAllQuanty(HttpSession session){
//		
//		StoreBean mystorebean=(StoreBean) session.getAttribute("storeUser");
//		
//		System.out.println(mystorebean.getStoreName());
//		
//		System.out.println(reportService.getQuantity(mystorebean));
//		
//		return reportService.getQuantity(mystorebean);
//		
//	}
	
}