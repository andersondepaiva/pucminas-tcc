package br.com.andersondepaiva.segurancacomunicacao.repository;

import java.util.List;

import org.springframework.stereotype.Repository;

import br.com.andersondepaiva.core.repository.IRepositoryMongoDb;
import br.com.andersondepaiva.segurancacomunicacao.model.Contato;

@Repository
public interface IContatoRepository extends IRepositoryMongoDb<Contato, String> {
	List<Contato> findAllByEscopoIdAndExcluidoFalse(String escopoId);
}
