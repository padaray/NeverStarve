package com.NeverStarve.store.service.Impl;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Blob;
import java.util.Base64;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Optional;

import javax.servlet.ServletContext;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import com.NeverStarve.store.model.StoreBean;
import com.NeverStarve.store.repository.StoreRepository;
import com.NeverStarve.store.service.StoreService;
import com.NeverStarve.util.NeverStarveUtil;

@Service
@Transactional
public class StoreServiceImpl implements StoreService {
	
	@Autowired
	StoreRepository storeRepository;
	
	@Autowired
	ServletContext context;
	
	private int recordsPerPage = 5; // 預設每頁三筆;
	private int totalPages = -1;
	private long totalcount=0;

	@Override
	public StoreBean save(StoreBean storeBean) {
		return storeRepository.save(storeBean) ;
	}

	@Override
	public Optional<StoreBean> findoneById(int id) {
		return storeRepository.findById(id);
	}
	
	@Override
	public boolean accountExist(String storeAccount) {
		boolean exist = false;
		List<StoreBean> beans = storeRepository.findByStoreAccount(storeAccount);
		if (beans.size() > 0) {
			exist = true;
		}
		return exist;
	}

	@Override
	public List<StoreBean> findAll() {
		return storeRepository.findAll();
	}
	
	public StoreBean findByStoreAccountAndStorePassword(String storeAccount, String storePassword) {
		return storeRepository.findByStoreAccountAndStorePassword(storeAccount, storePassword);
	}

	@Override
	public StoreBean findCookieByStoreAccount(String storeAccount) {
		return storeRepository.findCookieByStoreAccount(storeAccount);
	}

	@Override
	public Map<Integer, StoreBean> getPageStores(int pageNo) {
		Map<Integer, StoreBean> map = new LinkedHashMap<>();
		PageRequest pageable = PageRequest.of(pageNo - 1, recordsPerPage);
		Page<StoreBean> beans = storeRepository.findAll(pageable);	
		setTotalcount(beans.getTotalElements());
		List<StoreBean> list = beans.getContent();
		for (StoreBean bean : list) {
			map.put(bean.getPkStoreId(), bean);
		}
	
		return tobase64(map);
	}
	
	public void setTotalcount(long totalcount) {
		this.totalcount = totalcount;
	}
	
	private Map<Integer, StoreBean> tobase64(Map<Integer, StoreBean> memberMap ) {
		String filePath = "/images/NoImage.jpg";
		StringBuffer sb = new StringBuffer();
		byte[] media = null;
		NeverStarveUtil util = new NeverStarveUtil();
		for (Entry<Integer, StoreBean> m : memberMap.entrySet()) {
			sb.setLength(0);
			String filename = m.getValue().getStoreImageName();
			Blob coverImage = m.getValue().getCoverImage();
			if (filename != null && coverImage != null) {
				String base64img = util.blobToBase64(coverImage, context.getMimeType(filename));
				m.getValue().setBase64(base64img);

			} else {
				media = toByteArrayJSON(filePath);

				String mimeType = context.getMimeType(filePath);
				sb.append("data:" + mimeType + ";base64,");
				Base64.Encoder be = Base64.getEncoder();
				sb.append(new String(be.encode(media)));
				m.getValue().setBase64(sb.toString());
			}		
		}
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
	
}
