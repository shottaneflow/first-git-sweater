package com.example.servingwebcontent.repos;

import org.springframework.data.repository.CrudRepository;
import java.util.List;

import com.example.servingwebcontent.domain.Message;

public interface MessageRepository extends CrudRepository<Message, Integer> {
	
	
	
	List<Message> findByTag(String tag);
}
