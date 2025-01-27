<%@page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@page isELIgnored="false"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<title>ホーム画面</title>
		<link href="./js.css" rel="stylesheet" type="text/css">
	</head>
<body>
	<label>ホーム</label>
	<div class="main-contents">
		<div class="header">
			<a href="./">ホーム</a>
			<a href="newuser">新規ユーザー登録</a>
		</div>
		<div class="informations">
			<c:forEach items="${users}" var="user">
				<form action="edit" method="get">
					<br/><c:out value="${user.id}"></c:out>
					<c:out value="${user.loginId}"></c:out>
					<c:out value="${user.name}"></c:out>
					<c:out value="${user.branchName}"></c:out>
					<c:out value="${user.positionName}"></c:out>
					<c:choose>
						<c:when test="${user.activity == 0}">
							<c:out value="活動中"></c:out>
						</c:when>
						<c:otherwise>
							<c:out value="停止中"></c:out>
						</c:otherwise>
					</c:choose>
					<input type="submit" value="編集">
					<input type="hidden" name="id" value="${user.id}">
				</form>
				<div class="change">
					<form action="changeActivity" method="post" onSubmit="return checkSubmit()">
						<c:choose>
							<c:when test="${user.activity == 0}">
								<input type="hidden" name="change" value="1">
								<input type="submit" value="停止する">
							</c:when>
							<c:otherwise>
								<input type="hidden" name="change" value="0">
								<input type="submit" value="活動する">
							</c:otherwise>
						</c:choose>
						<input type="hidden" name="loginId" value="${user.id}">
						<script type="text/javascript">
							 function checkSubmit() {
								return confirm("変更してもいいですか？");
							}
						</script>
					</form>
				</div>
			</c:forEach>
		</div>
	</div>
</body>
</html>