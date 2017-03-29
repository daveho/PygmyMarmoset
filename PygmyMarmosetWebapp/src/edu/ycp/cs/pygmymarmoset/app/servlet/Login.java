package edu.ycp.cs.pygmymarmoset.app.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import edu.ycp.cs.pygmymarmoset.app.controller.LoginController;
import edu.ycp.cs.pygmymarmoset.app.model.ErrorMessage;
import edu.ycp.cs.pygmymarmoset.app.model.LoginCredentials;
import edu.ycp.cs.pygmymarmoset.app.model.User;

public class Login extends HttpServlet {
	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		req.getRequestDispatcher("/_view/login.jsp").forward(req, resp);
	}
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		Params params = new Params(req)
				.add("creds", LoginCredentials.class);
		params.unmarshal(req);
		
		LoginCredentials creds = params.get("creds");
		System.out.printf("Login credentials: username=%s, password=%s\n", creds.getUsername(), creds.getPassword());
		
		LoginController controller = new LoginController();
		User user = controller.execute(creds);
		if (user != null) {
			// Successful login.  Put user in session and redirect to
			// goal URL (if there is one) or the index page.

			req.getSession().setAttribute("user", user);
			
			String target;
			if (creds.hasGoal()) {
				target = creds.getGoal();
			} else {
				target = req.getContextPath() + "/u/index";
			}
			resp.sendRedirect(target);

			return;
		}
		
		// Unsuccessful login, display error message
		ErrorMessage errmsg = new ErrorMessage();
		errmsg.setText("Unknown username/password, please try again");
		req.setAttribute("errmsg", errmsg);
		creds.setPassword(""); // assume password was entered incorrectly
		req.setAttribute("creds", creds);
		req.getRequestDispatcher("/_view/login.jsp").forward(req, resp);
	}
}
