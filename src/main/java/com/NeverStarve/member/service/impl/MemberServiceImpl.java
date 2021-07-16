package com.NeverStarve.member.service.impl;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Blob;
import java.time.LocalDate;
import java.util.Base64;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Optional;
import java.util.Random;

import javax.annotation.Resource;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpSession;
import javax.sql.rowset.serial.SerialBlob;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.NeverStarve.member.model.MemberBean;
import com.NeverStarve.member.repository.MemberRepository;
import com.NeverStarve.member.service.MemberService;
import com.NeverStarve.util.NeverStarveUtil;

@Service
public class MemberServiceImpl implements MemberService {

	@Autowired
	MemberRepository memberDao;

	@Autowired
	ServletContext context;
	
	@Autowired
	HttpSession session;
	
	NeverStarveUtil nsUtil = new NeverStarveUtil();
	

	

	private int recordsPerPage = 5; // 預設每頁三筆;
	private int totalPages = -1;
	private long totalcount = 0;

	@Override
	public Optional<MemberBean> getMamberById(int id) {
		return memberDao.findById(id);
	}

	@Override
	public List<MemberBean> getMembers() {
		return memberDao.findAll();
	}

	
	//註冊時使用
	@Override
	public MemberBean save(MemberBean bean) {
		
		MultipartFile memberImage = bean.getMemberImage();
		if (memberImage != null && !memberImage.isEmpty() ) {
			String ImageFileName = memberImage.getOriginalFilename();
			bean.setFileName(ImageFileName);
			try {
				byte[] b = memberImage.getBytes();
				Blob blob = new SerialBlob(b);
				bean.setCoverImage(blob);
			} catch (Exception e) {
				e.printStackTrace();
				throw new RuntimeException("檔案上傳發生異常: " + e.getMessage());
			}
			//如果沒圖片的話就存入吉祥物圖片
		}else if(bean.getCoverImage()==null && memberImage == null) {
			try {
				byte[] b = toByteArrayJSON("/images/NeverStarvefavicon.png");
				bean.setFileName("NeverStrave.png");
				Blob blob = new SerialBlob(b);
				bean.setCoverImage(blob);
			} catch (Exception e) {
				e.printStackTrace();
				throw new RuntimeException("檔案上傳發生異常: " + e.getMessage());
			}
			
		}
		
		
		return memberDao.save(bean);
	}

	
	
	
	
