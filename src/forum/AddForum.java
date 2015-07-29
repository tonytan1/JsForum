package forum;

import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;

public class AddForum extends HttpServlet {

	private static final long serialVersionUID = 1L;

	DBConnectie db = new DBConnectie(Variable.getDb(), Variable.getDbLogin(), Variable.getDbPassword());

	@Override
	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		PrintWriter out = response.getWriter();
		try {

			int lastforum_id = Integer.parseInt(request.getParameter("lastforum_id"));
			String forum_id = Integer.toString(lastforum_id + 1);

			String title = request.getParameter("title");
			if (title.equals("")) {
				title = "No title";
			} else {
				title = Filter.filterAll(title);
			}

			String forum_info = request.getParameter("forum_info");
			forum_info = Filter.filterAll(forum_info);

			db.connect();

			db.query("INSERT INTO forum_forums(forum_id,title,forum_info) " + "VALUES(\"" + forum_id + "\",\"" + title + "\",\"" + forum_info + "\")");

			db.close();

			response.sendRedirect(Variable.getForumPath() + "index.jsp");

		} catch (Exception e) {
			out.println(e);
		}

	}

	@Override
	public void doGet(HttpServletRequest request, HttpServletResponse response)// {
			throws ServletException, IOException {
		doPost(request, response);
	}

}