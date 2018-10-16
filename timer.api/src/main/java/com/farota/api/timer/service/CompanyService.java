package com.farota.api.timer.service;

import java.util.ArrayList;
import java.util.HashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.farota.api.timer.config.filter.ErrorException;
import com.farota.api.timer.mapper.biz.CompanyMapper;
import com.farota.core.domain.Company;
import com.farota.core.domain.LoginUser;
import com.farota.core.extend.ResponseCode;


@Service
public class CompanyService {
	
	@Autowired private CompanyMapper companyMapper;

	/**
	 * 회사 목록
	 * @param
	 * @return ArrayList<Company>
	 */
	public ArrayList<Company> getCompanyList(LoginUser user, Integer offset, Integer size, String query){
		HashMap<String, Object> param = new HashMap<String, Object>();
		param.put("offset", offset);
		param.put("size", size);
		param.put("query", query);
		
		return companyMapper.getCompanyList(user, param);
	}
	
	/**
	 * 회사정보
	 * @param
	 * @return Company
	 */
	public Company getCompany(LoginUser user, Integer companyIndex){
		HashMap<String, Object> param = new HashMap<String, Object>();
		param.put("companyIndex", companyIndex);
		
		return companyMapper.getCompany(user, param);
	}
	
	/**
	 * 회사입력
	 * @param
	 * @return Company
	 */
	@Transactional
	public void postCompany(LoginUser user, Company company){
		if(companyMapper.postCompany(user, company) != 1) {
			throw new ErrorException(ResponseCode.SERVER_FAIL);
		}
	}
	
	/**
	 * 회사수정
	 * @param
	 * @return Company
	 */
	@Transactional
	public void putCompany(LoginUser user, Company company){
		if(companyMapper.putCompany(user, company) != 1) {
			throw new ErrorException(ResponseCode.SERVER_FAIL);
		}
	}
}