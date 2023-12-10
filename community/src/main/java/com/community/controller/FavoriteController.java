package com.community.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.community.domain.User;
import com.community.dto.Alert;
import com.community.service.FavoriteService;

import jakarta.servlet.http.HttpSession;


@Controller
public class FavoriteController {
	@Autowired
	private FavoriteService favoriteService;
	/*
	 * 즐겨찾기 목록
	 * 로그인X > session 값이 없음 로그인 해달라는 alert창을 띄움
	 * 로그인O > 즐겨찾기의 UserId 값으로 BoardList를 가져옴
	 * */
	@GetMapping("/favoriteList")
    public String getFavoriteBoards(HttpSession session, Model model) {
        User user = (User) session.getAttribute("user");
        if (user.getId() == null) {
        	Alert message = new Alert("로그인 해주세요", "/login", RequestMethod.GET, null);
			return DefaultController.showMessageAndRedirect(message, model);
        }else {
        	model.addAttribute("boardList", favoriteService.getFavoriteBoardsByUserId(user.getId()));
            return "favoriteList";
        }
    }
	
	/*
	 * Ajax
	 * 게시글 즐겨찾기 버튼 동작 수행
	 * 로그인X > session 값이 없음 로그인 해달라는 alert창을 띄움
	 * 로그인O, Favorite에 항목이 없으면 > 즐겨찾기 등록
	 * 로그인O, Favorite에 항목이 있으면 > 즐겨찾기 삭제
	 * */
	@GetMapping("/insertFavorite")
	@ResponseBody
	public ResponseEntity<String> insertFavorite(String userId, int boardSeq){
		if(userId == null) {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("로그인 해주세요");
		}
		try {
			if(favoriteService.insertAndDeleteFavorite(userId, boardSeq)) {
				return ResponseEntity.ok("즐겨찾기 등록 완료");
			}
				return ResponseEntity.ok("즐겨찾기 삭제 완료");
	    } catch (Exception e) {
	        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("서버 오류 발생");
	    }
	}
}
