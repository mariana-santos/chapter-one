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
	
	public void cadastrarCategoria(int id_categoria, HashMap<Integer, Categoria> listaCategorias) throws SQLException {
		Categoria nova_categoria = new Categoria();
		
		// SETANDO O ID DA CATEGORIA (OK)
		nova_categoria.setId_categoria(id_categoria);
		
		// SETANDO O NOME DA CATEGORIA (OK)
		System.out.println(id_categoria + ". DIGITE O NOME DA CATEGORIA:");
		nova_categoria.setNome_categoria(lerNome.nextLine());
		
		// ADICIONANDO CATEGORIA NA LISTA DE CATEGORIAS (OK)
		listaCategorias.put(nova_categoria.getId_categoria(), nova_categoria);
		
		// FAZENDO INSERT DA CATEGORIA NO BANCO DE DADOS (OK)
		categoriaDAO.insert(nova_categoria);
		
		
		System.out.println("CATEGORIA CADASTRADA COM SUCESSO!");
	}
	
	public void editarNome(Categoria categoria) throws IOException, SQLException {
		if (utils.confirmarAcao("EDITAR NOME")) {
			System.out.println(categoria.getId_categoria() + ". DIGITE O NOVO NOME DA CATEGORIA: ");
			String nome = lerNome.nextLine();
			nome = utils.validarPreenchimento(categoria.getId_categoria() + ". DIGITE O NOVO NOME DA CATEGORIA: ", nome);
			
			// EDITANDO CATEGORIA NA LISTA DE CATEGORIAS (OK)
			categoria.setNome_categoria(nome);
			
			// EDITANDO CATEGORIA NO BANCO DE DADOS (OK)
			categoriaDAO.update(categoria);
			
			System.out.println("NOME ATUALIZADO COM SUCESSO!");
		}
	}
	
	public void deletarCategoria(int id_categoria, HashMap<Integer, Categoria> listaCategorias) throws SQLException {
		try {
			if (utils.confirmarAcao("EXCLUIR CATEGORIA")) {
				// REMOVENDO CATEGORIA DA LISTA DE CATEGORIAS (OK)
				listaCategorias.remove(id_categoria);
				
				// FAZENDO DELETE DA CATEGORIA NO BANCO DE DADOS (OK)
				categoriaDAO.delete(id_categoria);
				
				System.out.println("CATEGORIA EXCLUÍDA COM SUCESSO!");
			}
		} catch (IOException e) {
			e.printStackTrace();
			
		}
	}

}
