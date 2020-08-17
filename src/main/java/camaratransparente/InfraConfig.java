package camaratransparente;

import java.time.Duration;

import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.caffeine.CaffeineCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.github.benmanes.caffeine.cache.Caffeine;

@Configuration
@EnableScheduling
@EnableCaching
public class InfraConfig {
	
	@Bean
	public RestTemplate restTemplate() {
		return new RestTemplate();
	}
	
	@Bean
	public WebMvcConfigurer corsConfigurer() {
		return new WebMvcConfigurer() {
			@Override
			public void addCorsMappings(CorsRegistry registry) {
				registry.addMapping("/**").allowedMethods("GET").allowedOrigins("*");
			}
		};
	}
	
	@Bean
	public Caffeine<?, ?> caffeine() {
		return Caffeine.newBuilder().expireAfterWrite(Duration.ofDays(7));
	}
	
	@Bean
	public CacheManager cacheManager(Caffeine<Object, Object> caffeine) {
	    CaffeineCacheManager caffeineCacheManager = new CaffeineCacheManager();
	    caffeineCacheManager.setCaffeine(caffeine);
	    return caffeineCacheManager;
	}
	
}