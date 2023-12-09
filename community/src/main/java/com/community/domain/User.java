package com.community.domain;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter @Setter
@Entity
@Table(name = "USERS")
@ToString(exclude = {"board"})
public class User {
	@Id
	private String id;
	private String pw;
	private String name;
	private String phone;
	private String nickname;
	private String email;
	
	@OneToMany(mappedBy ="user", cascade =CascadeType.ALL, fetch = FetchType.EAGER)
	private List<Board> boardList = new ArrayList<Board>();
	
	@OneToMany(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	private List<Favorite> favoriteList = new ArrayList<Favorite>();
	
	@OneToMany(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	private List<Comment> commentList = new ArrayList<Comment>();
	
	
	
}
