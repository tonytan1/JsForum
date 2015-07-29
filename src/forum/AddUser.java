package forum;

import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;
import java.sql.ResultSet;

public class AddUser extends HttpServlet {
	private static final long serialVersionUID = 1L;

	DBConnectie db = new DBConnectie(Variable.getDb(), Variable.getDbLogin(), Variable.getDbPassword());

	@Override
	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		PrintWriter out = response.getWriter();
		try {
			// Cookie[] cookies = request.getCookies();
			// String username = Cookies.getCookies(cookies,"username","null");
			// String password = Cookies.getCookies(cookies,"password","null");
			// String type = Cookies.getCookies(cookies,"type","null");

			String RegUser = request.getParameter("user");
			String RegEmail = request.getParameter("email");
			String RegPass = request.getParameter("password");
			String RegPass2 = request.getParameter("password2");

			db.connect();
			ResultSet rs = db.selectQuery("SELECT * " + "FROM forum_users " + "WHERE user_name=\"" + RegUser + "\"");

			String DBUsername = null;
			while (rs.next()) {
				DBUsername = rs.getString("user_name");
			}

			if (DBUsername == null) {

				if (RegPass.equals(RegPass2)) {

					db.query("INSERT INTO " + "forum_users(user_name,password,email,registerdate,type) " + "VALUES(\"" + RegUser + "\",password(\"" + RegPass + "\"),\"" + RegEmail
							+ "\",\"SYSDATE()\",\"user\")");
					db.close();
					response.sendRedirect(Variable.getForumPath() + "info.jsp?action=regcomplete");
				} else {
					response.sendRedirect(Variable.getForumPath() + "info.jsp?action=wrongpass");
				}
			} else {
				response.sendRedirect(Variable.getForumPath() + "info.jsp?action=userexists");
			}
		} catch (Exception e) {
			out.println(e);
		}

	}

	@Override
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}

}