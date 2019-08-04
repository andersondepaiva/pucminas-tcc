package br.com.andersondepaiva.monitoramentobarragens.repository;

import org.springframework.stereotype.Repository;

import br.com.andersondepaiva.core.repository.IRepositoryMongoDb;
import br.com.andersondepaiva.monitoramentobarragens.model.Barragem;

@Repository
public interface IBarragemRepository extends
		IRepositoryMongoDb<Barragem, String> {
}
