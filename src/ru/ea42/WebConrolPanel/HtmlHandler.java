package ru.ea42.WebRemoteControl;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.eclipse.jetty.server.Request;
import org.eclipse.jetty.server.handler.AbstractHandler;
import org.eclipse.jetty.util.component.Dumpable;

public class HtmlHandler extends AbstractHandler {
	public static void start(Object arg0) {
	}

	public static void stop(Object arg0) {
	}

	public static String dump(Dumpable arg0) {
		return null;
	}

	public static void dumpObject(Appendable arg0, Object arg1) throws IOException {
	}

	public static void dumpObjects(Appendable arg0, String arg1, Object arg2, Object[] arg3) throws IOException {
	}

	public String dumpSelf() {
		return null;
	}

	public void handle(String target, Request baseRequest, HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {
		String loc = request.getLocalAddr();
		String met = request.getMethod();
		String upl = request.getRequestURI();

		response.setContentType("text/html; charset=utf-8");
		response.setStatus(HttpServletResponse.SC_OK);
		response.getWriter().println("</h2>" + loc + " / " + met + " / " + upl + "</h2>");
		baseRequest.setHandled(true);
	}
}
