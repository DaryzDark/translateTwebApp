package dev.daryz.translateT.DeepL;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.*;


@Service
public class DeepLTranslatorService {

    private static final String DEEPL_API_URL = "https://api-free.deepl.com/v2/translate";
    private static final String DEEPL_API_KEY = "9232ecb3-ae3c-4617-831f-0e2e3d7ba6b5:fx";

    private final RestTemplate restTemplate;
    private final ObjectMapper objectMapper;

    @Autowired
    public DeepLTranslatorService(RestTemplate restTemplate, ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
        this.restTemplate = restTemplate;
    }

    public List<String> translateWords(List<String> words, String sourceLang, String targetLang) {
        ExecutorService executor = Executors.newFixedThreadPool(10);
        List<Future<String>> futures = new ArrayList<>();

        for (String word : words) {
            Callable<String> task = () -> translateWord(word, sourceLang, targetLang);
            futures.add(executor.submit(task));
        }

        List<String> translatedWords = new ArrayList<>();
        for (Future<String> future : futures) {
            try {
                translatedWords.add(future.get());
            } catch (InterruptedException | ExecutionException e) {
                throw new RuntimeException(e);
            }
        }

        executor.shutdown();
        return translatedWords;
    }

    public String translateWord(String word, String sourceLang, String targetLang) throws Exception {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        headers.set("Authorization", "DeepL-Auth-Key " + DEEPL_API_KEY);


        String body = UriComponentsBuilder.fromHttpUrl(DEEPL_API_URL)
                .queryParam("text", word)
                .queryParam("source_lang", sourceLang)
                .queryParam("target_lang", targetLang)
                .build()
                .toUriString().substring( DEEPL_API_URL.length() + 1);
        HttpEntity<String> entity = new HttpEntity<>(body, headers);

        try {
            ResponseEntity<String> response = restTemplate.exchange(DEEPL_API_URL, HttpMethod.POST, entity, String.class);
            return parseResponse(response.getBody());
        } catch (HttpClientErrorException e) {
            return "Error: " + e.getStatusCode() + " - " + e.getResponseBodyAsString();
        }
    }

    public String parseResponse(String responseBody) throws Exception {
        DeepLResponse translationResponse = objectMapper.readValue(responseBody, DeepLResponse.class);
        if (translationResponse.getTranslations() != null && !translationResponse.getTranslations().isEmpty()) {
            return translationResponse.getTranslations().getFirst().getText();
        }
        return null;
    }

    public String translateQuery(String query, String sourceLang, String targetLang) {
        List<String> wordList = Arrays.stream(query.split(" ")).toList();
        List<String> translatedList = translateWords(wordList, sourceLang, targetLang);
        return String.join(" ", translatedList);
    }
}
