package br.com.andersondepaiva.gateway.repository;

import org.springframework.stereotype.Repository;

import br.com.andersondepaiva.core.repository.IRepositoryMongoDb;
import br.com.andersondepaiva.gateway.model.Requisicao;

@Repository
public interface RequisicaoRepository extends IRepositoryMongoDb<Requisicao, String> {

}
