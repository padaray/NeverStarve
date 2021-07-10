package com.NeverStarve.backStage.service.impl;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Blob;
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

import com.NeverStarve.backStage.service.BackstageMemberSevice;
import com.NeverStarve.member.model.MemberBean;
import com.NeverStarve.member.repository.MemberRepository;
import com.NeverStarve.orders.model.OrderBean;

@Service
public class BackstageMemberSeviceimpl implements BackstageMemberSevice {

	@Autowired
	MemberRepository memberRepository;

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
}
