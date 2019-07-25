package br.com.andersondepaiva.cadastroativos.repository;

import org.springframework.stereotype.Repository;

import br.com.andersondepaiva.cadastroativos.model.Equipamento;
import br.com.andersondepaiva.core.repository.IRepositoryMongoDb;

@Repository
public interface IEquipamentoRepository extends IRepositoryMongoDb<Equipamento, String> {
}
