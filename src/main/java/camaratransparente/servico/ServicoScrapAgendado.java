package camaratransparente.servico;

import java.io.IOException;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ServicoScrapAgendado {

	private static final String TODO_DIA_AS_DUAS_E_QUINZE_DA_MANHA = "0 15 2 * * *";
	
	private final ServicoScrap servicoScrap;
	
	
	
	@Scheduled(cron = TODO_DIA_AS_DUAS_E_QUINZE_DA_MANHA)
	private void realizarScrap() {
		try {
			servicoScrap.realizarScrap();
		}catch(IOException | InterruptedException e) {
			e.printStackTrace();
		}
	}
	
}
