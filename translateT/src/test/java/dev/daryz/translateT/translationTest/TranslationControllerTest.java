package dev.daryz.translateT.translationTest;

import dev.daryz.translateT.DeepL.DeepLLanguageService;
import dev.daryz.translateT.DeepL.DeepLTranslatorService;
import dev.daryz.translateT.translation.TranslationController;
import dev.daryz.translateT.translation.TranslationRequest;
import dev.daryz.translateT.translation.TranslationService;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Arrays;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

public class TranslationControllerTest {

    @Mock
    private TranslationService translationService;

    @Mock
    private DeepLTranslatorService deepLTranslatorService;

    @Mock
    private DeepLLanguageService deepLLanguageService;

    @InjectMocks
    private TranslationController translationController;

    private MockMvc mockMvc;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(translationController).build();
    }

    @Test
    public void testShowForm() throws Exception {
        when(deepLLanguageService.getSupportedLanguages()).thenReturn(Arrays.asList(createLanguage("EN", "English")));

        mockMvc.perform(get("/"))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("TranslationRequest"))
                .andExpect(view().name("index"));
    }

    @Test
    public void testCreateTranslation() throws Exception {
        String query = "Hello";
        String translatedText = "Hallo";

        when(deepLTranslatorService.translateQuery(any(String.class), any(String.class), any(String.class)))
                .thenReturn(translatedText);
        when(deepLLanguageService.getSupportedLanguages()).thenReturn(Arrays.asList(createLanguage("EN", "English")));

        mockMvc.perform(post("/translate")
                        .param("sourceLang", "EN")
                        .param("targetLang", "DE")
                        .param("query", query))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("TranslationRequest"))
                .andExpect(model().attribute("TranslationRequest", Matchers.hasProperty("response", Matchers.equalTo(translatedText))))
                .andExpect(view().name("index"));
    }

    private DeepLLanguageService.Language createLanguage(String languageCode, String languageName) {
        DeepLLanguageService.Language language = new DeepLLanguageService.Language();
        language.setLanguage(languageCode);
        language.setName(languageName);
        return language;
    }
}


