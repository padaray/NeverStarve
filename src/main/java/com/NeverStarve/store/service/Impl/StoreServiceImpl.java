package com.NeverStarve.store.service.Impl;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Blob;
import java.util.ArrayList;
import java.util.Base64;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Optional;
import java.util.Random;

import javax.servlet.ServletContext;
import javax.sql.rowset.serial.SerialBlob;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

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

	NeverStarveUtil util = new NeverStarveUtil();

	private int recordsPerPage = 5; // 預設頁數;
	private int totalPages = -1;
	private long totalcount = 0;

	@Override
	public StoreBean save(StoreBean storeBean) {
		// 寫入圖片
		MultipartFile storeImage = storeBean.getStoreImage();
		if (storeImage != null && !storeImage.isEmpty()) {
			String ImageFileName = storeImage.getOriginalFilename();
			storeBean.setStoreImageName(ImageFileName);
			try {
				byte[] b = storeImage.getBytes();
				Blob blob = new SerialBlob(b);
				storeBean.setCoverImage(blob);
			} catch (Exception e) {
				e.printStackTrace();
				throw new RuntimeException("檔案上傳發生異常: " + e.getMessage());
			}
		} else {
			try {
				byte[] b = toByteArrayJSON("/images/NeverStarvefavicon.png");
				storeBean.setStoreImageName("NeverStarvefavicon.png");
				Blob blob = new SerialBlob(b);
				storeBean.setCoverImage(blob);
			} catch (Exception e) {
				e.printStackTrace();
				throw new RuntimeException("檔案上傳發生異常else: " + e.getMessage());
			}
		}
		return storeRepository.save(storeBean);
	}

	@Override
	public Integer saveNoPic(StoreBean storeBean) {
		Integer pkStoreId = storeBean.getPkStoreId();
		String storeAccount = storeBean.getStoreAccount();
		String storePassword = storeBean.getStorePassword();
		String storeName = storeBean.getStoreName();
		String storeAddress = storeBean.getStoreAddress();
		String storePhone = storeBean.getStorePhone();
		String storeType = storeBean.getStoreType();
		Integer seatNumber = storeBean.getSeatNumber();
		return storeRepository.saveNoPic(pkStoreId, storeAccount, storePassword, storeName, storeAddress, storePhone,
				storeType, seatNumber);
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
		List<StoreBean> sbls = storeRepository.findAll();
		for (StoreBean sb : sbls) {
			if (sb.getCoverImage() != null && sb.getStoreImageName() != null) {
				String mimetype = context.getMimeType(sb.getStoreImageName());
				sb.setBase64(util.blobToBase64(sb.getCoverImage(), mimetype));
			}
		}

		return sbls;
	}

	public StoreBean findByStoreAccountAndStorePassword(String storeAccount, String storePassword) {
		return storeRepository.findByStoreAccountAndStorePassword(storeAccount, storePassword);
	}

	@Override
	public StoreBean findCookieByStoreAccount(String storeAccount) {
		StoreBean storeBean = storeRepository.findCookieByStoreAccount(storeAccount);
		return tobase64simp(storeBean);
	}

	@Override
	public Map<Integer, StoreBean> getPageStores(int pageNo) {
		Map<Integer, StoreBean> map = new LinkedHashMap<>();
		PageRequest pageable = PageRequest.of(pageNo - 1, recordsPerPage);
		Page<StoreBean> beans = storeRepository.findAll(pageable);
		setTotalcount(beans.getTotalElements());
		List<StoreBean> list = beans.getContent();
		for (StoreBean bean : list) {
			bean.setTotalcount(beans.getTotalElements());
			map.put(bean.getPkStoreId(), bean);
		}

		return tobase64(map);
	}

	public void setTotalcount(long totalcount) {
		this.totalcount = totalcount;
	}

	private Map<Integer, StoreBean> tobase64(Map<Integer, StoreBean> memberMap) {
		String filePath = "/images/NoImage.jpg";
		StringBuffer sb = new StringBuffer();
		byte[] media = null;

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

	@Override
	public Map<Integer, StoreBean> findByAddressContaining(int pageNo, String adderss) {
		Map<Integer, StoreBean> map = new LinkedHashMap<>();
		Page<StoreBean> beans = storeRepository.findByStoreAddressContaining(PageRequest.of(pageNo - 1, recordsPerPage),
				adderss);
		List<StoreBean> list = beans.getContent();
		setTotalcount(beans.getTotalElements());
		for (StoreBean bean : list) {
			bean.setTotalcount(beans.getTotalElements());
			map.put(bean.getPkStoreId(), bean);
		}
		return tobase64(map);
	}

	@Override
	public int getRecordsPerPage() {
		return recordsPerPage;
	}

	@Override
	public long getRecordCounts() {
		return storeRepository.count();
	}

	@Override
	public int getTotalPages() {
		totalPages = (int) (Math.ceil(getTotalcount() / (double) recordsPerPage));
		return totalPages;
	}

	@Override
	public void setRecordsPerPage(int recordsPerPage) {
		this.recordsPerPage = recordsPerPage;
	}

	@Override
	public long getTotalcount() {
		return totalcount;
	}

	@Override
	public long getCityCount(String address) {
		return storeRepository.countByStoreAddressContaining(address);
	}

	// 將單一menuBean變成base64
	private StoreBean tobase64simp(StoreBean storeBean) {
		if (storeBean != null) {
//			String filePath = "/images/NeverStarvefavicon.png";
			StringBuffer stringBuff = new StringBuffer();
//			byte[] media = null;
			stringBuff.setLength(0);
			String filename = storeBean.getStoreImageName();
			Blob coverImage = storeBean.getCoverImage();
			if (filename != null && coverImage != null) {
				String base64img = util.blobToBase64(coverImage, context.getMimeType(filename));
				storeBean.setBase64(base64img);

			}
		}
		return storeBean;
	}

	@Override
	public List<StoreBean> findBystoreType(String storeType) {
		List<StoreBean> sbls = storeRepository.findByStoreTypeContaining(storeType);
		for (StoreBean sb : sbls) {
			if (sb.getCoverImage() != null && sb.getStoreImageName() != null) {
				sb.setBase64(util.blobToBase64(sb.getCoverImage(), context.getMimeType(sb.getStoreImageName())));
			}
		}

		return sbls;
	}

	@Override
	public List<StoreBean> searchBar(String keyword) {
		if (keyword == null) {

			return storeRepository.findAll();
		} else {

			return storeRepository.findByStoreNameContainingOrStoreAddressContainingOrStoreTypeContaining(keyword,
					keyword, keyword);
		}
	}

	@SuppressWarnings("null")
	@Override
	public List<StoreBean> getRandomAdvertising(Integer storeLv) {
		List<StoreBean> sbls = storeRepository.findByStoreLv(storeLv);
		List<StoreBean> newsbls = new ArrayList<StoreBean>();
		
		Random rd = new Random(); //產生Random物件
		List<Integer> al=new ArrayList<>();
		while(al.size()<=2){ //總共3個數字
			int n=rd.nextInt(sbls.size()); 
			if(al.contains(n)) 
				continue;     //重複的不加入
			else
				al.add(n);
			}

		for (int i :al) {
			StoreBean sb = sbls.get(i);
			System.out.println(sb.getPkStoreId());
			if(sb.getCoverImage()!=null) {
				sb.setBase64(util.blobToBase64(sb.getCoverImage(), context.getMimeType(sb.getStoreImageName())));
			}
			newsbls.add(sb);
		}

		return newsbls;
	}

}
