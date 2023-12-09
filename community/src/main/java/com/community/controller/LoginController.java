package com.community.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.community.domain.User;
import com.community.dto.Alert;
import com.community.service.UserService;

import jakarta.servlet.http.HttpSession;

@Controller
public class LoginController {
	@Autowired
	private UserService userService;
	
	@GetMapping("/signup")
	public String signup(User user, Model model) {
		model.addAttribute("user", user);
		return "signup";
	}
	
	@PostMapping("/signup")
	public String signupcheck(User user, Model model) {
		if(!userService.insertUser(user)) {
			Alert message = new Alert("이미 존재하는 아이디입니다.", "signup", RequestMethod.GET, null);
			return DefaultController.showMessageAndRedirect(message, model);
		}else {
			Alert message = new Alert("회원가입성공", "/", RequestMethod.GET, null);
			return DefaultController.showMessageAndRedirect(message, model);
		}	
	}
	
	@GetMapping("/login")
	public String login() {
		return "login";
	}
	
	@PostMapping("/login")
	public String login(HttpSession session, User user, Model model) {
		User findUser = userService.getUser(user);
		if(findUser != null && findUser.getPw().equals(user.getPw())) {
			session.setAttribute("user", findUser);
			return "redirect:/";
		}else if(findUser == null){
			Alert message = new Alert("존재하지 않는 아이디입니다.", "/login", RequestMethod.GET, null);
			return DefaultController.showMessageAndRedirect(message, model);
		}else {
			Alert message = new Alert("아이디 또는 비밀번호가 틀립니다.", "/login", RequestMethod.GET, null);
			return DefaultController.showMessageAndRedirect(message, model);
		}
	}

	@GetMapping("/logout")
	public String logout(HttpSession session) {
		session.invalidate();
		return "redirect:/";
	}
	
	@GetMapping("/deleteUser")
	public String deleteUser(HttpSession session, User user, Model model) {
		session.invalidate();
		userService.deleteUser(user);
		Alert message = new Alert("회원탈퇴가 완료되었습니다.", "/", RequestMethod.GET, null);
		return DefaultController.showMessageAndRedirect(message, model);
	}
}
