package dev.daryz.translateT.DeepL;

import lombok.Data;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;

@Service
public class DeepLLanguageService {

    private static final String DEEPL_API_URL_LANGUAGES = "https://api-free.deepl.com/v2/languages";
    private static final String DEEPL_API_KEY = "9232ecb3-ae3c-4617-831f-0e2e3d7ba6b5:fx";

    private final RestTemplate restTemplate;

    public DeepLLanguageService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public List<Language> getSupportedLanguages() {
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "DeepL-Auth-Key " + DEEPL_API_KEY);

        HttpEntity<String> entity = new HttpEntity<>(headers);
        ResponseEntity<Language[]> response = restTemplate.exchange(DEEPL_API_URL_LANGUAGES, HttpMethod.GET, entity, Language[].class);
        return Arrays.asList(response.getBody());
    }

    @Data
    public static class Language {
        private String language;
        private String name;
    }
}
