package com.generativeai.BlogGenerator1.Service;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

@Service
public class AIService {

    //Environment variable to fetch the URL
    @Autowired
    private Environment env;

    public String generateResponse(String prompt)
    {
        //URL containing the call to the free Gemini API embedded with the API key
        String URL=env.getProperty("gemini.secure.api.URL");

        //Creating new Http Client
        HttpClient client=HttpClient.newHttpClient();

        //Creating a new request based on the curl command from Gemini
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(URL))
                .POST(HttpRequest.BodyPublishers.ofString("{\"contents\":[{\"parts\":[{\"text\":\"" + prompt + "\"}]}]}"))
                .setHeader("Content-Type", "application/json")
                .build();
        try {
            //Send the request and get the response
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

            //Parse the JSON response
            JSONObject jsonResponse = new JSONObject(response.body());

            //Extract the "candidates" array
            JSONArray candidates = jsonResponse.getJSONArray("candidates");

            //Extract the first candidate's "content" object
            JSONObject content = candidates.getJSONObject(0).getJSONObject("content");

            //Extract the "parts" array
            JSONArray parts = content.getJSONArray("parts");

            //Extract the text from the first part
            String extractedText = parts.getJSONObject(0).getString("text");

            //Formatting the String to avoid any special characters except blankspace
            extractedText=extractedText.replaceAll("[*#]+", "");

            //Return the extracted text as plain String
            return extractedText;

        }
        catch (IOException | InterruptedException e) {
            //Locating the origin of the Exception(s) occurred
            e.printStackTrace();
            return "An error occurred while fetching the data!";
        }
    }
}