	@Override
	public MemberBean updateMember(MemberBean bean) {
		bean.setAddress(bean.getMemberCity() + " " + bean.getMemberTown() + " " + bean.getAddress());
		MemberBean orinigBean = memberDao.findById(bean.getPkMemberId()).get();
		MultipartFile memberImage = bean.getMemberImage();
		if (memberImage != null && !memberImage.isEmpty()) {
			String ImageFileName = memberImage.getOriginalFilename();
			bean.setFileName(ImageFileName);
			try {
				byte[] b = memberImage.getBytes();
				Blob blob = new SerialBlob(b);
				bean.setCoverImage(blob);
			} catch (Exception e) {
				e.printStackTrace();
				throw new RuntimeException("檔案上傳發生異常: " + e.getMessage());
			}
		} else {
			if (orinigBean.getFileName() != null) {
				bean.setCoverImage(orinigBean.getCoverImage());
				bean.setFileName(orinigBean.getFileName());
			}
		}

		bean.setLongTime(orinigBean.getLongTime());
		bean.setRegisterTime(orinigBean.getRegisterTime());
		bean.setUnpaid_amount(orinigBean.getUnpaid_amount());

		if (bean.getFileName() == null) {
			if (orinigBean.getFileName() != null) {
				bean.setFileName(orinigBean.getFileName());
				bean.setCoverImage(orinigBean.getCoverImage());
			}
		}
	    MemberBean member = memberDao.save(bean);
	    if(member.getCoverImage()!=null) {
	    	
	    	member.setBas64(nsUtil.blobToBase64( member.getCoverImage(),context.getMimeType(member.getFileName())));
	    } 
		return member; 
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
			bean.setTotalcount(beans.getTotalElements());
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

	// 搜尋註冊時間內的會員(無分頁)
	@Override
	public List<MemberBean> findByRegisterTimeBetween(String start, String end) {
		LocalDate begin = LocalDate.parse(start);
		LocalDate dateEnd = LocalDate.parse(end);
		return memberDao.findByRegisterTimeBetween(begin, dateEnd);

	}

	// 搜尋註冊時間內的會員(分頁)
	@Override
	public Map<Integer, MemberBean> findByRegisterTimeBetween(int pageNo, String start, String end) {
		Map<Integer, MemberBean> map = new LinkedHashMap<>();
		System.out.println("找時間");
		LocalDate begin = LocalDate.parse(start);
		LocalDate dateEnd = LocalDate.parse(end);
		Page<MemberBean> beans = memberDao.findByRegisterTimeBetween(PageRequest.of(pageNo - 1, recordsPerPage), begin,
				dateEnd);
		List<MemberBean> list = beans.getContent();
		for (MemberBean bean : list) {
			bean.setTotalcount(beans.getTotalElements());
			map.put(bean.getPkMemberId(), bean);
		}
		return tobase64(map);
	}

	// 限定城市找尋時間內註冊的會員
	@Override
	public Map<Integer, MemberBean> findCityandRegisterTime(int pageNo, String start, String end, String address) {
		Map<Integer, MemberBean> map = new LinkedHashMap<>();
		System.out.println("找時間市區");
		LocalDate begin = LocalDate.parse(start);
		LocalDate dateEnd = LocalDate.parse(end);
		Page<MemberBean> beans = memberDao.findByRegisterTimeBetweenAndAddressContaining(
				PageRequest.of(pageNo - 1, recordsPerPage), begin, dateEnd, address);
		List<MemberBean> list = beans.getContent();
		for (MemberBean bean : list) {
			bean.setTotalcount(beans.getTotalElements());
			map.put(bean.getPkMemberId(), bean);
		}
		return tobase64(map);
	}

//	@Override
//	public Map<Integer, MemberBean> getMemberData(int pageNo, String adderss) {
//		Map<Integer, MemberBean> data = new LinkedHashMap<>();
//
//		if (adderss.isEmpty() ) {
//			data = getPageMembers(pageNo);
//		}
//		data = findByAddressContaining(pageNo, adderss);
//		return data;
//	}

	@Override
	public Map<Integer, MemberBean> getMemberData(int pageNo, String adderss, String start, String end) {
		Map<Integer, MemberBean> data = new LinkedHashMap<>();

		if (adderss.isEmpty() && start.isEmpty()) { // if(沒參數) {搜尋全部}
			data = getPageMembers(pageNo);
			System.out.println("無條件");
		} else if (!adderss.isEmpty() && start.isEmpty()) { // else if(有city 無日期){ 搜尋城市}
			System.out.println("城市");
			data = findByAddressContaining(pageNo, adderss);
		} else if (adderss.isEmpty() && !start.isEmpty()) { // else if(有日期 無city) {搜尋時間內}
			data = findByRegisterTimeBetween(pageNo, start, end);
		} else { // else: 有日期 有city {搜尋地區and時間內}
			data = findCityandRegisterTime(pageNo, start, end, adderss);
		}

		return data;

	}

	@Override
	public Map<Integer, MemberBean> findByAddressContaining(int pageNo, String adderss) {
		System.out.println("找地區");
		Map<Integer, MemberBean> map = new LinkedHashMap<>();
		Page<MemberBean> beans = memberDao.findByAddressContaining(PageRequest.of(pageNo - 1, recordsPerPage), adderss);
		List<MemberBean> list = beans.getContent();
		setTotalcount(beans.getTotalElements());
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

	private Map<Integer, MemberBean> tobase64(Map<Integer, MemberBean> memberMap) {
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

	@Override
	public MemberBean loginMember(String email, String password) {
		MemberBean memberBean = memberDao.findByEmailAndPassword(email, password);
		if (memberBean != null) {
			if (memberBean.getCoverImage() != null && memberBean.getFileName() != null) {

				memberBean.setBas64(
						nsUtil.blobToBase64(memberBean.getCoverImage(), context.getMimeType(memberBean.getFileName())));
			}

			return memberBean;
		}
		return memberBean;

	}

	@Override
	public MemberBean cookieLogin(String email) {
		MemberBean memberBean = memberDao.findCookieByEmail(email);
		if (memberBean != null) {

			if (memberBean.getCoverImage() != null && memberBean.getFileName() != null) {

				memberBean.setBas64(
						nsUtil.blobToBase64(memberBean.getCoverImage(), context.getMimeType(memberBean.getFileName())));
			}

			return memberBean;
		}
		return memberBean;
	}

	@Override
	public long getCityCount(String address) {
		return memberDao.countByAddressContaining(address);
	}

//--------------------------email----------------------------
	//email使用
	@Resource
    private JavaMailSender mailSender;
	
	//email使用
	@Value("${spring.mail.username}")
    private String from;

	
	
	@Override
	@Async	//非同步請求的註釋
	public void sendSimpleMail(String to, String subject, String content) {
		
		SimpleMailMessage message = new SimpleMailMessage();
		//從哪裡寄出
        message.setFrom(from);
        //收件人
        message.setTo(to);
        //信的開頭
        message.setSubject(subject);
        //信的內容
        message.setText(content);
        
        mailSender.send(message);
 		
	}

	@Override
	@Async	//非同步請求的註釋
	public void sendForgotPasswordMail(String email, String subject, String content) {
		
		SimpleMailMessage message = new SimpleMailMessage();
		//從哪裡寄出
        message.setFrom(from);
        //收件人
        message.setTo(email);
        //信的開頭
        message.setSubject(subject);
        
        String code = randomCode();
        //信的內容
        message.setText(content+"您的驗證碼是:"+code);
        
        //将随机数放置到session中
        session.setAttribute("code",code);
        
        //将email存入session中
        session.setAttribute("email",email);
        mailSender.send(message);
	}

	   /**
     * 随机生成6位数的验证码
     * @return String code
     */
    public String randomCode(){
        StringBuilder str = new StringBuilder();
        Random random = new Random();
        for (int i = 0; i < 6; i++) {
            str.append(random.nextInt(9));
        }
        return str.toString();
    }
	
    
    /**
     * 检验验证码是否一致
     * @param userVo
     * @param session
     * @return
     */
	@Override
    public boolean checkCode(String userCode){
        //获取session中的验证信息
        String code = (String) session.getAttribute("code");
        
        //获取表单中的提交的验证信息
        String uc = userCode;

         if (code.equals(uc)){
        	 
            return true;
        }
		return false;
    
    }

	
    //忘記密碼的更新
	@Override
	@Transactional(propagation = Propagation.REQUIRED)
	public void updateForgotPassword(String password, String email) {

		memberDao.updateForgotPassword(password,email);
	}
	
}
