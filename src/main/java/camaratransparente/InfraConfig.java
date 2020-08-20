package camaratransparente;

import java.time.Duration;

import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.caffeine.CaffeineCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.github.benmanes.caffeine.cache.Caffeine;

import lombok.RequiredArgsConstructor;

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
	@Primary
	public CacheManager cacheManagerRespostasApi() {
		Caffeine<Object, Object> caffeine = Caffeine.newBuilder()
				.expireAfterWrite(Duration.ofDays(7));
		
		CaffeineCacheManager caffeineCacheManager = new CaffeineCacheManager();
	    caffeineCacheManager.setCaffeine(caffeine);
	    return caffeineCacheManager;
	}
	
	@Bean
	public CacheManager cacheManagerBucketsRateLimiting() {
		Caffeine<Object, Object> caffeine = Caffeine.newBuilder()
				.maximumSize(100000)
				.expireAfterWrite(Duration.ofHours(1));
				
	    CaffeineCacheManager caffeineCacheManager = new CaffeineCacheManager("buckets");
	    caffeineCacheManager.setCaffeine(caffeine);
	    return caffeineCacheManager;
	}
	
	@Configuration
	@RequiredArgsConstructor
	public static class RateLimitingConfig implements WebMvcConfigurer {
		
		private final RateLimitInterceptor interceptor;
		
	    @Override
	    public void addInterceptors(InterceptorRegistry registry) {
	        registry.addInterceptor(interceptor);
	    }
	    
	}
	
}