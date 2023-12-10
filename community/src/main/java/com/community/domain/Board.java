package com.community.domain;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter @Setter @Entity
@ToString(exclude = {"user"})
public class Board {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int seq;
	private String title;
	private String content;
	private Date date = new Date();
	private int cnt = 0;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "USER_ID", nullable = false)
	private User user;

	@OneToMany(mappedBy = "board", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	private List<Favorite> favoriteList = new ArrayList<Favorite>();

	@OneToMany(mappedBy = "board", cascade = CascadeType.ALL, fetch = FetchType.EAGER, orphanRemoval = true)
	private List<BoardLikes> boardLikes = new ArrayList<BoardLikes>();

	@OneToMany(mappedBy = "board", cascade = CascadeType.ALL, fetch = FetchType.EAGER, orphanRemoval = true)
	private List<BoardDislikes> boardDislikes = new ArrayList<BoardDislikes>();
	
	@OneToMany(mappedBy = "board", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	private List<Comment> commentList = new ArrayList<Comment>();
}
