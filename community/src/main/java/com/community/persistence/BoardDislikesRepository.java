package com.community.persistence;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.community.domain.BoardDislikes;
import com.community.domain.User;

public interface BoardDislikesRepository extends JpaRepository<BoardDislikes, Integer> {
	Optional<BoardDislikes> findByUser(User user);
}
