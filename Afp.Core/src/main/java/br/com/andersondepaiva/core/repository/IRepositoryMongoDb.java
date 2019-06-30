package br.com.andersondepaiva.core.repository;

import java.io.Serializable;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.repository.NoRepositoryBean;

@NoRepositoryBean
public interface IRepositoryMongoDb<TModel, PK extends Serializable> extends MongoRepository<TModel, PK> {
	Page<TModel> findAllByExcluidoFalse(Pageable pageable);
	List<TModel> findAllByExcluidoFalse();
}
