package model;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class Categoria {
	private int id_categoria;
	private String nome_categoria;
	
	public int getId_categoria() {
		return id_categoria;
	}
	
	public void setId_categoria(int id_categoria) {
		this.id_categoria = id_categoria;
	}
	
	public String getNome_categoria() {
		return nome_categoria;
	}
	
	public void setNome_categoria(String nome_categoria) {
		this.nome_categoria = nome_categoria;
	}
	
	public Categoria() {
		super();
	}
	
	public Categoria(int id_categoria, String nome_categoria) {
		super();
		this.id_categoria = id_categoria;
		this.nome_categoria = nome_categoria;
	}
	
	@Override
	public int hashCode() {
		return Objects.hash(id_categoria, nome_categoria);
	}
	
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Categoria other = (Categoria) obj;
		return id_categoria == other.id_categoria && Objects.equals(nome_categoria, other.nome_categoria);
	}
	
	@Override
	public String toString() {
		return "Categoria [id_categoria=" + id_categoria + ", nome_categoria=" + nome_categoria + "]";
	}
	
	public String exibirCategoria() {
		return ("ID: " + this.id_categoria + " | NOME: " + this.nome_categoria + 
				"\n------------------------------------------");
	}
	
	public int indexCategoria(int id_buscado, HashMap<Integer, Categoria> listaCategorias) {
	    int indexCategoria = -1;

	    for (Map.Entry<Integer, Categoria> categoria : listaCategorias.entrySet()) {
	        if (id_buscado == categoria.getValue().getId_categoria()) {
	            indexCategoria = categoria.getKey();
	            break;
	        }
	    }

	    return indexCategoria;
	}
	
	public void perfilCategoria(Categoria categoria) {
		System.out.println("------------------------------------------");
		System.out.println("01. ID: " + this.id_categoria);
		System.out.println("02. NOME: " + this.nome_categoria);
		System.out.println("03. SAIR");
		System.out.println("------------------------------------------");				
	}
}
