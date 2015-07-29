package forum;

import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;

public class AddThread extends HttpServlet {
	private static final long serialVersionUID = 1L;

	DBConnectie db = new DBConnectie(Variable.getDb(), Variable.getDbLogin(), Variable.getDbPassword());

	@Override
	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		PrintWriter out = response.getWriter();
		try {
			String forum_id = request.getParameter("forum_id");
			int lastThread_id = Integer.parseInt(request.getParameter("lastThread_id"));
			String thread_id = Integer.toString(lastThread_id + 1);

			String title = request.getParameter("title");

			if (title.equals("")) {
				title = "No title";
			} else {
				title = Filter.filterAll(title);
			}

			String message = request.getParameter("message");
			message = Filter.filterAll(message);
			String user = request.getParameter("user");

			db.connect();

			db.query("INSERT INTO forum_threads(forum_id,thread_id,title) " + "VALUES(\"" + forum_id + "\",\"" + thread_id + "\",\"" + title + "\")");

			db.query("INSERT INTO forum_message(forum_id,thread_id,reply_id,message,user,date_time) " + "VALUES(\"" + forum_id + "\",\"" + thread_id + "\",\"0" + "\",\"" + message + "\",\"" + user
					+ "\",SYSDATE())");

			db.close();

			response.sendRedirect(Variable.getForumPath() + "index.jsp?page=thread&forum_id=" + forum_id);

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