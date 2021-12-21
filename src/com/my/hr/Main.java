package com.my.hr;

import com.my.hr.dao.LaborerDao;
import com.my.hr.dao.LaborerDaoImpl;
import com.my.hr.presentation.Console;
import com.my.hr.presentation.HrIo;
import com.my.hr.service.HrService;
import com.my.hr.service.HrServiceImpl;

public class Main {
	public static void main(String[] args) {
		String[] menuItems = {"목록", "추가", "수정", "삭제"};		
		
		LaborerDao laborerDao = new LaborerDaoImpl();
		HrService hrService = new HrServiceImpl(laborerDao);
		HrIo hrIo = new HrIo(hrService, menuItems);
		
		hrIo.play();
		Console.info("끝.");
	}
}
