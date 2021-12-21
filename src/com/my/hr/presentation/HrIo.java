package com.my.hr.presentation;

import java.time.LocalDate;
import java.util.List;

import com.my.hr.domain.Laborer;
import com.my.hr.service.HrService;

public class HrIo {
	private HrService hrService;
	private int menuLength;
	private String menu;
	
	public HrIo(HrService hrService, String[] menuItems) {
		this.hrService = hrService;
		this.menuLength = menuItems.length;
		this.menu = Console.menu(menuItems);
	}
	
	public void play() {
		Job job = null;
		while((job = choose(menu)) != Job.EXIT) {
			switch(job) {
			case LIST: listLaborers(); break;
			case ADD: addLaborer(); break;
			case FIX: fixLaborer(); break;
			case DEL: delLaborer();
			}
		}
	}
	
	private Job choose(String menu) {
		boolean isGood = false;
		int choice = 0;
		
		do {
			System.out.println();
			choice = Console.inNum(menu);
			if(choice < 0 || choice > menuLength)
				Console.err("존재하는 메뉴 번호를 입력하세요.");
			else isGood = true;
		} while(!isGood);
		
		return toJob(choice);
	}
	
	private Job toJob(int num) {
		Job job = null;
		
		switch(num) {
		case 0: job = Job.EXIT; break;
		case 1: job = Job.LIST; break;
		case 2: job = Job.ADD; break;
		case 3: job = Job.FIX; break;
		case 4: job = Job.DEL;
		}
		
		return job;
	}
	
	private void listLaborers() {
		List<Laborer> laborers = hrService.getLaborers();
		
		System.out.println("ID 이름         입사일");
		System.out.println("-------------------");
		
		if(laborers.size() > 0) 
			for(Laborer laborer: laborers) Console.info(laborer);
		else Console.info("노동자가 없습니다.");
	}
	
	private void addLaborer() {
		String laborerName = Console.inStr("노동자 이름을 입력하세요.", 5);
		
		if(!laborerName.equals("0")) {
			LocalDate hireDate = Console.inDate("입사일을 입력하세요.");
			hrService.addLaborer(laborerName, hireDate);
			Console.info("노동자를 추가했습니다.");
		} else Console.info("노동자 추가를 취소합니다.");		
	}
	
	private void fixLaborer() {
		if(hrService.getLaborers().size() > 0) {			
			int laborerId = getLaborerId("노동자 수정");
			if(laborerId > 0) {
				String laborerName = Console.inStr("노동자 이름을 입력하세요.", 5);
				LocalDate hireDate = Console.inDate("입사일을 입력하세요.");
				
				hrService.fixLaborer(new Laborer(laborerId, laborerName, hireDate));
				Console.info("노동자를 수정했습니다.");
			}
		} else Console.info("노동자가 없습니다.");
	}
	
	private void delLaborer() {
		if(hrService.getLaborers().size() > 0) {
			int laborerId = getLaborerId("노동자 삭제");
			if(laborerId > 0) {
				hrService.delLaborer(laborerId);
				Console.info("노동자를 삭제했습니다.");
			}
		} else Console.info("노동자가 없습니다.");
	}
	
	private int getLaborerId(String job) {
		Laborer laborer = null;
		int laborerId = 0;
		
		do {
			laborerId = Console.inNum("노동자ID를 입력하세요.");
			if(laborerId == 0) {
				Console.info(job + "를 취소합니다.");
				return 0;
			}
			
			laborer = hrService.getLaborer(laborerId);
			if(laborer == null) Console.err("입력한 ID의 노동자가 없습니다.");
		} while(laborer == null);
		
		return laborerId;
	}
}


















