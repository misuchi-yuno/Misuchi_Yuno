<%@page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@page isELIgnored="false"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<title>ホーム画面</title>

	</head>
<body>
	<label>ホーム</label>
	<div class="main-contents">
		<div class="header">
			<a href="./">ホーム</a>
			<a href="newuser">新規ユーザー登録</a>
			<c:out value="${String}"></c:out>
		</div>
		<div class="informations">
			<c:forEach items="${informations}" var="information">
				<form action="./" method="post">
					<br/><c:out value="${information.id}"></c:out>
					<c:out value="${information.loginId}"></c:out>
					<c:out value="${information.name}"></c:out>
					<c:out value="${information.branchName}"></c:out>
					<c:out value="${information.positionName}"></c:out>
					<c:out value="${information.activity}"></c:out>
					<input type="submit" value="編集">
					<input type="hidden" name="id" value="${information.id}">
					<input type="hidden" name="loginId" value="${information.loginId}">
					<input type="hidden" name="name" value="${information.name}">
					<input type="hidden" name="branchId" value="${information.branchId}">
					<input type="hidden" name="positionId" value="${information.positionId}">
				</form>
				<div class="change">
					<form action="changeActivity" method="post" onSubmit="return checkSubmit()">
						<c:if test="${information.activity == '活動中'}">
							<input type="hidden" name="loginId" value="${information.id}">
							<input type="hidden" name="change" value="1">
							<input type="submit" value="停止する">
							<script type="text/javascript">
								 function checkSubmit() {
									return confirm("変更してもいいですか？");
									}
							</script>
						</c:if>
						<c:if test="${information.activity == '停止中'}">
							<input type="hidden" name="loginId" value="${information.id}">
							<input type="hidden" name="change" value="0">
							<input type="submit" value="活動する">
							<script type="text/javascript">
								 function checkSubmit() {
									return confirm("変更してもいいですか？");
								}
							</script>
						</c:if>
					</form>
				</div>
			</c:forEach>
		</div>
	</div>
</body>
</html>