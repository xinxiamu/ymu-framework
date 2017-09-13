package com.ymu.framework.sample.dao;

import com.ymu.framework.sample.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;


public interface UserRepository extends JpaRepository<User, Long> {
	
	User findById(Long id);
	User findByName(String name);
	List<User> findByAge(int age);

}
