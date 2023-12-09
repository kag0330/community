package com.community.persistence;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.community.domain.BoardLikes;
import com.community.domain.User;

public interface BoardLikesRepository extends JpaRepository<BoardLikes, Integer>{
	Optional<BoardLikes> findByUser(User user);
}
