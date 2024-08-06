package dev.daryz.translateT.translation;

import dev.daryz.translateT.DeepL.DeepLLanguageService;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
public class TranslationRequest {
    private String sourceLang;
    private String targetLang;
    private String query;
    private String response;
    private List<DeepLLanguageService.Language> languages;
}
