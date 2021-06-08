package com.NeverStarve.member.service;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import com.NeverStarve.member.model.MemberBean;

public interface MemberService {
	Optional<MemberBean> getMamberById(int id);

	List<MemberBean> getMembers();

	MemberBean save(MemberBean bean);

	MemberBean updateMember(MemberBean bean);

	MemberBean queryMember(String id);

	void deleteMemberByPrimaryKey(int id);

	List<MemberBean> findByAddressContaining(String address);

	boolean emailExists(String email);

	// 搜尋時間內註冊的會員
	List<MemberBean> findByRegisterTimeBetween(String begin, String end);

	// 分頁會員
	Map<Integer, MemberBean> getPageMembers(int pageNo);

	int getRecordsPerPage();

	long getRecordCounts();

	int getTotalPages();

	void setRecordsPerPage(int recordsPerPage);

}
