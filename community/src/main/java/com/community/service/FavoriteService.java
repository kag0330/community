package com.community.service;

import java.util.List;

import com.community.domain.Board;
import com.community.domain.Favorite;


public interface FavoriteService {
	public void insertFavorite(Favorite favorite);
	public Boolean insertFavorite(String userId, int boardSeq);
	public void deleteFavorite(Favorite favorite);
	public List<Favorite> getFavoriteList(Favorite favorite);
	public List<Board> getFavoriteBoardsByUserId(String userId);
	
}
