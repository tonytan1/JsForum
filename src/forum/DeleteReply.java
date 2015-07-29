package forum;

import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;

public class DeleteReply extends HttpServlet {

	private static final long serialVersionUID = 1L;

	DBConnectie db = new DBConnectie(Variable.getDb(), Variable.getDbLogin(), Variable.getDbPassword());

	@Override
	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {
			HttpSession session = request.getSession(true);
			String sessionType = (String) session.getAttribute("type");

			String forum_id = request.getParameter("forum_id");
			String thread_id = request.getParameter("thread_id");
			String reply_id = request.getParameter("reply_id");
			String start = request.getParameter("start");

			if (sessionType.equals("Admin")) {

				db.connect();

				db.query("DELETE FROM forum_message WHERE forum_id=\"" + forum_id + "\" AND thread_id=\"" + thread_id + "\" AND reply_id=\"" + reply_id + "\"");

				db.close();

				response.sendRedirect(Variable.getForumPath() + "index.jsp?page=message&forum_id=" + forum_id + "&thread_id=" + thread_id + "&start=" + start);
			}

		} catch (Exception e) {
		}

	}

	@Override
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}

}