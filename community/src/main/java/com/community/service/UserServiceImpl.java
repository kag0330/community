package com.community.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.community.domain.User;
import com.community.persistence.UserRepository;

@Service
public class UserServiceImpl implements UserService {
	@Autowired
	private UserRepository userRepository;

	@Override
	public Boolean insertUser(User user) {
		Optional<User> findUser = userRepository.findById(user.getId());
		if(findUser.isPresent()) {
			return false;
		}else {
			userRepository.save(user);
			return true;
		}
	}

	@Override
	public void updateUser(User user) {
		User findUser = userRepository.findById(user.getId()).get();
		findUser.setNickname(user.getNickname());
		findUser.setEmail(user.getEmail());
		userRepository.save(findUser);

	}

	@Override
	public void deleteUser(User user) {
		userRepository.deleteById(user.getId());

	}

	@Override
	public User getUser(User user) {
		Optional<User> findUser = userRepository.findById(user.getId());
		if (findUser.isPresent()) {
			return findUser.get();
		} else {
			return null;
		}

	}

	@Override
	public List<User> getUserList(User user) {
		return userRepository.findAll();
	}

}
