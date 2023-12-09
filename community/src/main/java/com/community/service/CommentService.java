package com.community.service;

import java.util.List;

import com.community.domain.Comment;

public interface CommentService {

	public List<Comment> getCommentList(int boardSeq);

	public void insertComment(String userId, int boardSeq, String comment);

	public void changeRead(int seq);
	
}
