package model;

import java.io.IOException;
import java.util.HashMap;
import java.util.InputMismatchException;
import java.util.Scanner;

public class Utils {
	
	Scanner ler = new Scanner(System.in);
	Scanner lerNome = new Scanner(System.in).useDelimiter("\n");
	
	// MENUS (OK)
	
	public void menuInicial() {
		System.out.println("==> CHAPTER ONE <==");
		System.out.println("------------------------------------------");
		System.out.println("01. CATEGORIAS");
		System.out.println("02. EDITORAS");
		System.out.println("03. AUTORES");
		System.out.println("04. LIVROS");
		System.out.println("05. SAIR");
		System.out.println("------------------------------------------");
		System.out.println("DIGITE A OPÇÃO DESEJADA: ");
	}
	
	public void menuCategorias() {
		System.out.println("==> CHAPTER ONE <==");
		System.out.println("------------------------------------------");
		System.out.println("01. CADASTRAR CATEGORIA");
		System.out.println("02. EXIBIR CATEGORIAS");
		System.out.println("03. EDITAR CATEGORIA");
		System.out.println("04. EXCLUIR CATEGORIA");
		System.out.println("05. SAIR");
		System.out.println("------------------------------------------");
		System.out.println("DIGITE A OPÇÃO DESEJADA: ");
	}
	
	public void menuEditoras() {
		System.out.println("==> CHAPTER ONE <==");
		System.out.println("------------------------------------------");
		System.out.println("01. CADASTRAR EDITORA");
		System.out.println("02. EXIBIR EDITORAS");
		System.out.println("03. EDITAR EDITORA");
		System.out.println("04. EXCLUIR EDITORA");
		System.out.println("05. SAIR");
		System.out.println("------------------------------------------");
		System.out.println("DIGITE A OPÇÃO DESEJADA: ");
	}
	
	public void menuAutores() {
		System.out.println("==> CHAPTER ONE <==");
		System.out.println("------------------------------------------");
		System.out.println("01. CADASTRAR AUTOR");
		System.out.println("02. EXIBIR AUTORES");
		System.out.println("03. EDITAR AUTOR");
		System.out.println("04. EXCLUIR AUTOR");
		System.out.println("05. SAIR");
		System.out.println("------------------------------------------");
		System.out.println("DIGITE A OPÇÃO DESEJADA: ");
	}
	
	public void menuLivros() {
		System.out.println("==> CHAPTER ONE <==");
		System.out.println("------------------------------------------");
		System.out.println("01. CADASTRAR LIVRO");
		System.out.println("02. EXIBIR LIVROS");
		System.out.println("03. EDITAR LIVRO");
		System.out.println("04. EXCLUIR LIVRO");
		System.out.println("05. SAIR");
		System.out.println("------------------------------------------");
		System.out.println("DIGITE A OPÇÃO DESEJADA: ");
	}
	
	public void voltarMenu() throws IOException {
		System.out.println("TECLE ENTER PARA VOLTAR AO MENU.");
		System.in.read();
	}
	
	// VALIDAÇÕES (OK)
	
	public int validarOpcao(int opcao, int opcao_min, int opcao_max, String menu, Autor autor_editar, Categoria categoria_editar, Editora editora_editar, Livro livro_editar) throws IOException {

	    while ((opcao < opcao_min) || (opcao > opcao_max)) {
	        System.out.println("OPÇÃO INVÁLIDA!");
	        voltarMenu();

	        if (menu.equals("menuInicial"))
	            menuInicial();

	        else if (menu.equals("menuCategorias"))
	            menuCategorias();

	        else if (menu.equals("menuEditoras"))
	            menuEditoras();

	        else if (menu.equals("menuAutores"))
	            menuAutores();

	        else if (menu.equals("menuLivros"))
	            menuLivros();
	        
	        else if (menu.equals("perfilAutor")) {
	        	autor_editar.perfilAutor(autor_editar);
		        System.out.println("DIGITE A OPÇÃO QUE DESEJA EDITAR: ");
	        }

	        else if (menu.equals("perfilCategoria")) {
	        	categoria_editar.perfilCategoria(categoria_editar);
	        	System.out.println("DIGITE A OPÇÃO QUE DESEJA EDITAR: ");
	        }
	        	
	        
	        else if (menu.equals("perfilEditora")) {
	        	editora_editar.perfilEditora(editora_editar);
	        	System.out.println("DIGITE A OPÇÃO QUE DESEJA EDITAR: ");
	        }
	        	
	        
	        else if (menu.equals("perfilLivro")) {
	        	livro_editar.perfilLivro(livro_editar);
	        	System.out.println("DIGITE A OPÇÃO QUE DESEJA EDITAR: ");
	        }
	        	
	        
	        try {
	            opcao = ler.nextInt();
	        } catch (InputMismatchException e) {
	            System.out.println("OPÇÃO INVÁLIDA! POR FAVOR DIGITE UM NÚMERO INTEIRO VÁLIDO.");
	            ler.nextLine();
	        }
	    }

	    return opcao;
	}

