package services;

import java.io.IOException;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.InputMismatchException;
import java.util.Map;
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
		
		// FAZENDO INSERT DO AUTOR NO BANCO DE DADOS (OK)
		if (autorDAO.insert(novo_autor) && autorDAO.insertLivrosAutor(novo_autor)) {
			// ADICIONANDO AUTOR NA LISTA DE AUTORES (OK)
			listaAutores.put(novo_autor.getId_autor(), novo_autor);
			
			if (novo_autor.getLivros_autor().isEmpty()) {
			    for (Map.Entry<Integer, Livro> livro_na_lista : listaLivros.entrySet()) {
			        Livro livro_lista = livro_na_lista.getValue();
			        livro_lista.getAutores_livro().remove(novo_autor.getId_autor());
			    }
			} 
			
			else {
			    for (Map.Entry<Integer, Livro> livro_do_autor : novo_autor.getLivros_autor().entrySet()) {
			        Livro livro_autor = livro_do_autor.getValue();
			        for (Map.Entry<Integer, Livro> livro_na_lista : listaLivros.entrySet()) {
			            Livro livro_lista = livro_na_lista.getValue();
			            if (livro_autor.equals(livro_lista)) {
			                boolean autorAtualizado = false;
			                for (Map.Entry<Integer, Autor> autor_livro : livro_lista.getAutores_livro().entrySet()) {
			                    Autor autor = autor_livro.getValue();
			                    if (autor.getId_autor() == novo_autor.getId_autor()) {
			                        autorAtualizado = true;
			                        break;
			                    }
			                }
			                
			                if (!autorAtualizado) {
			                    livro_lista.getAutores_livro().put(novo_autor.getId_autor(), novo_autor);
			                }
			            } else {
			                livro_lista.getAutores_livro().remove(novo_autor.getId_autor());
			            }
			        }
			    }
			}
			
			System.out.println("AUTOR CADASTRADO COM SUCESSO!");
		}
		
		else {
			System.out.println("FALHA AO CADASTRAR O AUTOR!");
			utils.voltarMenu();
		}
	}
	
	public void editarNome(Autor autor_editar) throws IOException, SQLException {
		if (utils.confirmarAcao("EDITAR NOME DO AUTOR " + autor_editar.getId_autor() + ". " + autor_editar.getNome_autor())) {
			System.out.println(autor_editar.getId_autor() + ". DIGITE O NOVO NOME DO AUTOR " + autor_editar.getNome_autor() + ": ");
			String nome = lerNome.nextLine();
			nome = utils.validarPreenchimentoString(autor_editar.getId_autor() + ". DIGITE O NOVO NOME DO AUTOR " + autor_editar.getNome_autor() + ": ", nome);
			
			Autor autor_antigo = new Autor(autor_editar.getId_autor(), autor_editar.getNome_autor(), autor_editar.getEmail_autor(), autor_editar.getTelefone_autor(), autor_editar.getBio_autor(), autor_editar.getImagem_autor(), autor_editar.getLivros_autor());

	        // EDITANDO AUTOR NA LISTA DE AUTORES (OK)
			autor_editar.setNome_autor(nome);
			
			// FAZENDO UPDATE NO BANCO DE DADOS (OK)
	        if (autorDAO.update(autor_editar)) {
	            System.out.println("NOME ATUALIZADO COM SUCESSO!");
	        } 
	        
	        else {
	            autor_editar.setNome_autor(autor_antigo.getNome_autor());
	            System.out.println("FALHA AO ATUALIZAR O NOME!");
	        }
		}
	}
	
	
	public void editarEmail(Autor autor_editar) throws IOException, SQLException {
		if (utils.confirmarAcao("EDITAR EMAIL DO AUTOR " + autor_editar.getId_autor() + ". " + autor_editar.getNome_autor())) {
			System.out.println(autor_editar.getId_autor() + ". DIGITE O NOVO EMAIL DO AUTOR " + autor_editar.getNome_autor() + ": ");
			String email = ler.next();
			email = utils.validarPreenchimentoString(autor_editar.getId_autor() + ". DIGITE O NOVO EMAIL DO AUTOR " + autor_editar.getNome_autor() + ": ", email);
			
	        Autor autor_antigo = new Autor(autor_editar.getId_autor(), autor_editar.getNome_autor(), autor_editar.getEmail_autor(), autor_editar.getTelefone_autor(), autor_editar.getBio_autor(), autor_editar.getImagem_autor(), autor_editar.getLivros_autor());

	        // EDITANDO AUTOR NA LISTA DE AUTORES (OK)
	     	autor_editar.setEmail_autor(email);
	     	
	     	// FAZENDO UPDATE NO BANCO DE DADOS (OK)
	        if (autorDAO.update(autor_editar)) {
	            System.out.println("EMAIL ATUALIZADO COM SUCESSO!");
	        } 
	        
	        else {
	            autor_editar.setEmail_autor(autor_antigo.getEmail_autor());
	            System.out.println("FALHA AO ATUALIZAR O EMAIL!");
	        }
		}
	}
	
	public void editarTelefone(Autor autor_editar) throws IOException, SQLException {
		if (utils.confirmarAcao("EDITAR TELEFONE DO AUTOR " + autor_editar.getId_autor() + ". " + autor_editar.getNome_autor())) {
			System.out.println(autor_editar.getId_autor() + ". DIGITE O NOVO TELEFONE DO AUTOR " + autor_editar.getNome_autor() + " (EXEMPLO: 11983050165): ");
			String telefone = ler.next();
			telefone = utils.validarPreenchimentoString(autor_editar.getId_autor() + ". DIGITE O NOVO TELEFONE DO AUTOR " + autor_editar.getNome_autor() + ": ", telefone);
			String telefone_formatado = utils.formatarTelefone(telefone);
			
			Autor autor_antigo = new Autor(autor_editar.getId_autor(), autor_editar.getNome_autor(), autor_editar.getEmail_autor(), autor_editar.getTelefone_autor(), autor_editar.getBio_autor(), autor_editar.getImagem_autor(), autor_editar.getLivros_autor());

	        // EDITANDO AUTOR NA LISTA DE AUTORES (OK)
			autor_editar.setTelefone_autor(telefone_formatado);
			
			// FAZENDO UPDATE NO BANCO DE DADOS (OK)
	        if (autorDAO.update(autor_editar)) {
	            System.out.println("TELEFONE ATUALIZADO COM SUCESSO!");
	        } 
	        
	        else {
	        	autor_editar.setTelefone_autor(autor_antigo.getTelefone_autor());
	            System.out.println("FALHA AO ATUALIZAR O TELEFONE!");
	        }
		}
	}
	
	public void editarBio(Autor autor_editar) throws IOException, SQLException {
		if (utils.confirmarAcao("EDITAR BIO DO AUTOR " + autor_editar.getId_autor() + ". " + autor_editar.getNome_autor())) {
			System.out.println(autor_editar.getId_autor() + ". DIGITE A NOVA BIO DO AUTOR " + autor_editar.getNome_autor() + ": ");
			String bio = lerNome.nextLine();
			bio = utils.validarPreenchimentoString(autor_editar.getId_autor() + ". DIGITE A NOVA BIO DO AUTOR " + autor_editar.getNome_autor() + ": ", bio);
			
			Autor autor_antigo = new Autor(autor_editar.getId_autor(), autor_editar.getNome_autor(), autor_editar.getEmail_autor(), autor_editar.getTelefone_autor(), autor_editar.getBio_autor(), autor_editar.getImagem_autor(), autor_editar.getLivros_autor());

	        // EDITANDO AUTOR NA LISTA DE AUTORES (OK)
			autor_editar.setBio_autor(bio);
			
			// FAZENDO UPDATE NO BANCO DE DADOS (OK)
	        if (autorDAO.update(autor_editar)) {
	            System.out.println("BIO ATUALIZADA COM SUCESSO!");
	        } 
	        
	        else {
	            autor_editar.setBio_autor(autor_antigo.getBio_autor());
	            System.out.println("FALHA AO ATUALIZAR A BIO!");
	        }
		}
	}
	
	public void editarImagem(Autor autor_editar) throws IOException, SQLException {
		if (utils.confirmarAcao("EDITAR IMAGEM DO AUTOR " + autor_editar.getId_autor() + ". " + autor_editar.getNome_autor())) {
			System.out.println(autor_editar.getId_autor() + ". DIGITE A NOVA URL DA IMAGEM DO AUTOR " + autor_editar.getNome_autor() + ": ");
			String imagem = ler.next();
			imagem = utils.validarPreenchimentoString(autor_editar.getId_autor() + ". DIGITE A NOVA URL DA IMAGEM DO AUTOR " + autor_editar.getNome_autor() + ": ", imagem);
			
			Autor autor_antigo = new Autor(autor_editar.getId_autor(), autor_editar.getNome_autor(), autor_editar.getEmail_autor(), autor_editar.getTelefone_autor(), autor_editar.getBio_autor(), autor_editar.getImagem_autor(), autor_editar.getLivros_autor());

	        // EDITANDO AUTOR NA LISTA DE AUTORES (OK)
			autor_editar.setImagem_autor(imagem);
			
			// FAZENDO UPDATE NO BANCO DE DADOS (OK)
	        if (autorDAO.update(autor_editar)) {
	            System.out.println("IMAGEM ATUALIZADA COM SUCESSO!");
	        } 
	        
	        else {
	            autor_editar.setImagem_autor(autor_antigo.getImagem_autor());
	            System.out.println("FALHA AO ATUALIZAR A IMAGEM!");
	        }
		}
	}
	
	public void editarLivrosAutor(int id_livro, HashMap<Integer, Autor> listaAutores, Autor autor_editar, HashMap<Integer, Livro> listaLivros) throws IOException, SQLException {
		if (utils.confirmarAcao("EDITAR LIVROS DO AUTOR " + autor_editar.getId_autor() + ". " + autor_editar.getNome_autor())) {
			HashMap<Integer, Livro> listaLivrosAutor = new HashMap<Integer, Livro>();
			HashMap<Integer, Livro> listaLivrosAdicionar = new HashMap<Integer, Livro>(listaLivros);
			Autor autor_antigo = new Autor(autor_editar.getId_autor(), autor_editar.getNome_autor(), autor_editar.getEmail_autor(), autor_editar.getTelefone_autor(), autor_editar.getBio_autor(), autor_editar.getImagem_autor(), autor_editar.getLivros_autor());
			
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
				// EDITANDO AUTOR NA LISTA DE AUTORES (OK)
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
			
			// EDITANDO AUTOR NA LISTA DE AUTORES (OK)
			autor_editar.setLivros_autor(listaLivrosAutor);
			
			// EDITANDO TODOS OS LIVROS NA LISTA DE LIVROS PARA ADICIONAR OU REMOVER O AUTOR DE ACORDO COM A NOVA LISTA DO AUTOR (OK)
			if (autor_editar.getLivros_autor().isEmpty()) {
			    for (Map.Entry<Integer, Livro> livro_na_lista : listaLivros.entrySet()) {
			        Livro livro_lista = livro_na_lista.getValue();
			        livro_lista.getAutores_livro().remove(autor_editar.getId_autor());
			    }
			} 
			
			else {
			    for (Map.Entry<Integer, Livro> livro_do_autor : autor_editar.getLivros_autor().entrySet()) {
			        Livro livro_autor = livro_do_autor.getValue();
			        for (Map.Entry<Integer, Livro> livro_na_lista : listaLivros.entrySet()) {
			            Livro livro_lista = livro_na_lista.getValue();
			            if (livro_autor.equals(livro_lista)) {
			                boolean autorAtualizado = false;
			                for (Map.Entry<Integer, Autor> entry : livro_lista.getAutores_livro().entrySet()) {
			                    Autor autor = entry.getValue();
			                    if (autor.getId_autor() == autor_editar.getId_autor()) {
			                        autorAtualizado = true;
			                        break;
			                    }
			                }
			                
			                if (!autorAtualizado) {
			                    livro_lista.getAutores_livro().put(autor_editar.getId_autor(), autor_editar);
			                }
			            } else {
			                livro_lista.getAutores_livro().remove(autor_editar.getId_autor());
			            }
			        }
			    }
			}

			// FAZENDO UPDATE NO BANCO DE DADOS (OK)
	        if (autorDAO.updateLivrosAutor(autor_editar)) {
	            System.out.println("LIVROS DO AUTOR ATUALIZADOS COM SUCESSO!");
	        } 
	        
	        else {
	            autor_editar.setLivros_autor(autor_antigo.getLivros_autor());
	            System.out.println("FALHA AO ATUALIZAR OS LIVROS DO AUTOR!");
	        }
		}
	}
	
	public void deletarAutor(int id_autor, HashMap<Integer, Autor> listaAutores) throws SQLException {
		try {
			if (utils.confirmarAcao("EXCLUIR AUTOR")) {
				// FAZENDO DELETE DO AUTOR NO BANCO DE DADOS (OK)
				if (autorDAO.delete(id_autor)) {
					// REMOVENDO AUTOR DA LISTA DE AUTORES (OK)
					listaAutores.remove(id_autor);
					System.out.println("AUTOR EXCLUÍDO COM SUCESSO!");
				}
				
				else {
					System.out.println("FALHA AO EXCLUIR O AUTOR!");
					utils.voltarMenu();
				}
			}
			
		} catch (IOException e) {
			e.printStackTrace();
			
		}
	}

}
