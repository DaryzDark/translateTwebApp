package dev.daryz.translateT.translation;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TranslationService {
    private final TranslationRepository translationRepository;

    public TranslationService(TranslationRepository translationRepository) {
        this.translationRepository = translationRepository;
    }

    public void saveTranslation(Translation translation) {
        translationRepository.save(translation);
    }

    public Translation getTranslation(Long id) {
        return translationRepository.findById(id);
    }

    public List<Translation> getAllTranslations() {
        return translationRepository.findAll();
    }

    public List<Translation> getTranslationsByIpAddress(String ipAddress) {
        return translationRepository.findByIpAddress(ipAddress);
    }

    public void deleteTranslation(Long id) {
        translationRepository.deleteById(id);
    }
}
