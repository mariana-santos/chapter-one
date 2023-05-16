package services;

import java.io.IOException;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.InputMismatchException;
import java.util.Scanner;

import dao.LivroDAO;
import model.Autor;
import model.Categoria;
import model.Editora;
import model.Livro;
import model.Utils;

public class LivroService {
	
	Scanner ler = new Scanner(System.in);
	Scanner lerNome = new Scanner(System.in).useDelimiter("\n");
	Utils utils = new Utils();
	LivroDAO livroDAO = new LivroDAO();
	CategoriaService categoriaService = new CategoriaService();
	EditoraService editoraService = new EditoraService();
	
	public void listarLivros(HashMap<Integer, Livro> listaLivros) {
	    for (Livro livro : listaLivros.values()) {
	        System.out.println(livro.exibirLivro());
	    }
	}
	
	public void cadastrarLivro(int id_autor, HashMap<Integer, Autor> listaAutores, int id_categoria, HashMap<Integer, Categoria> listaCategorias, int id_editora, HashMap<Integer, Editora> listaEditoras, int id_livro, HashMap<Integer, Livro> listaLivros) throws SQLException, IOException {
		Livro novo_livro = new Livro();
		
		// SETANDO O ID DO LIVRO (OK)
		novo_livro.setId_livro(id_livro);
		
		// SETANDO O TÍTULO DO LIVRO (OK)
		System.out.println(id_livro + ". DIGITE O TÍTULO DO LIVRO: ");
		novo_livro.setTitulo_livro(lerNome.nextLine());
		
		// SETANDO O RESUMO DO LIVRO (OK)
		System.out.println(id_livro + ". DIGITE O RESUMO DO LIVRO: ");
		novo_livro.setResumo_livro(lerNome.nextLine());
		
		// SETANDO O ANO DO LIVRO (OK)
		System.out.println(id_livro + ". DIGITE O ANO DO LIVRO: ");
		novo_livro.setAno_livro(ler.nextInt());
		
		// SETANDO AS PÁGINAS DO LIVRO (OK)
		System.out.println(id_livro + ". DIGITE A QUANTIDADE DE PÁGINAS DO LIVRO: ");
		novo_livro.setPaginas_livro(ler.nextInt());
		
		// SETANDO O RESUMO DO LIVRO (OK)
		System.out.println(id_livro + ". DIGITE O ISBN DO LIVRO: ");
		novo_livro.setIsbn_livro(ler.next());
		
		// SETANDO A CATEGORIA DO LIVRO
		categoriaService.listarCategorias(listaCategorias);
		System.out.println("DIGITE O ID DA CATEGORIA DO LIVRO: ");
		int id_buscado = ler.nextInt();
		id_buscado = utils.validarId_Buscado(id_buscado, 1, (id_categoria - 1), "listarCategorias", listaAutores, listaCategorias, listaEditoras, listaLivros);
		
		Categoria categoria_livro = new Categoria();
		
		while (categoria_livro.indexCategoria(id_buscado, listaCategorias) == -1) {
			System.out.println("ID NÃO ENCONTRADO");
			utils.voltarMenu();
		}
			
		categoria_livro = listaCategorias.get(categoria_livro.indexCategoria(id_buscado, listaCategorias));
		novo_livro.setId_categoria_livro(categoria_livro.getId_categoria());
		
		// SETANDO A EDITORA DO LIVRO
		editoraService.listarEditoras(listaEditoras);
		System.out.println("DIGITE O ID DA EDITORA DO LIVRO: ");
		id_buscado = ler.nextInt();
		id_buscado = utils.validarId_Buscado(id_buscado, 1, (id_editora - 1), "listarEditoras", listaAutores, listaCategorias, listaEditoras, listaLivros);
		
		Editora editora_livro = new Editora();
		
		while (editora_livro.indexEditora(id_buscado, listaEditoras) == -1) {
			System.out.println("ID NÃO ENCONTRADO");
			utils.voltarMenu();
		}
			
		editora_livro = listaEditoras.get(editora_livro.indexEditora(id_buscado, listaEditoras));
		novo_livro.setId_editora_livro(editora_livro.getId_editora());
		
		// SETANDO A IMAGEM DO LIVRO (OK)
		System.out.println(id_livro + ". DIGITE A URL DA IMAGEM DO LIVRO: ");
		novo_livro.setImagem_livro(ler.next());
		
		// SETANDO O PREÇO DO LIVRO (OK)
		System.out.println(id_livro + ". DIGITE O PREÇO DO LIVRO: ");
		novo_livro.setPreco_livro(ler.nextDouble());
		
		// SETANDO O DESCONTO DO LIVRO (OK)
		System.out.println(id_livro + ". DIGITE O VALOR DE DESCONTO DO LIVRO: ");
		novo_livro.setDesconto_livro(ler.nextDouble());
		
		// ADICIONANDO AUTORES NA LISTA DE AUTORES DO LIVRO (OK)
		HashMap<Integer, Autor> listaAutoresLivro = new HashMap<Integer, Autor>();
		HashMap<Integer, Autor> listaAutoresAdicionar = new HashMap<Integer, Autor>(listaAutores);
		
		boolean adicionar = false;
		
		System.out.println("------------------------------------------");
		System.out.println("DESEJA ADICIONAR AUTORES AO LIVRO?");
		System.out.println("01. SIM");
        System.out.println("02. NÃO");
        System.out.println("------------------------------------------");
        System.out.println("DIGITE A OPÇÃO DESEJADA: ");
		int opcao = ler.nextInt();
		
		while (opcao != 1 && opcao != 2) {
			System.out.println("OPÇÃO INVÁLIDA.");
			utils.voltarMenu();
			System.out.println("------------------------------------------");
			System.out.println("DESEJA ADICIONAR AUTORES AO LIVRO?");
			System.out.println("01. SIM");
	        System.out.println("02. NÃO");
	        System.out.println("------------------------------------------");
	        System.out.println("DIGITE A OPÇÃO DESEJADA: ");
			opcao = ler.nextInt();
		}
		
		if (opcao == 1) {
			adicionar = true;
		}
		
		else if (opcao == 2) {
			adicionar = false;
			novo_livro.setAutores_livro(listaAutoresLivro);
		}
		
		while (adicionar) {
			try {
				for (Autor autor : listaAutoresAdicionar.values()) {
			        System.out.println(autor.exibirAutor());
			    }
				System.out.println("DIGITE O ID DO AUTOR QUE DESEJA ADICIONAR AO LIVRO: ");
				id_buscado = ler.nextInt();
				id_buscado = utils.validarId_Buscado(id_buscado, 1, (id_autor - 1), "listarAutores", listaAutoresAdicionar, null, null, null);
				
				Autor autor_adicionar = new Autor();
				
				if (autor_adicionar.indexAutor(id_buscado, listaAutoresAdicionar) == -1) {
					System.out.println("ID NÃO ENCONTRADO");
					utils.voltarMenu();
				}
				
				else {
					autor_adicionar = listaAutoresAdicionar.get(autor_adicionar.indexAutor(id_buscado, listaAutoresAdicionar));
					listaAutoresLivro.put(autor_adicionar.getId_autor(), autor_adicionar);
					listaAutoresAdicionar.remove(autor_adicionar.getId_autor());
					System.out.println("ATOR ADICIONADO AO LIVRO COM SUCESSO!");
				}
				
				System.out.println("------------------------------------------");
				System.out.println("DESEJA ADICIONAR NOVO AUTOR AO LIVRO?");
				System.out.println("01. SIM");
		        System.out.println("02. NÃO");
		        System.out.println("------------------------------------------");
		        System.out.println("DIGITE A OPÇÃO DESEJADA: ");
				opcao = ler.nextInt();
				
				while (opcao != 1 && opcao != 2) {
					System.out.println("OPÇÃO INVÁLIDA.");
					utils.voltarMenu();
					System.out.println("------------------------------------------");
					System.out.println("DESEJA ADICIONAR NOVO AUTOR AO LIVRO?");
					System.out.println("01. SIM");
			        System.out.println("02. NÃO");
			        System.out.println("------------------------------------------");
			        System.out.println("DIGITE A OPÇÃO DESEJADA: ");
					opcao = ler.nextInt();
				}
				
				if (opcao == 1) {
					continue;
				}
				
				else if (opcao == 2) {
					adicionar = false;					
				}
				
				novo_livro.setAutores_livro(listaAutoresLivro);
				
			} catch (InputMismatchException e) {
		        System.out.println("OPÇÃO INVÁLIDA. POR FAVOR, DIGITE UM NÚMERO INTEIRO VÁLIDO.");
		        ler.nextLine();
		        utils.voltarMenu();
		    } catch (Exception e) {
		        System.out.println("OCORREU UM ERRO:" + e.getMessage());
		        ler.nextLine();
		        utils.voltarMenu();
		    }
		}
		
		// ADICIONANDO LIVRO NA LISTA DE LIVROS (OK)
		listaLivros.put(novo_livro.getId_livro(), novo_livro);
		
		// FAZENDO INSERT DO LIVRO NO BANCO DE DADOS (OK)
		livroDAO.insert(novo_livro);
		livroDAO.insertAutoresLivro(novo_livro);
		
		System.out.println("LIVRO CADASTRADO COM SUCESSO!");
	}
	
