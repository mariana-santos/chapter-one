package services;

import java.io.IOException;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.InputMismatchException;
import java.util.Scanner;

import dao.AutorDAO;
import model.Autor;
import model.Livro;
import model.Utils;

public class AutorService {
	
	Scanner ler = new Scanner(System.in);
	Scanner lerNome = new Scanner(System.in).useDelimiter("\n");
	Utils utils = new Utils();
	AutorDAO autorDAO = new AutorDAO();
	LivroService livroService = new LivroService();
	
	public void listarAutores(HashMap<Integer, Autor> listaAutores) {
	    for (Autor autor : listaAutores.values()) {
	        System.out.println(autor.exibirAutor());
	    }
	}
	
	public void cadastrarAutor(int id_autor, HashMap<Integer, Autor> listaAutores, int id_livro, HashMap<Integer, Livro> listaLivros) throws SQLException, IOException {
		Autor novo_autor = new Autor();
		
		// SETANDO O ID DO AUTOR (OK)
		novo_autor.setId_autor(id_autor);
		
		// SETANDO O NOME DO AUTOR (OK)
		System.out.println(id_autor + ". DIGITE O NOME DO AUTOR: ");
		novo_autor.setNome_autor(lerNome.nextLine());
		
		// SETANDO O EMAIL DO AUTOR (OK)
		System.out.println(id_autor + ". DIGITE O EMAIL DO AUTOR: ");
		novo_autor.setEmail_autor(ler.next());
		
		// SETANDO O TELEFONE DO AUTOR (OK)
		System.out.println(id_autor + ". DIGITE O TELEFONE DO AUTOR (EXEMPLO: 11983050165): ");
		String telefone = ler.next();
		String telefone_formatado = utils.formatarTelefone(telefone);
		novo_autor.setTelefone_autor(telefone_formatado);
		
		// SETANDO A BIO DO AUTOR (OK)
		System.out.println(id_autor + ". DIGITE A BIO DO AUTOR: ");
		novo_autor.setBio_autor(lerNome.nextLine());
		
		// SETANDO A IMAGEM DO AUTOR (OK)
		System.out.println(id_autor + ". DIGITE A URL DA IMAGEM DO AUTOR: ");
		novo_autor.setImagem_autor(ler.next());
		
		// ADICIONANDO LIVROS NA LISTA DE LIVROS DO AUTOR (OK)
		HashMap<Integer, Livro> listaLivrosAutor = new HashMap<Integer, Livro>();
		HashMap<Integer, Livro> listaLivrosAdicionar = new HashMap<Integer, Livro>(listaLivros);
		
		boolean adicionar = false;
		
		System.out.println("------------------------------------------");
		System.out.println("DESEJA ADICIONAR LIVROS AO AUTOR?");
		System.out.println("01. SIM");
        System.out.println("02. NÃO");
        System.out.println("------------------------------------------");
        System.out.println("DIGITE A OPÇÃO DESEJADA: ");
		int opcao = ler.nextInt();
		
		while (opcao != 1 && opcao != 2) {
			System.out.println("OPÇÃO INVÁLIDA.");
			utils.voltarMenu();
			System.out.println("------------------------------------------");
			System.out.println("DESEJA ADICIONAR LIVROS AO AUTOR?");
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
			novo_autor.setLivros_autor(listaLivrosAutor);
		}
		
		while (adicionar) {
			try {
				livroService.listarLivros(listaLivrosAdicionar);
				System.out.println("DIGITE O ID DO LIVRO QUE DESEJA ADICIONAR AO AUTOR: ");
				int id_buscado = ler.nextInt();
				id_buscado = utils.validarId_Buscado(id_buscado, 1, (id_livro - 1), "listarLivros", listaAutores, null, null, listaLivrosAdicionar);
				
				Livro livro_adicionar = new Livro();
				
				if (livro_adicionar.indexLivro(id_buscado, listaLivrosAdicionar) == -1) {
					System.out.println("ID NÃO ENCONTRADO");
					utils.voltarMenu();
				}
				
				else {
					livro_adicionar = listaLivrosAdicionar.get(livro_adicionar.indexLivro(id_buscado, listaLivrosAdicionar));
					listaLivrosAutor.put(livro_adicionar.getId_livro(), livro_adicionar);
					listaLivrosAdicionar.remove(livro_adicionar.getId_livro());
					System.out.println("LIVRO ADICIONADO AO AUTOR COM SUCESSO!");
				}
				
				System.out.println("------------------------------------------");
				System.out.println("DESEJA ADICIONAR NOVO LIVRO AO AUTOR?");
				System.out.println("01. SIM");
		        System.out.println("02. NÃO");
		        System.out.println("------------------------------------------");
		        System.out.println("DIGITE A OPÇÃO DESEJADA: ");
				opcao = ler.nextInt();
				
				while (opcao != 1 && opcao != 2) {
					System.out.println("OPÇÃO INVÁLIDA.");
					utils.voltarMenu();
					System.out.println("------------------------------------------");
					System.out.println("DESEJA ADICIONAR NOVO LIVRO AO AUTOR?");
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
				
				novo_autor.setLivros_autor(listaLivrosAutor);
				
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
		
		// ADICIONANDO AUTOR NA LISTA DE AUTORES (OK)
		listaAutores.put(novo_autor.getId_autor(), novo_autor);
		
		// FAZENDO INSERT DO AUTOR NO BANCO DE DADOS (OK)
		autorDAO.insert(novo_autor);
		autorDAO.insertLivrosAutor(novo_autor);
		
		System.out.println("AUTOR CADASTRADO COM SUCESSO!");
	}
	
	public void editarNome(Autor autor_editar) throws IOException, SQLException {
		if (utils.confirmarAcao("EDITAR NOME DO AUTOR " + autor_editar.getId_autor() + ". " + autor_editar.getNome_autor())) {
			System.out.println(autor_editar.getId_autor() + ". DIGITE O NOVO NOME DO AUTOR " + autor_editar.getNome_autor() + ": ");
			String nome = lerNome.nextLine();
			nome = utils.validarPreenchimentoString(autor_editar.getId_autor() + ". DIGITE O NOVO NOME DO AUTOR " + autor_editar.getNome_autor() + ": ", nome);
			
			// EDITANDO AUTOR NA LISTA DE AUTORES (OK)
			autor_editar.setNome_autor(nome);
			
			// EDITANDO AUTOR NO BANCO DE DADOS (OK)
			autorDAO.update(autor_editar);
			
			System.out.println("NOME ATUALIZADO COM SUCESSO!");
		}
	}
	
	public void editarEmail(Autor autor_editar) throws IOException, SQLException {
		if (utils.confirmarAcao("EDITAR EMAIL DO AUTOR " + autor_editar.getId_autor() + ". " + autor_editar.getNome_autor())) {
			System.out.println(autor_editar.getId_autor() + ". DIGITE O NOVO EMAIL DO AUTOR " + autor_editar.getNome_autor() + ": ");
			String email = ler.next();
			email = utils.validarPreenchimentoString(autor_editar.getId_autor() + ". DIGITE O NOVO EMAIL DO AUTOR " + autor_editar.getNome_autor() + ": ", email);

			// EDITANDO AUTOR NA LISTA DE AUTORES (OK)
			autor_editar.setEmail_autor(email);
			
			// EDITANDO AUTOR NO BANCO DE DADOS (OK)
			autorDAO.update(autor_editar);
			
			System.out.println("EMAIL ATUALIZADO COM SUCESSO!");
		}
	}
	
	public void editarTelefone(Autor autor_editar) throws IOException, SQLException {
		if (utils.confirmarAcao("EDITAR TELEFONE DO AUTOR " + autor_editar.getId_autor() + ". " + autor_editar.getNome_autor())) {
			System.out.println(autor_editar.getId_autor() + ". DIGITE O NOVO TELEFONE DO AUTOR " + autor_editar.getNome_autor() + " (EXEMPLO: 11983050165): ");
			String telefone = ler.next();
			telefone = utils.validarPreenchimentoString(autor_editar.getId_autor() + ". DIGITE O NOVO TELEFONE DO AUTOR " + autor_editar.getNome_autor() + ": ", telefone);
			String telefone_formatado = utils.formatarTelefone(telefone);

			// EDITANDO AUTOR NA LISTA DE AUTORES (OK)
			autor_editar.setTelefone_autor(telefone_formatado);
			
			// EDITANDO AUTOR NO BANCO DE DADOS (OK)
			autorDAO.update(autor_editar);
			
			System.out.println("TELEFONE ATUALIZADO COM SUCESSO!");
		}
	}
	
	public void editarBio(Autor autor_editar) throws IOException, SQLException {
		if (utils.confirmarAcao("EDITAR BIO DO AUTOR " + autor_editar.getId_autor() + ". " + autor_editar.getNome_autor())) {
			System.out.println(autor_editar.getId_autor() + ". DIGITE A NOVA BIO DO AUTOR " + autor_editar.getNome_autor() + ": ");
			String bio = lerNome.nextLine();
			bio = utils.validarPreenchimentoString(autor_editar.getId_autor() + ". DIGITE A NOVA BIO DO AUTOR " + autor_editar.getNome_autor() + ": ", bio);

			// EDITANDO AUTOR NA LISTA DE AUTORES (OK)
			autor_editar.setBio_autor(bio);
			
			// EDITANDO AUTOR NO BANCO DE DADOS (OK)
			autorDAO.update(autor_editar);
			
			System.out.println("BIO ATUALIZADA COM SUCESSO!");
		}
	}
	
	public void editarImagem(Autor autor_editar) throws IOException, SQLException {
		if (utils.confirmarAcao("EDITAR IMAGEM DO AUTOR " + autor_editar.getId_autor() + ". " + autor_editar.getNome_autor())) {
			System.out.println(autor_editar.getId_autor() + ". DIGITE A NOVA URL DA IMAGEM DO AUTOR " + autor_editar.getNome_autor() + ": ");
			String imagem = ler.next();
			imagem = utils.validarPreenchimentoString(autor_editar.getId_autor() + ". DIGITE A NOVA URL DA IMAGEM DO AUTOR " + autor_editar.getNome_autor() + ": ", imagem);

			// EDITANDO AUTOR NA LISTA DE AUTORES (OK)
			autor_editar.setImagem_autor(imagem);
			
			// EDITANDO AUTOR NO BANCO DE DADOS (OK)
			autorDAO.update(autor_editar);
			
			System.out.println("IMAGEM ATUALIZADA COM SUCESSO!");
		}
	}
	
	public void editarLivrosAutor(int id_livro, HashMap<Integer, Autor> listaAutores, Autor autor_editar, HashMap<Integer, Livro> listaLivros) throws IOException, SQLException {
		if (utils.confirmarAcao("EDITAR LIVROS DO AUTOR " + autor_editar.getId_autor() + ". " + autor_editar.getNome_autor())) {
			HashMap<Integer, Livro> listaLivrosAutor = new HashMap<Integer, Livro>();
			HashMap<Integer, Livro> listaLivrosAdicionar = new HashMap<Integer, Livro>(listaLivros);
			
			boolean adicionar = false;
			
			System.out.println("------------------------------------------");
			System.out.println("DESEJA ADICIONAR LIVROS AO AUTOR?");
			System.out.println("01. SIM");
	        System.out.println("02. NÃO");
	        System.out.println("------------------------------------------");
	        System.out.println("DIGITE A OPÇÃO DESEJADA: ");
			int opcao = ler.nextInt();
			
			while (opcao != 1 && opcao != 2) {
				System.out.println("OPÇÃO INVÁLIDA.");
				utils.voltarMenu();
				System.out.println("------------------------------------------");
				System.out.println("DESEJA ADICIONAR LIVROS AO AUTOR?");
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
				autor_editar.setLivros_autor(listaLivrosAutor);
			}
			
			while (adicionar) {
				try {
					for (Livro livro : listaLivrosAdicionar.values()) {
				        System.out.println(livro.exibirLivro());
				    }
					System.out.println("DIGITE O ID DO LIVRO QUE DESEJA ADICIONAR AO AUTOR: ");
					int id_buscado = ler.nextInt();
					id_buscado = utils.validarId_Buscado(id_buscado, 1, (id_livro - 1), "listarLivros", null, null, null, listaLivrosAdicionar);
					
					Livro livro_adicionar = new Livro();
					
					if (livro_adicionar.indexLivro(id_buscado, listaLivrosAdicionar) == -1) {
						System.out.println("ID NÃO ENCONTRADO");
						utils.voltarMenu();
					}
					
					else {
						livro_adicionar = listaLivrosAdicionar.get(livro_adicionar.indexLivro(id_buscado, listaLivrosAdicionar));
						listaLivrosAutor.put(livro_adicionar.getId_livro(), livro_adicionar);
						listaLivrosAdicionar.remove(livro_adicionar.getId_livro());
						System.out.println("LIVRO ADICIONADO AO AUTOR COM SUCESSO!");
					}
					
					System.out.println("------------------------------------------");
					System.out.println("DESEJA ADICIONAR NOVO LIVRO AO AUTOR?");
					System.out.println("01. SIM");
			        System.out.println("02. NÃO");
			        System.out.println("------------------------------------------");
			        System.out.println("DIGITE A OPÇÃO DESEJADA: ");
					opcao = ler.nextInt();
					
					while (opcao != 1 && opcao != 2) {
						System.out.println("OPÇÃO INVÁLIDA.");
						utils.voltarMenu();
						System.out.println("------------------------------------------");
						System.out.println("DESEJA ADICIONAR NOVO LIVRO AO AUTOR?");
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
					
					autor_editar.setLivros_autor(listaLivrosAutor);
					
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
			
			// ADICIONANDO AUTOR NA LISTA DE AUTORES (OK)
			listaAutores.put(autor_editar.getId_autor(), autor_editar);
			
			// FAZENDO UPDATE DO AUTOR NO BANCO DE DADOS (OK)
			autorDAO.updateLivrosAutor(autor_editar);
			
			System.out.println("LIVROS DO AUTOR EDITADOS COM SUCESSO!");
		}
	}
	
	public void deletarAutor(int id_autor, HashMap<Integer, Autor> listaAutores) throws SQLException {
		try {
			if (utils.confirmarAcao("EXCLUIR AUTOR")) {
				// REMOVENDO AUTOR DA LISTA DE AUTORES (OK)
				listaAutores.remove(id_autor);
				
				// FAZENDO DELETE DO AUTOR NO BANCO DE DADOS (OK)
				autorDAO.delete(id_autor);
				
				System.out.println("AUTOR EXCLUÍDO COM SUCESSO!");
			}
		} catch (IOException e) {
			e.printStackTrace();
			
		}
	}

}
