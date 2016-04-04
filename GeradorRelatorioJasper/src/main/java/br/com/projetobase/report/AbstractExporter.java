package br.com.projetobase.report;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.Map;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRExporter;
import net.sf.jasperreports.engine.JRExporterParameter;
import net.sf.jasperreports.engine.JasperPrint;

import com.google.common.collect.Maps;
import com.google.common.io.Closeables;
import com.google.common.io.Flushables;

public abstract class AbstractExporter implements ExportFormat {
	
	protected Map<JRExporterParameter, Object> parameters = Maps.newHashMap();
	
	public AbstractExporter(){
		defaultParameters();
	}
	
	public ExportFormat configure(JRExporterParameter parameter, Object value) {
		parameters.put(parameter, value);
		return this;
	}
	
	public Map<JRExporterParameter, Object> getParameters(){
		return this.parameters;
	}
	
	protected void defaultParameters(){
		configure(JRExporterParameter.CHARACTER_ENCODING, "UTF-8");
	}
	
	protected abstract JRExporter setup();
	
	public boolean supportsBatchMode() {
		return true;
	}
	
	public byte[] toByteArray(List<JasperPrint> print) {
		ByteArrayOutputStream output = new ByteArrayOutputStream();
	
		try {
			
			JRExporter exporter = setup();
			exporter.setParameters(getParameters());
			
			if (print.size() > 1)
				exporter.setParameter(JRExporterParameter.JASPER_PRINT_LIST, print);
			else
				exporter.setParameter(JRExporterParameter.JASPER_PRINT, print.get(0));
			
			exporter.setParameter(JRExporterParameter.OUTPUT_STREAM, output);
			exporter.exportReport();
			Flushables.flushQuietly(output);
			return output.toByteArray();  
			
		} catch (JRException e) {
			throw new RuntimeException(e);
		}
		finally {
			try {
				Closeables.close(output, true);
				parameters.clear();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
}
