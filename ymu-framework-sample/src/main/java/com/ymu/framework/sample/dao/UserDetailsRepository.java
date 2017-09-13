package com.ymu.framework.sample.dao;

import com.ymu.framework.sample.domain.User;
import com.ymu.framework.sample.domain.UserDetails;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;


public interface UserDetailsRepository extends JpaRepository<UserDetails, Long> {
	
	List<UserDetails> findByUser(User user);
	
	List<UserDetails> findByUserId(Long userId);

}
