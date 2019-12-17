package com.projeto.funancial.controller;

import static org.mockito.Mockito.when;

import org.bson.types.ObjectId;
import org.junit.Test;
import org.mockito.Mockito;

import com.projeto.funancial.canonical.UsuarioCanonical;
import com.projeto.funancial.model.Usuario;
import com.projeto.funancial.service.HistoriaService;
import com.projeto.funancial.service.JogoService;
import com.projeto.funancial.service.UsuarioService;
import com.projeto.funancial.transformation.HistoriaTransformation;
import com.projeto.funancial.transformation.UsuarioTransformation;

public class JogoControllerTest {
	private UsuarioService svc = Mockito.mock(UsuarioService.class);
	private UsuarioTransformation transformation = Mockito.mock(UsuarioTransformation.class);
	private JogoService jogoService = Mockito.mock(JogoService.class);
	private HistoriaService historiaService = Mockito.mock(HistoriaService.class);
	private HistoriaTransformation historiaTransformation = Mockito.mock(HistoriaTransformation.class);
	private JogoController jogoController = new JogoController(svc, transformation, jogoService, historiaService, historiaTransformation);

	
	@Test
	public void iniciar_jogo_deve_retornar_usuario_informado_com_atributos_populados() {
		//config
		UsuarioCanonical usuarioCanonical = UsuarioCanonical.builder()
												._id(ObjectId.get())
												.email("usuarioTeste@teste.com")
												.senha("123")
												.build();
		UsuarioCanonical usuarioCanonicalInicializado = UsuarioCanonical.builder()
				._id(usuarioCanonical.get_id())
				.email(usuarioCanonical.getEmail())
				.senha(usuarioCanonical.getSenha())
				.nivel(1)
				.sequencia(1)
				.vida(30)
				.energia(30)
				.dinheiro(30)
				.build();
		Usuario usuarioInicializado = Usuario.builder()
					._id(usuarioCanonicalInicializado.get_id())
					.email(usuarioCanonicalInicializado.getEmail())
					.senha(usuarioCanonicalInicializado.getSenha())
					.nivel(usuarioCanonicalInicializado.getNivel())
					.sequencia(usuarioCanonicalInicializado.getSequencia())
					.vida(usuarioCanonicalInicializado.getVida())
					.energia(usuarioCanonicalInicializado.getEnergia())
					.dinheiro(usuarioCanonicalInicializado.getDinheiro())
					.build();
		
		when(jogoService.iniciarUsuario(usuarioCanonical)).thenReturn(usuarioCanonicalInicializado);
		when(transformation.convert(usuarioCanonicalInicializado)).thenReturn(usuarioInicializado);
		when(svc.save(usuarioInicializado)).thenReturn(usuarioInicializado);
		when(transformation.convert(usuarioInicializado)).thenReturn(usuarioCanonicalInicializado);
		//exec
		/*UsuarioCanonical resultado = this.jogoController.iniciarJogo(usuarioCanonical);
		//check
		assertNotNull(resultado.getNivel());
		assertNotNull(resultado.getSequencia());
		assertNotNull(resultado.getVida());
		assertNotNull(resultado.getEnergia());
		assertNotNull(resultado.getDinheiro());*/
	}
}
