package com.springAi.Spring_Ai_Demo.Service;

import org.springframework.ai.image.ImagePrompt;
import org.springframework.ai.image.ImageResponse;
import org.springframework.ai.openai.OpenAiImageModel;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestAttribute;

@Service
public class ImageService {

    private final OpenAiImageModel openAiImageModel;


    public ImageService(OpenAiImageModel openAiImageModel) {
        this.openAiImageModel = openAiImageModel;
    }

    public ImageResponse generateImage(String prompt) {
        return openAiImageModel.call(
                new ImagePrompt(prompt)
        );

    }
}
