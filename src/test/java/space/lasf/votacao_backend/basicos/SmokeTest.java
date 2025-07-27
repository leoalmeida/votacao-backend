package space.lasf.votacao_backend.basicos;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;

import space.lasf.votacao_backend.VotacaoBackendApplication;
import space.lasf.votacao_backend.controller.AssociadoController;
import space.lasf.votacao_backend.controller.SessaoController;
import space.lasf.votacao_backend.controller.PautaController;
import space.lasf.votacao_backend.controller.UsuarioController;

@SpringBootTest
@ContextConfiguration(classes = VotacaoBackendApplication.class)
@ActiveProfiles("test")
public class SmokeTest {

	@Autowired
	private AssociadoController clienteController;
	@Autowired
	private PautaController produtoController;
	@Autowired
	private SessaoController pedidoController;
	@Autowired
	private UsuarioController usuarioController;

	@Test
	void contextLoads() throws Exception {
		assertThat(clienteController).isNotNull();
		assertThat(produtoController).isNotNull();
		assertThat(pedidoController).isNotNull();
		assertThat(usuarioController).isNotNull();
	}
}