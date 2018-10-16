package com.farota.api.timer.config.filter;

import javax.ws.rs.core.Context;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

import com.farota.core.extend.ResponseModel;

@Provider
public class ErrorExceptionHandler implements ExceptionMapper<ErrorException>{

    @Context
    private HttpHeaders headers;

    public Response toResponse(ErrorException ex){
    	ResponseModel error = new ResponseModel();
    	error.setResultCode(ex.getResponseCode().getResultCode());
    	error.setResultMessage(ex.getResponseCode().getResultMessage());
        return Response.ok().type(MediaType.APPLICATION_JSON_TYPE).entity(error).build();
    }
}