package br.com.andersondepaiva.monitoramentobarragens.repository;

import org.springframework.stereotype.Repository;

import br.com.andersondepaiva.core.repository.IRepositoryMongoDb;
import br.com.andersondepaiva.monitoramentobarragens.model.ParametroAlerta;

@Repository
public interface IParametroAlertaRepository extends
		IRepositoryMongoDb<ParametroAlerta, String> {
}
