package com.research.assistant;

import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.val;

@Service
public class ResearchServiceImpl implements ResearchService {

    @Value("${gemini.api.url}")
    private String geminiApiUrl;

    @Value("${gemini.api.key}")
    private String geminiApiKey;

    private final WebClient webClient;
    private final ObjectMapper objectMapper;

    public ResearchServiceImpl(WebClient.Builder webClientbBuilder) {
        this.webClient = webClientbBuilder.build();
        this.objectMapper = new ObjectMapper();
    }

    @Override
    public String processContent(ResearchRequest request) {

        //Building the prompt
        String prompt = buildPrompt(request);
        //Query the AI Model API
        Map<String, Object> requestBody = Map.of(
            "contents", new Object[]{
                Map.of("parts", new Object[]{
                    Map.of("text", prompt)
                })
            }
        );

        String response = webClient.post().uri(geminiApiUrl + geminiApiKey)
            .bodyValue(requestBody)
            .retrieve()
            .bodyToMono(String.class)
            .block();
        //Parse the response and return the extracted text
        return extractTextFromResponse(response);

    }
        
        private String extractTextFromResponse(String response){
            try{
                GeminiReponse geminiReponse = objectMapper.readValue(response, GeminiReponse.class);
                if(geminiReponse.getCandidates() != null && !geminiReponse.getCandidates().isEmpty()){
                    GeminiReponse.Candidate firstCandidate = geminiReponse.getCandidates().get(0);
                    if(firstCandidate.getContent() != null && firstCandidate.getContent().getParts() != null && 
                    !firstCandidate.getContent().getParts().get(0).getText().isEmpty()){
                        return firstCandidate.getContent().getParts().get(0).getText();
                    } else {
                        return "No content found in the response.";
                    }
                }
                return "No candidates found in the response.";
            }
            catch(Exception e){
                //Handle exception
                return "Error processing request: " + e.getMessage();

            }
        }
        //Return response to the controller

    private String buildPrompt(ResearchRequest request) {
        StringBuilder prompt = new StringBuilder();
        switch(request.getOperation()){
            case "summarize":
                prompt.append("You are an expert research assistant. Please provide a concise summary of the following content, highlighting the main points and key findings.\nContent: ").append(request.getContent());
                break;
            case "suggest":
                prompt.append("You are an expert editor. Carefully review the following content and suggest specific improvements for clarity, structure, grammar, and overall quality.\nContent: ").append(request.getContent());
                break;
            default:
                prompt.append("Operation not recognized. Please provide a valid research operation.");
                break;
        }
        return prompt.toString();
    }
}
