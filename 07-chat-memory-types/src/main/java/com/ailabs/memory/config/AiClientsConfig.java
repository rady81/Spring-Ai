package com.ailabs.memory.config;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.client.advisor.MessageChatMemoryAdvisor;
import org.springframework.ai.chat.client.advisor.SimpleLoggerAdvisor;
import org.springframework.ai.chat.memory.ChatMemory;
import org.springframework.ai.chat.model.ChatModel;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AiClientsConfig {
//
//    @Bean("anthropicClient")
//    public ChatClient anthropicClient(@Qualifier("anthropicChatModel") ChatModel model) {
//        return ChatClient.builder(model)
//                .defaultAdvisors(new SimpleLoggerAdvisor())
//                .build();
//    }

    // ✅ This bean is created ONLY if OpenAI api-key is present in properties
    @Bean("openAiClient")
    @ConditionalOnProperty(
            prefix = "spring.ai.openai",
            name = "api-key"
    )
    public ChatClient openAiClient(@Qualifier("openAiChatModel") ChatModel model, ChatMemory  chatMemory) {
        return ChatClient.builder(model)
                .defaultAdvisors(MessageChatMemoryAdvisor.builder(chatMemory).build(),
                        new SimpleLoggerAdvisor())
                .build();
    }
}
