package com.springAi.Spring_Ai_Demo.Controller;

import com.springAi.Spring_Ai_Demo.Service.ChatService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SpringAiController {
	
	ChatService chatService;

	public SpringAiController(ChatService chatService) {

		this.chatService = chatService;
	}

	@GetMapping("/ask-ai")
	public ResponseEntity<?> getResponse(@RequestParam String prompt) {
		return ResponseEntity.ok(chatService.getResponse(prompt));
	}

	@GetMapping("/ask-ai-options")
	public ResponseEntity<?> getResponseOptions(@RequestParam String prompt) {
		return ResponseEntity.ok(chatService.getResponseOptions(prompt));
	}

}
