package ksmart.mybatis.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import ksmart.mybatis.interceptor.CommonInterceptor;
import ksmart.mybatis.interceptor.LoginInterceptor;

@Configuration
public class WebConfig implements WebMvcConfigurer{

	private final CommonInterceptor commonInterceptor;
	private final LoginInterceptor loginInterceptor;
	
	
	public WebConfig(CommonInterceptor commonInterceptor, LoginInterceptor loginInterceptor ) {
		this.commonInterceptor = commonInterceptor;
		this.loginInterceptor = loginInterceptor;
	}
	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		
		registry.addInterceptor(commonInterceptor)
				.addPathPatterns("/**")
				.excludePathPatterns("/css/**")
				.excludePathPatterns("/js/**")
				.excludePathPatterns("/favicon.ico");
		
		/*
		 * registry.addInterceptor(loginInterceptor) .addPathPatterns("/**")
		 * .excludePathPatterns("/") .excludePathPatterns("/css/**")
		 * .excludePathPatterns("/js/**") .excludePathPatterns("/favicon.ico")
		 * .excludePathPatterns("/member/addMember")
		 * .excludePathPatterns("/member/idCheck") .excludePathPatterns("/login")
		 * .excludePathPatterns("/logout");
		 */
		
		WebMvcConfigurer.super.addInterceptors(registry);
		
	}
}
