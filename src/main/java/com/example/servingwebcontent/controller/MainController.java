package com.example.servingwebcontent.controller;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import com.example.servingwebcontent.domain.Message;
import com.example.servingwebcontent.domain.User;
import com.example.servingwebcontent.repos.MessageRepository;
import org.springframework.web.multipart.MultipartFile;


@Controller
public class MainController {
	
	@Autowired
	private MessageRepository messageRepository;
	
	
	@Value("${upload.path}")
	private String uploadPath; 
	
	
	
	
	
	@GetMapping("/")
	public String greeting() {
		
		return "greeting";
	}
	
	@GetMapping("/main")
	public String main(Model model) {
		Iterable<Message> messages=messageRepository.findAll();
		model.addAttribute("messages",messages);
		
		return "main";
	}
	
	@PostMapping("/main")
	public String add (
			    		@RequestParam("file") MultipartFile file,
						@AuthenticationPrincipal User user,
						@RequestParam String text,
					    @RequestParam String tag  ,
					    Model model) throws IllegalStateException, IOException {
		Message message= new Message(text,tag,user);
		if(file!=null && !file.getOriginalFilename().isEmpty()) {
			File uploadDir=new File(uploadPath);
			if(!uploadDir.exists()) {
				uploadDir.mkdir();
			}
			String uuidFile=UUID.randomUUID().toString();
			String relustFilename=uuidFile+"."+file.getOriginalFilename();
			file.transferTo(new File(uploadPath+"/"+relustFilename));
			message.setFilename(relustFilename);
		}
		messageRepository.save(message);
		
		
		
		Iterable<Message> messages=messageRepository.findAll();
		model.addAttribute("messages",messages);
		
		return "main";
	}
	@PostMapping("filter")
	public String filter(@RequestParam String filter ,Model model) {
		Iterable<Message> messages;
		if(filter==null || filter.isEmpty() ) {
			
			messages=messageRepository.findAll();
		}
		else  {
			messages=messageRepository.findByTag(filter);
		}
		model.addAttribute("messages",messages);
		
		
		return "main";
	}
	
}
