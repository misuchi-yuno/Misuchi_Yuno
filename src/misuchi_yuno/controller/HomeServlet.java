package misuchi_yuno.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import misuchi_yuno.beans.UserInformation;
import misuchi_yuno.service.UserService;

@WebServlet(urlPatterns = { "/index.jsp" })
public class HomeServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {

		List<UserInformation> informations = new UserService().getUsers();

		request.setAttribute("informations", informations);

		request.getRequestDispatcher("/home.jsp").forward(request, response);
	}


}

