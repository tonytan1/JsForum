package forum;

import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;

public class AddReply extends HttpServlet {

	private static final long serialVersionUID = 1L;

	DBConnectie db = new DBConnectie(Variable.getDb(), Variable.getDbLogin(), Variable.getDbPassword());

	@Override
	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		PrintWriter out = response.getWriter();
		try {
			String start = request.getParameter("start");
			String forum_id = request.getParameter("forum_id");
			int lastReply_id = Integer.parseInt(request.getParameter("lastReply_id"));
			String reply_id = Integer.toString(lastReply_id + 1);

			String thread_id = request.getParameter("thread_id");
			String message = request.getParameter("message");
			message = Filter.filterAll(message);

			String user = request.getParameter("user");

			db.connect();

			db.query("INSERT INTO forum_message(forum_id,thread_id,reply_id,message,user,date_time) " + "VALUES(\"" + forum_id + "\",\"" + thread_id + "\",\"" + reply_id + "\",\"" + message + "\",\""
					+ user + "\",SYSDATE())");

			db.close();

			response.sendRedirect(Variable.getForumPath() + "index.jsp?page=message&forum_id=" + forum_id + "&thread_id=" + thread_id + "&start=" + start);

		} catch (Exception e) {
			out.println(e);
		}

	}

	@Override
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}

}