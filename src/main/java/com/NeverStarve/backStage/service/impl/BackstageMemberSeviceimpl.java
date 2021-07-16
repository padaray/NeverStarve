package com.NeverStarve.backStage.service.impl;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Blob;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Collection;
import java.util.Comparator;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import javax.servlet.ServletContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.NeverStarve.backStage.model.Month;
import com.NeverStarve.backStage.repository.ReportOrderRepository;
import com.NeverStarve.backStage.service.BackstageMemberSevice;
import com.NeverStarve.member.model.MemberBean;
import com.NeverStarve.member.repository.MemberRepository;
import com.NeverStarve.orders.model.OrderBean;

@Service
public class BackstageMemberSeviceimpl implements BackstageMemberSevice {

	@Autowired
	MemberRepository memberRepository;
	
	@Autowired
	ReportOrderRepository reportOrderRepository;

	@Autowired
	ServletContext context;	

	@Override
	public List<MemberBean> findByEmailContaining(String email) {
		List<MemberBean> list = memberRepository.findByEmailContaining(email);
		for (MemberBean bean : list) {
			bean.setTotalcount(memberRepository.countByEmailContaining(email));
		}
		return tobase64(list);

	}

	private List<MemberBean> tobase64(List<MemberBean> memberList) {
		String filePath = "/images/NoImage.jpg";
		StringBuffer sb = new StringBuffer();
		byte[] media = null;
		for (MemberBean m : memberList) {
			sb.setLength(0);
			String filename = m.getFileName();
			Blob coverImage = m.getCoverImage();
			if (filename != null && coverImage != null) {
				String mimeType = context.getMimeType(filename);

				try (InputStream is = coverImage.getBinaryStream();
						ByteArrayOutputStream baos = new ByteArrayOutputStream();) {
//					System.out.println(filename);
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
				m.setBas64(sb.toString());

			} else {
				media = toByteArrayJSON(filePath);

				String mimeType = context.getMimeType(filePath);
				sb.append("data:" + mimeType + ";base64,");
				Base64.Encoder be = Base64.getEncoder();
				sb.append(new String(be.encode(media)));
//				System.out.println(sb.toString());
				m.setBas64(sb.toString());
			}
		}
		return memberList;
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
	public Collection<OrderBean> getOrders(MemberBean bean) {
		Set<OrderBean> order = bean.getOrders();
		List<OrderBean> list1 = new ArrayList<OrderBean>(order);
		return  list1.stream().sorted(Comparator.comparing(OrderBean::getPkOrderId).reversed()).collect(Collectors.toCollection(ArrayList::new));	
	}


	@Override
	public Month countMonthsMember(String yyyy) {
		if (yyyy==null) {
			yyyy="2021";
		}
		Month momth = new Month();
		momth.setOne(memberRepository.countRegisterTime(yyyy+"-01"));
		momth.setTwo(memberRepository.countRegisterTime(yyyy+"-02"));
		momth.setThere(memberRepository.countRegisterTime(yyyy+"-03"));
		momth.setFour(memberRepository.countRegisterTime(yyyy+"-04"));
		momth.setFive(memberRepository.countRegisterTime(yyyy+"-05"));
		momth.setSix(memberRepository.countRegisterTime(yyyy+"-06"));
		momth.setSeven(memberRepository.countRegisterTime(yyyy+"-07"));
		momth.setEight(memberRepository.countRegisterTime(yyyy+"-08"));
		momth.setNine(memberRepository.countRegisterTime(yyyy+"-09"));
		momth.setTen(memberRepository.countRegisterTime(yyyy+"-10"));
		momth.setEleven(memberRepository.countRegisterTime(yyyy+"-11"));
		momth.setTwelve(memberRepository.countRegisterTime(yyyy+"-12"));
		momth.setTotl(memberRepository.countRegisterTime(yyyy));
		
		return  momth; 	
	}

	@Override
	public Month countOrder(String yyyy) {
		if (yyyy==null) {
			yyyy="2021";
		}
		Month momth = new Month();
		momth.setOne(reportOrderRepository.countOrder(yyyy+"-01"));
		momth.setTwo(reportOrderRepository.countOrder(yyyy+"-02"));
		momth.setThere(reportOrderRepository.countOrder(yyyy+"-03"));
		momth.setFour(reportOrderRepository.countOrder(yyyy+"-04"));
		momth.setFive(reportOrderRepository.countOrder(yyyy+"-05"));
		momth.setSix(reportOrderRepository.countOrder(yyyy+"-06"));
		momth.setSeven(reportOrderRepository.countOrder(yyyy+"-07"));
		momth.setEight(reportOrderRepository.countOrder(yyyy+"-08"));
		momth.setNine(reportOrderRepository.countOrder(yyyy+"-09"));
		momth.setTen(reportOrderRepository.countOrder(yyyy+"-10"));
		momth.setEleven(reportOrderRepository.countOrder(yyyy+"-11"));
		momth.setTwelve(reportOrderRepository.countOrder(yyyy+"-12"));
		momth.setTotl(reportOrderRepository.countOrder(yyyy));
		return momth;
	}


	@Override
	public Month sumOrderMany(String yyyy) {
		if (yyyy==null) {
			yyyy="2021";
		}
		Month momth = new Month();
		try {
			momth.setOne(reportOrderRepository.countordermany( new SimpleDateFormat("yyyy-MM-dd").parse(yyyy+"-01-01")));
			momth.setTwo(reportOrderRepository.countordermany( new SimpleDateFormat("yyyy-MM-dd").parse(yyyy+"-02-01")));
			momth.setThere(reportOrderRepository.countordermany( new SimpleDateFormat("yyyy-MM-dd").parse(yyyy+"-03-01")));
			momth.setFour(reportOrderRepository.countordermany( new SimpleDateFormat("yyyy-MM-dd").parse(yyyy+"-04-01")));
			momth.setFive(reportOrderRepository.countordermany( new SimpleDateFormat("yyyy-MM-dd").parse(yyyy+"-05-01")));
			momth.setSix(reportOrderRepository.countordermany( new SimpleDateFormat("yyyy-MM-dd").parse(yyyy+"-06-01")));
			momth.setSeven(reportOrderRepository.countordermany( new SimpleDateFormat("yyyy-MM-dd").parse(yyyy+"-07-01")));
			momth.setEight(reportOrderRepository.countordermany( new SimpleDateFormat("yyyy-MM-dd").parse(yyyy+"-08-01")));
			momth.setNine(reportOrderRepository.countordermany( new SimpleDateFormat("yyyy-MM-dd").parse(yyyy+"-09-01")));
			momth.setTen(reportOrderRepository.countordermany( new SimpleDateFormat("yyyy-MM-dd").parse(yyyy+"-10-01")));
			momth.setEleven(reportOrderRepository.countordermany( new SimpleDateFormat("yyyy-MM-dd").parse(yyyy+"-11-01")));
			momth.setTwelve(reportOrderRepository.countordermany( new SimpleDateFormat("yyyy-MM-dd").parse(yyyy+"-12-01")));
			momth.setTotl(reportOrderRepository.countOrderYearmany( new SimpleDateFormat("yyyy-MM-dd").parse(yyyy+"-01-01")));
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return momth;
	}
	
	
	
}
