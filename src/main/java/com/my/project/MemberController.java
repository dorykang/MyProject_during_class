package com.my.project;

import java.util.Hashtable;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import mybatis.dao.MemDAO;
import mybatis.vo.MemVO;

@Controller
public class MemberController {
	
	@Autowired
	private MemDAO m_dao;
	//autowired되어 있으므로 MemberController 객체가 생성될 때
	//MemDAO객체가 같이 생성된다. 
	//(이게 가능하도록 하기 위해 root-context.xml에서 DAO를 component-scan한 것)
	
	@Autowired
	private HttpSession session; //자동으로 저장된다. 
	
	@RequestMapping("/login") //GET방식 호출시
	public String login() {
		return "login";
	}
	
	@RequestMapping(value="/login", method=RequestMethod.POST) //POST방식 호출시
	@ResponseBody //JSON으로 보낼 수 있게 해준다. 
	public Map<String, Object> login(String m_id, String m_pw){
		
		Map<String, Object> map = new Hashtable<String, Object>();
		
		//MemDAO의 login함수를 호출하면서 인자 두 개를 전달하면 
		//정확한 정보일 때만 MemVO 한 개를 받게 된다. 
		MemVO mvo = m_dao.login(m_id, m_pw);
		
		//mvo가 null이면 아이디 및 비번을 잘못 입력한 것.
		if(mvo != null) {
			//session에 mvo라는 이름으로 mvo를 저장한다.
			session.setAttribute("mvo", mvo);
			map.put("res", "1");
			map.put("mvo", mvo);
		}else {
			map.put("res", "0");
		}
		
		return map;
	}
	
	@RequestMapping("/logout")
	@ResponseBody
	public Map<String, String> logout() {
		//세션으로부터 저장된 mvo를 삭제하는 것이 목적
		session.removeAttribute("mvo");
		
		Map<String, String> map = new Hashtable<String, String>();
		map.put("res", "0");
		return map;
	}
	
}
