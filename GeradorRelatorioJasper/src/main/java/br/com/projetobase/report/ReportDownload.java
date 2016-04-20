package br.com.projetobase.report;

import java.io.IOException;

import javax.servlet.http.HttpServletResponse;

import br.com.caelum.vraptor.observer.download.ByteArrayDownload;
import br.com.caelum.vraptor.observer.download.Download;

/**
 * Reads bytes from a report into the result.
 *
 * @author William Pivotto
 *
 */

public class ReportDownload implements Download {
	
	private String format;
	private String nameFile;
	private boolean doDownload;
	private byte[] relatorio;
	
	public ReportDownload(byte[] relatorio, String format, String nameFile, boolean doDownlaod) {
		this.relatorio = relatorio;
		this.format = format;
		this.nameFile = nameFile;
		this.doDownload = doDownlaod;
	}

	public void write(HttpServletResponse response) throws IOException {
		new ByteArrayDownload(getContent(), getContentType(), getFileName(), doDownload).write(response);
	}
	
	private byte[] getContent(){
		return relatorio;
	}

	public String getContentType(){
		return format;
	}
	
	public String getFileName(){
		return nameFile + "." + format;
	}
}
