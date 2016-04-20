package br.com.projetobase.report.builder;

import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import javax.inject.Inject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import br.com.projetobase.report.ReportPathResolver;
import br.com.projetobase.report.StaticParameters;
import br.com.projetobase.report.exception.ServiceOrderNotFoundException;
import net.sf.jasperreports.engine.JREmptyDataSource;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRParameter;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.JasperRunManager;
import net.sf.jasperreports.engine.util.JRLoader;

/**
 * Classe responsável por gerar o relatorio de viagens.
 * 
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
	private String nomeRelatorio;
	private byte[] relatorioEmBytes;

	/**
	 * Construtor padrão.
	 */
	public UsuarioReportBuilder() {
		this.parameters = new HashMap<String, Object>();
		this.nomeRelatorio = StaticParameters.NAME_REPORT;
	}

	public UsuarioReportBuilder setInfoClienteReport(InfoClienteReport infoClienteReport) {
		this.infoClienteReport = infoClienteReport;
		return this;
	}

	public UsuarioReportBuilder setGraficoUm(ArrayList<BufferedImage> image) {
		logger.trace("Informando os gráfico.");
		this.parameters.put("grafico_principal", image.get(0));
		this.parameters.put("grafico_00", image.get(1));
		this.parameters.put("grafico_01", image.get(2));
		this.parameters.put("grafico_10", image.get(3));
		this.parameters.put("grafico_11", image.get(4));
		return this;
	}

	public String getNomeDoRelatorio() {
		return nomeRelatorio;
	}
	
	public JasperReport getJasperReport() throws JRException {
		return (JasperReport) JRLoader.loadObjectFromFile(pathResolver.getPathFor(StaticParameters.NAME_JASPER));
	}

	/**
	 * Adiciona os parametros de cabeçalho do relatório.
	 * 
	 * @throws ServiceOrderNotFoundException
	 */
	private void addParametrosDoCabecalho() throws ServiceOrderNotFoundException {
		logger.trace("Adicionando os parametros do cabeçalho do relatório");
		parameters.put(JRParameter.REPORT_LOCALE, new Locale("pt", "BR"));

		parameters.put("nomeEmpresa", infoClienteReport.getNome());
		parameters.put("enderecoEmpresa", infoClienteReport.getEndereco());
		parameters.put("cnpjEmpresa", "80.713.625/0001-30");	

		String pathImage = pathResolver.getImagesPath();
		parameters.put("IMAGES_DIR", pathImage);
		logger.trace("Prametros adicionados com sucesso.");
	}

	public void run() throws JRException, ServiceOrderNotFoundException {
		JasperReport jasperReport = getJasperReport();
		addParametrosDoCabecalho();
 		relatorioEmBytes = JasperRunManager.runReportToPdf(jasperReport, parameters,  new JREmptyDataSource());
	}

	public String getContentType() {
		return "pdf";
	}

	public byte[] getReportBytes() {
		return relatorioEmBytes;
	}
}
