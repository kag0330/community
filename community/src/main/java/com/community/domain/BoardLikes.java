package com.community.domain;

import jakarta.persistence.CascadeType;
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

@Getter @Setter @ToString(exclude = {"user","board"})
@Entity
public class BoardLikes {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int seq;
	
	@ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.DETACH)
	@JoinColumn(name = "USER_ID")
	private User user;
	
	@ManyToOne(fetch = FetchType.LAZY ,cascade = CascadeType.DETACH)
	@JoinColumn(name = "BOARD_SEQ")
	private Board board;
}
