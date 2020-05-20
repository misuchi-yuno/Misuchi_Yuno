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
			<label for="login_id">ログインID：</label> <input type="text" name="loginId" id="login_id" ><br/>
			<label for="password">パスワード：</label> <input type="password" name="password" id="password" >
			<label for="password2">確認用パスワード：</label> <input name="password2" id="password2"><br/>
			<label for="name">名前：</label> <input name="name" id="name" ><br/>
			<label for="branch_id">支店番号：</label> <input name="branchId" id="brabch_id" ><br/>
			<label for="position_id">部署・役職：</label> <input name="positionId" id="position_id" ><br/>
			<input type="submit" value="登録">

		</form>
	</body>
</html>