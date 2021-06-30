package com.NeverStarve.orders.controller;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.NeverStarve.member.model.MemberBean;
import com.NeverStarve.member.service.MemberService;
import com.NeverStarve.orders.model.OrderBean;
import com.NeverStarve.orders.model.OrderListBean;
import com.NeverStarve.orders.model.ShoppingCar;
import com.NeverStarve.orders.service.OrderListService;
import com.NeverStarve.orders.service.OrderService;
import com.NeverStarve.store.model.MenuBean;
import com.NeverStarve.store.service.MenuService;

@Controller
@RequestMapping("/Order")
public class OrderController {
	@Autowired
	OrderService orderservice;
	@Autowired
	MenuService menuService;
	@Autowired
	MemberService memberService;
	@Autowired
	OrderListService orderListService;
	
	private String productIDName = "productIDName";
	private String productQuantityName = "productQuantityName";

	@GetMapping("/getOrderById/{id}")
	public Optional<OrderBean> getOrderById(@PathVariable int id) {

		return orderservice.findByPkOrderId(id);
	}

	@PostMapping(value = "/saveShoppingCar",
			// 指定回傳內容一定要為JSON
			consumes = MediaType.APPLICATION_JSON_VALUE)
	// 讓前端送回JSON給後端可以用@RequestBody
	@ResponseBody
	public void saveShoppingCar(@RequestBody List<ShoppingCar> car, 
			HttpServletResponse response,HttpServletRequest request) {
		// 設定一個變數去接購物車的產品ID
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
		// 建立一個cookie物件，並把購物車Bean裡的ID放進Cookie
		Cookie carProductID = new Cookie(this.productIDName, productID);
		Cookie carProductQuantity = new Cookie(this.productQuantityName, productQuantity);
		// 設定cookie的存活時間
		carProductID.setMaxAge(2 * 60 * 60);
		carProductQuantity.setMaxAge(2 * 60 * 60);
		carProductID.setPath(request.getContextPath());
		carProductQuantity.setPath(request.getContextPath());
		response.addCookie(carProductID);
		response.addCookie(carProductQuantity);
	}

	// 0621完成導向訂單的頁面
	@GetMapping("/order")
	public String order() {
		return "order/order";
	}

	// 0621取得購物車內的商品資訊
	@GetMapping("/getShoppingCar")
	@ResponseBody
	public List<MenuBean> getShoppingCar(HttpServletRequest request) {
		// 將Cookie的內容拿出來
		Cookie[] cookieList = request.getCookies();
		if (cookieList != null) {
			String productID = null;
			String productQuantity = null;
			for (Cookie cookie : cookieList) {

				if (cookie.getName().equals(this.productIDName)) {
					productID = cookie.getValue();
				}

				if (cookie.getName().equals(this.productQuantityName)) {
					productQuantity = cookie.getValue();
				}

			}
			if (productID.contains("_")) {
				List<MenuBean> manyProducts = orderservice.getOrderList(productID.split("_"));
				for (int i = 0; i < manyProducts.size(); i++) {
					manyProducts.get(i).setQuantity(Integer.valueOf(productQuantity.split("_")[i]));

				}

				return manyProducts;

			} else {
				List<MenuBean> oneProductList = new ArrayList<MenuBean>();
				MenuBean oneProduct = menuService.getMenuById(Integer.valueOf(productID));
				oneProduct.setQuantity(Integer.valueOf(productQuantity));
				oneProductList.add(oneProduct);
				return oneProductList;
			}

		}

		return null;
	}

