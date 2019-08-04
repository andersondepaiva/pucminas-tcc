package br.com.andersondepaiva.monitoramentobarragens.repository;

import org.springframework.stereotype.Repository;

import br.com.andersondepaiva.core.repository.IRepositoryMongoDb;
import br.com.andersondepaiva.monitoramentobarragens.model.MonitoramentoBarragem;

@Repository
public interface IMonitoramentoBarragemRepository extends
		IRepositoryMongoDb<MonitoramentoBarragem, String> {
}
