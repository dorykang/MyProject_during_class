package com.my.project;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import mybatis.dao.BbsDAO;
import mybatis.vo.BbsVO;
import mybatis.vo.MemVO;
import spring.util.FileUploadUtil;

@Controller
public class ViewController {
	@Autowired
	private BbsDAO b_dao;
	
	//새로고침을 이용해서 게시물 조회수를 높이는 것을 막는 방법
	//session에 ArrayList로 게시물을 저장한다! 
	@Autowired
	private HttpSession session;
	
	@Autowired
	private HttpServletRequest request;
	
	@Autowired
	private ServletContext application;
	
	//첨부파일이 저장될 위치
	private String uploadPath="/resources/upload";
		//webapp → resources에 upload라는 폴더를 만들어준다.
	
	@RequestMapping("view")
	public ModelAndView view(String cPage, String b_idx) {
		//session에 한번 봤던 게시물(BbsVO)를 저장해 두는 List<BbsVO>를 
		//"view_list"라는 이름으로 저장했다고 가정하자!
		Object obj=session.getAttribute("view_list");
		List<BbsVO> v_list = null;
		if(obj== null) {
			//저장한 적이 없는 경우는 새롭게 만들어서 저장한다. 
			v_list = new ArrayList<BbsVO>();
			
			//session에 없는 경우였으므로 session에 v_list에 저장한다.
			session.setAttribute("view_list", v_list);
		}else {
			v_list = (List<BbsVO>) obj;
		}
		
		//v_list에 저장된 요소들의 b_idx가 인자로 넘어온 b_idx와 같은 것이 있다면
		//예전에 한번 본적이 있는 게시물이다. 
		boolean chk=false;
		for(BbsVO bvo : v_list) {
			if(bvo.getB_idx().equals(b_idx)) {
				//같은 것이 있는 경우(한번 봤던 게시물)
				chk = true;
				break;
			}
		}
		
		//사용자가 선택한 게시물을 BbsVO로 검색한다.
		BbsVO vo = b_dao.getBbs(b_idx);
		
		if(!chk) {
			b_dao.updateHit(b_idx); //조회수 증가!
			vo.setHit(String.valueOf(Integer.parseInt(vo.getHit())+1));   
			v_list.add(vo);
		}
		
		ModelAndView mv=new ModelAndView();
		mv.addObject("vo",vo);
		
		//수정을 선택했을 때, edit.jsp에서 사용하기 위해 session에 저장 
		session.setAttribute("bvo", vo);
		
		mv.setViewName("bbs/view");
		
		return mv;
	}
	
	@RequestMapping("/edit")
	public ModelAndView edit(BbsVO vo) throws Exception {
		ModelAndView mv = new ModelAndView();
		String c_type = request.getContentType(); 
			//GET방식은 null, POST방식은 application으로 시작
			//지금은 둘 다 POST방식이니까 contentType으로 구분해야 한다. 
		if(c_type != null && c_type.startsWith("multipart")) {
			//실제 DB 수정작업을 해줘야 한다. 
			
			//첨부된 파일을 vo로부터 얻어낸다.
			MultipartFile mf=vo.getFile();
				//정상적인 접근에서 파일이 첨부되지 않았다면
				//mf가 null은 아니지만 용량이 0일 것이디ㅏ. 
			    //따라서 파일첨부에 대한 확인은 size로 한다. 
			if(mf != null && mf.getSize() > 0 && mf.getOriginalFilename().trim().length() > 0) {
				
				//절대 경로
				String path = application.getRealPath(uploadPath);
				
				//첨부된 파일의 이림
				String f_name = mf.getOriginalFilename();
				
				//첨부파일과 동일한 이름이 있다면 파일명 변경
				f_name=FileUploadUtil.checkSameFileName(f_name, path);
				
				mf.transferTo(new File(path, f_name)); //파일 업로드
					//throw Exception 해주자.
				
				//DB에 파일이름을 저장해야 하므로 이름을 vo에 저장해주자. 
				vo.setFile_name(f_name);
			}
			vo.setIp(request.getRemoteAddr());//접속자 IP
			b_dao.editBbs(vo);
			mv.setViewName("redirect:/view?b_idx="+vo.getB_idx()+"&cPage=");
		}else {
			//수정할 수 있는 화면으로 이동하라는 것(view.jsp에서 요청하는 경우)
			mv.setViewName("bbs/edit");
		}
		
		return mv;
	}
	
}