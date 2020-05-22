<%@page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@page isELIgnored="false"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<title>ユーザー新規登録</title>

</head>
	<body>
		<c:if test="${not empty errorMessages }">
			<div class="errorMessages">
				<ul>
					<c:forEach items="${errorMessages}" var="messages">
						<li><c:out value="${messages}"></c:out>
					</c:forEach>
				</ul>
			</div>
			<c:remove var="errorMessages" scope="session" />
		</c:if>
		<form action="newuser" method="post">
			<label for="login_id">ログインID：</label> <input type="text" name="loginId" id="login_id" value="${errorUser.loginId}"><br/>
			<label for="password">パスワード：</label> <input type="password" name="password" id="password" >
			<label for="password2">確認用パスワード：</label> <input type="password" name="password2" id="password2"><br/>
			<label for="name">名前：</label> <input name="name" id="name" value="${errorUser.name}"><br/>
			<label>支店：</label>
			<select name="branchId">
				<c:forEach items="${branches}" var="branch">
					<c:choose>
						<c:when test="${branch.id == errorUser.branchId}">
							<option value="${branch.id}" selected>${branch.name}</option>
						</c:when>
						<c:otherwise>
							<option value="${branch.id}">${branch.name}</option>
						</c:otherwise>
					</c:choose>
				</c:forEach>
			</select>
			<label>部署・役職</label>
			<select name="positionId">
				<c:forEach items="${positions}" var="position">
					<c:choose>
						<c:when test="${position.id == error.positionId}">
							<option value="${position.id}" selected>${position.name}</option>
						</c:when>
						<c:otherwise>
							<option value="${position.id}">${position.name}</option>
						</c:otherwise>
					</c:choose>
				</c:forEach>
			</select>
			<input type="submit" value="登録">
		</form>
	</body>
</html>