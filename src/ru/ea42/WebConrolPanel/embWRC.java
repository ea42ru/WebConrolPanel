package ru.ea42.WebRemoteControl;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.ServerConnector;
import org.eclipse.jetty.server.handler.ContextHandler;
import org.eclipse.jetty.server.handler.HandlerList;
import org.eclipse.jetty.server.handler.ResourceHandler;

// ��������� ��� ���������� ���������� 
public class embWRC {
	private static embWRC instance;

	public static synchronized embWRC getInstace() {
		if (instance == null)
			instance = new embWRC();
		return instance;
	}

	private embWRC() {
	}

	// ����������
	public static int Port = 8282;
	public static Server JettyServer;
	public static HandlerList hl;
	public static SessionManager sm;

	public static void Init() {
		sm = new SessionManager();
		JettyServer = new Server();
		ServerConnector connector = new ServerConnector(JettyServer);
		connector.setPort(Port);
		JettyServer.addConnector(connector);

		// ������� �������
		// ResourceHandler ResHandl = new ResourceHandler();
		// ResHandl.setDirectoriesListed(false);
		// ResHandl.setWelcomeFiles(new String[] { "index.html" });
		// ResHandl.setResourceBase(".");
		// JettyServer.setHandler(ResHandl);

		// ������
		ContextHandler dataHandl = new ContextHandler();
		dataHandl.setContextPath("/data");
		DataHandler datah = new DataHandler();
		datah.sm = sm;
		dataHandl.setHandler(datah);

		// �������
		ContextHandler statHandl = new ContextHandler();
		statHandl.setContextPath(".");
		statHandl.setResourceBase("./web/");
		// statHandl.setWelcomeFiles(new String[] { "index.html" });
		statHandl.setHandler(new ResourceHandler());

		// �������� ����������
		// ContextHandler htmlHandl= new ContextHandler();
		// htmlHandl.setContextPath(".");
		// htmlHandl.setHandler(new HtmlHandler());

		hl = new HandlerList();
		hl.addHandler(dataHandl);
		hl.addHandler(statHandl);
		// hl.addHandler(htmlHandl);
	}

	public static void start() throws Exception {
		JettyServer.setHandler(hl);
		JettyServer.start();
	}

	public static void join() throws Exception {
		JettyServer.join();
	}

	public static void Finit() throws Exception {
		JettyServer.stop();
		sm = null;
		JettyServer = null;
		instance = null;
	}

	// private static Integer NomerId = 0;

	// public static synchronized Integer GetNextId() {
	// NomerId = NomerId + 1;
	// return NomerId;
	// }

}
