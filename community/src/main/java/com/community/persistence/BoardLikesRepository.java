package com.community.persistence;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.community.domain.Board;
import com.community.domain.BoardLikes;

public interface BoardLikesRepository extends JpaRepository<BoardLikes, Integer>{

	Optional<BoardLikes> findByBoard(Board user);
}
