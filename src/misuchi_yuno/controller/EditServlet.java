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
import misuchi_yuno.beans.UserInformation;
import misuchi_yuno.service.BranchService;
import misuchi_yuno.service.PositionService;
import misuchi_yuno.service.UserService;

@WebServlet(urlPatterns = {"/edit"})
public class EditServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {

		List<UserInformation> branch = new BranchService().getBranchList();
		List<UserInformation> position = new PositionService().getPositionList();

		request.setAttribute("branches", branch);
		request.setAttribute("positions", position);


		User user = new User();
		user.setId(Integer.valueOf(request.getParameter("id")));
		String id = request.getParameter("id");
		request.setAttribute("id", id);

		UserService ThisUser = new UserService();
		User thisUser = ThisUser.getThisUser(id);
		request.setAttribute("thisUser",  thisUser);

		request.getRequestDispatcher("/edit.jsp").forward(request, response);
	}



	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws IOException , ServletException {

		List<String> messages = new ArrayList<String>();

		HttpSession session = request.getSession();
		if(isValid(request , messages) == true) {

			User user = new User();
			user.setId(Integer.valueOf(request.getParameter("id")));
			user.setLoginId(request.getParameter("loginId"));
			user.setPassword(request.getParameter("password"));
			user.setName(request.getParameter("name"));
			user.setBranchId(Integer.valueOf(request.getParameter("branchId")));
			user.setPositionId(Integer.valueOf(request.getParameter("positionId")));

			new UserService().editRegister(user);

			response.sendRedirect("./");
		} else {
			session.setAttribute("errorMessages", messages);

			List<UserInformation> branch = new BranchService().getBranchList();
			List<UserInformation> position = new PositionService().getPositionList();

			request.setAttribute("branches", branch);
			request.setAttribute("positions", position);


			User user = new User();
			user.setId(Integer.valueOf(request.getParameter("id")));
			String id = request.getParameter("id");
			request.setAttribute("id", id);

			UserService ThisUser = new UserService();
			User thisUser = ThisUser.getThisUser(id);
			request.setAttribute("thisUser",  thisUser);
			request.getRequestDispatcher("edit.jsp").forward(request, response);
		}
	}
	private boolean isValid(HttpServletRequest request, List<String> messages) {

		String loginId = request.getParameter("loginId");
		String password = request.getParameter("password");
		String password2= request.getParameter("password2");
		String name = request.getParameter("name");

		if (StringUtils.isEmpty(loginId))  {
			messages.add("ログインIDを入力してください");
		}
		if (!loginId.matches("[a-z A-Z 0-9]{6,20}")) {
			messages.add("ログインIDを半角英数字6～20文字で入力してください");
		}


		if (password.matches("[亜-熙ぁ-んァ-ヶ０-９ａ-ｚＡ-Ｚ]+$")) {
			messages.add("パスワードは記号を含む半角英数字6～20文字で入力してください");
		} else {
			if (password.length() > 20 || password.length() < 6 && password.length() != 0) {
				messages.add("パスワードを6～20文字で入力してください");
			}
		}
		if (!password.equals(password2)) {
			messages.add("パスワードと確認用パスワードがちがいます");
		}


		if (StringUtils.isEmpty(name)) {
			messages.add("名前を入力してください");
		}
		if (name.length() > 10) {
			messages.add("名前を10文字以下で入力してください");
		}


		User user = new User();
		String OriginalLoginId = request.getParameter("originalLoginId");
		user.setLoginId(request.getParameter("loginId"));

		if (!OriginalLoginId.equals(user.getLoginId())) {
			int count = new UserService().count(user);
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

