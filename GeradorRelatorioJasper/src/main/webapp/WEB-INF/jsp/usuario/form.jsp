<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
</head>
<body>
	<form action="${linkTo[UsuarioController].salvar}" method="post" >
		<label>Nome: </label><br/>
		<input name = "entidade.nome" value="${entidade.nome}">
		<br/>
		<label>Email: </label><br/>
		<input name = "entidade.email" value="${entidade.email}">
		<br/>
		<br/>
		<input type="submit" value="Enviar"/>
	</form>
</body>
</html>