	public void editarTitulo(Livro livro_editar) throws IOException, SQLException {
		if (utils.confirmarAcao("EDITAR TÍTULO DO LIVRO " + livro_editar.getId_livro() + ". " + livro_editar.getTitulo_livro())) {
			System.out.println(livro_editar.getId_livro() + ". DIGITE O NOVO TÍTULO DO LIVRO " + livro_editar.getTitulo_livro() + ": ");
			String titulo = lerNome.nextLine();
			titulo = utils.validarPreenchimentoString(livro_editar.getId_livro() + ". DIGITE O NOVO TÍTULO DO LIVRO " + livro_editar.getTitulo_livro() + ": ", titulo);
			
			// EDITANDO LIVRO NA LISTA DE LIVROS (OK)
			livro_editar.setTitulo_livro(titulo);
			
			// EDITANDO LIVRO NO BANCO DE DADOS (OK)
			livroDAO.update(livro_editar);
			
			System.out.println("TÍTULO ATUALIZADO COM SUCESSO!");
		}
	}
	
	public void editarResumo(Livro livro_editar) throws IOException, SQLException {
		if (utils.confirmarAcao("EDITAR RESUMO DO LIVRO " + livro_editar.getId_livro() + ". " + livro_editar.getTitulo_livro())) {
			System.out.println(livro_editar.getId_livro() + ". DIGITE O NOVO RESUMO DO LIVRO " + livro_editar.getTitulo_livro() + ": ");
			String resumo = lerNome.nextLine();
			resumo = utils.validarPreenchimentoString(livro_editar.getId_livro() + ". DIGITE O NOVO RESUMO DO LIVRO " + livro_editar.getTitulo_livro() + ": ", resumo);
			
			// EDITANDO LIVRO NA LISTA DE LIVROS (OK)
			livro_editar.setResumo_livro(resumo);
			
			// EDITANDO LIVRO NO BANCO DE DADOS (OK)
			livroDAO.update(livro_editar);
			
			System.out.println("RESUMO ATUALIZADO COM SUCESSO!");
		}
	}
	
