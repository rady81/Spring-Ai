package com.ailabs.onechatmodule;

import com.ailabs.onechatmodule.controller.ChatController;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.assertj.MockMvcTester;
import org.springframework.test.web.servlet.assertj.MvcTestResult;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@AutoConfigureMockMvc
class AnthropicClaudeAiApplicationTests {
    @Autowired
    MockMvcTester mockMvcTester;

    @Test
    void chat() {
        MvcTestResult testResult = mockMvcTester.post().uri("/claude-ai/chat")
                .contentType(MediaType.APPLICATION_JSON)
                .content("""
                        {
                            "prompt": "What is Anthropic and Claude?"
                        }
                        """)
                .exchange();
        assertThat(testResult)
                .hasStatus(HttpStatus.OK)
                .bodyJson()
                .convertTo(ChatController.Output.class).satisfies(output -> {
                    assertThat(output.content()).isNotBlank();
                    System.out.println("Response:\n"+output.content());
                });
    }

}
