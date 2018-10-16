package com.farota.api.timer.service;

import java.util.TimerTask;

public class TimerRun extends TimerTask{

	@Override
	public void run() {
		// TODO Auto-generated method stub
		
		//밸브 제어 메소드
		System.out.println("밸브 열림");
		
	}

}
