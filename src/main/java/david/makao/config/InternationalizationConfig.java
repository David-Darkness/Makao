package david.makao.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.MessageSource;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.i18n.LocaleChangeInterceptor;
import org.springframework.web.servlet.i18n.SessionLocaleResolver;

import java.util.Locale;

@Configuration
public class InternationalizationConfig implements WebMvcConfigurer {

    @Bean
    public LocaleResolver localeResolver() {
        SessionLocaleResolver slr = new SessionLocaleResolver();
        slr.setDefaultLocale(Locale.forLanguageTag("es")); // Más robusto que new Locale("es")
        return slr;
    }

    @Bean
    public LocaleChangeInterceptor localeChangeInterceptor() {
        LocaleChangeInterceptor lci = new LocaleChangeInterceptor();
        lci.setParamName("lang");
        return lci;
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(localeChangeInterceptor())
                .addPathPatterns("/**"); // Asegura que se aplique a todas las rutas
    }

    @Bean
    public MessageSource messageSource() {
        ReloadableResourceBundleMessageSource messageSource =
                new ReloadableResourceBundleMessageSource();
        messageSource.setBasenames(
                "classpath:messages",
                "classpath:i18n/messages" // Rutas alternativas
        );
        messageSource.setDefaultEncoding("UTF-8");
        messageSource.setUseCodeAsDefaultMessage(true); // Usa el código si no encuentra mensaje
        messageSource.setFallbackToSystemLocale(false); // Evita fallback al locale del sistema
        messageSource.setCacheSeconds(3600); // Cache de 1 hora
        return messageSource;
    }
}

