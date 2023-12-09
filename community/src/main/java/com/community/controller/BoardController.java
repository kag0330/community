package com.community.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.community.domain.Board;
import com.community.domain.Comment;
import com.community.domain.User;
import com.community.dto.Alert;
import com.community.dto.LikeDislikeCounts;
import com.community.service.BoardService;
import com.community.service.CommentService;
import com.community.service.UserService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

@Controller
public class BoardController {
	@Autowired
	private BoardService boardService;
	@Autowired
	private CommentService commentService;
	@Autowired
	private UserService userSerivce;

	@GetMapping("/getBoardList")
	public String getBoardList(Model model, Board board) {
		model.addAttribute("boardList", boardService.getBoardList(board));
		return "getBoardList";
	}

	@GetMapping("/getBoard")
	public String getBoard(HttpSession session, Board board, Model model, Integer isRead) {
		boardService.updateCnt(board);
		if(isRead != null) {
			commentService.changeRead(isRead);
			User findUser = userSerivce.getUser((User)session.getAttribute("user"));
			session.setAttribute("user", findUser);
		}
		
		model.addAttribute("comment", commentService.getCommentList(board.getSeq()));
		model.addAttribute("board", boardService.getBoard(board));
		return "getBoard";
	}

	@GetMapping("/insertBoard")
	public String insertBoard(Model model, HttpSession session) {
		User user = (User) session.getAttribute("user");
		if (user == null) {
			Alert message = new Alert("로그인 해주세요", "/login", RequestMethod.GET, null);
			return DefaultController.showMessageAndRedirect(message, model);
		} else {
			model.addAttribute("board", new Board());
			return "insertBoard";
		}
	}

	@PostMapping("/insertBoard")
	public String insertBoard(Board board, String user) {
		boardService.insertBoard(board, user);
		return "redirect:getBoardList";
	}

	@PostMapping("/updateBoard")
	public String updateBoard(Board board, HttpSession session, HttpServletRequest request, Model model)
			throws Exception {
		User user = (User) session.getAttribute("user");
		Board findboard = boardService.getBoard(board);
		if (user == null) {
			Alert message = new Alert("로그인 해주세요", "/login", RequestMethod.GET, null);
			return DefaultController.showMessageAndRedirect(message, model);
		}
		if (!(findboard.getUser().getId().equals(user.getId()))) {
			Alert message = new Alert("아이디가 일치하지 않습니다.", "/getBoardList", RequestMethod.GET, null);
			return DefaultController.showMessageAndRedirect(message, model);
		} else {
			boardService.updateBoard(board);
			Alert message = new Alert("게시글 수정 완료", "/getBoardList", RequestMethod.GET, null);
			return DefaultController.showMessageAndRedirect(message, model);
		}
	}

	@GetMapping("/deleteBoard")
	public String deleteBoard(Board board, Model model, HttpSession session) {
		User user = (User) session.getAttribute("user");
		Board findboard = boardService.getBoard(board);
		if (user == null) {
			Alert message = new Alert("로그인 해주세요", "/login", RequestMethod.GET, null);
			return DefaultController.showMessageAndRedirect(message, model);
		} else if (!(user.getId().equals(findboard.getUser().getId()))) {
			Alert message = new Alert("아이디가 일치하지 않습니다.", "/getBoardList", RequestMethod.GET, null);
			return DefaultController.showMessageAndRedirect(message, model);
		} else {
			boardService.deleteBoard(board);
			Alert message = new Alert("게시글 삭제 완료", "/getBoardList", RequestMethod.GET, null);
			return DefaultController.showMessageAndRedirect(message, model);
		}
	}
	
	@GetMapping("/boardLike")
	@ResponseBody
	public ResponseEntity<String> boardLike(int boardSeq, String userId, Boolean likes) {
		if(userId == null) {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("로그인 해주세요");
		}
		try {
			return ResponseEntity.ok(boardService.likesBoard(likes, boardSeq, userId));
	    } catch (Exception e) {
	        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("서버 오류 발생");
	    }
	}
	
	@GetMapping("/getLikeDislikeCounts")
	@ResponseBody
    public LikeDislikeCounts getLikeDislikeCounts(@RequestParam int boardSeq) {
        int likesCount = boardService.getLikesCount(boardSeq);
        int dislikesCount = boardService.getDislikesCount(boardSeq); 
        return new LikeDislikeCounts(likesCount, dislikesCount);
    }
}
