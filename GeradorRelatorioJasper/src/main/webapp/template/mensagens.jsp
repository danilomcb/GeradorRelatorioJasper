<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<c:if test="${!empty errors}">
	<div class="alert alert-error">
		<a class="close" data-dismiss="alert" href="#">×</a>
		<c:forEach var="erro" items="${errors}">		
			${erro.message}<br />
		</c:forEach>
	</div>
</c:if>