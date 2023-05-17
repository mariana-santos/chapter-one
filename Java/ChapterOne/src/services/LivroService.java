package services;

import java.io.IOException;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.InputMismatchException;
import java.util.Map;
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
		
		// FAZENDO INSERT DO LIVRO NO BANCO DE DADOS (OK)
		if (livroDAO.insert(novo_livro) && livroDAO.insertAutoresLivro(novo_livro)) {
			// ADICIONANDO LIVRO NA LISTA DE LIVROS (OK)
			listaLivros.put(novo_livro.getId_livro(), novo_livro);
			System.out.println("LIVRO CADASTRADO COM SUCESSO!");
		}
		
		else {
			System.out.println("FALHA AO CADASTRAR O LIVRO!");
			utils.voltarMenu();
		}
	}
	
	public void editarTitulo(Livro livro_editar) throws IOException, SQLException {
		if (utils.confirmarAcao("EDITAR TÍTULO DO LIVRO " + livro_editar.getId_livro() + ". " + livro_editar.getTitulo_livro())) {
			System.out.println(livro_editar.getId_livro() + ". DIGITE O NOVO TÍTULO DO LIVRO " + livro_editar.getTitulo_livro() + ": ");
			String titulo = lerNome.nextLine();
			titulo = utils.validarPreenchimentoString(livro_editar.getId_livro() + ". DIGITE O NOVO TÍTULO DO LIVRO " + livro_editar.getTitulo_livro() + ": ", titulo);
			
			Livro livro_antigo = new Livro(livro_editar.getId_livro(), livro_editar.getTitulo_livro(), livro_editar.getResumo_livro(), livro_editar.getAno_livro(), livro_editar.getPaginas_livro(), livro_editar.getIsbn_livro(), livro_editar.getId_categoria_livro(), livro_editar.getId_editora_livro(), livro_editar.getImagem_livro(), livro_editar.getPreco_livro(), livro_editar.getDesconto_livro(), livro_editar.getAutores_livro());
			
			// EDITANDO LIVRO NA LISTA DE LIVROS (OK)
			livro_editar.setTitulo_livro(titulo);
			
			// FAZENDO UPDATE NO BANCO DE DADOS (OK)
			if (livroDAO.update(livro_editar)) {
				System.out.println("TÍTULO ATUALIZADO COM SUCESSO!");
			}
			
			else {
				livro_editar.setTitulo_livro(livro_antigo.getTitulo_livro());
				System.out.println("FALHA AO ATUALIZAR O TÍTULO!");
			}
		}
	}
	
	public void editarResumo(Livro livro_editar) throws IOException, SQLException {
		if (utils.confirmarAcao("EDITAR RESUMO DO LIVRO " + livro_editar.getId_livro() + ". " + livro_editar.getTitulo_livro())) {
			System.out.println(livro_editar.getId_livro() + ". DIGITE O NOVO RESUMO DO LIVRO " + livro_editar.getTitulo_livro() + ": ");
			String resumo = lerNome.nextLine();
			resumo = utils.validarPreenchimentoString(livro_editar.getId_livro() + ". DIGITE O NOVO RESUMO DO LIVRO " + livro_editar.getTitulo_livro() + ": ", resumo);
			
			Livro livro_antigo = new Livro(livro_editar.getId_livro(), livro_editar.getTitulo_livro(), livro_editar.getResumo_livro(), livro_editar.getAno_livro(), livro_editar.getPaginas_livro(), livro_editar.getIsbn_livro(), livro_editar.getId_categoria_livro(), livro_editar.getId_editora_livro(), livro_editar.getImagem_livro(), livro_editar.getPreco_livro(), livro_editar.getDesconto_livro(), livro_editar.getAutores_livro());
			
			// EDITANDO LIVRO NA LISTA DE LIVROS (OK)
			livro_editar.setResumo_livro(resumo);
			
			// FAZENDO UPDATE NO BANCO DE DADOS (OK)
			if (livroDAO.update(livro_editar)) {
				System.out.println("RESUMO ATUALIZADO COM SUCESSO!");
			}
			
			else {
				livro_editar.setResumo_livro(livro_antigo.getResumo_livro());
				System.out.println("FALHA AO ATUALIZAR O RESUMO!");
			}
		}
	}
	
	public void editarAno(Livro livro_editar) throws IOException, SQLException {
		if (utils.confirmarAcao("EDITAR ANO DO LIVRO " + livro_editar.getId_livro() + ". " + livro_editar.getTitulo_livro())) {
			System.out.println(livro_editar.getId_livro() + ". DIGITE O NOVO ANO DO LIVRO " + livro_editar.getTitulo_livro() + ": ");
			int ano = ler.nextInt();
			ano = utils.validarPreenchimentoInt(livro_editar.getId_livro() + ". DIGITE O NOVO ANO DO LIVRO " + livro_editar.getTitulo_livro() + ": ", ano);
			
			Livro livro_antigo = new Livro(livro_editar.getId_livro(), livro_editar.getTitulo_livro(), livro_editar.getResumo_livro(), livro_editar.getAno_livro(), livro_editar.getPaginas_livro(), livro_editar.getIsbn_livro(), livro_editar.getId_categoria_livro(), livro_editar.getId_editora_livro(), livro_editar.getImagem_livro(), livro_editar.getPreco_livro(), livro_editar.getDesconto_livro(), livro_editar.getAutores_livro());
			
			// EDITANDO LIVRO NA LISTA DE LIVROS (OK)
			livro_editar.setAno_livro(ano);
			
			// FAZENDO UPDATE NO BANCO DE DADOS (OK)
			if (livroDAO.update(livro_editar)) {
				System.out.println("ANO ATUALIZADO COM SUCESSO!");
			}
			
			else {
				livro_editar.setAno_livro(livro_antigo.getAno_livro());
				System.out.println("FALHA AO ATUALIZAR O ANO!");
			}
		}
	}
	
	public void editarPaginas(Livro livro_editar) throws IOException, SQLException {
		if (utils.confirmarAcao("EDITAR QUANTIDADE DE PÁGINAS DO LIVRO " + livro_editar.getId_livro() + ". " + livro_editar.getTitulo_livro())) {
			System.out.println(livro_editar.getId_livro() + ". DIGITE A NOVA QUANTIDADE DE PÁGINAS DO LIVRO " + livro_editar.getTitulo_livro() + ": ");
			int paginas = ler.nextInt();
			paginas = utils.validarPreenchimentoInt(livro_editar.getId_livro() + ". DIGITE A NOVA QUANTIDADE DE PÁGINAS DO LIVRO " + livro_editar.getTitulo_livro() + ": ", paginas);
			
			Livro livro_antigo = new Livro(livro_editar.getId_livro(), livro_editar.getTitulo_livro(), livro_editar.getResumo_livro(), livro_editar.getAno_livro(), livro_editar.getPaginas_livro(), livro_editar.getIsbn_livro(), livro_editar.getId_categoria_livro(), livro_editar.getId_editora_livro(), livro_editar.getImagem_livro(), livro_editar.getPreco_livro(), livro_editar.getDesconto_livro(), livro_editar.getAutores_livro());
			
			// EDITANDO LIVRO NA LISTA DE LIVROS (OK)
			livro_editar.setPaginas_livro(paginas);
			
			// FAZENDO UPDATE NO BANCO DE DADOS (OK)
			if (livroDAO.update(livro_editar)) {
				System.out.println("QUANTIDADE DE PÁGINAS ATUALIZADA COM SUCESSO!");
			}
			
			else {
				livro_editar.setPaginas_livro(livro_antigo.getPaginas_livro());
				System.out.println("FALHA AO ATUALIZAR A QUANTIDADE DE PÁGINAS!");
			}
		}
	}
	
	public void editarIsbn(Livro livro_editar) throws IOException, SQLException {
		if (utils.confirmarAcao("EDITAR ISBN DO LIVRO " + livro_editar.getId_livro() + ". " + livro_editar.getTitulo_livro())) {
			System.out.println(livro_editar.getId_livro() + ". DIGITE O NOVO ISBN DO LIVRO " + livro_editar.getTitulo_livro() + ": ");
			String isbn = ler.next();
			isbn = utils.validarPreenchimentoString(livro_editar.getId_livro() + ". DIGITE O NOVO ISBN DO LIVRO " + livro_editar.getTitulo_livro() + ": ", isbn);
			
			Livro livro_antigo = new Livro(livro_editar.getId_livro(), livro_editar.getTitulo_livro(), livro_editar.getResumo_livro(), livro_editar.getAno_livro(), livro_editar.getPaginas_livro(), livro_editar.getIsbn_livro(), livro_editar.getId_categoria_livro(), livro_editar.getId_editora_livro(), livro_editar.getImagem_livro(), livro_editar.getPreco_livro(), livro_editar.getDesconto_livro(), livro_editar.getAutores_livro());
			
			// EDITANDO LIVRO NA LISTA DE LIVROS (OK)
			livro_editar.setIsbn_livro(isbn);
			
			// FAZENDO UPDATE NO BANCO DE DADOS (OK)
			if (livroDAO.update(livro_editar)) {
				System.out.println("ISBN ATUALIZADO COM SUCESSO!");
			}
			
			else {
				livro_editar.setIsbn_livro(livro_antigo.getIsbn_livro());
				System.out.println("FALHA AO ATUALIZAR O ISBN!");
			}
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
			
			Livro livro_antigo = new Livro(livro_editar.getId_livro(), livro_editar.getTitulo_livro(), livro_editar.getResumo_livro(), livro_editar.getAno_livro(), livro_editar.getPaginas_livro(), livro_editar.getIsbn_livro(), livro_editar.getId_categoria_livro(), livro_editar.getId_editora_livro(), livro_editar.getImagem_livro(), livro_editar.getPreco_livro(), livro_editar.getDesconto_livro(), livro_editar.getAutores_livro());
			
			// EDITANDO LIVRO NA LISTA DE LIVROS (OK)
			livro_editar.setId_categoria_livro(categoria_livro.getId_categoria());
			
			// FAZENDO UPDATE NO BANCO DE DADOS (OK)
			if (livroDAO.update(livro_editar)) {
				System.out.println("ID DA CATEGORIA ATUALIZADO COM SUCESSO!");
			}
			
			else {
				livro_editar.setId_categoria_livro(livro_antigo.getId_categoria_livro());
				System.out.println("FALHA AO ATUALIZAR O ID DA CATEGORIA!");
			}
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
			
			Livro livro_antigo = new Livro(livro_editar.getId_livro(), livro_editar.getTitulo_livro(), livro_editar.getResumo_livro(), livro_editar.getAno_livro(), livro_editar.getPaginas_livro(), livro_editar.getIsbn_livro(), livro_editar.getId_categoria_livro(), livro_editar.getId_editora_livro(), livro_editar.getImagem_livro(), livro_editar.getPreco_livro(), livro_editar.getDesconto_livro(), livro_editar.getAutores_livro());
			
			// EDITANDO LIVRO NA LISTA DE LIVROS (OK)
			livro_editar.setId_editora_livro(editora_livro.getId_editora());
			
			// FAZENDO UPDATE NO BANCO DE DADOS (OK)
			if (livroDAO.update(livro_editar)) {
				System.out.println("ID DA EDITORA ATUALIZADO COM SUCESSO!");
			}
			
			else {
				livro_editar.setId_editora_livro(livro_antigo.getId_editora_livro());
				System.out.println("FALHA AO ATUALIZAR O ID DA EDITORA!");
			}
		}
	}
	
	public void editarImagem(Livro livro_editar) throws IOException, SQLException {
		if (utils.confirmarAcao("EDITAR IMAGEM DO LIVRO " + livro_editar.getId_livro() + ". " + livro_editar.getTitulo_livro())) {
			System.out.println(livro_editar.getId_livro() + ". DIGITE A NOVA URL DA IMAGEM DO LIVRO " + livro_editar.getTitulo_livro() + ": ");
			String imagem = ler.next();
			imagem = utils.validarPreenchimentoString(livro_editar.getId_livro() + ". DIGITE A NOVA URL DA IMAGEM DO LIVRO " + livro_editar.getTitulo_livro() + ": ", imagem);
			
			Livro livro_antigo = new Livro(livro_editar.getId_livro(), livro_editar.getTitulo_livro(), livro_editar.getResumo_livro(), livro_editar.getAno_livro(), livro_editar.getPaginas_livro(), livro_editar.getIsbn_livro(), livro_editar.getId_categoria_livro(), livro_editar.getId_editora_livro(), livro_editar.getImagem_livro(), livro_editar.getPreco_livro(), livro_editar.getDesconto_livro(), livro_editar.getAutores_livro());
			
			// EDITANDO LIVRO NA LISTA DE LIVROS (OK)
			livro_editar.setImagem_livro(imagem);
			
			// FAZENDO UPDATE NO BANCO DE DADOS (OK)
			if (livroDAO.update(livro_editar)) {
				System.out.println("IMAGEM ATUALIZADA COM SUCESSO!");
			}
			
			else {
				livro_editar.setImagem_livro(livro_antigo.getImagem_livro());
				System.out.println("FALHA AO ATUALIZAR A IMAGEM!");
			}
		}
	}
	
	public void editarPreco(Livro livro_editar) throws IOException, SQLException {
		if (utils.confirmarAcao("EDITAR PREÇO DO LIVRO " + livro_editar.getId_livro() + ". " + livro_editar.getTitulo_livro())) {
			System.out.println(livro_editar.getId_livro() + ". DIGITE O NOVO PREÇO DO LIVRO " + livro_editar.getTitulo_livro() + ": ");
			double preco = ler.nextDouble();
			preco = utils.validarPreenchimentoDouble(livro_editar.getId_livro() + ". DIGITE O NOVO PREÇO DO LIVRO " + livro_editar.getTitulo_livro() + ": ", preco);
			
			Livro livro_antigo = new Livro(livro_editar.getId_livro(), livro_editar.getTitulo_livro(), livro_editar.getResumo_livro(), livro_editar.getAno_livro(), livro_editar.getPaginas_livro(), livro_editar.getIsbn_livro(), livro_editar.getId_categoria_livro(), livro_editar.getId_editora_livro(), livro_editar.getImagem_livro(), livro_editar.getPreco_livro(), livro_editar.getDesconto_livro(), livro_editar.getAutores_livro());
			
			// EDITANDO LIVRO NA LISTA DE LIVROS (OK)
			livro_editar.setPreco_livro(preco);
			
			// FAZENDO UPDATE NO BANCO DE DADOS (OK)
			if (livroDAO.update(livro_editar)) {
				System.out.println("PREÇO ATUALIZADO COM SUCESSO!");
			}
			
			else {
				livro_editar.setPreco_livro(livro_antigo.getPreco_livro());
				System.out.println("FALHA AO ATUALIZAR O PREÇO!");
			}
		}
	}
	
	public void editarDesconto(Livro livro_editar) throws IOException, SQLException {
		if (utils.confirmarAcao("EDITAR DESCONTO DO LIVRO " + livro_editar.getId_livro() + ". " + livro_editar.getTitulo_livro())) {
			System.out.println(livro_editar.getId_livro() + ". DIGITE O NOVO DESCONTO DO LIVRO " + livro_editar.getTitulo_livro() + ": ");
			double desconto = ler.nextDouble();
			desconto = utils.validarPreenchimentoDouble(livro_editar.getId_livro() + ". DIGITE O NOVO DESCONTO DO LIVRO " + livro_editar.getTitulo_livro() + ": ", desconto);
			
			Livro livro_antigo = new Livro(livro_editar.getId_livro(), livro_editar.getTitulo_livro(), livro_editar.getResumo_livro(), livro_editar.getAno_livro(), livro_editar.getPaginas_livro(), livro_editar.getIsbn_livro(), livro_editar.getId_categoria_livro(), livro_editar.getId_editora_livro(), livro_editar.getImagem_livro(), livro_editar.getPreco_livro(), livro_editar.getDesconto_livro(), livro_editar.getAutores_livro());
			
			// EDITANDO LIVRO NA LISTA DE LIVROS (OK)
			livro_editar.setDesconto_livro(desconto);
			
			// FAZENDO UPDATE NO BANCO DE DADOS (OK)
			if (livroDAO.update(livro_editar)) {
				System.out.println("DESCONTO ATUALIZADO COM SUCESSO!");
			}
			
			else {
				livro_editar.setDesconto_livro(livro_antigo.getDesconto_livro());
				System.out.println("FALHA AO ATUALIZAR O DESCONTO!");
			}
		}
	}
	
	public void editarAutoresLivro(int id_autor, HashMap<Integer, Autor> listaAutores, Livro livro_editar, HashMap<Integer, Livro> listaLivros) throws IOException {
		if (utils.confirmarAcao("EDITAR AUTORES DO LIVRO " + livro_editar.getId_livro() + ". " + livro_editar.getTitulo_livro())) {
			HashMap<Integer, Autor> listaAutoresLivro = new HashMap<Integer, Autor>();
			HashMap<Integer, Autor> listaAutoresAdicionar = new HashMap<Integer, Autor>(listaAutores);
			Livro livro_antigo = new Livro(livro_editar.getId_livro(), livro_editar.getTitulo_livro(), livro_editar.getResumo_livro(), livro_editar.getAno_livro(), livro_editar.getPaginas_livro(), livro_editar.getIsbn_livro(), livro_editar.getId_categoria_livro(), livro_editar.getId_editora_livro(), livro_editar.getImagem_livro(), livro_editar.getPreco_livro(), livro_editar.getDesconto_livro(), livro_editar.getAutores_livro());
		
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
				livro_editar.setAutores_livro(listaAutoresLivro);
			}
			
			while (adicionar) {
				try {
					for (Autor autor : listaAutoresAdicionar.values()) {
				        System.out.println(autor.exibirAutor());
				    }
					System.out.println("DIGITE O ID DO AUTOR QUE DESEJA ADICIONAR AO LIVRO: ");
					int id_buscado = ler.nextInt();
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
					
					// EDITANDO LIVRO NA LISTA DE LIVROS
					livro_editar.setAutores_livro(listaAutoresLivro);
					
					// EDITANDO TODOS OS AUTORES NA LISTA DE AUTORES PARA ADICIONAR O LIVRO DE ACORDO COM A NOVA LISTA DO LIVROS (OK)
					for (Map.Entry<Integer, Autor> autor_do_livro : livro_editar.getAutores_livro().entrySet()) {
					    Autor autor_livro = autor_do_livro.getValue();
					    for (Map.Entry<Integer, Autor> autor_na_lista : listaAutores.entrySet()) {
					        Autor autor_lista = autor_na_lista.getValue();
					        if (autor_livro.equals(autor_lista)) {
					            boolean livroAtualizado = false;
					            for (Map.Entry<Integer, Livro> entry : autor_lista.getLivros_autor().entrySet()) {
					                Livro livro = entry.getValue();
					                if (livro.getId_livro() == livro_editar.getId_livro()) {
					                	livroAtualizado = true;
					                    break;
					                }
					            }
					            
					            if (!livroAtualizado) {
					                autor_lista.getLivros_autor().put(livro_editar.getId_livro(), livro_editar);
					            }
					        } else {
					        	autor_lista.getLivros_autor().remove(livro_editar.getId_livro());
					        }
					    }
					}

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
			
			// FAZENDO UPDATE DO LIVRO NO BANCO DE DADOS (OK)
			if (livroDAO.updateAutoresLivro(livro_editar)) {
				System.out.println("AUTORES DO LIVRO EDITADOS COM SUCESSO!");				
			}
			
			else {
				livro_editar.setAutores_livro(livro_antigo.getAutores_livro());
				System.out.println("FALHA AO EDITAR OS AUTORES DO LIVRO!");
			}	
		}
	}
	
	public void deletarLivro(int id_livro, HashMap<Integer, Livro> listaLivros) throws SQLException {
		try {
			if (utils.confirmarAcao("EXCLUIR LIVRO")) {
				// FAZENDO DELETE DO LIVRO NO BANCO DE DADOS (OK)
				if (livroDAO.delete(id_livro)) {
					// REMOVENDO LIVRO DA LISTA DE LIVROS (OK)
					listaLivros.remove(id_livro);
					System.out.println("LIVRO EXCLUÍDO COM SUCESSO!");
				}
				
				else {
					System.out.println("FALHA AO EXCLUIR O LIVRO!");
					utils.voltarMenu();
				}
			}
			
		} catch (IOException e) {
			e.printStackTrace();
			
		}
	}

}
