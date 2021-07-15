package com.NeverStarve.orders.controller;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import javax.servlet.ServletContext;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.NeverStarve.member.model.MemberBean;
import com.NeverStarve.member.service.MemberService;
import com.NeverStarve.orders.model.EcPayBean;
import com.NeverStarve.orders.model.OrderBean;
import com.NeverStarve.orders.model.OrderListBean;
import com.NeverStarve.orders.model.ShoppingCar;
import com.NeverStarve.orders.service.OrderListService;
import com.NeverStarve.orders.service.OrderService;
import com.NeverStarve.store.model.MenuBean;
import com.NeverStarve.store.service.MenuService;

import ecpay.payment.integration.AllInOne;
import ecpay.payment.integration.domain.AioCheckOutALL;

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
	@Autowired
    HttpSession session;
	@Autowired
	ServletContext context;
	
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
				
				return  manyProducts;

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
	//送出訂單
	@PostMapping(value = {"/saveOrder/{addres}","/saveOrder"},
				consumes = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public void saveOrder(@RequestBody List<OrderListBean> orderListBeanList,
						@PathVariable(value = "addres",required = false) String addres,
						HttpServletRequest request,
						HttpServletResponse response) {
		OrderBean orderBean = new OrderBean();
		Cookie[] cookieList = request.getCookies();
		Cookie productIDCookie = null;
		Cookie productQuantityCookie = null;
		Cookie memberCookie = null;
		if (cookieList != null) {
			for (Cookie cookie : cookieList) {
				if(cookie.getName().equals("userId")) {
					memberCookie = cookie;
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
		if(memberCookie != null) {
			if(addres == null) {
				addres = "";
			}
			for(int i = 0; i<orderListBeanList.size(); i++) {
				orderListBeanList.get(i).setMenuBean(menuService.getMenuById(orderListBeanList.get(i).getMenuID()));
			}
			
			orderBean.setStoreBean(orderListBeanList.get(0).getMenuBean().getStoreBean());
			//用後端把總價送回前端
			Double alltotal=0.0;
			for( OrderListBean i:orderListBeanList) {
				//取得總價
			 	alltotal += i.getQuantity()*i.getMenuBean().getDishPrice();
			}
			orderBean.setTotalCost(alltotal);
			orderBean.setOrderDate(LocalDateTime.now().withNano(0));
			orderBean.setShipping_address(addres);
			orderBean.setTrading(0);
			orderBean.setConfirm(0);
			String memberkey = memberCookie.getValue();
			System.out.println("87878787"+memberkey);
			 Optional<MemberBean> memberBean = memberService.getMamberById(Integer.valueOf(memberkey));
			 memberService.sendSimpleMail(memberBean.get().getEmail(), "[NeverStarve通知] 訂單建立成功囉!", "您的訂單已經建立成功");
			if(orderservice.saveOrderBeanAndOrderList(orderBean, orderListBeanList)) {
				productIDCookie.setMaxAge(0);
				productQuantityCookie.setMaxAge(0);	
				productIDCookie.setPath(request.getContextPath());
				productQuantityCookie.setPath(request.getContextPath());
				response.addCookie(productIDCookie);
				response.addCookie(productQuantityCookie);
			}
		
		}

		
	}
	
	//用會員ID找到會員的地址
	@GetMapping(value = "/getaddresByID",
			consumes = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public Optional<MemberBean> getMaddresById(HttpServletRequest request ) {
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
	
	//發送請求給綠界
	@PostMapping(value="/toPayECpay",
				consumes = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public EcPayBean  aioCheckOutALL(HttpServletRequest request) {
		AioCheckOutALL aio = new AioCheckOutALL();
		AllInOne aioOne = new AllInOne("");
		EcPayBean ecpay = new EcPayBean();
		Cookie[] cookieList = request.getCookies();
		String memberCookieID = null;
		if (cookieList != null) {
			for (Cookie cookie : cookieList) {
				if(cookie.getName().equals("userId")) {
					memberCookieID = cookie.getValue();
				}
			}
		}
		Optional<MemberBean> memberID = memberService.getMamberById(Integer.valueOf(memberCookieID));
		Optional<OrderBean> orderBean = orderservice.getNewestOrderByMember(memberID.get());
		aio.setMerchantID("2000132");
		aio.setMerchantTradeNo("NeverStarve"+String.valueOf(orderBean.get().getPkOrderId()));
	    aio.setMerchantTradeDate(String.valueOf(orderBean.get().getOrderDate()).substring(0,19).replace("T", " ").replace("-", "/"));
	    aio.setTotalAmount(String.valueOf(orderBean.get().getTotalCost().intValue()));
		aio.setTradeDesc("test shopping");
		aio.setItemName(orderBean.get().getItemName());
		aio.setReturnURL("http://localhost:9527/NeverStarve/Order/returnURL");
		aio.setOrderResultURL("http://localhost:9527/NeverStarve/Order/order/EcpayOrder");
		ecpay.setHi(aioOne.aioCheckOut(aio,null));
		return ecpay ;
	}

		//0707訂單的展示
		@GetMapping("order/NowOrder")
		public String getNowOrder(Model model,
				@CookieValue(value = "userId") String userid) {
			
			MemberBean m =null ;
			m = memberService.getMamberById(Integer.valueOf(userid)).get();
			if (m!=null) {
				List<OrderBean> order = orderservice.findOrderByMemberBean(m);
				model.addAttribute("id", m);
				model.addAttribute("orderSet",order);
			}
			return "order/OrderMember" ;
		}
		//0709給綠界的
		@PostMapping("order/EcpayOrder")
		public String getEcpayOrder(Model model,
				@RequestParam("RtnCode") int RtnCode,
				@RequestParam("MerchantTradeNo") String MerchantTradeNo,
				HttpServletResponse response,
				HttpServletRequest request) {
			MemberBean member =(MemberBean) session.getAttribute("member");	
			MemberBean memberCookie =null ;
			String userid = null;
			Cookie[] cookieList = request.getCookies();
			
			if (cookieList != null) {
				for (Cookie cookie : cookieList) {
					if(cookie.getName().equals("userId")) {
						userid = cookie.getValue();
					}
				}
			}
			if(userid != null) {
				
				memberCookie = memberService.getMamberById(Integer.valueOf(userid)).get();
			}
			if (memberCookie!=null) {
				System.out.println("08是餅乾啦耖");
				List<OrderBean> order = orderservice.findOrderByMemberBean(memberCookie);
				model.addAttribute("id", memberCookie);
				model.addAttribute("orderSet",order);
			}else if(member!=null) {
				System.out.println("08是Session啦");
				List<OrderBean> order = orderservice.findOrderByMemberBean(member);
				model.addAttribute("id", member);
				model.addAttribute("orderSet",order);
			}else {
				System.out.println("08是死人啦");
				member = memberService.getMamberById(1).get();
				List<OrderBean> order = orderservice.findOrderByMemberBean(member);
				model.addAttribute("id", member);
				model.addAttribute("orderSet",order);
			}
			if(RtnCode == 1) {
				System.out.println("^_^凸，抓到你囉字串"+ MerchantTradeNo);
			 	 String mno= MerchantTradeNo.replace("NeverStarve","");
			 	 int ino = Integer.valueOf(mno);
			 	  OrderBean findorder = orderservice.findByPkOrderId(ino).get();
			 	  findorder.setTrading(1);
			 	  orderservice.update(findorder);
			 	  
			}
			
			return "order/OrderMember" ;
		}
		
		@GetMapping("list/{id}")
		public String getliString (@PathVariable int id , Model model) {
			Optional<OrderBean> order = orderservice.findByPkOrderId(id);
			OrderBean o = order.get();
			Set<OrderListBean> list = o.getOrderListBean();
			model.addAttribute("orderList",list);
			return "order/OrderList";
			
		}
	
		
		
	

	
}
