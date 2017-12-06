package modelo;

import java.io.Serializable;

public class Proteina implements Serializable{

	private String id;
	private String nome;
	private String organismo;
	private String sequencia;
	
	public Proteina(String id, String nome, String organismo, String sequencia) {
		super();
		this.id = id;
		this.nome = nome;
		this.organismo = organismo;
		this.sequencia = sequencia;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}
	
	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getSequencia() {
		return sequencia;
	}

	public void setSequencia(String sequencia) {
		this.sequencia = sequencia;
	}

	public String getOrganismo() {
		return organismo;
	}

	public void setOrganismo(String organismo) {
		this.organismo = organismo;
	}

}
