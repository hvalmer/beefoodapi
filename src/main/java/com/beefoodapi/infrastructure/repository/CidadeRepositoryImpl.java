package com.beefoodapi.infrastructure.repository;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.beefoodapi.domain.model.Cidade;
import com.beefoodapi.domain.repository.CidadeRepository;

@Component
public class CidadeRepositoryImpl implements CidadeRepository {

	// interface que gerencia o contexto de persistencia
	@PersistenceContext
	private EntityManager manager;
	
	// metodo para listar todos as cidades
	@Override
	public List<Cidade> todos(){
		return manager.createQuery("from Cidade", Cidade.class)
				.getResultList();
	}
	
	//metodo para buscar um objeto cidade por id
	@Override
	public Cidade porId(Long id) {
		return manager.find(Cidade.class, id);
	}
	
	//implementando a inclusao de um novo estado, usando JPA
	//o merge retorna a instancia persistida, no caso estado, novos estados
	@Transactional
	@Override
	public Cidade adicionar(Cidade cidade) {
		return manager.merge(cidade);
	}
	
	//método para excluir um item do BD
	/*no metodo remover, buscar uma instancia que vai ser gerenciada pelo
	* contexto de persistencia*/
	@Transactional
	@Override
	public void remover(Cidade cidade) {
		//implementando o metodo buscar pelo id, ou seja, fazendo um find e chamando o remove
		cidade = porId(cidade.getId());
		manager.remove(cidade);
	}
	
}
