package br.com.andersondepaiva.core.business;

import java.io.Serializable;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import br.com.andersondepaiva.core.dto.BaseDto;
import br.com.andersondepaiva.core.model.BaseModel;

public interface IBusiness<TModel extends BaseModel, PK extends Serializable, TDto extends BaseDto> {

	TDto save(TDto entity) throws ReflectiveOperationException;

	void delete(PK pk);

	Optional<TDto> findById(PK pk);

	Page<TDto> getAll(Pageable pageable);

	TModel save(TModel objModel);

	void fillUserChange(PK pk);
}
