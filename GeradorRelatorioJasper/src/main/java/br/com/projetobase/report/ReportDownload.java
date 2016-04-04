package br.com.projetobase.report;

import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import br.com.caelum.vraptor.observer.download.ByteArrayDownload;
import br.com.caelum.vraptor.observer.download.Download;
import net.sf.jasperreports.engine.JasperPrint;

/**
 * Reads bytes from a report into the result.
 *
 * @author William Pivotto
 *
 */

public class ReportDownload implements Download {
	
	private final ExportFormat format;
	private List<JasperPrint> prints;
	private String nameFile;
	private boolean doDownload;
	
	public ReportDownload(List<JasperPrint> prints, ExportFormat format, String nameFile, boolean doDownload) {
		this.prints = prints;
		this.format = format;
		this.nameFile = nameFile;
		this.doDownload = doDownload;
	}

	public void write(HttpServletResponse response) throws IOException {
		new ByteArrayDownload(getContent(), getContentType(), getFileName(), doDownload).write(response);
	}
	
	private byte[] getContent(){
		return format.toByteArray(prints);
	}

	public String getContentType(){
		return format.getContentType();
	}
	
	public String getFileName(){
		return nameFile + "." + format.getExtension();
	}

}
