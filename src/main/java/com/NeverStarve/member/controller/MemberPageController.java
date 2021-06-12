package com.NeverStarve.member.controller;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Blob;
import java.util.Base64;
import java.util.Map;
import java.util.Map.Entry;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.NeverStarve.member.model.MemberBean;
import com.NeverStarve.member.service.MemberService;

@Controller
@SessionAttributes({ "pageNo", "products_DPP" })
@RequestMapping("/Member")
public class MemberPageController {
	@Autowired
	ServletContext context;

	@Autowired
	MemberService memberService;

	@GetMapping("/DisplayPageProducts")
	public String displayPageProducts(Model model, HttpServletRequest request, HttpServletResponse response,
			@RequestParam(value = "pageNo", required = false) Integer pageNo
			,@RequestParam(value = "city", required = false)String city) {

		if (pageNo == null) {
			pageNo = 1;
			
		}		
		Map<Integer, MemberBean> memberMap = memberService.getPageMembers(pageNo);
		tobase64(memberMap);
		model.addAttribute("pagecounts",memberService.getTotalcount());
		model.addAttribute("counts",memberService.getRecordCounts());
		model.addAttribute("pageNo", String.valueOf(pageNo));
		model.addAttribute("totalPages", memberService.getTotalPages());
		// 將讀到的一頁資料放入request物件內，成為它的屬性物件
		model.addAttribute("products_DPP", memberMap);
		// 使用Cookie來儲存目前讀取的網頁編號，Cookie的名稱為memberId + "pageNo"
		// -----------------------
//		Cookie pnCookie = new Cookie(memberId + "pageNo", String.valueOf(pageNo));
		// 設定Cookie的存活期為30天
//		pnCookie.setMaxAge(30 * 24 * 60 * 60);
		// 設定Cookie的路徑為 Context Path
//		pnCookie.setPath(request.getContextPath());
		// 將Cookie加入回應物件內
//		response.addCookie(pnCookie);

		return "backstage/MemberPagListBoot";
	}
		
	
	@GetMapping("/PageCityMember")
	public @ResponseBody Map<Integer, MemberBean> PageCityMember(Model model, HttpServletRequest request, HttpServletResponse response,RedirectAttributes ra,
			@RequestParam(value = "pageNo", required = false) Integer pageNo,@RequestParam(value = "city", required = false)String city) {

		if (pageNo == null) {
			pageNo = 1;
		}
		Map<Integer, MemberBean> memberMap;
		if(city != null) {
			memberMap = memberService.findByAddressContaining(pageNo, city);			
			tobase64(memberMap);
			model.addAttribute("products_DPP", memberMap);
			model.addAttribute("counts",memberService.getTotalcount());
			String flush = city+"會員";
			model.addAttribute("SearchResult",flush);
		}else {	
			memberMap = memberService.getPageMembers(pageNo);
			tobase64(memberMap);
			model.addAttribute("products_DPP", memberMap);
			model.addAttribute("counts",memberService.getRecordCounts());
			model.addAttribute("SearchResult","所有會員");


		}
		model.addAttribute("pageNo", String.valueOf(pageNo));
		model.addAttribute("totalPages", memberService.getTotalPages());	// 將讀到的一頁資料放入request物件內，成為它的屬性物件
		return memberMap;
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
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
	
	private Map<Integer, MemberBean> tobase64(Map<Integer, MemberBean> memberMap ) {
		String filePath = "/images/NoImage.jpg";
		StringBuffer sb = new StringBuffer();
		byte[] media = null;
		for (Entry<Integer, MemberBean> m : memberMap.entrySet()) {
			sb.setLength(0);
			String filename = m.getValue().getFileName();
			Blob coverImage = m.getValue().getCoverImage();
			if (filename != null && coverImage != null) {
				String mimeType = context.getMimeType(filename);

				try (InputStream is = coverImage.getBinaryStream();
						ByteArrayOutputStream baos = new ByteArrayOutputStream();) {
					System.out.println(filename);

					int len = 0;
					byte[] b = new byte[81920]; // 512的整數倍
					while ((len = is.read(b)) != -1) {
						baos.write(b, 0, len);
					}
					byte[] ba = baos.toByteArray();

					sb.append("data:" + mimeType + ";base64,");
					Base64.Encoder be = Base64.getEncoder();
					sb.append(new String(be.encode(ba)));
				} catch (Exception ex) {
					ex.printStackTrace();
				}

//				System.out.println(sb.toString());
				m.getValue().setBas64(sb.toString());

			} else {
				media = toByteArrayJSON(filePath);

				String mimeType = context.getMimeType(filePath);
				sb.append("data:" + mimeType + ";base64,");
				Base64.Encoder be = Base64.getEncoder();
				sb.append(new String(be.encode(media)));

				System.out.println(sb.toString());
				m.getValue().setBas64(sb.toString());
			}		
		}
		return memberMap;
	}

}