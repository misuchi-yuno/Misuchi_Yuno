<%@page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@page isELIgnored="false"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<title>編集画面</title>
		<link href="./js.css" rel="stylesheet" type="text/css">
	</head>
<body>
	<label>編集</label>
	<div class="main-contents">
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

		<form action="edit" method="post" name="edit" onsubmit="return checkText()">
			<input type="hidden" name="id" id="id" value="${editUser.id}">
			<label for="loginId">ログインID：</label> <input type="text" name="loginId" id="loginId" value=${editUser.loginId } required>
			<p class="errorMessages" id="loginIdError"></p><br/>
			<label for="password">パスワード：</label> <input type="password" name="password" id="password" >
			<label for="password2">確認用パスワード：</label> <input type="password" name="password2" id="password2">
			<p class="errorMessages" id="passwordError"></p>
			<p class="errorMessages" id="conPasswordError"></p><br/>
			<label for="name">名前：</label> <input name="name" id="name" value="${editUser.name}" required>
			<p class="errorMessages" id="nameError"></p><br/>
			<label>支店：</label>
			<select name="branchId">
				<c:forEach items="${branches}" var="branch">
					<c:choose>
						<c:when test="${branch.id == editUser.branchId}">
							<option value="${branch.id}" selected>${branch.name}</option>
						</c:when>
						<c:otherwise>
							<option value="${branch.id}">${branch.name}</option>
						</c:otherwise>
					</c:choose>
				</c:forEach>
			</select>
			<br/><label>部署・役職</label>
			<select name="positionId">
				<c:forEach items="${positions}" var="position">
					<c:choose>
						<c:when test="${position.id == editUser.positionId}">
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
		<script type="text/javascript">
			function checkText() {
				var error = true;
				nameError.innerHTML = "";
				loginIdError.innerHTML = "";
				passwordError.innerHTML = "";
				conPasswordError.innerHTML = "";

				const name = document.getElementById("name").value;
				if (name.length >= 11) {
					nameError.innerHTML = "10文字以内で入力してください";
					error = false;
				}

				const loginId = document.getElementById("loginId").value;
				const trueLoginId = /^[a-z A-Z 0-9]{6,20}$/;
				if (!loginId.match(trueLoginId)) {
					loginIdError.innerHTML = "半角英数字6～20文字で入力してください";
					error = false;
				}

				const password = document.getElementById("password").value;
				const password2 = document.getElementById("password2").value;
				const truePassword = /^[ -~]{6,20}$/
				if (!password && password2) {
					passwordError.innerHTML = "パスワードを入力してください";
					error = false;
				} else if (password) {
					if (password != password2) {
						conPasswordError.innerHTML = "パスワードと確認用パスワードが違います";
						error = false;
					}
					if (!password.match(truePassword)) {
						passwordError.innerHTML = "パスワードは記号を含む半角英数字6～20文字で入力してください";
						error = false;
					}
				}
				return error;
			}
		</script>
	</div>
</body>
</html>