package com.mindcare.mindcareapi.client;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;

@Component
public class AiClient {

    private static final Logger logger = LoggerFactory.getLogger(AiClient.class);

    private final WebClient webClient;
    private final String apiKey;
    private final String apiUrl;

    public AiClient(
            @Value("${openai.api.key:}") String apiKey,
            @Value("${openai.api.url:https://api.openai.com/v1/chat/completions}") String apiUrl
    ) {
        this.apiKey = apiKey;
        this.apiUrl = apiUrl;

        this.webClient = WebClient.builder()
                .baseUrl(apiUrl)
                .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .build();
    }

    public String analyzeText(String text) {
       
        String prompt = "Você é um assistente de triagem NÃO médico. Retorne JSON com keys: risk, reason, recommendations[], referral[]. Usuário: "
                + text;

        
        String body = """
                {
                  "model": "gpt-4o-mini",
                  "messages": [{"role":"user","content":"%s"}],
                  "max_tokens": 300
                }
                """.formatted(prompt);

        if (apiKey == null || apiKey.isBlank()) {
            logger.warn("OpenAI API key não configurada (openai.api.key). Retornando resposta fallback.");
            return fallbackResponse();
        }

        try {
            String response = webClient.post()
                    .header("Authorization", "Bearer " + apiKey)
                    .bodyValue(body)
                    .retrieve()
                    .bodyToMono(String.class)
                    .block();

            
            logger.info("OpenAI raw response: {}", response);
            return response == null ? fallbackResponse() : response;

        } catch (WebClientResponseException e) {
            logger.error("Erro na chamada OpenAI: status={} body={}", e.getRawStatusCode(), e.getResponseBodyAsString(), e);
            return fallbackResponse();
        } catch (Exception e) {
            logger.error("Erro inesperado ao chamar OpenAI", e);
            return fallbackResponse();
        }
    }

    private String fallbackResponse() {
        return """
                {
                  "risk":"LOW",
                  "reason":"Resposta padrão por falha na integração",
                  "recommendations":["Descansar","Procurar água"],
                  "referral":[]
                }
                """;
    }
}
