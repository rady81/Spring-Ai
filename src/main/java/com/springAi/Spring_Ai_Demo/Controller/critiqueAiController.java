package com.springAi.Spring_Ai_Demo.Controller;

import com.springAi.Spring_Ai_Demo.Service.ChatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class critiqueAiController {

    ChatService chatService;
    @Autowired
    public critiqueAiController(ChatService chatService) {}

    @GetMapping("//ask-ai-critique")
    public ResponseEntity<?> getResponseOptions(@RequestParam PromptRequest req) {
        return ResponseEntity.ok(chatService.answerWithCritique(req.prompt()));
    }
    public record PromptRequest(String prompt) {}
}
