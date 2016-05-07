package javasrc.init;

import javasrc.filter.AuthorityFilter;
import javasrc.listener.SessionListener;
import javax.servlet.ServletContext;

import org.springframework.web.filter.CharacterEncodingFilter;
import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;

import config.WebConfig;
/**
 * 配置DispatcherServlet*/
public class MyWebAppInitializer extends
		AbstractAnnotationConfigDispatcherServletInitializer {
	
	static{
		String path=Thread.currentThread().getContextClassLoader().getResource("").getPath();
		int length=path.length();
		path=path.substring(0, length-16);
		path=path.replaceAll("%20", " ");
	}

	@Override
	protected Class<?>[] getRootConfigClasses() {
		return null;
	}

	@Override
	protected Class<?>[] getServletConfigClasses() {
		System.out.println("获取上下文配置。");
		return new Class[] {WebConfig.class};
	}

	@Override
	protected String[] getServletMappings() {
		System.out.println("配置URL映射。");
		return new String[]{"*.do"}; 
	}
	
	/**
	 * 注册监听器和过滤器。
	 * @param servletContext Servlet运行环境（tomcat）。*/
	@Override
	protected void registerContextLoaderListener(ServletContext servletContext){
		System.out.println("注册监听器和过滤器。");
		super.registerContextLoaderListener(servletContext);
		servletContext.addListener(new SessionListener());
		AuthorityFilter authorityFilter=new AuthorityFilter();
		CharacterEncodingFilter characterEncodingFilter=new CharacterEncodingFilter();
		characterEncodingFilter.setEncoding("utf-8");
		servletContext.addFilter("authorityFilter", authorityFilter).addMappingForUrlPatterns(null, false, "/loginuser/*");
		servletContext.addFilter("characterEncodingFilter", characterEncodingFilter).addMappingForUrlPatterns(null, false, "/*");
	}
}
