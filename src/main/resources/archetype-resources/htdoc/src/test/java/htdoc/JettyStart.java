/**
 * mario.com Inc.
 * Copyright (c) 2014-2015 All Rights Reserved.
 */
package ${package}.htdoc;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.webapp.WebAppContext;

/**
 * 
 * @author liming
 * @version $Id: JettyStart.java, v 0.1 2015年9月2日 下午6:48:02 liming Exp $
 */
public class JettyStart {
	public static void main(String[] args) throws Exception {
		// 服务器的监听端口
		Server server = new Server(8080);
		// 关联一个已经存在的上下文
		WebAppContext context = new WebAppContext();
		// 设置描述符位置
		context.setDescriptor("src/main/webapp/WEB-INF/web.xml");
		// 设置Web内容上下文路径
		context.setResourceBase("src/main/webapp");
		// 设置上下文路径
		context.setContextPath("/");
		context.setParentLoaderPriority(true);
		server.setHandler(context);
		// 启动
		server.start();
		server.join();
	}
}
