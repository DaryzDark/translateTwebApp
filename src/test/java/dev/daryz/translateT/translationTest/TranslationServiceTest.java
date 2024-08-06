package dev.daryz.translateT.translationTest;

import dev.daryz.translateT.translation.Translation;
import dev.daryz.translateT.translation.TranslationRepository;
import dev.daryz.translateT.translation.TranslationService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class TranslationServiceTest {

    @Mock
    private TranslationRepository translationRepository;

    @InjectMocks
    private TranslationService translationService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testSaveTranslation() {
        Translation translation = createTranslation();
        translationService.saveTranslation(translation);
        verify(translationRepository, times(1)).save(translation);
    }

    @Test
    public void testGetTranslation() {
        Translation expectedTranslation = createTranslation();
        when(translationRepository.findById(1L)).thenReturn(expectedTranslation);

        Translation translation = translationService.getTranslation(1L);
        assertEquals(expectedTranslation, translation);
    }

    @Test
    public void testGetAllTranslations() {
        Translation translation1 = createTranslation();
        Translation translation2 = createTranslation();
        translation2.setIpAddress("127.0.0.2");
        List<Translation> expectedTranslations = Arrays.asList(translation1, translation2);

        when(translationRepository.findAll()).thenReturn(expectedTranslations);

        List<Translation> translations = translationService.getAllTranslations();
        assertEquals(expectedTranslations, translations);
    }

    @Test
    public void testGetTranslationsByIpAddress() {
        Translation translation = createTranslation();
        List<Translation> expectedTranslations = Arrays.asList(translation);

        when(translationRepository.findByIpAddress("127.0.0.1")).thenReturn(expectedTranslations);

        List<Translation> translations = translationService.getTranslationsByIpAddress("127.0.0.1");
        assertEquals(expectedTranslations, translations);
    }

    @Test
    public void testDeleteTranslation() {
        translationService.deleteTranslation(1L);
        verify(translationRepository, times(1)).deleteById(1L);
    }

    private Translation createTranslation() {
        Translation translation = new Translation();
        translation.setIpAddress("127.0.0.1");
        translation.setLocalDateTime(LocalDateTime.now());
        translation.setSourceLang("EN");
        translation.setTargetLang("DE");
        translation.setQuery("Hello");
        translation.setResponse("Hallo");
        return translation;
    }
}
