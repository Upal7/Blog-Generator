package com.generativeai.BlogGenerator1.Controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import com.generativeai.BlogGenerator1.Service.AIService;


@CrossOrigin("http://localhost:5173")
@RestController
@RequestMapping("/api/geminiai")
public class AIController {


    @Autowired
    AIService service;

    @PostMapping("/generate")
    public String sendResponse(@RequestBody Map<String,String> prompt)
    {
        //Creating the prompt for Gemini AI
        String newPrompt="Write a blog for " + prompt.get("blog_style") + " job profile for a topic " + prompt.get("input") + " in not less than " + prompt.get("words") + " words.";

        //Returning the response from Gemini
        return service.generateResponse(newPrompt);
    }
}
