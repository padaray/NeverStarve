package com.NeverStarve.store.service.Impl;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Blob;
import java.util.Base64;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.servlet.ServletContext;
import javax.sql.rowset.serial.SerialBlob;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.NeverStarve.store.model.MenuBean;
import com.NeverStarve.store.model.StoreBean;
import com.NeverStarve.store.repository.MenuRepository;
import com.NeverStarve.store.repository.StoreRepository;
import com.NeverStarve.store.service.MenuService;
import com.NeverStarve.util.NeverStarveUtil;

@Service
@Transactional
public class MenuServiceImpl implements MenuService {

	@Autowired
	MenuRepository menuRepository;
	@Autowired
	StoreRepository storeRepository;
	@Autowired
	ServletContext context;

	@Override
	public MenuBean save(MenuBean menuBean) {
		return menuRepository.save(menuBean);
	}

	//用cookie找到storeBean再去抓菜單
	@Override
	public List<MenuBean> getMenuByStoreBean(StoreBean storeBean) {
		List<MenuBean> menuBeanList =  menuRepository.findByStoreBean(storeBean);
		return tobase64(menuBeanList);
	}

	//育霆用的
	@Override
	public MenuBean getMenuById(Integer id) {
		MenuBean menuBean =  menuRepository.getById(id);
		return tobase64(menuBean);
	}

	//育霆用的
	@Override
	public List<MenuBean> getMenuByStroeId(Integer id) {
		StoreBean storeBean = storeRepository.getById(id);
		return menuRepository.findByStoreBean(storeBean);
	}
	
	//用菜品ID去刪除菜品
	@Override
	public void deleteByDishId(Integer dishId) {
		menuRepository.deleteByDishId(dishId);
	}

	@Override
	public void saveMenuList(MenuBean menuBean) {
		// TODO Auto-generated method stub
		//寫入圖片
		MultipartFile dishPicture = menuBean.getDishPicture();
		if (dishPicture != null && !dishPicture.isEmpty()) {
			String ImageFileName = dishPicture.getOriginalFilename();
			menuBean.setDishImageName(ImageFileName);
			try {
				byte[] b = dishPicture.getBytes();
				Blob blob = new SerialBlob(b);
				menuBean.setCoverImage(blob);
			} catch (Exception e) {
				e.printStackTrace();
				throw new RuntimeException("檔案上傳發生異常: " + e.getMessage());
			}
		}
		menuRepository.save(menuBean);
	}
	
	//將整個菜單變成base64
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
	
	//將單一menuBean變成base64
	private MenuBean tobase64(MenuBean menuBean) {
		String filePath = "/images/NoImage.jpg";
		StringBuffer stringBuff = new StringBuffer();
		byte[] media = null;
		NeverStarveUtil util = new NeverStarveUtil();
		stringBuff.setLength(0);
		String filename = menuBean.getDishImageName();
		Blob coverImage = menuBean.getCoverImage();
		if (filename != null && coverImage != null) {
			String base64img = util.blobToBase64(coverImage, context.getMimeType(filename));
			menuBean.setBase64(base64img);

		} else {
			media = toByteArrayJSON(filePath);
			String mimeType = context.getMimeType(filePath);
			stringBuff.append("data:" + mimeType + ";base64,");
			Base64.Encoder be = Base64.getEncoder();
			stringBuff.append(new String(be.encode(media)));
			menuBean.setBase64(stringBuff.toString());
		}
		return menuBean;
	}
	
	
	//變成byte
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
//	@Override
//	public void saveMenuList(List<MenuBean> menuListS, StoreBean storeBean) {
//		// TODO Auto-generated method stub
//		for(MenuBean MLS: menuListS) {
//			MLS.setStoreBean(storeBean);
//			//寫入圖片
//			MultipartFile dishPicture = MLS.getDishPicture();
//			if (dishPicture != null && !dishPicture.isEmpty()) {
//				String ImageFileName = dishPicture.getOriginalFilename();
//				MLS.setDishImageName(ImageFileName);
//				try {
//					byte[] b = dishPicture.getBytes();
//					Blob blob = new SerialBlob(b);
//					MLS.setCoverImage(blob);
//				} catch (Exception e) {
//					e.printStackTrace();
//					throw new RuntimeException("檔案上傳發生異常: " + e.getMessage());
//				}
//			}
//			menuRepository.save(MLS);
//		}
//	}
	
	

}
