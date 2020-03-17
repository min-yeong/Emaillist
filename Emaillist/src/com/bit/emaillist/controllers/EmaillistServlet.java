package com.bit.emaillist.controllers;

import java.io.IOException;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.bit.emaillist.dao.EmaillistDAO;
import com.bit.emaillist.dao.EmaillistDAOImpl;
import com.bit.emaillist.vo.EmailVO;


@WebServlet(name="EnailllistServlet", urlPatterns="/el")
public class EmaillistServlet extends BaseServlet {
	// @WebServlet 어노테이션을 잉요한 서블릿 매핑
	// @WebServlet("/el") -> urlPattern만 필요한 경우
	// Model2 에서 Servlet 은 Controller 의 역할 수행
	// Model1 의 JSP가 요청처리, 로직처리를 모두 담당했던 역할을 대신 수행
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// Action parameter를 이용한 조건 분기
		String action = req.getParameter("a");
		
		if("form".equals(action)) {
			// a = form 일 경우 
			RequestDispatcher rd = getServletContext().getRequestDispatcher("/WEB-INF/views/emaillist/form.jsp");
			rd.forward(req, resp);
		} else {
			// List 페이지 처리
			EmaillistDAO dao = new EmaillistDAOImpl(dbuser, dbpass);
			List<EmailVO> list = dao.getList();
		
			// 사용자로부터 받은 요청 객체 + controller에서 수행한 로직을 수행하고 난 후의 Model을 싣고 jsp로 제어권을 넘김
			req.setAttribute("list", list);
			RequestDispatcher rd = getServletContext().getRequestDispatcher("/WEB-INF/views/emaillist/index.jsp");  
		
			// 요청과 응답의 제어권을 jsp로 전달
			rd.forward(req, resp);
		}
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String action = req.getParameter("a");
		if("insert".equals(action)) {
			// a hidden field가 insert인 경우의 처리
			String lastName = req.getParameter("last_name");
			String firstName = req.getParameter("first_name");
			String email = req.getParameter("email");
			
			EmailVO vo = new EmailVO(lastName, firstName, email);
			EmaillistDAO dao = new EmaillistDAOImpl(dbuser, dbpass);
			
			boolean isSuccess = dao.insert(vo);
			if(isSuccess) System.out.println("INSERT SUCCESS");
			else System.err.println("INSERT FAILED");
			
			resp.sendRedirect(req.getContextPath()+"/el");
		} 
		else if("delete".equals(action)) {
			// a hidden 필드가 delete 일 경우를 처리
			String no = req.getParameter("no");
			EmaillistDAO dao = new EmaillistDAOImpl(dbuser, dbpass);
			
			boolean isSuccess = dao.delete(Long.valueOf(no));
			if (isSuccess) System.out.println("DELETE SUCCESS");
			else System.err.println("DELETE FAILED");
			
			resp.sendRedirect(req.getContextPath()+"/el");
		}
		else {
			resp.sendError(405);
		}
	}
	
}
