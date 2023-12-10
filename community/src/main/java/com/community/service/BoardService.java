package com.community.service;

import java.util.List;

import com.community.domain.Board;



public interface BoardService {
	public void insertBoard(Board board, String user);
	
	public void updateBoard(Board board);
	
	public void deleteBoard(Board board);
	
	public Board getBoard(Board board);
	
	public List<Board> getBoardList(Board board);
	
	public void updateCnt(Board board);
	
	public String likesBoard(Boolean likes, int boardSeq, String userId);
	
	public int getLikesCount(int boardSeq);
	
	public int getDislikesCount(int boardSeq);

}
