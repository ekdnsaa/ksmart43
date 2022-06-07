package ksmart.mybatis.controller;

import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import ksmart.mybatis.dto.Member;
import ksmart.mybatis.service.MemberService;

@Controller
public class LoginController {
	
	private final MemberService memberService;
	
	public LoginController(MemberService memberService) {
		this.memberService = memberService;
	}
	@GetMapping("/loginHistory")
	public String loginHistory(@RequestParam(name = "currentPage", required = false, defaultValue = "1") int currentPage
							   ,Model model) {
		Map<String, Object> resultMap = memberService.getLoginHistory(currentPage);
	
		
		model.addAttribute("resultMap", resultMap);
		model.addAttribute("currentPage", currentPage);
		model.addAttribute("loginHistoryList", resultMap.get("loginHistoryList"));
		model.addAttribute("lastPage", resultMap.get("lastPage"));
		model.addAttribute("startPageNum", resultMap.get("startPageNum"));
		model.addAttribute("endPageNum", resultMap.get("endPageNum"));
		
		
		return "login/loginHistory"; 
	}
	
	
	@GetMapping("/logout")
	public String logout(HttpSession session) {
		session.invalidate();
		
		return "redirect:/login";
	}
	
	
	@PostMapping("/login")
	public String login(@RequestParam(name="memberId", required = false) String memberId
					   ,@RequestParam(name="memberPw", required = false) String memberPw
					   ,HttpSession session){
		Member member = memberService.getMemberInfoById(memberId);
		
		if(member != null) {
			String memberPwCheck = member.getMemberPw();
			if(memberPw != null && memberPw.equals(memberPwCheck)) {
				session.setAttribute("SID", memberId);
				session.setAttribute("SLEVEL", member.getMemberLevel());
				session.setAttribute("SNAME", member.getMemberName());
				return "redirect:/";
				
			}
		}
		return "redirect:/login";
						}
	
	
	@GetMapping("/login")
	public String login() {
		return "login/login";
	}
}
