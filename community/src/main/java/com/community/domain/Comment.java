package com.community.domain;

import java.util.Date;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter @Setter @ToString(exclude = {"user", "board"})
@Entity
public class Comment {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int seq;						//자동 PK
	
	private String comment;					//내용
	private Date date = new Date();			//날짜
	private Boolean isDeletetd = false;		//삭제유무
	private Boolean isRead = false;			//확인유무
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "USER_ID", nullable = false)
	private User user;						//댓글 단 유저
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "BOARD_SEQ", nullable = false)
	private Board board;					//댓글 단 게시글
}
