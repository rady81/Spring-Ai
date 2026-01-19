package com.ailabs.memory.controller;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.client.advisor.MessageChatMemoryAdvisor;
import org.springframework.ai.chat.client.advisor.SimpleLoggerAdvisor;
import org.springframework.ai.chat.memory.ChatMemory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/")
public class ChatMemoryController {
    public final ChatClient chatClient;
    // public final ChatMemory chatMemory;

    public ChatMemoryController(@Qualifier("openAiClient") ChatClient chatClient) {
//        this.chatClient = builder
//                .defaultAdvisors(
//                        new SimpleLoggerAdvisor()
//                )
//                .build();
        this.chatClient = chatClient;
    }

    @PostMapping("/api/chatmemory")
   ResponseEntity<Output> sendMessage(@RequestBody @Valid Input input,
                                      @CookieValue(name = "X-CONV-ID", required = false) String convId) {

        String conversationId = convId == null ?
                UUID.randomUUID().toString()
                : convId;
        var response = this.chatClient.prompt()
                .user(input.prompt())
                .advisors(a -> a.param(ChatMemory.CONVERSATION_ID, conversationId))
                .call().content();
        ResponseCookie cookie = ResponseCookie.from("X-CONV-ID", conversationId)
                .path("/")
                .maxAge(3600)
                .build();

        Output output = new Output(response);
        return ResponseEntity.ok().header(HttpHeaders.SET_COOKIE, cookie.toString()).body(output);

    }
    public record Input(@NotBlank String prompt){}
    public record Output(String content){}


}