	public void editarAno(Livro livro_editar) throws IOException, SQLException {
		if (utils.confirmarAcao("EDITAR ANO DO LIVRO " + livro_editar.getId_livro() + ". " + livro_editar.getTitulo_livro())) {
			System.out.println(livro_editar.getId_livro() + ". DIGITE O NOVO ANO DO LIVRO " + livro_editar.getTitulo_livro() + ": ");
			int ano = ler.nextInt();
			ano = utils.validarPreenchimentoInt(livro_editar.getId_livro() + ". DIGITE O NOVO ANO DO LIVRO " + livro_editar.getTitulo_livro() + ": ", ano);
			
			// EDITANDO LIVRO NA LISTA DE LIVROS (OK)
			livro_editar.setAno_livro(ano);
			
			// EDITANDO LIVRO NO BANCO DE DADOS (OK)
			livroDAO.update(livro_editar);
			
			System.out.println("ANO ATUALIZADO COM SUCESSO!");
		}
	}
	
	public void editarPaginas(Livro livro_editar) throws IOException, SQLException {
		if (utils.confirmarAcao("EDITAR QUANTIDADE DE PÁGINAS DO LIVRO " + livro_editar.getId_livro() + ". " + livro_editar.getTitulo_livro())) {
			System.out.println(livro_editar.getId_livro() + ". DIGITE A NOVA QUANTIDADE DE PÁGINAS DO LIVRO " + livro_editar.getTitulo_livro() + ": ");
			int paginas = ler.nextInt();
			paginas = utils.validarPreenchimentoInt(livro_editar.getId_livro() + ". DIGITE A NOVA QUANTIDADE DE PÁGINAS DO LIVRO " + livro_editar.getTitulo_livro() + ": ", paginas);
			
			// EDITANDO LIVRO NA LISTA DE LIVROS (OK)
			livro_editar.setPaginas_livro(paginas);
			
			// EDITANDO LIVRO NO BANCO DE DADOS (OK)
			livroDAO.update(livro_editar);
			
			System.out.println("QUANTIDADE DE PÁGINAS ATUALIZADAS COM SUCESSO!");
		}
	}
	
