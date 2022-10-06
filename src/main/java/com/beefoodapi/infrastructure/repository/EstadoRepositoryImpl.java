package com.beefoodapi.infrastructure.repository;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.beefoodapi.domain.model.Estado;
import com.beefoodapi.domain.repository.EstadoRepository;

@Component
public class EstadoRepositoryImpl implements EstadoRepository {

	// interface que gerencia o contexto de persistencia
	@PersistenceContext
	private EntityManager manager;

	// metodo para listar todos os estados
	@Override
	public List<Estado> todos() {
		// implementando a consulta
		return manager.createQuery("from Estado", Estado.class)
				.getResultList();
	}

	//metodo para buscar um objeto estado por id
	@Override
	public Estado porId(Long id) {
		return manager.find(Estado.class, id);
	}

	//implementando a inclusao de um novo estado, usando JPA
	//o merge retorna a instancia persistida, no caso estado, novos estados
	@Transactional
	@Override
	public Estado adicionar(Estado estado) {
		return manager.merge(estado);
	}

	//método para excluir um item do BD
	/*no metodo remover, buscar uma instancia que vai ser gerenciada pelo
	* contexto de persistencia*/
	@Transactional
	@Override
	public void remover(Estado estado) {
		//implementando o metodo buscar pelo id, ou seja, fazendo um find e chamando o remove
		estado = porId(estado.getId());
		manager.remove(estado);
	}
}
