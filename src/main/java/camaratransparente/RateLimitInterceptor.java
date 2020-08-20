package camaratransparente;

import java.time.Duration;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import camaratransparente.error.exception.QuantidadeRequisicoesEsgotadaException;
import camaratransparente.servico.ServicoBuckets;
import io.github.bucket4j.Bucket;
import io.github.bucket4j.ConsumptionProbe;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class RateLimitInterceptor implements HandlerInterceptor {

	private final ServicoBuckets servicoBucket;
	
	
	
    @Override
    public boolean preHandle(HttpServletRequest requisicao, HttpServletResponse resposta, Object handler) throws Exception {
    	String ip = requisicao.getRemoteAddr();
    	
    	Bucket tokenBucket = servicoBucket.pegarBucket(ip);
    	ConsumptionProbe detalhesConsumoTokens = tokenBucket.tryConsumeAndReturnRemaining(1);
    	
    	if (detalhesConsumoTokens.isConsumed()) {
            resposta.addHeader("X-Rate-Limit-Remaining", String.valueOf(detalhesConsumoTokens.getRemainingTokens()));
            return true;
        } else {
        	long segundosAteProximaTentativa = Duration.ofNanos(detalhesConsumoTokens.getNanosToWaitForRefill()).getSeconds();
        	
        	throw new QuantidadeRequisicoesEsgotadaException(segundosAteProximaTentativa);
        }
    }
    
}
