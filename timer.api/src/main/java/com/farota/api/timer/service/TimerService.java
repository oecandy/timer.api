package com.farota.api.timer.service;

import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.farota.api.timer.config.filter.ErrorException;
import com.farota.api.timer.domain.Timer;
import com.farota.api.timer.domain.TimerTemplate;
import com.farota.api.timer.mapper.biz.TimerMapper;
import com.farota.core.domain.LoginUser;
import com.farota.core.extend.ResponseCode;

@Service
public class TimerService {
	
	@Autowired
	private TimerMapper timerMapper;
	
	/**
	 * 타이머 템플레이트 뷰
	 * @param
	 * @return
	 */
	public TimerTemplate getTimerTemplate(LoginUser user, Timer timer) {
		HashMap<String, Object> param = new HashMap<String, Object>();
		param.put("companyId", timer.getCompanyId());
		param.put("siteId", timer.getSiteId());
		param.put("timerTId", timer.getTimerTId());
		return timerMapper.getTimerTemplate(user, param);
	}
	
	public Timer getTimer(LoginUser user, Timer timer) {
		HashMap<String, Object> param = new HashMap<String, Object>();
		param.put("companyId", timer.getCompanyId());
		param.put("siteId", timer.getSiteId());
		param.put("timerId", timer.getTimerId());
		param.put("timerTId", timer.getTimerTId());
		return timerMapper.getTimer(user, param);
	}
	
	public void postTimer(LoginUser user, Timer timer) {
		if(timerMapper.postTimer(user, timer) != 1) {
			throw new ErrorException(ResponseCode.SERVER_FAIL);
		}
	}
	
	public void postTimerTemplate(LoginUser user, TimerTemplate timerTemplate) {
		if(timerMapper.postTimerTemplate(user, timerTemplate) != 1) {
			throw new ErrorException(ResponseCode.SERVER_FAIL);
		}
	}
	
	public void startTimer(TimerTemplate timerTemplate) {
		while(true) {
			TimerRun timerRun= new TimerRun();
			java.util.Timer t = new java.util.Timer();
			
			int startTime = timerTemplate.getStartTime();
			
			Calendar cal = Calendar.getInstance();
			
			int year = cal.get(Calendar.YEAR);
	        int mon = cal.get(Calendar.MONTH);
	        int day = cal.get(Calendar.DAY_OF_MONTH);
	        int hour = startTime/24;
	        int min = startTime%24;
	        int sec = 0;
	        
	        cal.set(year, mon, day, hour, min, sec);
	        
			Date time = cal.getTime();
			System.out.println(time);
			t.schedule(timerRun, time);
			
			try {
				Thread.sleep(1000 * 86400 * 7);
			} catch(Exception e) {
				
			}
		}
	}

}
