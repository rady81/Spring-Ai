package com.ailabs.onechatmodule.controller;

import jakarta.validation.constraints.NotBlank;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.client.advisor.SimpleLoggerAdvisor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/")
public class ChatController {

    private final ChatClient chatClient;

    public ChatController(@Qualifier("anthropicClient") ChatClient chatClient) {
//        this.chatClient = builder
//                //advisor / interceptor mechanism (AOP for AI calls.)
//                .defaultAdvisors(new SimpleLoggerAdvisor())
//                .build();
        this.chatClient = chatClient;
    }

    //http://localhost:8080/claude-ai/chat
    @PostMapping("/claude-ai/chat")
    Output chat(@RequestBody @Valid Input input) {
        // these sys to show as preset? true length: xx number (other than 0)
        String key = System.getenv("ANTHROPIC_API_KEY");
        System.out.println("API_KEY present? " + (key != null));
        System.out.println("API_KEY length: " + (key == null ? 0 : key.length()));
        String response = chatClient.prompt(input.prompt()).call().content();
        return new Output(response);

    }
    public record Input(@NotBlank String prompt){};
    public record Output(String content) {};
}
