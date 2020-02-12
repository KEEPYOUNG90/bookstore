package com.bookshop01.cs.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.bookshop01.common.base.BaseController;
import com.bookshop01.cs.service.CSService;
import com.bookshop01.cs.vo.csVO;
import com.bookshop01.member.vo.MemberVO;

@Controller("csController")
@RequestMapping(value="/cs")
public class CSControllerImpl extends BaseController implements CSController{
	@Autowired
	CSService csService;
	@Autowired
	csVO csVO;
	@Autowired
	MemberVO memberVO;
	
	@RequestMapping(value="/csList.do" ,method = RequestMethod.GET)
	public ModelAndView myCartMain(HttpServletRequest request, HttpServletResponse response)  throws Exception {
		String viewName=(String)request.getAttribute("viewName");
		ModelAndView mav = new ModelAndView(viewName);
		HttpSession session=request.getSession();
		
		List<csVO> CSList = csService.CSList();
		session.setAttribute("CSList", CSList);//��ٱ��� ��� ȭ�鿡�� ��ǰ �ֹ� �� ����ϱ� ���ؼ� ��ٱ��� ����� ���ǿ� �����Ѵ�.
		//mav.addObject("cartMap", cartMap);
		return mav;
	}
}