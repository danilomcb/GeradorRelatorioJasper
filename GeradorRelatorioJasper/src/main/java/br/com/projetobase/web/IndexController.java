package br.com.projetobase.web;

import br.com.caelum.vraptor.Controller;
import br.com.caelum.vraptor.Path;
import br.com.projetobase.arq.web.AbstractController;

@Controller
public class IndexController extends AbstractController {

	@Path("/")
	public void index() {
		result.include("variable", "VRaptor!");
	}
	
}