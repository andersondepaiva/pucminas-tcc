package br.com.andersondepaiva.core.business;

import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.google.common.base.Strings;

import br.com.andersondepaiva.core.client.IUsuarioClient;
import br.com.andersondepaiva.core.dto.BaseDto;
import br.com.andersondepaiva.core.dto.Comparasion;
import br.com.andersondepaiva.core.dto.ParamDto;
import br.com.andersondepaiva.core.dto.UsuarioDto;
import br.com.andersondepaiva.core.infra.security.AuthService;
import br.com.andersondepaiva.core.infra.security.JwtConfig;
import br.com.andersondepaiva.core.model.BaseModel;
import br.com.andersondepaiva.core.model.UserChange;
import br.com.andersondepaiva.core.repository.IRepositoryMongoDb;

public abstract class Business<TModel extends BaseModel, PK extends Serializable, TDto extends BaseDto>
		implements IBusiness<TModel, PK, TDto> {

	@SuppressWarnings("unchecked")
	protected Class<TModel> tipoModel = (Class<TModel>) getTypeClass(0);
	@SuppressWarnings("unchecked")
	protected Class<TDto> tipoDto = (Class<TDto>) getTypeClass(2);
	protected IRepositoryMongoDb<TModel, PK> baseRepository;
	protected ModelMapper modelMapper = new ModelMapper();

	@Autowired
	protected AuthService authService;

	@Autowired
	protected JwtConfig jwtConfig;

	@Autowired
	protected IUsuarioClient usuarioClient;

	@Autowired
	protected MongoTemplate mongoTemplate;

	public Business(IRepositoryMongoDb<TModel, PK> baseRepository) {
		this.baseRepository = baseRepository;
		modelMapper.getConfiguration().setAmbiguityIgnored(true);
	}

	public void delete(PK pk) {
		Optional<TModel> entity = baseRepository.findById(pk);

		if (entity.isPresent()) {
			TModel entityModel = entity.get();
			entityModel.setExcluido(true);
			this.save(entityModel);
		}
	}

	public TDto save(TDto dto) throws ReflectiveOperationException {
		TModel objPersisted = (TModel) modelMapper.map(dto, tipoModel);
		objPersisted = save(objPersisted);
		TDto dtoPersisted = (TDto) modelMapper.map(objPersisted, tipoDto);

		if (!Strings.isNullOrEmpty(dto.getCorrelationId()))
			dtoPersisted.setCorrelationId(dto.getCorrelationId());

		return dtoPersisted;
	}

	public TModel save(TModel objModel) {
		String userId = getUserIdRequest();

		boolean sendEvent = false;

		if (objModel.getId() == null || objModel.getId().isEmpty()) {
			objModel.setDataInclusao(LocalDateTime.now());
			if (!Strings.isNullOrEmpty(userId)) {
				sendEvent = true;
				objModel.setIncluidoPor(UserChange.builder().id(userId).build());
			}
		} else {
			objModel.setDataAlteracao(LocalDateTime.now());
			if (!Strings.isNullOrEmpty(userId)) {
				sendEvent = true;
				objModel.setAlteradoPor(UserChange.builder().id(userId).build());
			}
		}

		objModel = baseRepository.save(objModel);

		if (sendEvent) {
			sendEventFillUserChange(modelMapper.map(objModel, tipoDto));
		}

		return objModel;
	}

	/*
	 * Method to send event to populate change user - Need Override
	 */
	protected void sendEventFillUserChange(TDto dto) {
		return;
	}

	protected String getUserIdRequest() {
		if (RequestContextHolder.getRequestAttributes() != null) {
			HttpServletRequest requestContext = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes())
					.getRequest();
			String headerAuthorization = requestContext.getHeader(jwtConfig.getHeader());
			return authService.getUserIdByToken(headerAuthorization);
		}

		return null;
	}

	public Optional<TDto> findById(PK pk) {
		Optional<TModel> entity = baseRepository.findById(pk);

		if (entity.isPresent()) {
			return Optional.of((TDto) modelMapper.map(entity.get(), tipoDto));
		} else {
			return Optional.ofNullable(null);
		}
	}

	public Page<TDto> getAll(Pageable pageable) {
		Page<TModel> dataSet = baseRepository.findAllByExcluidoFalse(pageable);

		if (dataSet.hasContent()) {
			List<TDto> dtos = new ArrayList<TDto>();
			dataSet.getContent().stream().forEach(model -> {
				dtos.add((TDto) modelMapper.map(model, tipoDto));
			});

			return new PageImpl<TDto>(dtos, pageable, dataSet.getTotalElements());
		}

		return new PageImpl<TDto>(new ArrayList<TDto>());
	}

	public void fillUserChange(PK pk) {
		if (pk == null) {
			return;
		}

		Optional<TModel> optionalEntity = baseRepository.findById(pk);

		if (!optionalEntity.isPresent()) {
			return;
		}

		TModel entity = optionalEntity.get();

		fillAlteradoPor(entity);

		fillIncluidoPor(entity);
	}

	protected TModel fillIncluidoPor(TModel entity) {
		if (entity.getIncluidoPor() != null && Strings.isNullOrEmpty(entity.getIncluidoPor().getNome())) {
			UsuarioDto usuarioDto = findUserChange(entity.getIncluidoPor().getId());

			if (usuarioDto != null) {
				updateUserChange("incluidoPor",
						UserChange.builder().id(usuarioDto.getId()).nome(usuarioDto.getPessoa().getNome()).build(),
						entity.getId());
			}
		}

		return entity;
	}

	protected TModel fillAlteradoPor(TModel entity) {
		if (entity.getAlteradoPor() != null && Strings.isNullOrEmpty(entity.getAlteradoPor().getNome())) {
			UsuarioDto usuarioDto = findUserChange(entity.getAlteradoPor().getId());

			if (usuarioDto != null) {
				updateUserChange("alteradoPor",
						UserChange.builder().id(usuarioDto.getId()).nome(usuarioDto.getPessoa().getNome()).build(),
						entity.getId());
			}
		}

		return entity;
	}

	protected void updateUserChange(String userChangeType, UserChange userChange, String idEntity) {
		Update update = new Update();
		update.set(userChangeType, userChange);
		executeAtomicUpdate(update, idEntity);
	}

	protected void executeAtomicUpdate(Update update, String idEntity) {
		update.set("dataAlteracao", LocalDateTime.now());
		mongoTemplate.updateFirst(buildQueryById(idEntity), update, tipoModel);
	}

	protected Query buildQueryById(String id) {
		return new Query(Criteria.where("id").is(id));
	}

	protected Page<TDto> filterAndPaginate(List<ParamDto> params, Pageable pageable, Sort sortable) {
		Page<TModel> resultPage = filterAndPaginateModel(params, pageable, sortable);

		if (resultPage.hasContent()) {
			List<TDto> dtos = new ArrayList<TDto>();
			resultPage.getContent().stream().forEach(model -> {
				dtos.add(modelMapper.map(model, tipoDto));
			});

			return new PageImpl<TDto>(dtos, pageable, resultPage.getTotalElements());
		}

		return new PageImpl<TDto>(new ArrayList<TDto>());
	}

	protected Page<TModel> filterAndPaginateModel(List<ParamDto> params, Pageable pageable, Sort sortable) {
		Query query = new Query().with(pageable);
		
		if (sortable != null) {
			query.with(sortable);
		}

		params.forEach(param -> {
			query.addCriteria(buildCriteria(param));
		});

		List<TModel> dataSet = mongoTemplate.find(query, tipoModel);
		long count = mongoTemplate.count(query, tipoModel);

		return new PageImpl<TModel>(dataSet, pageable, count);
	}

	private Criteria buildCriteria(ParamDto param) {
		Criteria criteria = Criteria.where(param.getField());

		if (param.isNot()) {
			criteria = criteria.not();
		}

		Comparasion comparator = param.getComparasion();

		switch (comparator) {
		case EQUALS:
			return criteria.is(param.getValue());
		case REGEX:
			return criteria.regex(param.getValue().toString());
		case MINOR:
			return criteria.lt(param.getValue());
		case MAJOR:
			return criteria.gt(param.getValue());
		case LIKE:
			String pattern = String.format(".*?%s.*?", param.getValue());
			return criteria.regex(pattern, "i");
		default:
			return criteria.is(param.getValue());
		}
	}

	protected UsuarioDto findUserChange(String id) {
		return usuarioClient.getUsuarioById(id);
	}

	private Class<?> getTypeClass(Integer indexClass) {
		Class<?> clazz = (Class<?>) ((ParameterizedType) this.getClass().getGenericSuperclass())
				.getActualTypeArguments()[indexClass == null ? 0 : indexClass];
		return clazz;
	}

}
