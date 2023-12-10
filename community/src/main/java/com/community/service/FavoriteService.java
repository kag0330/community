package com.community.service;

import java.util.List;

import com.community.domain.Board;


public interface FavoriteService {
	public Boolean insertAndDeleteFavorite(String userId, int boardSeq);
	
	public List<Board> getFavoriteBoardsByUserId(String userId);
	
	
	
}
