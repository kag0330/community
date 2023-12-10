package com.community.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.community.domain.Board;
import com.community.domain.Favorite;
import com.community.domain.User;
import com.community.persistence.BoardRepository;
import com.community.persistence.FavoriteRepository;
import com.community.persistence.UserRepository;

@Service
public class FavoriteServiceImpl implements FavoriteService{
	@Autowired
	private FavoriteRepository favoriteRepository;
	@Autowired
	private UserRepository userRepository;
	@Autowired
	private BoardRepository boardRepository;
	
	@Override
	public Boolean insertAndDeleteFavorite(String userId, int boardSeq) {
		Optional<Favorite> findFavorite = favoriteRepository.findByUserIdAndBoardSeq(userId, boardSeq);
		if(findFavorite.isEmpty()) {
			Board board = boardRepository.findById(boardSeq).get();
			User user = userRepository.findById(userId).get();
			Favorite favorite = new Favorite();
			favorite.setBoard(board);
			favorite.setUser(user);
			favoriteRepository.save(favorite);
			return true;
		}else {
			favoriteRepository.delete(findFavorite.get());
			return false;
		}
	}

	@Override
	public List<Board> getFavoriteBoardsByUserId(String userId) {
        List<Favorite> favorites = favoriteRepository.findByUserId(userId);
        return favorites.stream()
                .map(Favorite::getBoard)
                .toList();
    }

}
