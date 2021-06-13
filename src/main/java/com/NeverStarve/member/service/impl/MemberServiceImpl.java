package com.NeverStarve.member.service.impl;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Blob;
import java.util.Base64;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Map.Entry;

import javax.servlet.ServletContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import com.NeverStarve.member.model.MemberBean;
import com.NeverStarve.member.repository.MemberRepository;
import com.NeverStarve.member.service.MemberService;

@Service
public class MemberServiceImpl implements MemberService {

	@Autowired
	MemberRepository memberDao;
	
	@Autowired
	ServletContext context;

	private int recordsPerPage = 5; // 預設每頁三筆;
	private int totalPages = -1;
	private long totalcount=0;

	@Override
	public Optional<MemberBean> getMamberById(int id) {
		return memberDao.findById(id);
	}

	@Override
	public List<MemberBean> getMembers() {
		return memberDao.findAll();
	}

	@Override
	public MemberBean save(MemberBean bean) {
		return memberDao.save(bean);
	}

	@Override
	public MemberBean updateMember(MemberBean bean) {
		return memberDao.save(bean);
	}

	@Override
	public void deleteMemberByPrimaryKey(int id) {
		memberDao.deleteById(id);

	}

	@Override
	public List<MemberBean> findByAddressContaining(String address) {
		return memberDao.findByAddressContaining(address);

	}

	@Override
	public MemberBean queryMember(String email) {
		MemberBean mb = null;

		List<MemberBean> beans = memberDao.findByEmail(email);
		if (beans.size() > 0) {
			mb = beans.get(0);
		}
		return mb;
	}

	@Override
	public boolean emailExists(String email) {
		boolean exist = false;
		List<MemberBean> beans = memberDao.findByEmail(email);
		if (beans.size() > 0) {
			exist = true;
		}
		return exist;
	}
	@Override
	public Map<Integer, MemberBean> getPageMembers(int pageNo) {
		Map<Integer, MemberBean> map = new LinkedHashMap<>();
		PageRequest pageable = PageRequest.of(pageNo - 1, recordsPerPage);
		Page<MemberBean> beans = memberDao.findAll(pageable);	
		setTotalcount(beans.getTotalElements());
		List<MemberBean> list = beans.getContent();
		for (MemberBean bean : list) {
			map.put(bean.getPkMemberId(), bean);
		}
		return tobase64(map);
	}

	@Override
	public int getRecordsPerPage() {

		return recordsPerPage;
	}

	@Override
	public long getRecordCounts() {
		return memberDao.count();
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

	// 搜尋註冊時間內的會員
	@Override
	public List<MemberBean> findByRegisterTimeBetween(String start, String end) {
		
//		memberDao.findByRegisterTimeBetween(start, end);
		return null;
	}
	
	@Override
	public Map<Integer, MemberBean> getMemberData(int pageNo, String adderss) {
		Map<Integer, MemberBean> data = new LinkedHashMap<>();

		if (adderss.isEmpty()) {
			data = getPageMembers(pageNo);
		}
		data = findByAddressContaining(pageNo, adderss);
		return data;
	}
	

	@Override
	public Map<Integer, MemberBean> findByAddressContaining(int pageNo,String adderss){
		Map<Integer, MemberBean> map = new LinkedHashMap<>();	
		Page<MemberBean> beans 	=memberDao.findByAddressContaining(PageRequest.of(pageNo - 1, recordsPerPage), adderss);
		List<MemberBean> list = beans.getContent();
		setTotalcount(beans.getTotalElements());
		beans.getTotalElements();
		for (MemberBean bean : list) {
			bean.setTotalcount(beans.getTotalElements());
			map.put(bean.getPkMemberId(), bean);
		}
		return tobase64(map);
	}

	public long getTotalcount() {
		return totalcount;
	}

	public void setTotalcount(long totalcount) {
		this.totalcount = totalcount;
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
