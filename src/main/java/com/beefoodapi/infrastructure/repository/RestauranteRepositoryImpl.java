package com.beefoodapi.infrastructure.repository;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.beefoodapi.domain.model.Restaurante;
import com.beefoodapi.domain.repository.RestauranteRepository;

@Component
public class RestauranteRepositoryImpl implements RestauranteRepository {

	// interface que gerencia o contexto de persistencia
	@PersistenceContext
	private EntityManager manager;
	
	//metodo para listar todos os restaurantes
	@Override
	public List<Restaurante> todos() {
		//implementando a consulta
		return manager.createQuery("from Restaurante", Restaurante.class)
				.getResultList();
	}

	//metodo para buscar um objeto restaurante por id
	@Override
	public Restaurante porId(Long id) {
		return manager.find(Restaurante.class, id);
	}

	//implementando a inclusao de um novo restaurante, usando JPA
	//o merge retorna a instancia persistida, no caso restauranre, novos restaurantes
	@Override
	@Transactional
	public Restaurante adicionar(Restaurante restaurante) {
		return manager.merge(restaurante);
	}

	//método para excluir um item do BD
	/*no metodo remover, buscar uma instancia que vai ser gerenciada pelo
	* contexto de persistencia*/
	@Override
	@Transactional
	public void remover(Restaurante restaurante) {
		//implementando o metodo buscar pelo id, ou seja, fazendo um find e chamando o remove
		restaurante = porId(restaurante.getId());
		manager.remove(restaurante);
	}

}
