package dev.daryz.translateT.DeepL;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class DeepLResponse {

    @JsonProperty("translations")
    private List<TranslationDeepL> translations;

    public List<TranslationDeepL> getTranslations() {
        return translations;
    }

    public void setTranslations(List<TranslationDeepL> translations) {
        this.translations = translations;
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class TranslationDeepL {

        @JsonProperty("detected_source_language")
        private String detectedSourceLanguage;

        @JsonProperty("text")
        private String text;

        public String getDetectedSourceLanguage() {
            return detectedSourceLanguage;
        }

        public void setDetectedSourceLanguage(String detectedSourceLanguage) {
            this.detectedSourceLanguage = detectedSourceLanguage;
        }

        public String getText() {
            return text;
        }

        public void setText(String text) {
            this.text = text;
        }
    }
}
