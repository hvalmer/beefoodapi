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
import com.beefoodapi.domain.model.Estado;
import com.beefoodapi.domain.repository.EstadoRepository;
import com.beefoodapi.domain.service.CadastroEstadoService;

@RestController
@RequestMapping("/estados")
public class EstadoController {

	@Autowired
	private EstadoRepository estadoRepository;

	// injetando o service controller
	@Autowired
	private CadastroEstadoService cadastroEstado;

	@GetMapping()
	public List<Estado> listar() {
		return estadoRepository.todos();
	}

	// metodo para retornar um unico estado
	@GetMapping("/{estadoId}")
	public ResponseEntity<Estado> buscar(@PathVariable Long estadoId) {
		Estado estado = estadoRepository.porId(estadoId);

		if (estado == null) {
			return ResponseEntity.ok(estado);
		}

		return ResponseEntity.notFound().build();
	}

	// metodo para adicionar um novo cadastro de estado
	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public Estado adicionar(@RequestBody Estado estado) {
		return cadastroEstado.salvar(estado);
	}

	// metodo para atualizar os estados
	@PutMapping("/{estadoId}")
	public ResponseEntity<Estado> atualizar(@PathVariable Long estadoId, 
			@RequestBody Estado estado) {
		// buscando estado existente no repositorio
		Estado estadoAtual = estadoRepository.porId(estadoId);

		// tratando com if...
		if (estadoAtual != null) {
			// o copyProperties atualiza o id e o nome do estado atual
			BeanUtils.copyProperties(estado, estadoAtual, "id");

			estadoAtual = cadastroEstado.salvar(estadoAtual);
			return ResponseEntity.ok(estadoAtual);
		}

		return ResponseEntity.notFound().build();
	}

	// metodo para remover um estado da lista
	@DeleteMapping("/{estadoId}")
	public ResponseEntity<?> remover(@PathVariable Long estadoId){
			
			//tratando estado excluido com sucesso(200) 
			try {
				cadastroEstado.excluir(estadoId);			
				return ResponseEntity.noContent().build();
				
			//tratando para entidade ñ encontrada(404)	
			}catch(EntidadeNaoEncontradaException ex){
				return ResponseEntity.notFound().build();
			
			//tratando para entidade que tem um estado que ñ pode ser excluido(409)	
			}catch(EntidadeEmUsoException ex) {
				return ResponseEntity.status(HttpStatus.CONFLICT)
						.body(ex.getMessage());
			}
	}
}
