package services;

import java.io.IOException;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Scanner;

import dao.CategoriaDAO;
import model.Categoria;
import model.Utils;

public class CategoriaService {
	
	Scanner ler = new Scanner(System.in);
	Scanner lerNome = new Scanner(System.in).useDelimiter("\n");
	Utils utils = new Utils();
	CategoriaDAO categoriaDAO = new CategoriaDAO();
	
	public void listarCategorias(HashMap<Integer, Categoria> listaCategorias) {
	    for (Categoria categoria : listaCategorias.values()) {
	        System.out.println(categoria.exibirCategoria());
	    }
	}
	
	public void cadastrarCategoria(int id_categoria, HashMap<Integer, Categoria> listaCategorias) throws SQLException, IOException {
		Categoria nova_categoria = new Categoria();
		
		// SETANDO O ID DA CATEGORIA (OK)
		nova_categoria.setId_categoria(id_categoria);
		
		// SETANDO O NOME DA CATEGORIA (OK)
		System.out.println(id_categoria + ". DIGITE O NOME DA CATEGORIA:");
		nova_categoria.setNome_categoria(lerNome.nextLine());
		
		// FAZENDO INSERT DA CATEGORIA NO BANCO DE DADOS (OK)
		if (categoriaDAO.insert(nova_categoria)) {
			// ADICIONANDO CATEGORIA NA LISTA DE CATEGORIAS (OK)
			listaCategorias.put(nova_categoria.getId_categoria(), nova_categoria);
			System.out.println("CATEGORIA CADASTRADA COM SUCESSO!");
		}
		
		else {
			System.out.println("FALHA AO CADASTRAR CATEGORIA");
			utils.voltarMenu();
		}
	}
	
	public void editarNome(Categoria categoria_editar) throws IOException, SQLException {
		if (utils.confirmarAcao("EDITAR NOME DA CATEGORIA " + categoria_editar.getId_categoria() + ". " + categoria_editar.getNome_categoria())) {
			System.out.println(categoria_editar.getId_categoria() + ". DIGITE O NOVO NOME DA CATEGORIA " + categoria_editar.getNome_categoria() + ": ");
			String nome = lerNome.nextLine();
			nome = utils.validarPreenchimentoString(categoria_editar.getId_categoria() + ". DIGITE O NOVO NOME DA CATEGORIA " + categoria_editar.getNome_categoria() + ": ", nome);
			
			Categoria categoria_antiga = new Categoria(categoria_editar.getId_categoria(), categoria_editar.getNome_categoria());
			
			// EDITANDO CATEGORIA NA LISTA DE CATEGORIAS (OK)
			categoria_editar.setNome_categoria(nome);
			
			// FAZENDO UPDATE NO BANCO DE DADOS (OK)
			if (categoriaDAO.update(categoria_editar)) {
				System.out.println("NOME ATUALIZADO COM SUCESSO!");
			}
			
			else {
				categoria_editar.setNome_categoria(categoria_antiga.getNome_categoria());
				System.out.println("FALHA AO ATUALIZAR O NOME!");
			}
		}
	}
	
	public void deletarCategoria(int id_categoria, HashMap<Integer, Categoria> listaCategorias) throws SQLException {
		try {
			if (utils.confirmarAcao("EXCLUIR CATEGORIA")) {
				// FAZENDO DELETE DA CATEGORIA NO BANCO DE DADOS (OK)
				if (categoriaDAO.delete(id_categoria)) {
					// REMOVENDO CATEGORIA DA LISTA DE CATEGORIAS (OK)
					listaCategorias.remove(id_categoria);
					
					System.out.println("CATEGORIA EXCLUÍDA COM SUCESSO!");
				}
				
				else {
					System.out.println("FALHA AO EXCLUIR A CATEGORIA!");
					utils.voltarMenu();
				}
			}
			
		} catch (IOException e) {
			e.printStackTrace();
			
		}
	}

}
