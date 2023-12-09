package com.community.persistence;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.community.domain.Favorite;

public interface FavoriteRepository extends JpaRepository<Favorite, Integer>{
	List<Favorite> findByUserId(String userId);
	Optional<Favorite> findByUserIdAndBoardSeq(String userId, int boardSeq);
}