	public int validarId_Buscado(int id_buscado, int id_min, int id_max, String menu, HashMap<Integer, Autor> listaAutores, HashMap<Integer, Categoria> listaCategorias, HashMap<Integer, Editora> listaEditoras, HashMap<Integer, Livro> listaLivros) throws IOException {
		
		while ((id_buscado < id_min) || (id_buscado > id_max)) {
			System.out.println("ID INVÁLIDO!");
			voltarMenu();

			if (menu.equals("listarCategorias")) {
				listarCategorias(listaCategorias);
				System.out.println("DIGITE O ID DA CATEGORIA QUE DESEJA EDITAR: ");
			}
			
			else if (menu.equals("listarEditoras")) {
				listarEditoras(listaEditoras);
				System.out.println("DIGITE O ID DA EDITORA QUE DESEJA EDITAR: ");
			}
			
			else if (menu.equals("listarAutores")) {
				listarAutores(listaAutores);
				System.out.println("DIGITE O ID DO AUTOR QUE DESEJA EDITAR: ");
			}
			
			else if (menu.equals("listarLivros")) {
				listarLivros(listaLivros);
				System.out.println("DIGITE O ID DO LIVRO QUE DESEJA EDITAR: ");
			}

			id_buscado = ler.nextInt();
		}
		
		return id_buscado;
	}
	
	public String validarPreenchimento(String stringRepeticao, String campopreenchido) throws IOException {
		while ((campopreenchido.length() == 0) || (campopreenchido.equals("")) || (campopreenchido.equals(null) || (campopreenchido.equals("\r")))) {
			System.out.println("O PREENCHIMENTO DO CAMPO É OBRIGATÓRIO.");
			System.out.println(stringRepeticao);
			campopreenchido = lerNome.next();
		}
		
		return campopreenchido;
	}
	
	// CONFIRMAÇÕES (OK)
	
	public boolean confirmarAcao(String acao) throws IOException {
		System.out.println("TEM CERTEZA QUE DESEJA " + acao + "?");
		System.out.println("01. SIM");
		System.out.println("02. NÃO");
		System.out.println("DIGITE A OPÇÃO DESEJADA: ");
		int opcao = ler.nextInt();
		
		while (opcao != 1 && opcao != 2) {
			System.out.println("OPÇÃO INVÁLIDA.");
			voltarMenu();
			System.out.println("TEM CERTEZA QUE DESEJA " + acao + "?");
			System.out.println("01. SIM");
			System.out.println("02. NÃO");
			System.out.println("DIGITE A OPÇÃO DESEJADA: ");
			opcao = ler.nextInt();
		}
		
		if (opcao == 1)
			return true;
		else
			return false;
	}
	
	// LISTAR (OK)
	
	public void listarCategorias(HashMap<Integer, Categoria> listaCategorias) throws IOException {
	    System.out.println("LISTA DE CATEGORIAS CADASTRADAS:\n");

	    if (listaCategorias.size() == 0) {
	        System.out.println("NENHUMA CATEGORIA CADASTRADA!");
	    } else {
	        for (Categoria categoria : listaCategorias.values()) {
	            System.out.println(categoria.exibirCategoria());
	        }
	    }
	}
	
	public void listarEditoras(HashMap<Integer, Editora> listaEditoras) throws IOException {
	    System.out.println("LISTA DE EDITORAS CADASTRADAS:\n");

	    if (listaEditoras.size() == 0) {
	        System.out.println("NENHUMA EDITORA CADASTRADA!");
	    } else {
	        for (Editora editora : listaEditoras.values()) {
	        	System.out.println(editora.exibirEditora());
	        }
	    }
	}
	
	public void listarAutores(HashMap<Integer, Autor> listaAutores) throws IOException {
	    System.out.println("LISTA DE AUTORES CADASTRADOS:\n");

	    if (listaAutores.size() == 0) {
	        System.out.println("NENHUM AUTOR CADASTRADO!");
	    } else {
	        for (Autor autor : listaAutores.values()) {
	        	System.out.println(autor.exibirAutor());
	        }
	    }
	}
	
	public void listarLivros(HashMap<Integer, Livro> listaLivros) throws IOException {
	    System.out.println("LISTA DE LIVROS CADASTRADOS:\n");

	    if (listaLivros.size() == 0) {
	        System.out.println("NENHUM LIVRO CADASTRADO!");
	    } else {
	        for (Livro livro : listaLivros.values()) {
	        	System.out.println(livro.exibirLivro());
	        }
	    }
	}
	
	// EDITAR (OK)
	
	public void editarNegativo(String atributo) throws IOException {
		System.out.println("NÃO É POSSIVEL EDITAR A OPÇÃO " + atributo + ".");
		voltarMenu();
	}
}
