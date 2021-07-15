package com.NeverStarve.member.service;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.servlet.http.HttpSession;

import com.NeverStarve.member.model.MemberBean;

public interface MemberService {
	Optional<MemberBean> getMamberById(int id);

	List<MemberBean> getMembers();

	MemberBean save(MemberBean bean);

	MemberBean updateMember(MemberBean bean);

	MemberBean queryMember(String id);
	
	MemberBean loginMember(String email, String password);

	void deleteMemberByPrimaryKey(int id);

	List<MemberBean> findByAddressContaining(String address);

	boolean emailExists(String email);
	
	// 搜尋時間內註冊的會員
	List<MemberBean> findByRegisterTimeBetween(String begin, String end); 
	
	// 分頁會員
	Map<Integer, MemberBean> getPageMembers(int pageNo);														//無條件搜尋會員
	Map<Integer, MemberBean> findByRegisterTimeBetween(int pageNo, String begin, String end); 			//搜尋時間內註冊的會員
	Map<Integer, MemberBean> findByAddressContaining(int pageNo,String adderss);								//設定城市條件搜尋會員
	Map<Integer, MemberBean> findCityandRegisterTime(int pageNo, String start, String end, String address);//搜尋限定城市時間內註冊的會員
	Map<Integer, MemberBean> getMemberData(int pageNo,String adderss,String start, String end);											//中繼功能
//	Map<Integer, MemberBean> getPageCityMembers(String city, int pageNo);	
//	Map<Integer, MemberBean> getPageRTimeMembers(Timestamp begin,Timestamp end,int pageNo);
//	Map<Integer, MemberBean> getPageCAndRMembers(Timestamp begin,Timestamp end,String address,int pageNo);

	int getRecordsPerPage();

	long getRecordCounts();

	int getTotalPages();

	void setRecordsPerPage(int recordsPerPage);

	long getTotalcount();
	
	long getCityCount(String address);

	public MemberBean cookieLogin(String email);
	
	//寄簡單信件
    void sendSimpleMail(String to, String subject, String content);
    //記驗證信
    void sendForgotPasswordMail(String to, String subject, String content);
    //確認session CODE跟用戶的CODE是否一致
    boolean checkCode(String userCode);
    //忘記密碼的更新
    void updateForgotPassword (String password,String email);
 
}
