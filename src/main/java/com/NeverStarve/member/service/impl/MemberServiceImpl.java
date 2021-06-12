package com.NeverStarve.member.service.impl;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import com.NeverStarve.member.model.MemberBean;
import com.NeverStarve.member.repository.MemberRepository;
import com.NeverStarve.member.service.MemberService;

@Service
public class MemberServiceImpl implements MemberService {

	private int recordsPerPage = 5; // 預設每頁三筆;
	private int totalPages = -1;
	private long totalcount=0;

	@Autowired
	MemberRepository memberDao;

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
		beans.getTotalElements();
		List<MemberBean> list = beans.getContent();
		for (MemberBean bean : list) {
			map.put(bean.getPkMemberId(), bean);
		}
		return map;
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

		totalPages = (int) (Math.ceil(getRecordCounts() / (double) recordsPerPage));
		return totalPages;
	}

	@Override
	public void setRecordsPerPage(int recordsPerPage) {
		this.recordsPerPage = recordsPerPage;

	}

	// 搜尋註冊時間內的會員
	@Override
	public List<MemberBean> findByRegisterTimeBetween(String begin, String end) {
		// TODO Auto-generated method stub
		return null;
	}		
	@Override
	public Map<Integer, MemberBean> findByAddressContaining(int pageNo,String adderss){
		Map<Integer, MemberBean> map = new LinkedHashMap<>();	
		Page<MemberBean> beans 	=memberDao.findByAddressContaining(PageRequest.of(pageNo - 1, recordsPerPage), adderss);
		List<MemberBean> list = beans.getContent();
		setTotalcount(beans.getTotalElements());
		beans.getTotalElements();
		for (MemberBean bean : list) {
			map.put(bean.getPkMemberId(), bean);
		}
		return map;
	}

	public long getTotalcount() {
		return totalcount;
	}

	public void setTotalcount(long totalcount) {
		this.totalcount = totalcount;
	}

	

}
