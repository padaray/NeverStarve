package com.NeverStarve.init;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.sql.Blob;
import java.time.LocalDate;

import javax.servlet.ServletContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.NeverStarve.member.model.MemberBean;
import com.NeverStarve.member.repository.MemberRepository;
import com.fasterxml.jackson.databind.introspect.TypeResolutionContext.Empty;

@Service
public class MemberInitImp implements MemberInitins  {

	@Value("classpath:static/data/member.json")
	static String memberData;

	@Autowired
	ServletContext ctx;

	@Autowired
	MemberRepository memberDao;
	
	@Override
	public void initData() {
		
	
	String realPath = ctx.getRealPath("/");
	String line = "";
	int count = 0;

	try(
	FileInputStream fis = new FileInputStream(realPath + "/data/members.txt");
	InputStreamReader isr0 = new InputStreamReader(fis, "UTF-8");
	BufferedReader br = new BufferedReader(isr0);)
	{
		while ((line = br.readLine()) != null) {
			String[] sa = line.split("\\|");
			MemberBean bean = new MemberBean();
			bean.setPersonId(sa[0]);
			bean.setName(sa[1]);
			String pswd = sa[2];
			bean.setPassword(pswd);
			bean.setMemberCity(sa[3]);
			bean.setMemberTown(sa[4]);
			bean.setAddress(sa[3]+sa[4]+sa[5]);
			bean.setEmail(sa[6]);
			bean.setMobilePhone(sa[7]);
			bean.setUserType(sa[8]);
			bean.setRegisterTime(LocalDate.now());
			// --------------處理Blob(圖片)欄位----------------
			if (!sa[9].isBlank()) {				
			Blob sb = GlobalService.fileToBlob(realPath + "/" + sa[9]);
			bean.setCoverImage(sb);
			String imageFileName = sa[9].substring(sa[9].lastIndexOf("/") + 1);
			bean.setFileName(imageFileName);
			}
			
			memberDao.save(bean);
			count++;
			System.out.println("新增" + count + "筆記錄:" + sa[1]);
		}
	}catch(Exception ex){
		ex.printStackTrace();
	}
	}
}
