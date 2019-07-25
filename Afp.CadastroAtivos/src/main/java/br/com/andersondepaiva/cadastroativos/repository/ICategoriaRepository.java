package br.com.andersondepaiva.cadastroativos.repository;

import org.springframework.stereotype.Repository;

import br.com.andersondepaiva.cadastroativos.model.Categoria;
import br.com.andersondepaiva.core.repository.IRepositoryMongoDb;

@Repository
public interface ICategoriaRepository extends IRepositoryMongoDb<Categoria, String> {
}
