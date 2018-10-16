package com.farota.api.timer.domain;

import java.sql.Timestamp;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;


@Getter
@Setter
@ToString
@ApiModel
public class TimerLog {
   
	@ApiModelProperty(value="회사 식별자(개인사업자/법인의 경우 사업자번호, 개인의 경우 이메일 또는 전화번호)", required = true)
	private String companyId;
	
	@ApiModelProperty(value="", required = true)
	private String siteId;
	
	@ApiModelProperty(value="", required = true)
	private String timerId;
	
	@ApiModelProperty(value="", required = true)
	private Timestamp startDate;
	
	@ApiModelProperty(value="", required = true)
	private Integer seq;
	
	@ApiModelProperty(value="", required = true)
	private Integer startTime;
	
	@ApiModelProperty(value="", required = true)
	private Integer endTime;
	
	@ApiModelProperty(value="", required = false)
	private Integer command;
	
	@ApiModelProperty(value="", required = true)
	private String serialNo;
	
	@ApiModelProperty(value="", required = true)
	private Integer supplyWater;
	
}