package com.farota.api.timer.mapper.biz;

import java.util.HashMap;

import org.apache.ibatis.annotations.Param;

import com.farota.api.timer.domain.Timer;
import com.farota.api.timer.domain.TimerTemplate;
import com.farota.core.domain.LoginUser;

public interface TimerMapper {

	/**
	 * 타이머정보
	 * 
	 * @return TimerTemplate
	 */
	public TimerTemplate getTimerTemplate(@Param("user") LoginUser user, @Param("param") HashMap<String, Object> param);

	public Timer getTimer(@Param("user") LoginUser user, @Param("param") HashMap<String, Object> param);

	public int postTimer(@Param("user") LoginUser user, @Param("timer") Timer timer);

	public int postTimerTemplate(@Param("user") LoginUser user, @Param("timerTemplate") TimerTemplate timerTemplate);

}
