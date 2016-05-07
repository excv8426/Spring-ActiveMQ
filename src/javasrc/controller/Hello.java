package javasrc.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class Hello {
	
	@ModelAttribute
	public void getsession(HttpServletRequest request){
		HttpSession session=request.getSession(true);
		System.out.println("session id:::::::::::::::::::"+session.getId());
	}
	
	@RequestMapping("/hello")
	public @ResponseBody String hello(@RequestParam("myname") String name){
		return "hello"+name;
	}
}
