package dev.daryz.translateT.DeepLTest;

import com.fasterxml.jackson.databind.ObjectMapper;
import dev.daryz.translateT.DeepL.DeepLTranslatorService;
import dev.daryz.translateT.DeepL.DeepLResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.*;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

public class DeepLTranslatorServiceTest {

    @Mock
    private RestTemplate restTemplate;

    @Mock
    private ObjectMapper objectMapper;

    @InjectMocks
    private DeepLTranslatorService translatorService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }


    @Test
    public void testTranslateWordSuccess() throws Exception {
        String word = "Hello";
        String translatedWord = "Hallo";

        when(restTemplate.exchange(any(String.class), eq(HttpMethod.POST), any(HttpEntity.class), eq(String.class)))
                .thenReturn(new ResponseEntity<>("{\"translations\":[{\"detected_source_language\":\"EN\",\"text\":\"" + translatedWord + "\"}]}", HttpStatus.OK));
        when(objectMapper.readValue(any(String.class), eq(DeepLResponse.class)))
                .thenReturn(createDeepLResponse(translatedWord));

        String result = translatorService.translateWord(word, "EN", "DE");
        assertEquals(translatedWord, result);
    }

    @Test
    public void testTranslateWordError() throws Exception {
        String word = "Hello";

        when(restTemplate.exchange(any(String.class), eq(HttpMethod.POST), any(HttpEntity.class), eq(String.class)))
                .thenThrow(new HttpClientErrorException(HttpStatus.BAD_REQUEST, "Bad Request"));

        String result = translatorService.translateWord(word, "EN", "DE");
        assertEquals("Error: 400 BAD_REQUEST - ", result);
    }

    @Test
    public void testParseResponse() throws Exception {
        String responseBody = "{\"translations\":[{\"detected_source_language\":\"EN\",\"text\":\"Hallo\"}]}";
        when(objectMapper.readValue(responseBody, DeepLResponse.class)).thenReturn(createDeepLResponse("Hallo"));

        String result = translatorService.parseResponse(responseBody);
        assertEquals("Hallo", result);
    }


    private DeepLResponse createDeepLResponse(String translatedText) {
        DeepLResponse.TranslationDeepL translation = new DeepLResponse.TranslationDeepL();
        translation.setText(translatedText);
        DeepLResponse response = new DeepLResponse();
        response.setTranslations(List.of(translation));
        return response;
    }
}
