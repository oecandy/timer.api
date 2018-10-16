package com.farota.api.timer.endpoint;

import java.util.ArrayList;

import javax.ws.rs.DefaultValue;
import javax.ws.rs.GET;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import com.farota.api.timer.config.filter.ErrorException;
import com.farota.api.timer.service.CompanyService;
import com.farota.core.cipher.JWTCrypt;
import com.farota.core.domain.Company;
import com.farota.core.domain.LoginUser;
import com.farota.core.extend.LocalResponse;
import com.farota.core.extend.ResponseCode;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@Controller
@Path("/company")
@Api(value = "Front API - Company")
@Produces(MediaType.APPLICATION_JSON + ";charset=utf-8")
public class CompanyEndpoint {

	@Autowired private JWTCrypt jwtCrypt;
	@Autowired private CompanyService companyService;

//	@Path("")	//[컴파일 WARN 제거를 위한 주석처리]
	@GET
	@ApiOperation(value = "회사정보 리스트", notes = "")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Sucess", response = Company.class, responseContainer="list") })
	public Response getCompanyList(
			  @ApiParam(required=false, value="자동입력 입력 하지마세요.")	@HeaderParam("X-Authentication")					String accessToken
			, @ApiParam(required=false, value="페이지 offset 값")		@QueryParam("offset") 	@DefaultValue(value="0")	Integer offset
			, @ApiParam(required=false, value="페이지 size 값")		@QueryParam("size")		@DefaultValue(value="20") 	Integer size
			, @ApiParam(required=false, value="검색값(이름)")			@QueryParam("query")								String query
			) throws Exception {

		LoginUser user = jwtCrypt.getUserTextToModel(accessToken);
		
		// company_index 0이 아니면 에러
		if(user.getCompanyIndex() != 0) {
			throw new ErrorException(ResponseCode.FORBIDDEN);
		}
		
		ArrayList<Company> result = companyService.getCompanyList(user, offset, size, query);
		
		return LocalResponse.getResponse(result);
	}
	
	
	@Path("/{companyIndex}")
	@GET
	@ApiOperation(value = "회사정보", notes = "companyIndex=0 또는 본인 회사만 조회가능")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Sucess", response = Company.class) })
	public Response getCompany(
			  @ApiParam(required=false, value="자동입력 입력 하지마세요.")	@HeaderParam("X-Authentication")	String accessToken
			, @ApiParam(required=true, 	value="회사 일련번호")			@PathParam("companyIndex") 			Integer companyIndex
			) throws Exception {

		LoginUser user = jwtCrypt.getUserTextToModel(accessToken);
		
		// 유효성 검사
		if(companyIndex == null) {
			throw new ErrorException(ResponseCode.VALD_ERR);
		}
		
		// 조회할 수 있는 권한만 체크
		if(user.getCompanyIndex() != 0 && user.getCompanyIndex() != companyIndex) {
			throw new ErrorException(ResponseCode.FORBIDDEN);
		}
		
		Company result = companyService.getCompany(user, companyIndex);
		
		return LocalResponse.getResponse(result);
	}
	
//	@Path("") //[컴파일 WARN 제거를 위한 주석처리]
	@POST
	@ApiOperation(value = "회사정보 등록", notes = "회사정보를 등록한다.")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Sucess", response = Company.class) })
	public Response postCompany(
			  @ApiParam(required=false, value="자동입력 입력 하지마세요.")	@HeaderParam("X-Authentication")	String accessToken
			, @ApiParam(required=true, 	value="회사 정보")			 										Company company
			) throws Exception {

		LoginUser user = jwtCrypt.getUserTextToModel(accessToken);
		
//		TODO : User.company_id 필수여부 체크, User.name 필수여부 체크
		
		companyService.postCompany(user, company);
		
		return LocalResponse.getResponse(company);
	}
	
	
	@Path("/{companyIndex}")
	@PUT
	@ApiOperation(value = "회사정보 등록", notes = "회사정보를 등록한다.")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Sucess", response = Company.class) })
	public Response putCompany(
			  @ApiParam(required=false, value="자동입력 입력 하지마세요.")	@HeaderParam("X-Authentication")	String accessToken
			, @ApiParam(required=true, 	value="회사 정보")			 										Company company
			, @ApiParam(required=true, 	value="회사 일련번호")			@PathParam("companyIndex") 			Integer companyIndex
			) throws Exception {

		LoginUser user = jwtCrypt.getUserTextToModel(accessToken);
		
//		TODO : User.company_id 필수여부 체크, User.name 필수여부 체크
		
		if(company.getCompanyIndex() != companyIndex) {
			throw new ErrorException(ResponseCode.PARAM_NOT_MATCH);
		}
		
		companyService.putCompany(user, company);
		
		return LocalResponse.getResponse(company);
	}
	
}