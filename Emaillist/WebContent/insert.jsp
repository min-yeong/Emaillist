<%@page import="com.bit.emaillist.dao.EmaillistDAOImpl"%>
<%@page import="com.bit.emaillist.dao.EmaillistDAO"%>
<%@page import="com.bit.emaillist.vo.EmailVO"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <!--  이페이지는 기능형 수행 -->
	<%
	// DAO 를 이영해서 insert 실행
	// 인코딩 실행
	request.setCharacterEncoding("UTF-8");
	// 서블릿 초기 화 파라미터에서 db 정보 확인
	ServletContext context = getServletContext();
	String dbuser = context.getInitParameter("dbuser");
	String dbpass = context.getInitParameter("dbpass");
	// 폼 데이터 확인
	String lastName = request.getParameter("last_name");
	String firstName = request.getParameter("first_name");
	String email = request.getParameter("email");
	// VO 생성
	EmailVO vo = new EmailVO(lastName, firstName, email);
	EmaillistDAO dao = new EmaillistDAOImpl(dbuser, dbpass);
	boolean isSuccess = dao.insert(vo);
	
	//성공하면 index.jsp로 Redirect
	if (isSuccess) response.sendRedirect(request.getContextPath());
	else response.sendRedirect(request.getContextPath()+"/form.jsp");
	%>