	// 0623刪除購物車餅乾內對應的食物
	@GetMapping("/deleteProduct/{id}")
	// 先取出購物車餅乾裡對應的食物，在寫一個刪除後的餅乾回去
	public void deleteCookieMenuById(@PathVariable String id, 
			HttpServletRequest request,
			HttpServletResponse response) {
		Cookie[] cookieList = request.getCookies();
		if (cookieList != null) {
			String productID = null;
			String productQuantity = null;
			for (Cookie cookie : cookieList) {

				if (cookie.getName().equals(this.productIDName)) {
					productID = cookie.getValue();
				}

				if (cookie.getName().equals(this.productQuantityName)) {
					productQuantity = cookie.getValue();
				}
			}
			String[] productIDArray = productID.split("_");
			String[] productQuantityArray = productQuantity.split("_");			
			String productNewID = "";
			String productNewQuantity = "";
			for (int i = 0; i < productIDArray.length; i++) {
				if (!productIDArray[i].equals(id)) {
					if (i != (productIDArray.length - 2)) {
						productNewID += productIDArray[i] + "_";
						productNewQuantity += productQuantityArray[i] + "_";
					} else {
						productNewID += productIDArray[i];
						productNewQuantity += productQuantityArray[i];
					}

				}
			}
			// 前面名稱，後面是值
			Cookie carProductID = new Cookie(this.productIDName, productNewID);
			Cookie carProductQuantity = new Cookie(this.productQuantityName, productNewQuantity);
			// 設定cookie的存活時間
			carProductID.setMaxAge(2 * 60 * 60);
			carProductQuantity.setMaxAge(2 * 60 * 60);
			carProductID.setPath(request.getContextPath());
			carProductQuantity.setPath(request.getContextPath());
			response.addCookie(carProductID);
			response.addCookie(carProductQuantity);
		}

	}
	
	@PostMapping(value = "/saveOrder/{addres}",
				consumes = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public void saveOrder(@RequestBody List<OrderListBean> orderListBeanList,
						@PathVariable String addres,
						HttpServletRequest request,
						HttpServletResponse response) {
		for(int i = 0; i<orderListBeanList.size(); i++) {
			orderListBeanList.get(i).setMenuBean(menuService.getMenuById(orderListBeanList.get(i).getMenuID()));
		}
		
		OrderBean orderBean = new OrderBean();
		orderBean.setStoreBean(orderListBeanList.get(0).getMenuBean().getStoreBean());
		Cookie[] cookieList = request.getCookies();
		Cookie productIDCookie = null;
		Cookie productQuantityCookie = null;
		if (cookieList != null) {
			for (Cookie cookie : cookieList) {
				if(cookie.getName().equals("userId")) {
					orderBean.setMemberBean(memberService.getMamberById(Integer.valueOf(cookie.getValue())).get());
				}
				//找到我要找的餅乾
				if(cookie.getName().equals(productIDName)) {
					productIDCookie = cookie;
				}
				if(cookie.getName().equals(productQuantityName)) {
					productQuantityCookie = cookie;
				}
				
			}
		}
		//用後端把總價送回前端
		Double alltotal=0.0;
		for( OrderListBean i:orderListBeanList) {
			//取得總價
		 	alltotal += i.getQuantity()*i.getMenuBean().getDishPrice();
		}
		orderBean.setTotalCost(alltotal);
		orderBean.setOrderDate(LocalDate.now());
		orderBean.setShipping_address(addres);
		
		if(orderservice.saveOrderBeanAndOrderList(orderBean, orderListBeanList)) {
			productIDCookie.setMaxAge(0);
			productQuantityCookie.setMaxAge(0);	
			productIDCookie.setPath(request.getContextPath());
			productQuantityCookie.setPath(request.getContextPath());
			response.addCookie(productIDCookie);
			response.addCookie(productQuantityCookie);
		}
	
		
	}
	
	@GetMapping(value = "/getaddresByID",
			consumes = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public Optional<MemberBean> getMaddresById(HttpServletRequest request) {
		Cookie[] cookieList = request.getCookies();
		if (cookieList != null) {
			for (Cookie cookie : cookieList) {
				if(cookie.getName().equals("userId")) {
					return	memberService.getMamberById(Integer.valueOf(cookie.getValue()));
				}
			}
		}
		return null;
	}
	
}
