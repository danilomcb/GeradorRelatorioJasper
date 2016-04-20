package br.com.projetobase.web;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.Serializable;
import java.net.URLEncoder;
import java.util.ArrayList;

import javax.enterprise.context.RequestScoped;
import javax.imageio.ImageIO;
import javax.inject.Inject;

import org.jboss.resteasy.client.ClientRequest;
import org.jboss.resteasy.client.ClientResponse;

import br.com.caelum.vraptor.Controller;
import br.com.caelum.vraptor.Get;
import br.com.caelum.vraptor.Path;
import br.com.caelum.vraptor.observer.download.Download;
import br.com.projetobase.arq.dao.Dao;
import br.com.projetobase.arq.web.CrudController;
import br.com.projetobase.dao.UsuarioDAO;
import br.com.projetobase.dominio.Usuario;
import br.com.projetobase.report.ReportDownload;
import br.com.projetobase.report.builder.InfoClienteReport;
import br.com.projetobase.report.builder.UsuarioReportBuilder;
import br.com.projetobase.report.exception.ServiceOrderNotFoundException;
import net.sf.jasperreports.engine.JRException;

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
	public Download downloadReport() throws IOException, ServiceOrderNotFoundException, JRException {
		ArrayList<BufferedImage> images = new ArrayList<BufferedImage>();
		images.add(0, gerarImage(getOptionsHeaderGraph()));
		images.add(1, gerarImage(getOptionsGraphs()));
		images.add(2, gerarImage(getOptionsGraphs()));
		images.add(3, gerarImage(getOptionsGraphs()));
		images.add(4, gerarImage(getOptionsGraphs()));

		generateJasperReport(images);
		return new ReportDownload(usuarioReportBuild.getReportBytes(), usuarioReportBuild.getContentType(),
				usuarioReportBuild.getNomeDoRelatorio(), true);

	}

	private String getOptionsGraphs() {
		return "{\"chart\":{\"width\":556,\"height\":380},\"xAxis\":{\"categories\":[\"Jan\",\"Feb\",\"Mar\"]},\"series\":[{\"data\":[106.4,71.5,29.9]}],\"width\":278,\"height\":130}";
	}

	private String getOptionsHeaderGraph() {
		return "{\"chart\":{\"width\":1110,\"height\":584},\"xAxis\":{\"categories\":[\"Jan\",\"Feb\",\"Mar\"]},\"series\":[{\"data\":[29.9,71.5,106.4]}]}";
	}

	private BufferedImage gerarImage(String opt) throws IOException {
		ClientRequest request = new ClientRequest("http://localhost:8080/highcharts-export-web/");
		request.accept("application/json");

		request.queryParameter("type", "image/png");
		request.queryParameter("filename", "graficoconsumido");
		request.queryParameter("options", URLEncoder.encode(opt, "ISO-8859-1"));// ISO-8859-1
																				// UTF-8
		request.queryParameter("constr", "Chart");
		request.queryParameter("async", "false");

		ClientResponse<byte[]> response = null;
		try {
			response = request.post(byte[].class);
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		if (response != null && response.getStatus() != 200) {
			throw new RuntimeException("Failed : HTTP error code : " + response.getStatus());
		}
		byte[] bytesEncoded = response.getEntity();
		BufferedImage img = ImageIO.read(new ByteArrayInputStream(bytesEncoded));
		return img;
	}

	private void generateJasperReport(ArrayList<BufferedImage> img) throws ServiceOrderNotFoundException, JRException {
		InfoClienteReport infoClienteReport = new InfoClienteReport("CPCLIN", "Rua dos igapos, 112, Natal-RN", "");
		usuarioReportBuild.setInfoClienteReport(infoClienteReport).setGraficoUm(img).run();
	}
}
