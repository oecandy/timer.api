package com.farota.api.timer.config.filter;

import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerResponseContext;
import javax.ws.rs.container.ContainerResponseFilter;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.ext.Provider;

@Provider
public class OifResponseFilter implements ContainerResponseFilter {

//	private static Logger logger = LoggerFactory.getLogger(OifResponseFilter.class);

	@Override
	public void filter(ContainerRequestContext requestContext, ContainerResponseContext responseContext){
		
		MultivaluedMap<String, Object> headers = responseContext.getHeaders();
		
		headers.add("Access-Control-Allow-Origin", "*");
		headers.add("Access-Control-Allow-Methods", "GET, POST, DELETE, PUT, PATCH, OPTIONS");			
		headers.add("Access-Control-Allow-Headers", "Accept, Content-Type, access-token");

	}
}