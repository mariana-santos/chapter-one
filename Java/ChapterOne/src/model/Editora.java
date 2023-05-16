package model;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class Editora {
	private int id_editora;
	private String nome_editora;
	private String endereco_editora;
	private String telefone_editora;
	
	public int getId_editora() {
		return id_editora;
	}
	
	public void setId_editora(int id_editora) {
		this.id_editora = id_editora;
	}
	
	public String getNome_editora() {
		return nome_editora;
	}
	
	public void setNome_editora(String nome_editora) {
		this.nome_editora = nome_editora;
	}
	
	public String getEndereco_editora() {
		return endereco_editora;
	}
	
	public void setEndereco_editora(String endereco_editora) {
		this.endereco_editora = endereco_editora;
	}
	
	public String getTelefone_editora() {
		return telefone_editora;
	}
	
	public void setTelefone_editora(String telefone_editora) {
		this.telefone_editora = telefone_editora;
	}

	public Editora() {
		super();
	}

	public Editora(int id_editora, String nome_editora, String endereco_editora, String telefone_editora) {
		super();
		this.id_editora = id_editora;
		this.nome_editora = nome_editora;
		this.endereco_editora = endereco_editora;
		this.telefone_editora = telefone_editora;
	}
	
	@Override
	public int hashCode() {
		return Objects.hash(endereco_editora, id_editora, nome_editora, telefone_editora);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Editora other = (Editora) obj;
		return Objects.equals(endereco_editora, other.endereco_editora) && id_editora == other.id_editora
				&& Objects.equals(nome_editora, other.nome_editora)
				&& Objects.equals(telefone_editora, other.telefone_editora);
	}

	@Override
	public String toString() {
		return "Editora [id_editora=" + id_editora + ", nome_editora=" + nome_editora + ", endereco_editora="
				+ endereco_editora + ", telefone_editora=" + telefone_editora + "]";
	}
	
	public String exibirEditora() {
		return ("ID: " + this.id_editora + " | NOME: " + this.nome_editora + 
				"\n------------------------------------------");
	}
	
	public int indexEditora(int id_buscado, HashMap<Integer, Editora> listaEditoras) {
	    int indexEditora = -1;

	    for (Map.Entry<Integer, Editora> entry : listaEditoras.entrySet()) {
	        if (id_buscado == entry.getValue().getId_editora()) {
	            indexEditora = entry.getKey();
	            break;
	        }
	    }

	    return indexEditora;
	}
	
	public void perfilEditora(Editora editora) {
		System.out.println("------------------------------------------");
		System.out.println("01. ID: " + this.id_editora);
		System.out.println("02. NOME: " + this.nome_editora);
		System.out.println("03. ENDEREÇO: " + this.endereco_editora);
		System.out.println("04. TELEFONE: " + this.telefone_editora);
		System.out.println("05. SAIR");
		System.out.println("------------------------------------------");				
	}
}
