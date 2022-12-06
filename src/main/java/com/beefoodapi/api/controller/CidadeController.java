package com.beefoodapi.api.controller;

import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
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
import com.beefoodapi.domain.model.Cidade;
import com.beefoodapi.domain.repository.CidadeRepository;
import com.beefoodapi.domain.service.CadastroCidadeService;

@RestController
@RequestMapping("/cidades")
public class CidadeController {

	@Autowired
	private CidadeRepository cidadeRepository;

	// injetando o service controller
	@Autowired
	private CadastroCidadeService cadastroCidade;

	@GetMapping()
	public List<Cidade> listar() {
		return cidadeRepository.todos();
	}

	// metodo para retornar uma unica cidade
	@GetMapping("/{cidadeId}")
	public ResponseEntity<Cidade> buscar(@PathVariable Long cidadeId) {
		Cidade cidade = cidadeRepository.porId(cidadeId);

		if (cidade != null) {
			return ResponseEntity.ok(cidade);
		}

		return ResponseEntity.notFound().build();
	}

	// metodo para adicionar uma nova cadastro de cidade
	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public Cidade adicionar(@RequestBody Cidade cidade) {
		return cadastroCidade.salvar(cidade);
	}

	// metodo para atualizar as cidades
	@PutMapping("/{cidadeId}")
	public ResponseEntity<Cidade> atualizar(@PathVariable Long cidadeId, 
			@RequestBody Cidade cidade) {
		// buscando cidade existente no repositorio
		Cidade cidadeAtual = cidadeRepository.porId(cidadeId);

		// tratando com if...
		if (cidadeAtual != null) {
			// o copyProperties atualiza o id e o nome do cidade atual
			BeanUtils.copyProperties(cidade, cidadeAtual, "id");

			cidadeAtual = cadastroCidade.salvar(cidadeAtual);
			return ResponseEntity.ok(cidadeAtual);
		}

		return ResponseEntity.notFound().build();
	}

	// metodo para remover um cidade da lista
	@DeleteMapping("/{cidadeId}")
	public ResponseEntity<?> remover(@PathVariable Long cidadeId){
			
			//tratando cidade excluido com sucesso(200) 
			try {
				cadastroCidade.excluir(cidadeId);			
				return ResponseEntity.noContent().build();
				
			//tratando para entidade ñ encontrada(404)	
			}catch(EntidadeNaoEncontradaException ex){
				return ResponseEntity.notFound().build();
			
			//tratando para entidade que tem uma cidade que ñ pode ser excluida(409)	
			}catch(EntidadeEmUsoException ex) {
				return ResponseEntity.status(HttpStatus.CONFLICT)
						.body(ex.getMessage());
			}
	}
}
