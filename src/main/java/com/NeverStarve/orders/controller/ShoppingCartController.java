package com.NeverStarve.orders.controller;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Blob;
import java.util.Base64;
import java.util.List;
import java.util.Optional;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.NeverStarve.member.model.MemberBean;
import com.NeverStarve.store.model.MenuBean;
import com.NeverStarve.store.model.StoreBean;
import com.NeverStarve.store.service.MenuService;
import com.NeverStarve.store.service.StoreService;
import com.NeverStarve.util.NeverStarveUtil;

@Controller
@RequestMapping("/shop")
public class ShoppingCartController {
	
	@Autowired
	MenuService menuService;
	@Autowired
    HttpSession session;	
	
	/*
	 * Tony-20210706
	 */
	@Autowired
	StoreService storeService;
	
	@Autowired
	ServletContext context;
	@GetMapping("/getMenuByStoreId/{id}")
	public String getMenuByStroeId(@PathVariable Integer id,Model model){
		MemberBean member =(MemberBean) session.getAttribute("member");
		List<MenuBean> MenuByStorId = tobase64(menuService.getMenuByStroeId(id));
		model.addAttribute("menu",MenuByStorId);
		model.addAttribute("member",member);
		
		/*
		 * Tony-20210706
		 */
		//由路徑變數的storeId取得該storeBean(測試中)
		StoreBean store = null;
		Optional<StoreBean> opt = storeService.findoneById(id);
			if(opt.isPresent()) {
				store = opt.get();
			}else {
				return "redirect:/";
			}
			model.addAttribute("store", store);
		
		return "test/testcrat";
	}
	
	private List<MenuBean> tobase64(List<MenuBean> menuBeanList) {
        String filePath = "/images/NoImage.jpg";
        StringBuffer stringBuff = new StringBuffer();
        byte[] media = null;
        NeverStarveUtil util = new NeverStarveUtil();
        for (MenuBean MBL : menuBeanList) {
            stringBuff.setLength(0);
            String filename = MBL.getDishImageName();
            Blob coverImage = MBL.getCoverImage();
            if (filename != null && coverImage != null) {
                String base64img = util.blobToBase64(coverImage, context.getMimeType(filename));
                MBL.setBase64(base64img);

            } else {
                media = toByteArrayJSON(filePath);
                String mimeType = context.getMimeType(filePath);
                stringBuff.append("data:" + mimeType + ";base64,");
                Base64.Encoder be = Base64.getEncoder();
                stringBuff.append(new String(be.encode(media)));
                MBL.setBase64(stringBuff.toString());
            }
        }
        return menuBeanList;
    }
	//把沒有圖片的東西轉成Byte陣列
	 private byte[] toByteArrayJSON(String filepath) {
	        byte[] b = null;
	        String realPath = context.getRealPath(filepath);
	        try {
	            File file = new File(realPath);
	            long size = file.length();
	            b = new byte[(int) size];
	            InputStream fis = context.getResourceAsStream(filepath);
	            fis.read(b);
	        } catch (FileNotFoundException e) {
	            e.printStackTrace();
	        } catch (IOException e) {
	            e.printStackTrace();
	        }
	        return b;
	    }
}
