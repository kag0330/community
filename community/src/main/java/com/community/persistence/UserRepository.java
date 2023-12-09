package com.community.persistence;

import org.springframework.data.jpa.repository.JpaRepository;

import com.community.domain.User;

public interface UserRepository extends JpaRepository<User, String> {

}
