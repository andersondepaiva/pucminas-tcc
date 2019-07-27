package br.com.andersondepaiva.segurancacomunicacao.repository;

import org.springframework.stereotype.Repository;

import br.com.andersondepaiva.core.repository.IRepositoryMongoDb;
import br.com.andersondepaiva.segurancacomunicacao.model.Escopo;

@Repository
public interface IEscopoRepository extends IRepositoryMongoDb<Escopo, String> {
}
