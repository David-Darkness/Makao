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

/**
 * Configuración para la internacionalización (i18n) de la aplicación.
 * Permite manejar múltiples idiomas mediante parametros de solicitud (ej: "?lang=en").
 *
 * <p>Incluye:
 * <ul>
 *   <li>Resolución del locale (idioma) basado en sesión.</li>
 *   <li>Interceptor para cambiar dinámicamente el locale.</li>
 *   <li>Fuente de mensajes con soporte para UTF-8 y caché.</li>
 * </ul>
 *
 * @author David Makao
 * @version 1.0
 * @see WebMvcConfigurer
 * @see MessageSource
 */
@Configuration
public class InternationalizationConfig implements WebMvcConfigurer {

    /**
     * Define el resolver de locale predeterminado (basado en sesión).
     * Establece el idioma predeterminado como español ("es").
     *
     * @return Instancia de {@link SessionLocaleResolver} configurada.
     */
    @Bean
    public LocaleResolver localeResolver() {
        SessionLocaleResolver slr = new SessionLocaleResolver();
        slr.setDefaultLocale(Locale.forLanguageTag("es")); // Más robusto que new Locale("es")
        return slr;
    }

    /**
     * Crea un interceptor para cambiar el locale dinámicamente mediante el parámetro "lang".
     * Ejemplo: "/ruta?lang=en" cambia a inglés.
     *
     * @return Interceptor configurado.
     */
    @Bean
    public LocaleChangeInterceptor localeChangeInterceptor() {
        LocaleChangeInterceptor lci = new LocaleChangeInterceptor();
        lci.setParamName("lang");
        return lci;
    }

    /**
     * Registra el interceptor de cambio de locale para todas las rutas.
     *
     * @param registry Registro de interceptores de Spring MVC.
     */
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(localeChangeInterceptor())
                .addPathPatterns("/**"); // Aplica a todas las rutas
    }

    /**
     * Configura la fuente de mensajes para internacionalización.
     * Busca archivos de propiedades en:
     * <ul>
     *   <li><code>classpath:messages.properties</code></li>
     *   <li><code>classpath:i18n/messages.properties</code></li>
     * </ul>
     *
     * @return {@link ReloadableResourceBundleMessageSource} configurado con:
     *         <ul>
     *           <li>Codificación UTF-8.</li>
     *           <li>Uso del código como mensaje predeterminado (si no se encuentra la clave).</li>
     *           <li>Deshabilitación del fallback al locale del sistema.</li>
     *           <li>Caché de 1 hora (3600 segundos).</li>
     *         </ul>
     */
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