package com.community.persistence;

import org.springframework.data.jpa.repository.JpaRepository;

import com.community.domain.Board;

public interface BoardRepository extends JpaRepository<Board, Integer> {

}
