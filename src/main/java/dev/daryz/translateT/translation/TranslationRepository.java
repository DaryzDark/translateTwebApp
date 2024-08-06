package dev.daryz.translateT.translation;

import java.util.List;
import java.util.Optional;

public interface TranslationRepository {
    void save(Translation translation);
    Translation findById(Long id);
    List<Translation> findAll();
    List<Translation> findByIpAddress(String ipAddress);
    void deleteById(Long id);
}
