package com.beefoodapi.infrastructure.repository;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.beefoodapi.domain.model.Cozinha;
import com.beefoodapi.domain.repository.CozinhaRepository;

@Component
public class CozinhaRepositoryImpl implements CozinhaRepository {

	// interface que gerencia o contexto de persistencia
		@PersistenceContext
		private EntityManager manager;

		//metodo para listar todas as cozinhas
		@Override
		public List<Cozinha> todas() {
			// implementando a consulta
			return manager.createQuery("from Cozinha", Cozinha.class)
					.getResultList();
		}
		
		//criando um metodo para buscar um objeto cozinha por id
		@Override
		public Cozinha porId(Long id) {
			return manager.find(Cozinha.class, id);
		}
		
		//implementando a inclusao de uma nova cozinha, usando JPA
		//o merge retorna a instancia persistida, no caso cozinha, novas cozinhas
		@Transactional
		@Override
		public Cozinha adicionar(Cozinha cozinha) {
			return manager.merge(cozinha);
		}
		
		//método para excluir um item do BD
		/*no metodo remover, buscar uma instancia que vai ser gerenciada pelo
		 * contexto de persistencia*/
		@Transactional
		@Override
		public void remover(Cozinha cozinha) {
			//implementando o metodo buscar pelo id, ou seja, fazendo um find e chamando o remove
			cozinha = porId(cozinha.getId());
			manager.remove(cozinha);
		}
}
