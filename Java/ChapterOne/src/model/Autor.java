package model;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class Autor {
	private int id_autor;
	private String nome_autor;
	private String email_autor;
	private String telefone_autor;
	private String bio_autor;
	private String imagem_autor;
	private HashMap<Integer, Livro> livros_autor;
	
	public int getId_autor() {
		return id_autor;
	}
	
	public void setId_autor(int id_autor) {
		this.id_autor = id_autor;
	}
	
	public String getNome_autor() {
		return nome_autor;
	}
	
	public void setNome_autor(String nome_autor) {
		this.nome_autor = nome_autor;
	}
	
	public String getEmail_autor() {
		return email_autor;
	}
	
	public void setEmail_autor(String email_autor) {
		this.email_autor = email_autor;
	}
	
	public String getTelefone_autor() {
		return telefone_autor;
	}
	
	public void setTelefone_autor(String telefone_autor) {
		this.telefone_autor = telefone_autor;
	}
	
	public String getBio_autor() {
		return bio_autor;
	}
	
	public void setBio_autor(String bio_autor) {
		this.bio_autor = bio_autor;
	}
	
	public String getImagem_autor() {
		return imagem_autor;
	}
	
	public void setImagem_autor(String imagem_autor) {
		this.imagem_autor = imagem_autor;
	}

	public HashMap<Integer, Livro> getLivros_autor() {
		return livros_autor;
	}

	public void setLivros_autor(HashMap<Integer, Livro> livros_autor) {
		this.livros_autor = livros_autor;
	}

	public Autor() {
		super();
	}

	public Autor(int id_autor, String nome_autor, String email_autor, String telefone_autor, String bio_autor,
			String imagem_autor, HashMap<Integer, Livro> livros_autor) {
		super();
		this.id_autor = id_autor;
		this.nome_autor = nome_autor;
		this.email_autor = email_autor;
		this.telefone_autor = telefone_autor;
		this.bio_autor = bio_autor;
		this.imagem_autor = imagem_autor;
		this.livros_autor = livros_autor;
	}
	
	@Override
	public int hashCode() {
		return Objects.hash(bio_autor, email_autor, id_autor, imagem_autor, livros_autor, nome_autor, telefone_autor);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Autor other = (Autor) obj;
		return Objects.equals(bio_autor, other.bio_autor) && Objects.equals(email_autor, other.email_autor)
				&& id_autor == other.id_autor && Objects.equals(imagem_autor, other.imagem_autor)
				&& Objects.equals(livros_autor, other.livros_autor) && Objects.equals(nome_autor, other.nome_autor)
				&& Objects.equals(telefone_autor, other.telefone_autor);
	}

	@Override
	public String toString() {
		return "Autor [id_autor=" + id_autor + ", nome_autor=" + nome_autor + ", email_autor=" + email_autor
				+ ", telefone_autor=" + telefone_autor + ", bio_autor=" + bio_autor + ", imagem_autor=" + imagem_autor
				+ ", livros_autor=" + livros_autor + "]";
	}
	
	public String exibirAutor() {
		return ("ID: " + this.id_autor + " | NOME: " + this.nome_autor + 
				"\n------------------------------------------");
	}
	
	public int indexAutor(int id_buscado, HashMap<Integer, Autor> listaAutores) {
	    int indexAutor = -1;

	    for (Map.Entry<Integer, Autor> entry : listaAutores.entrySet()) {
	        if (id_buscado == entry.getValue().getId_autor()) {
	            indexAutor = entry.getKey();
	            break;
	        }
	    }

	    return indexAutor;
	}
	
	public void perfilAutor(Autor autor) {
		System.out.println("------------------------------------------");
		System.out.println("01. ID: " + this.id_autor);
		System.out.println("02. NOME: " + this.nome_autor);
		System.out.println("03. EMAIL: " + this.email_autor);
		System.out.println("04. TELEFONE: " + this.telefone_autor);
		System.out.println("05. BIO: " + this.bio_autor);
		System.out.println("06. IMAGEM: " + this.imagem_autor);
		System.out.println("07. LIVROS");
		System.out.println("08. SAIR");
		System.out.println("------------------------------------------");				
	}
}
