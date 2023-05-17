package model;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class Livro {
	private int id_livro;
	private String titulo_livro;
	private String resumo_livro;
	private int ano_livro;
	private int paginas_livro;
	private String isbn_livro;
	private int id_categoria_livro;
	private int id_editora_livro;
	private String imagem_livro;
	private double preco_livro;
	private double desconto_livro;
	private HashMap<Integer, Autor> autores_livro;
	
	public int getId_livro() {
		return id_livro;
	}
	
	public void setId_livro(int id_livro) {
		this.id_livro = id_livro;
	}
	
	public String getTitulo_livro() {
		return titulo_livro;
	}
	
	public void setTitulo_livro(String titulo_livro) {
		this.titulo_livro = titulo_livro;
	}
	
	public String getResumo_livro() {
		return resumo_livro;
	}
	
	public void setResumo_livro(String resumo_livro) {
		this.resumo_livro = resumo_livro;
	}
	
	public int getAno_livro() {
		return ano_livro;
	}
	
	public void setAno_livro(int ano_livro) {
		this.ano_livro = ano_livro;
	}
	
	public int getPaginas_livro() {
		return paginas_livro;
	}
	
	public void setPaginas_livro(int paginas_livro) {
		this.paginas_livro = paginas_livro;
	}
	public String getIsbn_livro() {
		return isbn_livro;
	}
	
	public void setIsbn_livro(String isbn_livro) {
		this.isbn_livro = isbn_livro;
	}
	
	public int getId_categoria_livro() {
		return id_categoria_livro;
	}
	
	public void setId_categoria_livro(int id_categoria_livro) {
		this.id_categoria_livro = id_categoria_livro;
	}
	
	public int getId_editora_livro() {
		return id_editora_livro;
	}
	
	public void setId_editora_livro(int id_editora_livro) {
		this.id_editora_livro = id_editora_livro;
	}
	
	public String getImagem_livro() {
		return imagem_livro;
	}
	
	public void setImagem_livro(String imagem_livro) {
		this.imagem_livro = imagem_livro;
	}
	
	public double getPreco_livro() {
		return preco_livro;
	}
	
	public void setPreco_livro(double preco_livro) {
		this.preco_livro = preco_livro;
	}
	
	public double getDesconto_livro() {
		return desconto_livro;
	}
	
	public void setDesconto_livro(double desconto_livro) {
		this.desconto_livro = desconto_livro;
	}
	
	public HashMap<Integer, Autor> getAutores_livro() {
		return autores_livro;
	}
	
	public void setAutores_livro(HashMap<Integer, Autor> autores_livro) {
		this.autores_livro = autores_livro;
	}

	public Livro() {
		super();
	}

	public Livro(int id_livro, String titulo_livro, String resumo_livro, int ano_livro, int paginas_livro,
			String isbn_livro, int id_categoria_livro, int id_editora_livro, String imagem_livro, double preco_livro,
			double desconto_livro, HashMap<Integer, Autor> autores_livro) {
		super();
		this.id_livro = id_livro;
		this.titulo_livro = titulo_livro;
		this.resumo_livro = resumo_livro;
		this.ano_livro = ano_livro;
		this.paginas_livro = paginas_livro;
		this.isbn_livro = isbn_livro;
		this.id_categoria_livro = id_categoria_livro;
		this.id_editora_livro = id_editora_livro;
		this.imagem_livro = imagem_livro;
		this.preco_livro = preco_livro;
		this.desconto_livro = desconto_livro;
		this.autores_livro = autores_livro;
	}

	@Override
	public int hashCode() {
		return Objects.hash(ano_livro, autores_livro, desconto_livro, id_categoria_livro, id_editora_livro, id_livro,
				imagem_livro, isbn_livro, paginas_livro, preco_livro, resumo_livro, titulo_livro);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Livro other = (Livro) obj;
		return ano_livro == other.ano_livro && Objects.equals(autores_livro, other.autores_livro)
				&& Double.doubleToLongBits(desconto_livro) == Double.doubleToLongBits(other.desconto_livro)
				&& id_categoria_livro == other.id_categoria_livro && id_editora_livro == other.id_editora_livro
				&& id_livro == other.id_livro && imagem_livro == other.imagem_livro
				&& Objects.equals(isbn_livro, other.isbn_livro) && paginas_livro == other.paginas_livro
				&& Double.doubleToLongBits(preco_livro) == Double.doubleToLongBits(other.preco_livro)
				&& Objects.equals(resumo_livro, other.resumo_livro) && Objects.equals(titulo_livro, other.titulo_livro);
	}

	@Override
	public String toString() {
		return "Livro [id_livro=" + id_livro + ", titulo_livro=" + titulo_livro + ", resumo_livro=" + resumo_livro
				+ ", ano_livro=" + ano_livro + ", paginas_livro=" + paginas_livro + ", isbn_livro=" + isbn_livro
				+ ", id_categoria_livro=" + id_categoria_livro + ", id_editora_livro=" + id_editora_livro
				+ ", imagem_livro=" + imagem_livro + ", preco_livro=" + preco_livro + ", desconto_livro="
				+ desconto_livro + ", autores_livro=" + autores_livro + "]";
	}
	
	public String exibirLivro() {
		return ("ID: " + this.id_livro + " | TÍTULO: " + this.titulo_livro + 
				"\n------------------------------------------");
	}
	
	public int indexLivro(int id_buscado, HashMap<Integer, Livro> listaLivros) {
	    int indexLivro = -1;

	    for (Map.Entry<Integer, Livro> livro : listaLivros.entrySet()) {
	        if (id_buscado == livro.getValue().getId_livro()) {
	            indexLivro = livro.getKey();
	            break;
	        }
	    }

	    return indexLivro;
	}
	
	public void perfilLivro(Livro livro) {
		System.out.println("------------------------------------------");
		System.out.println("01. ID: " + this.id_livro);
		System.out.println("02. TÍTULO: " + this.titulo_livro);
		System.out.println("03. RESUMO: " + this.resumo_livro);
		System.out.println("04. ANO: " + this.ano_livro);
		System.out.println("05. PÁGINAS: " + this.paginas_livro);
		System.out.println("06. ISBN: " + this.isbn_livro);
		System.out.println("07. ID CATEGORIA: " + this.id_categoria_livro);
		System.out.println("08. ID EDITORA: " + this.id_editora_livro);
		System.out.println("09. IMAGEM: " + this.imagem_livro);
		System.out.println("10. PREÇO: " + this.preco_livro);
		System.out.println("11. DESCONTO: " + this.desconto_livro);
		
		System.out.println("12. AUTORES:");
		
		if (this.autores_livro.size() == 0) {
			System.out.println("   NENHUM AUTOR CADASTRADO");
		}
		
		else {
			int contador = 1;
			for (Autor autor : this.autores_livro.values()) {
				String numeroAutor = String.format("%02d", contador);
			    System.out.println("   12." + numeroAutor + ". ID: " + autor.getId_autor() + " | NOME: " + autor.getNome_autor());
			    contador++;
			}
		}

		System.out.println("13. SAIR");
		System.out.println("------------------------------------------");				
	}
}