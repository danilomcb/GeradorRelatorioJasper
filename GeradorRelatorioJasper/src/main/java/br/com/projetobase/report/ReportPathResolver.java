package br.com.projetobase.report;

import java.io.File;
import java.util.Locale;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.servlet.ServletContext;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@ApplicationScoped
public class ReportPathResolver {

	public static final String DEFAULT_REPORTS_PATH = "/WEB-INF/reports";
	public static final String DEFAULT_SUBREPORTS_PATH = "/WEB-INF/reports/subreports";
	public static final String DEFAULT_IMAGES_PATH = "/WEB-INF/reports/images";
	private static final String DEFAULT_BUNDLE_NAME = "i18n_";
	private static final String SEPARATOR = File.separator;
	private final Logger logger = LoggerFactory.getLogger(ReportPathResolver.class);
	
	private ServletContext context;
	
	public ReportPathResolver() {
	}
	
	@Inject
	public ReportPathResolver(ServletContext context) {
		this.context = context;
		logger.debug("REPORT_DIR --> " + getReportsPath());
		logger.debug("SUBREPORT_DIR --> " + getSubReportsPath());
		logger.debug("IMAGES_DIR --> " + getImagesPath());
	}
	
	public String getPathFor(String pathReport){
		return getReportsPath() + pathReport;
	}
	
	public String getReportsPath(){
		return context.getRealPath(DEFAULT_REPORTS_PATH) + SEPARATOR;
	}
	
	public String getSubReportsPath(){
		return context.getRealPath(DEFAULT_SUBREPORTS_PATH) + SEPARATOR;
	}
	
	public String getImagesPath(){
		return context.getRealPath(DEFAULT_IMAGES_PATH) + SEPARATOR;
	}
	
	public String getResourceBundleFor(Locale locale){
		return getReportsPath() + DEFAULT_BUNDLE_NAME + locale.toString() + ".properties";
	}

	public String getImagesURI() {
		return context.getContextPath() + "/report.image?image=";
	}

}