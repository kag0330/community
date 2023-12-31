package com.community.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import com.community.service.CommentService;

@Controller
public class CommentController {
	@Autowired
	private CommentService commentService;
	/*
	 * 댓글 등록 기능
	 * 로그인X > session 값이 없음 로그인 해달라는 alert창을 띄움
	 * 로그인O > 댓글 등록 기능 수행
	 * */
	@GetMapping("/addComment")
	private ResponseEntity<String> addComment(String userId, int boardSeq, String comment) {
		if (userId == null) {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("로그인 해주세요");
		}
		try {
			commentService.insertComment(userId, boardSeq, comment);
			return ResponseEntity.ok("댓글 등록 완료");
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("서버 오류 발생");
		}

	}
}
