<%@page import="com.bit.emaillist.vo.EmailVO"%>
<%@page import="java.util.List"%>
<%@page import="com.bit.emaillist.dao.EmaillistDAOImpl"%>
<%@page import="com.bit.emaillist.dao.EmaillistDAO"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
// Context 초기화 파라미터 불러오기
ServletContext context = getServletContext();
String dbuser = context.getInitParameter("dbuser");
String dbpass = context.getInitParameter("dbpass");
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>메일 리스트</title>
</head>
<body>
	<h1>Email List</h1>
	<%
	EmaillistDAO dao = new EmaillistDAOImpl(dbuser, dbpass);
	List<EmailVO> list = dao.getList();
	
	for(EmailVO vo : list) {
	%>
	<!-- 리스트 출력을 위해 Loop -->
	<table border = "1">
			<tr>
				<th>성</th>
				<td><%= vo.getLastName() %></td>
			</tr>
			<tr>
				<th>이름</th>
				<td><%= vo.getFirstName() %></td>
			</tr>
			<tr>
				<th>이메일</th>
				<td><%= vo.getEmail() %></td>
			</tr>
			<!--삭제버튼 -->
			<tr>
				<td colspan = "2">
					<form method="POST" action="<%= request.getContextPath() %>/delete.jsp">
					<input type="hidden" name="no" value="<%= vo.getNo() %>">
					<button type="submit">삭제</button>
					</form>
				</td>
	</table>
	<% } %>
	<!-- 작성 폼으로 링크 -->
	<a href="<%= request.getContextPath() %>/form.jsp">이메일등록</a>
</body>
</html>