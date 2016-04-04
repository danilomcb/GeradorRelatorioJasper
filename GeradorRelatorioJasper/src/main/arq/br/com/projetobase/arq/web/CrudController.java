package br.com.projetobase.arq.web;

import java.util.Collection;

import javax.inject.Inject;
import javax.transaction.Transactional;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import br.com.caelum.vraptor.Get;
import br.com.caelum.vraptor.Path;
import br.com.caelum.vraptor.Post;
import br.com.caelum.vraptor.validator.Validator;
import br.com.projetobase.arq.dao.Dao;
import br.com.projetobase.arq.dominio.AbstractEntity;

public abstract class CrudController<T extends AbstractEntity> extends AbstractController {

	@Inject
	protected Validator validator;
	
	abstract protected Dao<T> getRepository();
	
	public void form() {
		Collection<T> entidades = getRepository().all();
		result.include("entidades", entidades);
	}
	
	@Get
	@Path("/form/{entidade.id}")
	public void form(T entidade) {
		Collection<T> entidades = getRepository().all();
		T entidadePersistida = getRepository().find(entidade.getId());
		result.include("entidade", entidadePersistida)
			.include("entidades", entidades);
	}
	
	@Post
	@Transactional
	public void salvar(@NotNull @Valid T entidade) {
		validator.onErrorRedirectTo(this).form();
		persistirEntidade(entidade);
		result.redirectTo(this).form();
	}

	private void persistirEntidade(T entidade) {
		if (entidade.getId() != null && entidade.getId() != 0) {
			getRepository().update(entidade);
		} else {
			getRepository().save(entidade);
		}
	}
	
	public T show(T entidade) {
		T entity = getRepository().find(entidade.getId());
		return entity;
	}
	
	@Path("/remover/{entidade.id}")
	@Get
	@Transactional
	public void remover(T entidade) {
		getRepository().remove(entidade);
		result.redirectTo(this).form();
	}
	
	public void list() {
		Collection<T> entidadesCadastradas = getRepository().all();
		result.include("entidades", entidadesCadastradas);
	}
	
}