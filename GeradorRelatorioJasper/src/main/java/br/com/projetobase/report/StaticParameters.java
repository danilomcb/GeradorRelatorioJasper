package br.com.projetobase.report;

/**
 * Parâmetros estáticos necessários para gerar o relatório.  
 * @author danilo.barros
 *
 */
public interface StaticParameters {
	
	/**
	 * Informa o inicio do titulo do Relatório, para o caso de ser o relatório.
	 */
	static final String BEGINNING_TITTLE_REPORT = "Relátorio";
	
	/**
	 * Informa o inicio do titulo do Relatório, para o caso de ser uma pre-visualização.
	 */
	static final String BEGINNING_TITTLE_PREVIEWER = "Pré-relátorio";
	
	/**
	 * Nome do arquivo de template do jasperReport.
	 */
	static final String NAME_JASPER = "report.jasper";

}
