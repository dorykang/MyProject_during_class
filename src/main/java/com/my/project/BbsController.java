package com.my.project;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import bbs.util.Paging;
import mybatis.dao.BbsDAO;
import mybatis.vo.BbsVO;

@Controller
public class BbsController {
	@Autowired
	private BbsDAO b_dao;
	
	private int blockList = 2; //한 페이지에 보여질 게시물 수
	private int blockPage =  5; //한 블럭당 보여질 페이지 수
	
	@RequestMapping("/bbs")
	public ModelAndView list(String bname, String cPage) {
		//rowTotal를 얻기 위해 bname을 인자로 받는다.
		//현재페이지(cPage) 또한 인자로 받을 수 있다. 
		//Spring의 장점! 인자로 처리가능하다는 것.

		ModelAndView mv = new ModelAndView();
		
		//rowTotal을 얻자. 
		if(bname==null) {
			bname="BBS";
		}
		int rowTotal = b_dao.totalCount(bname);
		mv.addObject("rowTotal", rowTotal);
		
		//사용자가 선택한 페이지 번호를 받아야 한다. 
		int c_page = 1;
		if(cPage != null) { //무엇인가 넘어왔다는 것
			c_page = Integer.parseInt(cPage);
		}
			//현재 페이지값인 cPage값이 넘어오지 않는다면 
			//무조건 첫번째 페이지(1)가 기본이 된다.
		
		//Paging 객체를 생성한다.
		Paging page = new Paging(c_page, rowTotal, blockList, blockPage);
		mv.addObject("p_code", page.getSb().toString());
		mv.addObject("nowPage", c_page);
		mv.addObject("blockList", blockList);
		
		//목록을 얻어낸다.
		BbsVO[] ar = b_dao.getList(page.getBegin(), page.getEnd(), bname);
		mv.addObject("ar", ar); //list.jsp의 EL에서 ar로 받는다.
		
		//화면을 표현할 뷰페이지를 설정
		mv.setViewName("bbs/list");
		return mv;
	}
}
