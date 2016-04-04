package br.com.projetobase.report.builder;

import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.UUID;

import javax.inject.Inject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import br.com.projetobase.dominio.Usuario;
import br.com.projetobase.report.ReportPathResolver;
import br.com.projetobase.report.StaticParameters;
import br.com.projetobase.report.exception.ServiceOrderNotFoundException;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRParameter;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.util.JRLoader;

/**
 * Classe responsável por gerar o relatorio de viagens.
 * @author rafael.queiroz
 * @author danilo.barros
 *
 */
public class UsuarioReportBuilder {
	
	public static final Logger logger = LoggerFactory.getLogger(UsuarioReportBuilder.class);
	
	@Inject
	private ReportPathResolver pathResolver;
	
	private Map<String, Object> parameters;
	private InfoClienteReport infoClienteReport;
	private List<Usuario> reportList;
	private String tituloReport;
	private JasperPrint print;
	
	/**
	 * Construtor padrão.
	 */
	public UsuarioReportBuilder() {
		this.parameters = new HashMap<String, Object>();
		this.tituloReport = StaticParameters.BEGINNING_TITTLE_REPORT;
	}
	
	public void build() throws ServiceOrderNotFoundException {
		logger.trace("Contruindo relatório.");
		addParametrosDoCabecalho();
		criarJasperReport();
		logger.trace("Relatório contruido com sucesso.");
	}
	
	public UsuarioReportBuilder setUsuarios(List<Usuario> usuarios) {
		this.reportList = usuarios;
		return this;
	}
	
	public UsuarioReportBuilder setInfoClienteReport(InfoClienteReport infoClienteReport) {
		this.infoClienteReport = infoClienteReport;
		return this;
	}
	
	public UsuarioReportBuilder setBeginningTittle(String beginningTittle) {
		logger.trace("Informando o inicio do titulo do Relatório.");
		this.tituloReport = beginningTittle;
		return this;
	}
	
	public JasperPrint getReport() {
		return print;
	}

	public String getNomeDoRelatorio() {
		return "RV - " + UUID.randomUUID();
	}
	
	private void criarJasperReport() {
		logger.trace("Criando jasperReport.");
		try {
			String pathJasper = pathResolver.getPathFor(StaticParameters.NAME_JASPER);
			logger.trace("Carregando template a partir do path: {}.", pathJasper);
			JasperReport jasperReport = (JasperReport) JRLoader.loadObjectFromFile(pathJasper);
			JRBeanCollectionDataSource data = new JRBeanCollectionDataSource(reportList);
			print = JasperFillManager.fillReport(jasperReport, parameters, data);
			logger.trace("JasperRepor criado com sucesso.");
	      } catch (JRException e) {
	    	  logger.debug("Erro ao criar JasperReport", e);
	      }
	}

	/**
	 * Adiciona os parametros de cabeçalho do relatório.
	 * @throws ServiceOrderNotFoundException 
	 */
	private void addParametrosDoCabecalho() throws ServiceOrderNotFoundException {
		logger.trace("Adicionando os parametros do cabeçalho do relatório");
		parameters.put(JRParameter.REPORT_LOCALE, new Locale("pt","BR"));
		
		parameters.put("preReport", tituloReport);
		parameters.put("nomeEmpresa", infoClienteReport.getNome());
		parameters.put("enderecoEmpresa", infoClienteReport.getEndereco());
		
		String pathImage = pathResolver.getImagesPath(); 
		parameters.put("IMAGES_DIR", pathImage);
		logger.trace("Prametros adicionados com sucesso.");
	}
}
