package com.community.controller;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.community.dto.Alert;

import jakarta.servlet.http.HttpSession;

public class DefaultController {
	/*
	 * alert창을 띄움
	 * */
	public static String showMessageAndRedirect(final Alert params, Model model) {
		model.addAttribute("params", params);
		return "alert";
	}
	/*
	 * 기본 index창
	 * */
	@GetMapping("/")
	public String index(HttpSession session) {
		return "index";
	}
}