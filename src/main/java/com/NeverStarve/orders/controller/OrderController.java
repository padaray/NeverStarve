package com.NeverStarve.orders.controller;

import java.util.List;
import java.util.Optional;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import com.NeverStarve.orders.model.OrderBean;
import com.NeverStarve.orders.model.ShoppingCar;
import com.NeverStarve.orders.service.OrderService;

@Controller
@RequestMapping("/Order")
public class OrderController {
	@Autowired
	OrderService orderservice;

	@GetMapping("/getOrderById/{id}")
	public Optional<OrderBean> getOrderById(@PathVariable int id) {

		return orderservice.findByPkOrderId(id);
	}

	@PostMapping(value = "/saveShoppingCar",
			// 指定回傳內容一定要為JSON
			consumes = MediaType.APPLICATION_JSON_VALUE)
	// 讓前端送回JSON給後端可以用@RequestBody

	public void saveShoppingCar(@RequestBody List<ShoppingCar> car, HttpServletResponse response) {
		String productID = "";
		String productQuantity = "";
		for (int i = 0; i < car.size(); i++) {

			if (i != car.size() - 1) {
				productID += car.get(i).getProductID() + "_";
				productQuantity += car.get(i).getProductQuantity() + "_";
			} else {
				productID += car.get(i).getProductID();
				productQuantity += car.get(i).getProductQuantity();
			}
		}
		
		Cookie carProductID = new Cookie("productID", productID);
		Cookie carProductQuantity = new Cookie("productQuantity",productQuantity);
		carProductID.setMaxAge(2*60*60);
		carProductQuantity.setMaxAge(2*60*60);
		response.addCookie(carProductID);
		response.addCookie(carProductQuantity);
	}
	
	

}
