package com.farota.api.timer.config.filter;

import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.SignatureException;

import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.core.Request;
import javax.ws.rs.ext.Provider;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.farota.core.cipher.JWTCrypt;
import com.farota.core.config.LocalConstant;
import com.farota.core.extend.ResponseCode;
import com.fasterxml.jackson.databind.JsonNode;

@Provider
public class OifAuthFilter implements ContainerRequestFilter {

	@Autowired JWTCrypt jwtCrypt;

	private static Logger logger = LoggerFactory.getLogger(OifAuthFilter.class);

	@Override
	public void filter(ContainerRequestContext requestContext) throws IOException {

		Request request = (Request) requestContext.getRequest();

		String authCheckPath = requestContext.getUriInfo().getPath();
		
		// swagger auth 예외
		if((authCheckPath.indexOf("swagger") > -1)){
			return;
		}

		if(request.getMethod().equals("OPTIONS")){
			throw new ErrorException(ResponseCode.OK);
		}
		
		String encAccessToken = (String) requestContext.getHeaderString("access-token");
		
		if(encAccessToken == null || encAccessToken.equals("")){
			throw new ErrorException(ResponseCode.UNAUTHORIZED);
		}
		
		try {
			requestContext.getHeaders().add("X-Authentication", checkAccessToken(encAccessToken));
		} catch (Exception e) {
			throw new ErrorException(ResponseCode.UNAUTHORIZED);
		}
	}
	
	private String checkAccessToken(String encAccessToken) {
		
		String temp[] = encAccessToken.split("\\.");
		
		// 잘못된 토큰 구분자가 3개가 아님
		if(temp == null || temp.length != 3) {
			logger.debug("token의 구분자값이 3이 아닙니다.");
			throw new ErrorException(ResponseCode.UNAUTHORIZED);
		}
		
		// 사인값 검증
		if(!jwtCrypt.verifySignature(temp, LocalConstant.JWT_TOKEN_ALGORITHM)) {
			logger.debug("사인값 검증에 실패하였습니다.");
			throw new ErrorException(ResponseCode.UNAUTHORIZED);
		}
		
        // get JWTClaims JSON object
		JsonNode jwtPayload;
        try {
			jwtPayload = jwtCrypt.decodeAndParse(temp[1]);
			
			if(jwtPayload == null) {
				logger.debug("jwtPayload가 null입니다.");
				throw new ErrorException(ResponseCode.UNAUTHORIZED);
	        }
			
		} catch (IOException e) {
			logger.debug("token을 JsonNode로 Parse하는 도중 에러가 발생하였습니다.");
			throw new ErrorException(ResponseCode.UNAUTHORIZED);
		}
        
        
		// 유효시간 검증
		if(!jwtCrypt.verifyExpiration(jwtPayload)) {
			logger.debug("token이 만료 되었습니다.");
			throw new ErrorException(ResponseCode.EXPIRED);
		}
		
		String accessTokenText = jwtCrypt.JsonNodeToString(jwtPayload);
		
		if(accessTokenText == null) {
			logger.debug("token형식이 잘못되었습니다.");
			throw new ErrorException(ResponseCode.UNAUTHORIZED);
		}
		
		return accessTokenText;
	}

}
