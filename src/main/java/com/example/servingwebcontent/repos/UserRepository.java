package com.example.servingwebcontent.repos;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import com.example.servingwebcontent.domain.User;


@EnableJpaRepositories
public interface UserRepository extends JpaRepository<User,Long> {
	 User findByUsername(String username);

}
