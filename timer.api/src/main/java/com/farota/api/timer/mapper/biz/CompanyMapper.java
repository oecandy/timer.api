package com.farota.api.timer.mapper.biz;

import java.util.ArrayList;
import java.util.HashMap;

import org.apache.ibatis.annotations.Param;

import com.farota.core.database.DsBiz;
import com.farota.core.domain.Company;
import com.farota.core.domain.LoginUser;

@DsBiz
public interface CompanyMapper {
	
	/**
	 * 회사 목록
	 * @return ArrayList<Company>
	 */
	public ArrayList<Company> getCompanyList(@Param("user") LoginUser user, @Param("param") HashMap<String, Object> param);
	
	/**
	 * 회사정보
	 * @return Company
	 */
	public Company getCompany(@Param("user") LoginUser user, @Param("param") HashMap<String, Object> param);
	
	/**
	 * 회사정보 등록
	 * @return Company
	 */
	public int postCompany(@Param("user") LoginUser user, @Param("company") Company company);
	
	/**
	 * 회사정보 수정
	 * @return Company
	 */
	public int putCompany(@Param("user") LoginUser user, @Param("company") Company company);
	
}