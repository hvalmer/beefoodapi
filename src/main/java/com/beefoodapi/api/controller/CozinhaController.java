package com.beefoodapi.api.controller;

import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.beefoodapi.domain.exception.EntidadeEmUsoException;
import com.beefoodapi.domain.exception.EntidadeNaoEncontradaException;
import com.beefoodapi.domain.model.Cozinha;
import com.beefoodapi.domain.repository.CozinhaRepository;
import com.beefoodapi.domain.service.CadastroCozinhaService;

@RestController
@RequestMapping("/cozinhas")
public class CozinhaController {

	//definindo uma variavel de instancia
	@Autowired
	private CozinhaRepository cozinhaRepository;
	
	@Autowired
	private CadastroCozinhaService cadastroCozinha;
	
	//esse metodo aceita apenas o formato JSON nas suas requisicoes
	@GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
	public List<Cozinha> listar(){
		return cozinhaRepository.todas();
	}
	
	//metodo para retornar uma unica cozinha
	@GetMapping(value = "/{cozinhaId}")
	public ResponseEntity<Cozinha> buscar(@PathVariable Long cozinhaId) {
		Cozinha cozinha = cozinhaRepository.porId(cozinhaId);
		
		if(cozinha != null) {
			return ResponseEntity.ok(cozinha);
		}
		
		//return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
		return ResponseEntity.notFound().build();
	}
	
	//metodo para adicionar uma nova cozinha
	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public Cozinha adicionar(@RequestBody Cozinha cozinha) {
		return cadastroCozinha.salvar(cozinha);
	}
	
	//metodo para atualizar uma cozinha
	@PutMapping("/{cozinhaId}")
	public ResponseEntity<Cozinha> atualizar(@PathVariable Long cozinhaId,
			@RequestBody Cozinha cozinha){
		//buscando cozinha existente no repositorio
		Cozinha cozinhaAtual = cozinhaRepository.porId(cozinhaId);
		
		//tratativa com if
		if(cozinhaAtual != null) {
			//cozinhaAtual.setNome(cozinha.getNome());
			//o copyProperties atualiza o id e o nome da cozinha atual
			BeanUtils.copyProperties(cozinha, cozinhaAtual, "id");
			
			cozinhaAtual = cadastroCozinha.salvar(cozinhaAtual);
			return ResponseEntity.ok(cozinhaAtual);
		}
		
		return ResponseEntity.notFound().build();
	}
	
	//metodo para remover uma cozinha
	@DeleteMapping("/{cozinhaId}")
	public ResponseEntity<Cozinha> remover(@PathVariable Long cozinhaId){
		
		//tratando cozinha excluida com sucesso(200) 
		try {
			cadastroCozinha.excluir(cozinhaId);			
			return ResponseEntity.noContent().build();
			
		//tratando para entidade ñ encontrada(404)	
		}catch(EntidadeNaoEncontradaException ex){
			return ResponseEntity.notFound().build();
		
		//tratando para entidade que tem um restaurante que ñ pode ser excluido(409)	
		}catch(EntidadeEmUsoException ex) {
			return ResponseEntity.status(HttpStatus.CONFLICT).build();
		}
	}
}
