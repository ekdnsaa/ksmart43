package ksmart.mybatis.interceptor;

import java.util.Set;
import java.util.StringJoiner;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

@Component
public class CommonInterceptor implements HandlerInterceptor {

	private static final Logger log = LoggerFactory.getLogger(CommonInterceptor.class);

	/*
	 * HandlerMapping이 핸들러 객체를 결정한 후 HandlerAdapter가 핸들러를 호출하기 전에 호출되는 메소드
	 * return true : controller 진입 o, false : controller 진입 x
	 * 
	 * */
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		
		Set<String> paramKeys = request.getParameterMap().keySet();
		
		StringJoiner param = new StringJoiner(",");
		if(paramKeys != null) {
			
			
			for(String key : paramKeys) {
				param.add(key + ":" + request.getParameter(key));
			}
		}
		log.info("AccessInfo======================================================================");
		log.info("PORT		::::		{}", request.getLocalPort());
		log.info("SEVERName		::::		{}", request.getServerName());
		log.info("URI		::::		{}", request.getRequestURI());
		log.info("HTTPMETHOD		::::		{}", request.getMethod());
		if(param != null) log.info("PARAMETER		::::		{}", param);
		log.info("PreHandle======================================================================end");

		return HandlerInterceptor.super.preHandle(request, response, handler);
	}
	
	/*
	 * HandlerAdapter가 실제로 핸들러를 호출한 후 DispatcherServlet이 뷰를 렌더링하기 전에 호출되는 메소드
	 * */
	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {
		log.info("postHandle======================================================================");
		HandlerInterceptor.super.postHandle(request, response, handler, modelAndView);
	}
	
	/*
	 * DispatcherServlet이 뷰를 렌더링하기 전에 호출되는 메소드
	 * */
	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
			throws Exception {
		log.info("afterCompletion======================================================================");

		HandlerInterceptor.super.afterCompletion(request, response, handler, ex);
	}
}
