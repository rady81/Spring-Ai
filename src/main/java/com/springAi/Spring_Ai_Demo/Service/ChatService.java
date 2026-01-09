package com.springAi.Spring_Ai_Demo.Service;


import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.model.ChatModel;
import org.springframework.ai.chat.model.ChatResponse;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.ai.openai.OpenAiChatOptions;
import org.springframework.stereotype.Service;

@Service
public class ChatService {

    private final ChatClient chatClient;

    private final ChatModel chatModel;

    public ChatService(ChatClient.Builder builder, ChatModel chatModel) {
        this.chatClient = builder.build();
        this.chatModel = chatModel;
    }

    public String getResponse(String prompt) {
        return chatClient.prompt(prompt)
                .call()
                .content();
    }

    public String getResponseOptions(String prompt) {
        ChatResponse response = chatModel.call(
                new Prompt(
                        "Generate the names of 5 famous pirates.",
                        OpenAiChatOptions.builder()
                                .model("gpt-4o-mini")
                                .temperature(0.4)
                                .build()
                ));
        return response.getResult().getOutput().getText();
    }


}
