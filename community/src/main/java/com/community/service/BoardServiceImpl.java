package com.community.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.community.domain.Board;
import com.community.domain.BoardDislikes;
import com.community.domain.BoardLikes;
import com.community.domain.User;
import com.community.persistence.BoardDislikesRepository;
import com.community.persistence.BoardLikesRepository;
import com.community.persistence.BoardRepository;
import com.community.persistence.UserRepository;

@Service
public class BoardServiceImpl implements BoardService {
	@Autowired
	private BoardRepository boardRepository;
	@Autowired 
	private BoardLikesRepository blRepository;
	@Autowired
	private BoardDislikesRepository bdlRepository;
	@Autowired
	private UserRepository userRepository;

	@Override
	public void insertBoard(Board board) {
		boardRepository.save(board);
	}

	@Override
	public void insertBoard(Board board, String user) {
		User findUser = userRepository.findById(user).get();
		board.setUser(findUser);
		boardRepository.save(board);

	}

	@Override
	public void updateBoard(Board board) {
		Board findBoard = boardRepository.findById(board.getSeq()).get();
		findBoard.setTitle(board.getTitle());
		findBoard.setContent(board.getContent());
		boardRepository.save(findBoard);

	}

	@Override
	public void deleteBoard(Board board) {
		boardRepository.findById(board.getSeq());
	}

	@Override
	public Board getBoard(Board board) {
		if (boardRepository.findById(board.getSeq()).isPresent()) {
			return boardRepository.findById(board.getSeq()).get();
		} else {
			return null;
		}
	}

	@Override
	public List<Board> getBoardList(Board board) {
		return boardRepository.findAll();
	}

	@Override
	public void updateCnt(Board board) {
		Board findBoard = boardRepository.findById(board.getSeq()).get();
		findBoard.setCnt(findBoard.getCnt() + 1);
		boardRepository.save(findBoard);
	}

	@Override
	public String likesBoard(Boolean like, int boardSeq, String userId) {
		System.out.println("like : " + like + ", boardSeq : " + boardSeq + ", userId : "+userId);
		Optional<Board> findBoard = boardRepository.findById(boardSeq);
		Optional<User> findUser = userRepository.findById(userId);
		Optional<BoardLikes> findlikes = blRepository.findByUser(findUser.get());
		Optional<BoardDislikes> finddlikes = bdlRepository.findByUser(findUser.get());
		
		//게시글의 작성자와 사용자의 작성자가 같을 경우
		if(findBoard.get().getUser().getId().equals(userId)) {
			return "자신의 게시글에는 추천할 수 없습니다.";
		}
		//좋아요 처음 눌렀을 때
		else if(like && findlikes.isEmpty() && finddlikes.isEmpty()) {
			BoardLikes likes = new BoardLikes();
			likes.setBoard(findBoard.get());
			likes.setUser(findUser.get());
			blRepository.save(likes);
			return "좋아요를 눌렀습니다.";
		}
		//싫어요 처음 눌렀을 때
		else if(!like && findlikes.isEmpty() && finddlikes.isEmpty()){
			BoardDislikes dlikes = new BoardDislikes();
			dlikes.setBoard(findBoard.get());
			dlikes.setUser(findUser.get());
			bdlRepository.save(dlikes);
			return "싫어요를 눌렀습니다.";
		}
		//좋아요를 이미 눌렀을 때 > 좋아요 취소
		else if(like && findlikes.isPresent() && finddlikes.isEmpty()) {
			blRepository.delete(findlikes.get());
			return "좋아요를 취소했습니다.";
		}
		//싫어요를 이미 눌렀을 때 > 싫어요 취소
		else if(!like && findlikes.isEmpty() && finddlikes.isPresent()) {
			bdlRepository.delete(finddlikes.get());
			return "싫어요를 취소했습니다.";
		}
		//좋아요를 눌렀는데 싫어요에 값이 존재할 때 > 싫어요 삭제 후 좋아요
		else if(like && findlikes.isEmpty() && finddlikes.isPresent()) {
			bdlRepository.delete(finddlikes.get());
			BoardLikes likes = new BoardLikes();
			likes.setBoard(findBoard.get());
			likes.setUser(findUser.get());
			blRepository.save(likes);
			return "싫어요를 취소하고 좋아요를 눌렀습니다.";
		}
		//싫어요를 눌렀는데 좋아요에 값이 존재할 때 > 좋아요 삭제 후 싫어요
		else if(!like && findlikes.isPresent() && finddlikes.isEmpty()) {
			blRepository.delete(findlikes.get());
			BoardDislikes dlikes = new BoardDislikes();
			dlikes.setBoard(findBoard.get());
			dlikes.setUser(findUser.get());
			bdlRepository.save(dlikes);
			return "좋아요를 취소하고 싫어요를 눌렀습니다.";
		}else {
			return "알 수 없는 에러";
		}

	}

	@Override
	public int getLikesCount(int boardSeq) {
		Board board = boardRepository.findById(boardSeq).get();
		return board.getBoardLikes().size();
	}

	@Override
	public int getDislikesCount(int boardSeq) {
		Board board = boardRepository.findById(boardSeq).get();
		return board.getBoardDislikes().size();
		
	}
}
