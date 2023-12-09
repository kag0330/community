package com.community.service;

import java.util.List;

import com.community.domain.User;


public interface UserService {
	public Boolean insertUser(User user);
	public void updateUser(User user);
	public void deleteUser(User user);
	public User getUser(User user);
	public List<User> getUserList(User user);
}
