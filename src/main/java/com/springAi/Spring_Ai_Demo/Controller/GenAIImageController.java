package com.springAi.Spring_Ai_Demo.Controller;

import com.springAi.Spring_Ai_Demo.Service.ImageService;
import org.springframework.ai.image.ImagePrompt;
import org.springframework.ai.image.ImageResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.constraints.Size;

@RestController
@RequestMapping("/images")
@Validated
public class GenAIImageController {

    private final ImageService imageService;


    public GenAIImageController(ImageService imageService) {
        this.imageService = imageService;
    }
    @GetMapping("/generate")
    public ResponseEntity<Void> generateImage(
            @RequestParam @Size(max = 500) String prompt) {

        ImageResponse imagePrompt = imageService.generateImage(prompt);
        String url = imagePrompt.getResult().getOutput().getUrl();

        return ResponseEntity
                .status(302)
                .header("Location", url)
                .build();
    }


}