	public void editarIsbn(Livro livro_editar) throws IOException, SQLException {
		if (utils.confirmarAcao("EDITAR ISBN DO LIVRO " + livro_editar.getId_livro() + ". " + livro_editar.getTitulo_livro())) {
			System.out.println(livro_editar.getId_livro() + ". DIGITE O NOVO ISBN DO LIVRO " + livro_editar.getTitulo_livro() + ": ");
			String isbn = ler.next();
			isbn = utils.validarPreenchimentoString(livro_editar.getId_livro() + ". DIGITE O NOVO ISBN DO LIVRO " + livro_editar.getTitulo_livro() + ": ", isbn);
			
			// EDITANDO LIVRO NA LISTA DE LIVROS (OK)
			livro_editar.setIsbn_livro(isbn);
			
			// EDITANDO LIVRO NO BANCO DE DADOS (OK)
			livroDAO.update(livro_editar);
			
			System.out.println("ISBN ATUALIZADO COM SUCESSO!");
		}
	}
	
	public void editarIdCategoriaLivro(Livro livro_editar, int id_categoria, HashMap<Integer, Categoria> listaCategorias) throws IOException, SQLException {
		if (utils.confirmarAcao("EDITAR ID DA CATEGORIA DO LIVRO " + livro_editar.getId_livro() + ". " + livro_editar.getTitulo_livro())) {
			categoriaService.listarCategorias(listaCategorias);
			System.out.println("DIGITE O ID DA NOVA CATEGORIA DO LIVRO: ");
			int id_buscado = ler.nextInt();
			id_buscado = utils.validarId_Buscado(id_buscado, 1, (id_categoria - 1), "listarCategorias", null, listaCategorias, null, null);
			
			Categoria categoria_livro = new Categoria();
			
			while (categoria_livro.indexCategoria(id_buscado, listaCategorias) == -1) {
				System.out.println("ID NÃO ENCONTRADO");
				utils.voltarMenu();
			}
				
			categoria_livro = listaCategorias.get(categoria_livro.indexCategoria(id_buscado, listaCategorias));
			
			// EDITANDO LIVRO NA LISTA DE LIVROS (OK)
			livro_editar.setId_categoria_livro(categoria_livro.getId_categoria());
			
			// EDITANDO LIVRO NO BANCO DE DADOS (OK)
			livroDAO.update(livro_editar);
			
			System.out.println("ID DA CATEGORIA DO LIVRO ATUALIZADO COM SUCESSO!");
		}
	}
	
	public void editarIdEditoraLivro(Livro livro_editar, int id_editora, HashMap<Integer, Editora> listaEditoras) throws IOException, SQLException {
		if (utils.confirmarAcao("EDITAR ID DA EDITORA DO LIVRO " + livro_editar.getId_livro() + ". " + livro_editar.getTitulo_livro())) {
			editoraService.listarEditoras(listaEditoras);
			System.out.println("DIGITE O ID DA NOVA EDITORA DO LIVRO: ");
			int id_buscado = ler.nextInt();
			id_buscado = utils.validarId_Buscado(id_buscado, 1, (id_editora - 1), "listarEditoras", null, null, listaEditoras, null);
			
			Editora editora_livro = new Editora();
			
			while (editora_livro.indexEditora(id_buscado, listaEditoras) == -1) {
				System.out.println("ID NÃO ENCONTRADO");
				utils.voltarMenu();
			}
				
			editora_livro = listaEditoras.get(editora_livro.indexEditora(id_buscado, listaEditoras));
			
			// EDITANDO LIVRO NA LISTA DE LIVROS (OK)
			livro_editar.setId_editora_livro(editora_livro.getId_editora());
			
			// EDITANDO LIVRO NO BANCO DE DADOS (OK)
			livroDAO.update(livro_editar);
			
			System.out.println("ID DA EDITORA DO LIVRO ATUALIZADO COM SUCESSO!");
		}
	}
	
