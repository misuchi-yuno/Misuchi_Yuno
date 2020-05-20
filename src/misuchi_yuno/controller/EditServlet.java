package misuchi_yuno.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;

import misuchi_yuno.beans.User;
import misuchi_yuno.service.UserService;

@WebServlet(urlPatterns = {"/edit"})
public class EditServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;



	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws IOException , ServletException {

		List<String> messages = new ArrayList<String>();

		HttpSession session = request.getSession();
		if(isValid(request , messages) == true) {

			User user = new User();
			user.setId(request.getParameter("id"));
			user.setLogin_id(request.getParameter("loginId"));
			user.setPassword(request.getParameter("password"));
			user.setName(request.getParameter("name"));
			user.setBranch_id(request.getParameter("branchId"));
			user.setPosition_id(request.getParameter("positionId"));

			new UserService().editRegister(user);

			response.sendRedirect("./");
		} else {
			session.setAttribute("errorMessages", messages);
			response.sendRedirect("edit.jsp");
		}
	}
	private boolean isValid(HttpServletRequest request, List<String> messages) {

		String loginId = request.getParameter("loginId");
		String password = request.getParameter("password");
		String password2= request.getParameter("password2");
		String name = request.getParameter("name");
		String branchId = request.getParameter("branchId");
		String positionId = request.getParameter("positionId");

		if (StringUtils.isEmpty(loginId))  {
			messages.add("ログインIDを入力してください");
		}
		if (loginId.matches("[a-z A-Z 0-9]{6,20}")) {
		} else {
			messages.add("ログインIDを半角英数字6～20文字で入力してください");
		}


		if (password.matches("[亜-熙ぁ-んァ-ヶ０-９ａ-ｚＡ-Ｚ]+$")) {
			messages.add("パスワードは記号を含む半角英数字6～20文字で入力してください");
		} else {
			if (password.length() < 21 && password.length() > 5 || password.length() == 0) {
			} else {
				messages.add("パスワードを6～20文字で入力してください");
			}
		}
		if (password.equals(password2)) {
		} else {
			messages.add("パスワードと確認用パスワードがちがいます");
		}


		if (StringUtils.isEmpty(name)) {
			messages.add("名前を入力してください");
		}
		if (name.length() > 10) {
			messages.add("名前を10文字以下で入力してください");
		}


		if (StringUtils.isEmpty(branchId)) {
			messages.add("支店コードを入力してください");
		}
		if (branchId.matches("^[0*9]+$")) {
			messages.add("支店コードの数字を入力してください");
		}


		if (StringUtils.isEmpty(positionId)) {
			messages.add("部署・役職コードを入力してください");
		}
		if (positionId.matches("^[0*9]+$")) {
			messages.add("部署・役職コードの数字を入力してください");
		}


		User user = new User();
		String OriginalLoginId = request.getParameter("originalLoginId");
		user.setLogin_id(request.getParameter("loginId"));
		if (!OriginalLoginId.equals(user.getLogin_id())) {
			new UserService().count(user);
			int count = new UserService().getCount(user);
			if (count != 0) {
				messages.add("ログインIDが使われています");
			}
		}



		if (messages.size() == 0) {
			return true;
		} else {
			return false;
		}
	}
}

