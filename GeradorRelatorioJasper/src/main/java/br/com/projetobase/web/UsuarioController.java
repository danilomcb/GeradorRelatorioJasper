package br.com.projetobase.web;

import java.io.Serializable;
import java.util.Arrays;
import java.util.List;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;

import br.com.caelum.vraptor.Controller;
import br.com.caelum.vraptor.Get;
import br.com.caelum.vraptor.Path;
import br.com.caelum.vraptor.observer.download.Download;
import br.com.caelum.vraptor.validator.I18nMessage;
import br.com.projetobase.arq.dao.Dao;
import br.com.projetobase.arq.web.CrudController;
import br.com.projetobase.dao.UsuarioDAO;
import br.com.projetobase.dominio.Usuario;
import br.com.projetobase.report.Pdf;
import br.com.projetobase.report.ReportDownload;
import br.com.projetobase.report.StaticParameters;
import br.com.projetobase.report.builder.InfoClienteReport;
import br.com.projetobase.report.builder.UsuarioReportBuilder;
import br.com.projetobase.report.exception.ServiceOrderNotFoundException;
import net.sf.jasperreports.engine.JasperPrint;

@Controller
@RequestScoped
@Path("/usuario")
public class UsuarioController extends CrudController<Usuario> implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@Inject
	private UsuarioDAO usuarioDAO;
	
	@Inject
	private UsuarioReportBuilder usuarioReportBuild; 
	
	@Override
	protected Dao<Usuario> getRepository() {
		return usuarioDAO;
	}
	
	@Get("/download/relatorio")
	public Download downloadReport() {
		List<Usuario> usuarios = usuarioDAO.all();
		String startHeadReport = StaticParameters.BEGINNING_TITTLE_REPORT;
		JasperPrint pringReport = null;
		try {
			pringReport = generateJasperPrint(startHeadReport, usuarios);
		} catch (ServiceOrderNotFoundException e) {
			validator.add(new I18nMessage("", "travelReport.empty"));
		}
		validator.onErrorRedirectTo(this).list();
		return new ReportDownload(Arrays.asList(pringReport), new Pdf(), usuarioReportBuild.getNomeDoRelatorio(), true);
	}
	
	private JasperPrint generateJasperPrint(String beginningTittle, List<Usuario> usuarios) throws ServiceOrderNotFoundException {
		InfoClienteReport infoClienteReport = new InfoClienteReport("CPCLIN", "Rua dos igapos, 112, Natal-RN", "");
		usuarioReportBuild.setBeginningTittle(beginningTittle).setInfoClienteReport(infoClienteReport).setUsuarios(usuarios).build();
		JasperPrint pringReport = usuarioReportBuild.getReport();
		return pringReport;
	}
}
