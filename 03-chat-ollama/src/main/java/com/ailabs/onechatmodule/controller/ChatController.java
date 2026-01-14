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

    //http://localhost:8080/ollamallmai/chat
    //in body raw - {
    //  "prompt": "what is Ollama Use?"
    //}
    @PostMapping("/ollamallmai/chat")
    Output chat(@RequestBody @Valid Input input) {
        // these sys to show as preset? true length: xx number (other than 0)
        String key = System.getenv("OPENROUTER_API_KEY");
        System.out.println("API_KEY present? " + (key != null));
        System.out.println("API_KEY length: " + (key == null ? 0 : key.length()));
        String response = chatClient.prompt(input.prompt()).call().content();
        return new Output(response);

    }
    public record Input(@NotBlank String prompt){};
    public record Output(String content) {};
}
