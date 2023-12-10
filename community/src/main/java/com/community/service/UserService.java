package com.community.service;

import com.community.domain.User;


public interface UserService {
	public void insertUser(User user);
	public void deleteUser(User user);
	public User getUser(User user);
}
