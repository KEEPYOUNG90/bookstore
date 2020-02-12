<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"	isELIgnored="false"
	%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<c:set var="contextPath"  value="${pageContext.request.contextPath}"  />
<%
  request.setCharacterEncoding("UTF-8");
%>  
<div>

<table class="list_cs">
		<tbody align=center >
			<tr style="background:#33ff00" >
				<td class="fixed" >번호</td>
				<td class="fixed">제목</td>
				<td>작성자</td>
				<td>등록일</td>
			</tr>
			
			 <c:choose>
				    <c:when test="${empty CSList}">
				    <tr>
				       <td colspan=8 class="fixed">
				         <strong>등록된 질문이 없습니다.</strong>
				       </td>
				     </tr>
				    </c:when>
			     <c:otherwise>
			 <tr>       
               <form name="frm_order_all_cs">
				 <c:forEach var="item" items="${CSList}" varStatus="cnt">
					<td>${item.acrticle_id}</td>
					<td>${item.article_title}</td>
					<td>${item.user_id}</td>
					<td>${item.regdate}</td>
		   		</c:forEach>
			   </c:otherwise>
			</c:choose> 
		</tbody>
	</table>
  </div>  	