package br.com.projetobase.dominio;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import br.com.projetobase.arq.dominio.AbstractEntity;

@Entity
public class Usuario extends AbstractEntity implements Serializable {
	
	private static final long serialVersionUID = 1L;

	@NotNull(message="{nome.vazio}")
	@Size(min=5, max=50, message="{nome.invalido}")
	private String nome;
	
	@NotNull(message="{email.vazio}")
	@Size(min=5, max=50, message="{email.invalido}")
	private String email;
	
	@Override
	public int hashCode() {
		return super.hashCode();
	}

	@Override
	public boolean equals(Object objeto) {
		return super.equals(objeto);
	}
	
	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

}