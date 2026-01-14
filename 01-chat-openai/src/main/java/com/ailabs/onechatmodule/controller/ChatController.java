package com.ailabs.onechatmodule.controller;

import jakarta.validation.constraints.NotBlank;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.client.advisor.SimpleLoggerAdvisor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/")
public class ChatController {

    private ChatClient chatClient;

    public ChatController(ChatClient.Builder builder) {
        this.chatClient = builder
                //advisor / interceptor mechanism (AOP for AI calls.)
                .defaultAdvisors(new SimpleLoggerAdvisor())
                .build();
    }

    //http://localhost:8080/openaiapi/chat
    //in body raw - {
    //  "prompt": "what is generative AI?"
    //}
    @PostMapping("/openaiapi/chat")
    Output chat(@RequestBody @Valid Input input) {
        String response = chatClient.prompt(input.prompt()).call().content();
        return new Output(response);

    }
    record Input(@NotBlank String prompt){};
    record Output(String content) {};
}
