package com.ailabs.structuredoutput.controller;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.client.advisor.SimpleLoggerAdvisor;
import org.springframework.ai.chat.messages.Message;
import org.springframework.ai.chat.messages.SystemMessage;
import org.springframework.ai.chat.messages.UserMessage;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.ai.chat.prompt.PromptTemplate;
import org.springframework.ai.converter.BeanOutputConverter;
import org.springframework.ai.converter.ListOutputConverter;
import org.springframework.ai.converter.MapOutputConverter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import org.springframework.core.io.Resource;

import static java.nio.charset.StandardCharsets.UTF_8;

@RestController
@RequestMapping("/")
public class StructuredOutputController {

    private static final Logger log = LoggerFactory.getLogger(StructuredOutputController.class);

    private final ChatClient chatClient;
    private final Resource tweetSystemMsgResource;

    public StructuredOutputController(ChatClient.Builder builder,
                                      @Value("classpath:prompts/tweet-system-message.st") Resource tweetSystemMsgResource) {
        this.chatClient = builder
                //advisor / interceptor mechanism (AOP for AI calls.)
                .defaultAdvisors(new SimpleLoggerAdvisor())
                .build();
        this.tweetSystemMsgResource = tweetSystemMsgResource;
    }

   //http://localhost:8080/claude-ai/chat
    @PostMapping("/ollamallm-structured-ai/chat")
    Output chat(@RequestBody @Valid Input input) {
        // these sys to show as preset? true length: xx number (other than 0)
        String key = System.getenv("OPENROUTER_API_KEY");
        System.out.println("API_KEY present? " + (key != null));
        System.out.println("API_KEY length: " + (key == null ? 0 : key.length()));
        //String systemPrompt = "You are a friendly, helpful assistant. You always respond professionally.";
        String systemPrompt = "You are a funny, and helpful assistant. You always respond in a sarcastic manner.";
        SystemMessage systemMessage = new SystemMessage(systemPrompt);
        UserMessage userMessage = new UserMessage(input.prompt());

        Prompt prompt = new Prompt(List.of(systemMessage, userMessage));
        String response = chatClient.prompt(prompt).call().content();

        return new Output(response);
    }
    public record Input(@NotBlank String prompt){};
    public record Output(String content) {};

    public record TitleSuggestionsRequest(@NotBlank String topic, @NotNull Integer count) {};
    public record TitleSuggestionsResponse(List<String> titles) {};
    public record Tweet(String content, List<String> hashtags) {};

    @PostMapping("/api/suggest-titles")
    TitleSuggestionsResponse suggestTitles(@RequestBody @Valid TitleSuggestionsRequest request) {

        String response;
        ListOutputConverter loc =  new ListOutputConverter();

        PromptTemplate pt = new PromptTemplate("""
        I would like to give a presentation about the following:
        
        {topic}
        
        Give me {count} title suggestions for this topic.
        
        Make sure the title is relevant to the topic and it should be a single short sentence.
        
        {format}
        """);

        Map<String, Object> vars = Map.of("topic", request.topic(),
                "count", request.count(),
                "format", loc.getFormat());
        Message message = pt.createMessage(vars);
        response = chatClient.prompt().messages(message).call().content();

        List<String> titles = loc.convert(response);

        return new TitleSuggestionsResponse(titles);
    }

    @PostMapping("/api/gen-tweet")
    Tweet generateTweet(@RequestBody @Valid Input input) throws IOException {
        String systemPrompt = tweetSystemMsgResource.getContentAsString(UTF_8);
        SystemMessage systemMessage = new SystemMessage(systemPrompt);

        PromptTemplate pt = new PromptTemplate("""
        Generate a tweet for the following content:
        
        {content}
        
        {format}
        """);

        BeanOutputConverter<Tweet> boc = new BeanOutputConverter<>(Tweet.class);
        String format = boc.getFormat();
        Map<String, Object> vars = Map.of("content", input.prompt(), "format", format);
        Message message = pt.createMessage(vars);

        Prompt prompt = new Prompt(List.of(systemMessage, message));
        String response = chatClient.prompt(prompt).call().content();

        Tweet tweet = boc.convert(response);
        log.info("Generated tweet: {}", tweet);

        return tweet;
    }

    @GetMapping("/api/langs")
    Map<String, Object> languages(){
        String response;
        MapOutputConverter moc = new  MapOutputConverter();

        PromptTemplate pt = new PromptTemplate("""
        Return all popular programming languages and their inception year.
        
        {format}
        """);

        Map<String, Object> vars = Map.of("format", moc.getFormat());
        Message message = pt.createMessage(vars);
        response = chatClient.prompt().messages(message).call().content();

        Map<String, Object> languages = moc.convert(response);
        log.info("Generated languages: {}", languages);
        return languages;

    }
}
