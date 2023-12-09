package com.community.persistence;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import com.community.domain.Comment;

public interface CommentRepository extends JpaRepository<Comment, Integer>{

	List<Comment> findByBoardSeq(int boardSeq);

}