	public void editarImagem(Livro livro_editar) throws IOException, SQLException {
		if (utils.confirmarAcao("EDITAR IMAGEM DO LIVRO " + livro_editar.getId_livro() + ". " + livro_editar.getTitulo_livro())) {
			System.out.println(livro_editar.getId_livro() + ". DIGITE A NOVA URL DA IMAGEM DO LIVRO " + livro_editar.getTitulo_livro() + ": ");
			String imagem = ler.next();
			imagem = utils.validarPreenchimentoString(livro_editar.getId_livro() + ". DIGITE A NOVA URL DA IMAGEM DO LIVRO " + livro_editar.getTitulo_livro() + ": ", imagem);

			// EDITANDO LIVRO NA LISTA DE LIVROS (OK)
			livro_editar.setImagem_livro(imagem);
			
			// EDITANDO LIVRO NO BANCO DE DADOS (OK)
			livroDAO.update(livro_editar);
			
			System.out.println("IMAGEM ATUALIZADA COM SUCESSO!");
		}
	}
	
	public void editarPreco(Livro livro_editar) throws IOException, SQLException {
		if (utils.confirmarAcao("EDITAR PREÇO DO LIVRO " + livro_editar.getId_livro() + ". " + livro_editar.getTitulo_livro())) {
			System.out.println(livro_editar.getId_livro() + ". DIGITE O NOVO PREÇO DO LIVRO " + livro_editar.getTitulo_livro() + ": ");
			double preco = ler.nextDouble();
			preco = utils.validarPreenchimentoDouble(livro_editar.getId_livro() + ". DIGITE O NOVO PREÇO DO LIVRO " + livro_editar.getTitulo_livro() + ": ", preco);
			
			// EDITANDO LIVRO NA LISTA DE LIVROS (OK)
			livro_editar.setPreco_livro(preco);
			
			// EDITANDO LIVRO NO BANCO DE DADOS (OK)
			livroDAO.update(livro_editar);
			
			System.out.println("PREÇO ATUALIZADO COM SUCESSO!");
		}
	}
	
	public void editarDesconto(Livro livro_editar) throws IOException, SQLException {
		if (utils.confirmarAcao("EDITAR DESCONTO DO LIVRO " + livro_editar.getId_livro() + ". " + livro_editar.getTitulo_livro())) {
			System.out.println(livro_editar.getId_livro() + ". DIGITE O NOVO DESCONTO DO LIVRO " + livro_editar.getTitulo_livro() + ": ");
			double desconto = ler.nextDouble();
			desconto = utils.validarPreenchimentoDouble(livro_editar.getId_livro() + ". DIGITE O NOVO DESCONTO DO LIVRO " + livro_editar.getTitulo_livro() + ": ", desconto);
			
			// EDITANDO LIVRO NA LISTA DE LIVROS (OK)
			livro_editar.setDesconto_livro(desconto);
			
			// EDITANDO LIVRO NO BANCO DE DADOS (OK)
			livroDAO.update(livro_editar);
			
			System.out.println("DESCONTO ATUALIZADO COM SUCESSO!");
		}
	}
	
	public void deletarLivro(int id_livro, HashMap<Integer, Livro> listaLivros) throws SQLException {
		try {
			if (utils.confirmarAcao("EXCLUIR LIVRO")) {
				// REMOVENDO LIVRO DA LISTA DE LIVROS (OK)
				listaLivros.remove(id_livro);
				
				// FAZENDO DELETE DO LIVRO NO BANCO DE DADOS (OK)
				livroDAO.delete(id_livro);
				
				System.out.println("LIVRO EXCLUÍDO COM SUCESSO!");
			}
		} catch (IOException e) {
			e.printStackTrace();
			
		}
	}

}
