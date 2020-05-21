package misuchi_yuno.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import misuchi_yuno.beans.Activity;
import misuchi_yuno.service.UserService;

@WebServlet(urlPatterns = { "/changeActivity"})
public class ChangeActivity  extends HttpServlet {
	private static final long serialVersionUID = 1L;

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws IOException , ServletException {

		Activity activity = new Activity();
		activity.setLoginId(request.getParameter("loginId"));
		activity.setActivity(Integer.valueOf(request.getParameter("change")));

		new UserService().activityRegister(activity);

		response.sendRedirect("./");
	}
}


