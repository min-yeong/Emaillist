<%@page import="com.bit.emaillist.dao.EmaillistDAOImpl"%>
<%@page import="com.bit.emaillist.dao.EmaillistDAO"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<% 
String no = request.getParameter("no");
// db정보 불러오기
ServletContext context = getServletContext();
String dbuser = context.getInitParameter("dbuser");
String dbpass = context.getInitParameter("dbpass");

EmaillistDAO dao = new EmaillistDAOImpl(dbuser,dbpass);
boolean isSuccess = dao.delete(Long.valueOf(no));

if (isSuccess) response.sendRedirect(request.getContextPath());
else {
	%>
	<h1>Error</h1>
	<p>삭제하지 못했어요</p>
	<a href="<%= request.getContextPath() %>">목록보기</a>
	<%
}
%>