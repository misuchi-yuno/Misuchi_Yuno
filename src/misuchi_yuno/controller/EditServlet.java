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
import misuchi_yuno.exception.NoRowsUpdatedRuntimeException;
import misuchi_yuno.service.BranchService;
import misuchi_yuno.service.PositionService;
import misuchi_yuno.service.UserService;

@WebServlet(urlPatterns = {"/edit"})
public class EditServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {

		List<User> branch = new BranchService().getBranchList();
		List<User> position = new PositionService().getPositionList();

		request.setAttribute("branches", branch);
		request.setAttribute("positions", position);

		User user = new UserService().getEditUser(request.getParameter("id"));
		request.setAttribute("editUser", user);

		request.getRequestDispatcher("/edit.jsp").forward(request, response);
	}



	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {

		List<String> messages = new ArrayList<String>();
		List<User> branch = new BranchService().getBranchList();
		List<User> position = new PositionService().getPositionList();

		User user = new User();
		user.setId(Integer.valueOf(request.getParameter("id")));
		user.setLoginId(request.getParameter("loginId"));
		user.setPassword(request.getParameter("password"));
		user.setName(request.getParameter("name"));
		user.setBranchId(Integer.valueOf(request.getParameter("branchId")));
		user.setPositionId(Integer.valueOf(request.getParameter("positionId")));

		HttpSession session = request.getSession();
		if(isValid(request, messages)) {

			try {
				new UserService().editRegister(user);
			} catch (NoRowsUpdatedRuntimeException e) {
				messages.add("他の人によって更新されています。最新のデータを表示しました。データを確認してください。");
				session.setAttribute("errorMessages", messages);
				request.setAttribute("branches", branch);
				request.setAttribute("positions", position);
				request.setAttribute("editUser", user);
				request.getRequestDispatcher("edit.jsp").forward(request, response);
				return;
			}
				response.sendRedirect("./");

		} else {
			session.setAttribute("errorMessages", messages);

			request.setAttribute("branches", branch);
			request.setAttribute("positions", position);
			request.setAttribute("editUser", user);
			request.getRequestDispatcher("edit.jsp").forward(request, response);
		}
	}

	private boolean isValid(HttpServletRequest request, List<String> messages) {

		String id = request.getParameter("id");
		String loginId = request.getParameter("loginId");
		String password = request.getParameter("password");
		String password2 = request.getParameter("password2");
		String name = request.getParameter("name");

		if (StringUtils.isEmpty(loginId))  {
			messages.add("ログインIDを入力してください");
		} else if (!loginId.matches("[a-z A-Z 0-9]{6,20}")) {
			messages.add("ログインIDを半角英数字6～20文字で入力してください");
		}

		if (StringUtils.isEmpty(password) && !StringUtils.isEmpty(password2)) {
			messages.add("パスワードを入力してください");
		} else if (!password.equals(password2)) {
			messages.add("パスワードと確認用パスワードがちがいます");
		}
		if (!password.matches("[ -~]*$")) {
			messages.add("パスワードは記号を含む半角英数字6～20文字で入力してください");
		} else if (password.length() > 0 && password.length() < 6 || password.length() > 20) {
			messages.add("パスワードを6～20文字で入力してください");
		} else if (StringUtils.isEmpty(password2) && !StringUtils.isEmpty(password)) {
			messages.add("確認用パスワードを入力してください");
		}


		if (StringUtils.isEmpty(name)) {
			messages.add("名前を入力してください");
		} else if (name.length() > 10) {
			messages.add("名前を10文字以下で入力してください");
		}

		User user = new UserService().getEditUser(id);
		if (user.getLoginId() != null) {
			if (!user.getLoginId().equals(loginId)) {
				int count = new UserService().count(loginId);
				if (count != 0) {
					messages.add("ログインIDが使われています");
				}
			}
		}

		return messages.size() == 0;
	}
}

