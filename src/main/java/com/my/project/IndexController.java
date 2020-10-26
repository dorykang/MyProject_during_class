package com.my.project;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import mybatis.vo.MemVO;

@Controller
public class IndexController {
	
	@RequestMapping("/") 
	//아무것도 없는 상태에서는 index.jsp로 가는 것을 의미
	public String index() {
		return "index"; //views에 있는 index.jsp를 의미
	}
	
	/*
	@RequestMapping("/login")
	public String login() {
		return "login";
	}
	
	//같은 경로("login")으로 요청해도 구분가능한 이유는
	//기본적으로는 GET방식이지만 
	//아래처럼 코드를 쓰면 POST방식으로 들어오는 경우
	@RequestMapping(value = "/login", method=RequestMethod.POST)
	public ModelAndView login(MemVO vo) {
		ModelAndView mv = new ModelAndView();
		
		System.out.println(vo.getM_id()+"/"+vo.getM_pw());
		return mv;
	}*/
}