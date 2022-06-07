package ksmart.mybatis.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

@Component
public class LoginInterceptor implements HandlerInterceptor {
	
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
		throws Exception{
		HttpSession session = request.getSession();
		String sessionId = (String) session.getAttribute("SID");
		
		if(sessionId == null) {
			response.sendRedirect("/login");
			return false;
		}else {
			String sessionLevel = (String)session.getAttribute("SLEVEL");
			String requestURI = request.getRequestURI();
			
			if("2".equals(sessionLevel)) {
				if(requestURI.indexOf("/member/memberList")		 > -1 ||
					requestURI.indexOf("/member/memberInfoList") > -1 ||
					requestURI.indexOf("/member/modifyMember")	 > -1 ||
					requestURI.indexOf("/member/removeMember")	 > -1 ||
					requestURI.indexOf("/member/sellerinfoList")	 > -1) {
					response.sendRedirect("/");
					return false;
				}
			}else if("3".equals(sessionLevel)) {
				if(requestURI.indexOf("/member/memberList") 	> -1 ||
					requestURI.indexOf("/member/memberInfoList")> -1 ||
					requestURI.indexOf("/member/modifyMember") 	> -1 ||
					requestURI.indexOf("/member/removeMember")	> -1 ||
					requestURI.indexOf("/member/sellerinfoList") 	> -1) {
					response.sendRedirect("/");
					return false;
				}
				
			}else {
				
			}
					
		}
		return true;
	}
}
