package javasrc.filter;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class AuthorityFilter implements Filter {
	static Map<String, String[]> authorities=new HashMap<>();
	static String[] systemauthority={"/loginuser/system/","/loginuser/default/"};
	static String[] adminauthority={"/loginuser/admin/","/loginuser/adminmanager/","/loginuser/default/"};
	static String[] managerauthority={"/loginuser/manager/","/loginuser/adminmanager/","/loginuser/default/"};
	static String[] businessauthority={"/loginuser/business/","/loginuser/default/"};
	
	@Override
	public void destroy() {
		System.out.println("AuthorityFilter  destroy");
	}

	@Override
	public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain)
			throws IOException, ServletException {
		HttpServletRequest httpServletRequest=(HttpServletRequest) servletRequest;
		HttpServletResponse httpServletResponse=(HttpServletResponse) servletResponse;
		httpServletResponse.setContentType("text/html; charset=UTF-8");
		HttpSession session=httpServletRequest.getSession(false);
		if (hasauthority(httpServletRequest.getRequestURI(), session).equals("login")) {
			redirecttologin(httpServletRequest, httpServletResponse);
		}else {
			filterChain.doFilter(servletRequest, servletResponse);
		}
	}

	@Override
	public void init(FilterConfig arg0) throws ServletException {
		System.out.println("AuthorityFilter  init");
		authorities.put("0", systemauthority);
		authorities.put("1", adminauthority);
		authorities.put("2", managerauthority);
		authorities.put("3", businessauthority);
	}
	
	/**
	 * 判断用户的请求是否有权限。
	 * @param url 请求的URL。
	 * @param session 用户的session。
	 * @return pass 有权限。*/
	private static String hasauthority(String url,HttpSession session) {
		String res="login";
		if (session!=null) {
			String[] authoriiedurl=authorities.get(session.getAttribute("authority").toString());
			for (int j = 0; j < authoriiedurl.length; j++) {
				if (url.indexOf(authoriiedurl[j])!=-1) {
					res="pass";
					break;
				}
			}
		}
		return res;
	}
	
	private static void redirecttologin(HttpServletRequest httpServletRequest,HttpServletResponse httpServletResponse){
		String rootpath=httpServletRequest.getRequestURL().toString().replace(httpServletRequest.getRequestURI(), "");
		rootpath=rootpath+"/"+httpServletRequest.getRequestURI().split("/")[1];
		String noauthority="<script>alert('没有权限。');window.location='"+rootpath+"/login.html'</script>";
		PrintWriter printWriter=null;
		try {
			printWriter = httpServletResponse.getWriter();
			printWriter.println(noauthority);
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (printWriter!=null) {
				printWriter.flush();
				printWriter.close();
			}
		}
	}
}
