package com.community.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.community.domain.Comment;
import com.community.persistence.BoardRepository;
import com.community.persistence.CommentRepository;
import com.community.persistence.UserRepository;

@Service
public class CommentServiceImpl implements CommentService{
	@Autowired 
	private CommentRepository commentRepository;
	@Autowired 
	private BoardRepository boardRepository;
	@Autowired 
	private UserRepository userRepository;

	@Override
	public void insertComment(String userId, int boardSeq, String commentStr) {
		Comment comment = new Comment();
		comment.setComment(commentStr);	
		comment.setBoard(boardRepository.findById(boardSeq).get());
		comment.setUser(userRepository.findById(userId).get());				
		commentRepository.save(comment);
	}

	@Override
	public List<Comment> getCommentList(int boardSeq) {
		return commentRepository.findByBoardSeq(boardSeq);
	}

	@Override
	public void changeRead(int seq) {
		Optional<Comment> findComment = commentRepository.findById(seq);
		if(findComment.isPresent()) {
			findComment.get().setIsRead(true);
			commentRepository.save(findComment.get());
		}
		
	}

}
