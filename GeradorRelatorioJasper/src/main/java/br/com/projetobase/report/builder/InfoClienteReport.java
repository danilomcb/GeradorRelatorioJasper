package br.com.projetobase.report.builder;

public class InfoClienteReport {

	private String nome;
	private String endereco;
	private String pathImagem;

	public InfoClienteReport(String nome, String endereco, String pathImagem) {
		this.nome = nome;
		this.endereco = endereco;
		this.setPathImagem(pathImagem);
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getEndereco() {
		return endereco;
	}

	public void setEndereco(String endereco) {
		this.endereco = endereco;
	}

	public String getPathImagem() {
		return pathImagem;
	}

	public void setPathImagem(String pathImagem) {
		this.pathImagem = pathImagem;
	}

}
