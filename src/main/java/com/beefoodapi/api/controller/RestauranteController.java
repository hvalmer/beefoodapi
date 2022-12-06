package com.beefoodapi.api.controller;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.ReflectionUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.beefoodapi.domain.exception.EntidadeNaoEncontradaException;
import com.beefoodapi.domain.model.Restaurante;
import com.beefoodapi.domain.repository.RestauranteRepository;
import com.beefoodapi.domain.service.CadastroRestauranteService;
import com.fasterxml.jackson.databind.ObjectMapper;

@RestController
@RequestMapping("/restaurantes")
public class RestauranteController {

	@Autowired
	private RestauranteRepository restauranteRepository;

	// injetando a classe de servico(Service)
	@Autowired
	private CadastroRestauranteService cadastroRestaurante;

	// listando todos os restaurantes
	@GetMapping
	public List<Restaurante> listar() {
		return restauranteRepository.todos();
	}

	// listando restaurante por ID
	@GetMapping(value = "/{restauranteId}")
	public ResponseEntity<Restaurante> buscar(@PathVariable Long restauranteId) {
		Restaurante restaurante = restauranteRepository.porId(restauranteId);

		// condicional
		if (restaurante != null) {
			return ResponseEntity.ok(restaurante);
		}

		return ResponseEntity.notFound().build();
	}

	// metodo para customizar as respostas dos status
	@PostMapping
	public ResponseEntity<?> adicionar(@RequestBody Restaurante restaurante) {
		try {
			restaurante = cadastroRestaurante.salvar(restaurante);

			return ResponseEntity.status(HttpStatus.CREATED).body(restaurante);

			// badRequest() requisicao ñ pode ser aceita
		} catch (EntidadeNaoEncontradaException ex) {
			return ResponseEntity.badRequest().body(ex.getMessage());

			// mensagem que infroma o cliente com a mensagem da excecao
		}
	}
	
	/*
	 * Utilizamos @PathVariable para especificar que o parâmetro fará parte da URL

		@RequestBody para obter esses valores do body da requisição.

		@PutMapping para mapear nosso endpoint para esse verbo, com esse path.
	*/
	@PutMapping("/{restauranteId}")
	public ResponseEntity<?> atualizar(@PathVariable Long restauranteId, 
			@RequestBody Restaurante restaurante){
		//implementando o metodo
		try {
			Restaurante restauranteAtual = restauranteRepository.porId(restauranteId);
			
			if(restauranteAtual != null) {
				//o copyProperties atualiza o id e o nome do restaurante atual
				BeanUtils.copyProperties(restaurante, restauranteAtual, "id");
				
				restauranteAtual = cadastroRestaurante.salvar(restauranteAtual);
				return ResponseEntity.ok(restauranteAtual);
			}
			
			return ResponseEntity.notFound().build();
			
		}catch(EntidadeNaoEncontradaException ex) {
			return ResponseEntity.badRequest()
					.body(ex.getMessage());
		}
	}
	
	@PatchMapping("/{restauranteId}")
	public ResponseEntity<?> atualizarParcial(@PathVariable("restauranteId") Long id,
			@RequestBody Map<String, Object> campos){
		//realizando uma busca
		Restaurante restauranteAtual = restauranteRepository.porId(id);
		//e verificando...
		if(restauranteAtual == null) {
			return ResponseEntity.notFound().build();
		}
		//imprimindo o conteudo do Map, atualizando os campos passados no Postman
		//metodo que mescla os valores do mapa campos para dentro do restaurante atual
		merge(campos, restauranteAtual);
		
		//atualizar propriedade específicas 
		return atualizar(id, restauranteAtual);
	}

	//metodo que mescla os valores do mapa campos para dentro do restaurante atual
	private void merge(Map<String, Object> dadosOrigem, Restaurante restauranteDestino) {
		//instanciando com objectMapper, que converte objetos JAVA em JSON e vice-versa	
		ObjectMapper objectMapper = new ObjectMapper();
		Restaurante restauranteOrigem = objectMapper.convertValue(dadosOrigem, Restaurante.class);
		
		System.out.println(restauranteOrigem);
		
		dadosOrigem.forEach((nomePropriedade, valorPropriedade) -> {
			//ReflectionUtils buscar um field(campo) da variavel nome da classe Restaurante
			Field field = ReflectionUtils.findField(Restaurante.class, nomePropriedade);
			//acessando um campo privado, variavel nomePropriedade
			field.setAccessible(true);
			
			//getField busca o valor do campo 
			Object novoValor = ReflectionUtils.getField(field, restauranteOrigem);
			
		//	System.out.println(nomePropriedade + " = " + valorPropriedade + " = " + novoValor);
			
			ReflectionUtils.setField(field, restauranteDestino, novoValor);
		});
	}
	
}
