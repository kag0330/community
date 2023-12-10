package com.community.persistence;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.community.domain.Board;
import com.community.domain.BoardDislikes;

public interface BoardDislikesRepository extends JpaRepository<BoardDislikes, Integer> {
	Optional<BoardDislikes> findByBoard(Board board);
}
