package misuchi_yuno.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import misuchi_yuno.beans.User;
import misuchi_yuno.beans.UserInformation;
import misuchi_yuno.service.UserService;

@WebServlet(urlPatterns = { "/index.jsp" })
public class HomeServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {

		List<UserInformation> informations = new UserService().getInformation();

		request.setAttribute("informations", informations);

		request.getRequestDispatcher("/home.jsp").forward(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {

		User user = new User();
		user.setId(request.getParameter("id"));
		user.setLogin_id(request.getParameter("loginId"));
		user.setName(request.getParameter("name"));
		user.setBranch_id(request.getParameter("branchId"));
		user.setPosition_id(request.getParameter("positionId"));

		request.setAttribute("user", user );

		request.getRequestDispatcher("/edit.jsp").forward(request, response);
	}
}
