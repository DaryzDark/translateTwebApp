package dev.daryz.translateT.DeepLTest;

import dev.daryz.translateT.DeepL.DeepLLanguageService;
import dev.daryz.translateT.DeepL.DeepLLanguageService.Language;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.*;
import org.springframework.web.client.RestTemplate;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

public class DeepLLanguageServiceTest {

    @Mock
    private RestTemplate restTemplate;

    @InjectMocks
    private DeepLLanguageService languageService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testGetSupportedLanguages() {
        Language[] languages = {
                createLanguage("EN", "English"),
                createLanguage("DE", "German")
        };

        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "DeepL-Auth-Key 9232ecb3-ae3c-4617-831f-0e2e3d7ba6b5:fx");
        HttpEntity<String> entity = new HttpEntity<>(headers);

        when(restTemplate.exchange(
                "https://api-free.deepl.com/v2/languages",
                HttpMethod.GET,
                entity,
                Language[].class)).thenReturn(new ResponseEntity<>(languages, HttpStatus.OK));

        List<Language> supportedLanguages = languageService.getSupportedLanguages();

        assertEquals(2, supportedLanguages.size());
        assertEquals("EN", supportedLanguages.get(0).getLanguage());
        assertEquals("English", supportedLanguages.get(0).getName());
        assertEquals("DE", supportedLanguages.get(1).getLanguage());
        assertEquals("German", supportedLanguages.get(1).getName());
    }

    private Language createLanguage(String languageCode, String languageName) {
        Language language = new Language();
        language.setLanguage(languageCode);
        language.setName(languageName);
        return language;
    }
}

