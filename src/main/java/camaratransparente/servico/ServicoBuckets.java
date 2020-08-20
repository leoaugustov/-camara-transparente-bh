package camaratransparente.servico;

import java.time.Duration;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import io.github.bucket4j.Bandwidth;
import io.github.bucket4j.Bucket;
import io.github.bucket4j.Bucket4j;
import io.github.bucket4j.Refill;

@Service
public class ServicoBuckets {

	/**
	 * Pega o bucket referente ao IP informado. Se ele ainda não existir ele é criado.
	 */
	@Cacheable(value = "buckets", cacheManager = "cacheManagerBucketsRateLimiting")
	public Bucket pegarBucket(String ip) {
		return Bucket4j.builder()
				.addLimit(Bandwidth.classic(500, Refill.intervally(500, Duration.ofMinutes(1))))
				.build();
	}
	
}
