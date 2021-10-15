package ru.ea42.WebRemoteControl;

import org.eclipse.jetty.server.handler.ContextHandler;
import org.eclipse.jetty.server.handler.ResourceHandler;

public class Demo_Start {
	public static void main(String[] args) throws Exception {
		// embWRC.Port=8183; //меняем порт, если надо
		embWRC.Init();
		// коструируем интерфейс в Demo_Session, отнаследаваным от AbstractSession
		embWRC.sm.SessionClass = Demo_Session.class;

		// можно добавить свой обработчик к встроенному веб серверу, если надо
		// ContextHandler statHandl = new ContextHandler();
		// statHandl.setContextPath("./demo_web/");
		// statHandl.setResourceBase("./demo_web/");
		// statHandl.setWelcomeFiles(new String[] { "index.html" });
		// statHandl.setHandler(new ResourceHandler());
		// embWRC.hl.addHandler(statHandl);

		System.out.println("Start WebRemoteControl => http://127.0.0.1:" + embWRC.Port);
		embWRC.start();
		embWRC.join();
	}
}
