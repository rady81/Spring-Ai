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


    public CritiqueFlowResponse answerWithCritique(String userPrompt) {
        // Draft answer
        String draft = chatClient.prompt()
                .system("You are a helpful assistant. Provide a clear, practical answer.")
                .user(userPrompt)
                .call()
                .content();

        // 2) Critique (strict reviewer)
        String critique = chatClient.prompt()
                .system("""
                        You are a strict reviewer.
                        Find issues in the draft:
                        - missing steps
                        - vague claims
                        - risky assumptions
                        - better alternatives
                        Output ONLY bullet points.
                        """)
                .user(u -> u.text("""
                        USER PROMPT:
                        {prompt}

                        DRAFT ANSWER:
                        {draft}
                        """)
                        .param("prompt", userPrompt)
                        .param("draft", draft))
                .call()
                .content();

        // 3) Improve using critique
        String improved = chatClient.prompt()
                .system("""
                        Rewrite the draft using the critique.
                        Requirements:
                        - concise
                        - actionable
                        - include steps where needed
                        - remove fluff
                        """)
                .user(u -> u.text("""
                        USER PROMPT:
                        {prompt}

                        DRAFT:
                        {draft}

                        CRITIQUE:
                        {critique}
                        """)
                        .param("prompt", userPrompt)
                        .param("draft", draft)
                        .param("critique", critique))
                .call()
                .content();

        return new CritiqueFlowResponse(draft, critique, improved);
    }

    public record CritiqueFlowResponse(String draft, String critique, String finalAnswer) {}
}
