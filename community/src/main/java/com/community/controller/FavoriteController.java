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
	@GetMapping("/insertFavorite")
	@ResponseBody
	public ResponseEntity<String> insertFavorite(String userId, int boardSeq){
		if(userId == null) {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("로그인 해주세요");
		}
		try {
			if(favoriteService.insertFavorite(userId, boardSeq)) {
				return ResponseEntity.ok("즐겨찾기 등록 완료");
			}
				return ResponseEntity.ok("즐겨찾기 삭제 완료");
	    } catch (Exception e) {
	        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("서버 오류 발생");
	    }
	}
}
