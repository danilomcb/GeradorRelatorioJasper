package br.com.projetobase.arq.web;

import javax.inject.Inject;

import br.com.caelum.vraptor.Result;

public abstract class AbstractController {

	@Inject
	protected Result result;

}