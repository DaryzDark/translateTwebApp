package dev.daryz.translateT.translation;

import dev.daryz.translateT.DeepL.DeepLLanguageService;
import dev.daryz.translateT.DeepL.DeepLTranslatorService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.time.LocalDateTime;

@Controller
public class TranslationController {

    private final TranslationService translationService;
    private final DeepLTranslatorService deepLTranslatorService;
    private final DeepLLanguageService deepLLanguageService;

    public TranslationController(TranslationService translationService, DeepLTranslatorService deepLTranslatorService,
                                 DeepLLanguageService deepLLanguageService) {
        this.translationService = translationService;
        this.deepLTranslatorService = deepLTranslatorService;
        this.deepLLanguageService = deepLLanguageService;
    }

    @GetMapping("/")
    public String showForm(Model model, HttpServletRequest request) {
        TranslationRequest translationRequest = new TranslationRequest();
        translationRequest.setLanguages(deepLLanguageService.getSupportedLanguages());
        model.addAttribute("TranslationRequest", translationRequest);
        return "index";
    }

    @PostMapping("/translate")
    public String createTranslation(@ModelAttribute TranslationRequest translationRequest,
                                    DeepLTranslatorService translatorService,
                                    HttpServletRequest request,
                                    Model model
                                     )
    {
        Translation translation = new Translation(
        request.getRemoteAddr(),
        LocalDateTime.now(),
        translationRequest.getSourceLang(),
        translationRequest.getTargetLang(),
        translationRequest.getQuery(),
        null);
        translation.setResponse(deepLTranslatorService.translateQuery(translation.getQuery().toLowerCase(), translation.getSourceLang(), translation.getTargetLang()));
        translationService.saveTranslation(translation);
        translationRequest.setResponse(translation.getResponse());
        translationRequest.setLanguages(deepLLanguageService.getSupportedLanguages());
        model.addAttribute("TranslationRequest", translationRequest);
        return "index";
    }
}
