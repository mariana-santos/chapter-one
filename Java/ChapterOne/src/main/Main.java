package main;

import java.io.IOException;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Scanner;

import dao.AutorDAO;
import dao.CategoriaDAO;
import dao.EditoraDAO;
import dao.LivroDAO;
import model.Autor;
import model.Categoria;
import model.Editora;
import model.Livro;
import model.Utils;
import services.AutorService;
import services.CategoriaService;
import services.EditoraService;
import services.LivroService;

public class Main {

	public static void main(String[] args) throws IOException, SQLException {
		
		// INSTANCIANDO OBJETOS (OK)
		Scanner ler = new Scanner(System.in);
		Autor autor = new Autor();
		AutorService autorService = new AutorService();
		AutorDAO autorDAO = new AutorDAO();
		Categoria categoria = new Categoria();
		CategoriaService categoriaService = new CategoriaService();
		CategoriaDAO categoriaDAO = new CategoriaDAO();
		Editora editora = new Editora();
		EditoraService editoraService = new EditoraService();
		EditoraDAO editoraDAO = new EditoraDAO();
		Livro livro = new Livro();
		LivroService livroService = new LivroService();
		LivroDAO livroDAO = new LivroDAO();		
		Utils utils = new Utils();
		
		// CRIANDO HASHMAPS (OK)
		HashMap<Integer, Autor> listaAutores = new HashMap<Integer, Autor>();
		HashMap<Integer, Categoria> listaCategorias = new HashMap<Integer, Categoria>();
		HashMap<Integer, Editora> listaEditoras = new HashMap<Integer, Editora>();
		HashMap<Integer, Livro> listaLivros = new HashMap<Integer, Livro>();
		
		// POPULANDO LISTAS ATRAVÉS DE SELECT NO BANCO DE DADOS (OK)
		listaAutores = autorDAO.getAll();
		listaCategorias = categoriaDAO.getAll();
		listaEditoras = editoraDAO.getAll();
		listaLivros = livroDAO.getAll();
				
		// CRIANDO VARIÁVEIS INICIAIS (OK)
		boolean iniciar, editar, menuAutores, menuCategorias, menuEditoras, menuLivros;
		int opcao, id_autor, id_categoria, id_editora, id_livro;
		
		// SETANDO VARIÁVEIS INICIAIS (OK)
		iniciar = true;
		id_autor = autorDAO.getIdMax() + 1;
		id_categoria = categoriaDAO.getIdMax() + 1;
		id_editora = editoraDAO.getIdMax() + 1;
		id_livro = livroDAO.getIdMax() + 1;
		
		while (iniciar) {
			utils.menuInicial();
			opcao = ler.nextInt();
			opcao = utils.validarOpcao(opcao, 1, 5, "menuInicial", autor, categoria, editora, livro);
			
			if (opcao == 1) {
				// MENU DE AUTORES (OK)
				menuAutores = true;
				
				while (menuAutores) {
					utils.menuAutores();
					opcao = ler.nextInt();
					opcao = utils.validarOpcao(opcao, 1, 5, "menuAutores", autor, categoria, editora, livro);
					
					if (opcao == 1) {
						// CADASTRAR AUTOR (OK)
						autorService.cadastrarAutor(id_autor, listaAutores);
						id_autor++;
						utils.voltarMenu();
					}
					
					else if (opcao == 2) {
						// LISTAR AUTORES (OK)
						autorService.listarAutores(listaAutores);
						utils.voltarMenu();
					}
					
					else if (opcao == 3) {
						// EDITAR AUTOR (OK)
						editar = true;
						
						if (listaAutores.size() == 0) {
							utils.voltarMenu();
						}
						
						else {
							autorService.listarAutores(listaAutores);
							System.out.println("DIGITE O ID DO AUTOR QUE DESEJA DELETAR: ");
							int id_buscado = ler.nextInt();
							id_buscado = utils.validarId_Buscado(id_buscado, 1, (id_autor - 1), "listarAutores", listaAutores, listaCategorias, listaEditoras, listaLivros);
							
							Autor autor_editar = new Autor();
							
							if (autor_editar.indexAutor(id_buscado, listaAutores) == -1) {
								System.out.println("ID NÃO ENCONTRADO");
								utils.voltarMenu();
							}
							
							else {
								autor_editar = listaAutores.get(autor_editar.indexAutor(id_buscado, listaAutores));
								
								while (editar) {
									autor_editar.perfilAutor(autor_editar);
									System.out.println("DIGITE A OPÇÃO QUE DESEJA EDITAR: ");
									opcao = ler.nextInt();
									opcao = utils.validarOpcao(opcao, 1, 7, "perfilAutor", autor_editar, categoria, editora, livro);
									
									
									if (opcao == 1) {
										// EDITAR ID DO AUTOR (OK)
										utils.editarNegativo("ID");
										utils.voltarMenu();
									}
									
									else if (opcao == 2) {
										// EDITAR O NOME DO AUTOR (OK)
										autorService.editarNome(autor_editar);
										utils.voltarMenu();
									}
									
									else if (opcao == 3) {
										// EDITAR O EMAIL DO AUTOR (OK)
										autorService.editarEmail(autor_editar);
										utils.voltarMenu();
									}
									
									else if (opcao == 4) {
										// EDITAR O TELEFONE DO AUTOR (OK)
										autorService.editarTelefone(autor_editar);
										utils.voltarMenu();
									}
									
									else if (opcao == 5) {
										// EDITAR A BIO DO AUTOR (OK)
										autorService.editarBio(autor_editar);
										utils.voltarMenu();
									}
									
									else if (opcao == 6) {
										// EDITAR A IMAGEM DO AUTOR (OK)
										autorService.editarImagem(autor_editar);
										utils.voltarMenu();
									}
									
									else if (opcao == 7) {
										// SAIR DO MENU EDITAR AUTOR (OK)
										editar = false;
									}
								}
							}
						}
					}
					
					else if (opcao == 4) {
						// DELETAR AUTOR (OK)
						autorService.listarAutores(listaAutores);
						System.out.println("DIGITE O ID DO AUTOR QUE DESEJA DELETAR: ");
						int id_buscado = ler.nextInt();
						id_buscado = utils.validarId_Buscado(id_buscado, 1, (id_autor - 1), "listarAutores", listaAutores, listaCategorias, listaEditoras, listaLivros);
						
						Autor autor_deletar = new Autor();
						
						if (autor_deletar.indexAutor(id_buscado, listaAutores) == -1) {
							System.out.println("ID NÃO ENCONTRADO");
							utils.voltarMenu();
						}
						
						else {
							autor_deletar = listaAutores.get(autor_deletar.indexAutor(id_buscado, listaAutores));
						
							autorService.deletarAutor(autor_deletar.getId_autor(), listaAutores);
							utils.voltarMenu();
						}
					}
					
					else if (opcao == 5) {
						// SAIR DO MENU DE AUTORES (OK)
						menuAutores = false;
					}
				}
			}
			
			if (opcao == 2) {
				// MENU DE CATEGORIAS (OK)
				menuCategorias = true;
				
				while (menuCategorias) {
					utils.menuCategorias();
					opcao = ler.nextInt();
					opcao = utils.validarOpcao(opcao, 1, 5, "menuCategorias", autor, categoria, editora, livro);
					
					if (opcao == 1) {
						// CADASTRAR CATEGORIA (OK)
						categoriaService.cadastrarCategoria(id_categoria, listaCategorias);
						id_categoria++;
						utils.voltarMenu();
					}
					
					else if (opcao == 2) {
						// LISTAR CATEGORIAS (OK)
						categoriaService.listarCategorias(listaCategorias);
						utils.voltarMenu();
					}
					
					else if (opcao == 3) {
						// EDITAR CATEGORIA (OK)
						editar = true;
						
						if (listaCategorias.size() == 0) {
							utils.voltarMenu();
						}
						
						else {
							categoriaService.listarCategorias(listaCategorias);
							System.out.println("DIGITE O ID DA CATEGORIA QUE DESEJA EDITAR: ");
							int id_buscado = ler.nextInt();
							id_buscado = utils.validarId_Buscado(id_buscado, 1, (id_categoria - 1), "listarCategorias", listaAutores, listaCategorias, listaEditoras, listaLivros);
							
							Categoria categoria_editar = new Categoria();
							
							if (categoria_editar.indexCategoria(id_buscado, listaCategorias) == -1) {
								System.out.println("ID NÃO ENCONTRADO");
								utils.voltarMenu();
							}
							
							else {
								categoria_editar = listaCategorias.get(categoria_editar.indexCategoria(id_buscado, listaCategorias));
								
								while (editar) {
									categoria_editar.perfilCategoria(categoria_editar);
									System.out.println("DIGITE A OPÇÃO QUE DESEJA EDITAR: ");
									opcao = ler.nextInt();
									opcao = utils.validarOpcao(opcao, 1, 3, "perfilCategoria", autor, categoria_editar, editora, livro);
									
									
									if (opcao == 1) {
										// EDITAR ID DA CATEGORIA (OK)
										utils.editarNegativo("ID");
										utils.voltarMenu();
									}
									
									else if (opcao == 2) {
										// EDITAR O NOME DA CATEGORIA (OK)
										categoriaService.editarNome(categoria_editar);
										utils.voltarMenu();
									}
									
									else if (opcao == 3) {
										// SAIR DO MENU EDITAR CATEGORIA (OK)
										editar = false;
									}
								}
							}
						}
					}
					
					else if (opcao == 4) {
						// DELETAR CATEGORIA (OK)
						categoriaService.listarCategorias(listaCategorias);
						System.out.println("DIGITE O ID DA CATEGORIA QUE DESEJA DELETAR: ");
						int id_buscado = ler.nextInt();
						id_buscado = utils.validarId_Buscado(id_buscado, 1, (id_categoria - 1), "listarCategorias", listaAutores, listaCategorias, listaEditoras, listaLivros);
						
						Categoria categoria_deletar = new Categoria();
						
						if (categoria_deletar.indexCategoria(id_buscado, listaCategorias) == -1) {
							System.out.println("ID NÃO ENCONTRADO");
							utils.voltarMenu();
						}
						
						else {
							categoria_deletar = listaCategorias.get(categoria_deletar.indexCategoria(id_buscado, listaCategorias));
						
							categoriaService.deletarCategoria(categoria_deletar.getId_categoria(), listaCategorias);
							utils.voltarMenu();
						}
					}
					
					else if (opcao == 5) {
						// SAIR DO MENU DE CATEGORIAS (OK)
						menuCategorias = false;
					}
				}
			}
			
			if (opcao == 3) {
				// MENU DE EDITORAS (OK)
				menuEditoras = true;
				
				while (menuEditoras) {
					utils.menuEditoras();
					opcao = ler.nextInt();
					opcao = utils.validarOpcao(opcao, 1, 5, "menuEditoras", autor, categoria, editora, livro);
					
					if (opcao == 1) {
						// CADASTRAR EDITORA (OK)
						editoraService.cadastrarEditora(id_editora, listaEditoras);
						id_editora++;
						utils.voltarMenu();
					}
					
					else if (opcao == 2) {
						// LISTAR EDITORAS (OK)
						editoraService.listarEditoras(listaEditoras);
						utils.voltarMenu();
					}
					
					else if (opcao == 3) {
						// EDITAR EDITORAS (OK)
						editar = true;
						
						if (listaEditoras.size() == 0) {
							utils.voltarMenu();
						}
						
						else {
							editoraService.listarEditoras(listaEditoras);
							System.out.println("DIGITE O ID DA EDITORA QUE DESEJA EDITAR: ");
							int id_buscado = ler.nextInt();
							id_buscado = utils.validarId_Buscado(id_buscado, 1, (id_editora - 1), "listarEditoras", listaAutores, listaCategorias, listaEditoras, listaLivros);
							
							Editora editora_editar = new Editora();
							
							if (editora_editar.indexEditora(id_buscado, listaEditoras) == -1) {
								System.out.println("ID NÃO ENCONTRADO");
								utils.voltarMenu();
							}
							
							else {
								editora_editar = listaEditoras.get(editora_editar.indexEditora(id_buscado, listaEditoras));
								
								while (editar) {
									editora_editar.perfilEditora(editora_editar);
									System.out.println("DIGITE A OPÇÃO QUE DESEJA EDITAR: ");
									opcao = ler.nextInt();
									opcao = utils.validarOpcao(opcao, 1, 5, "perfilEditora", autor, categoria, editora_editar, livro);
									
									
									if (opcao == 1) {
										// EDITAR ID DA EDITORA (OK)
										utils.editarNegativo("ID");
										utils.voltarMenu();
									}
									
									else if (opcao == 2) {
										// EDITAR O NOME DA EDITORA (OK)
										editoraService.editarNome(editora_editar);
										utils.voltarMenu();
									}
									
									else if (opcao == 3) {
										// EDITAR O ENDEREÇO DA EDITORA (OK)
										editoraService.editarEndereco(editora_editar);
										utils.voltarMenu();
									}
									
									else if (opcao == 4) {
										// EDITAR O TELEFONE DA EDITORA (OK)
										editoraService.editarTelefone(editora_editar);
										utils.voltarMenu();
									}
									
									else if (opcao == 5) {
										// SAIR DO MENU EDITAR EDITORA (OK)
										editar = false;
									}
								}
							}
						}
					}
					
					else if (opcao == 4) {
						// DELETAR EDITORA (OK)
						editoraService.listarEditoras(listaEditoras);
						System.out.println("DIGITE O ID DA EDITORA QUE DESEJA EDITAR: ");
						int id_buscado = ler.nextInt();
						id_buscado = utils.validarId_Buscado(id_buscado, 1, (id_editora - 1), "listarEditoras", listaAutores, listaCategorias, listaEditoras, listaLivros);
						
						Editora editora_deletar = new Editora();
						
						if (editora_deletar.indexEditora(id_buscado, listaEditoras) == -1) {
							System.out.println("ID NÃO ENCONTRADO");
							utils.voltarMenu();
						}
						
						else {
							editora_deletar = listaEditoras.get(editora_deletar.indexEditora(id_buscado, listaEditoras));
						
							editoraService.deletarEditora(editora_deletar.getId_editora(), listaEditoras);
							utils.voltarMenu();
						}
					}
					
					else if (opcao == 5) {
						// SAIR DO MENU DE EDITORAS (OK)
						menuEditoras = false;
					}
				}
			}
		}
		
		ler.close();
		System.out.println("PROGRAMA ENCERRADO.");

	}
}
