package services;

import java.io.IOException;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Scanner;

import dao.AutorDAO;
import model.Autor;
import model.Utils;

public class AutorService {
	
	Scanner ler = new Scanner(System.in);
	Scanner lerNome = new Scanner(System.in).useDelimiter("\n");
	Utils utils = new Utils();
	AutorDAO autorDAO = new AutorDAO();
	
	public void listarAutores(HashMap<Integer, Autor> listaAutores) {
	    for (Autor autor : listaAutores.values()) {
	        System.out.println(autor.exibirAutor());
	    }
	}
	
	public void cadastrarAutor(int id_autor, HashMap<Integer, Autor> listaAutores) throws SQLException {
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
		
		// ADICIONANDO AUTOR NA LISTA DE AUTORES (OK)
		listaAutores.put(novo_autor.getId_autor(), novo_autor);
		
		// FAZENDO INSERT DO AUTOR NO BANCO DE DADOS (OK)
		autorDAO.insert(novo_autor);
		
		System.out.println("AUTOR CADASTRADO COM SUCESSO!");
	}
	
	public void editarNome(Autor autor_editar) throws IOException, SQLException {
		if (utils.confirmarAcao("EDITAR NOME DO AUTOR " + autor_editar.getId_autor() + ". " + autor_editar.getNome_autor())) {
			System.out.println(autor_editar.getId_autor() + ". DIGITE O NOVO NOME DO AUTOR " + autor_editar.getNome_autor() + ": ");
			String nome = lerNome.nextLine();
			nome = utils.validarPreenchimento(autor_editar.getId_autor() + ". DIGITE O NOVO NOME DO AUTOR " + autor_editar.getNome_autor() + ": ", nome);
			
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
			email = utils.validarPreenchimento(autor_editar.getId_autor() + ". DIGITE O NOVO EMAIL DO AUTOR " + autor_editar.getNome_autor() + ": ", email);

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
			telefone = utils.validarPreenchimento(autor_editar.getId_autor() + ". DIGITE O NOVO TELEFONE DO AUTOR " + autor_editar.getNome_autor() + ": ", telefone);
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
			bio = utils.validarPreenchimento(autor_editar.getId_autor() + ". DIGITE A NOVA BIO DO AUTOR " + autor_editar.getNome_autor() + ": ", bio);

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
			imagem = utils.validarPreenchimento(autor_editar.getId_autor() + ". DIGITE A NOVA URL DA IMAGEM DO AUTOR " + autor_editar.getNome_autor() + ": ", imagem);

			// EDITANDO AUTOR NA LISTA DE AUTORES (OK)
			autor_editar.setImagem_autor(imagem);
			
			// EDITANDO AUTOR NO BANCO DE DADOS (OK)
			autorDAO.update(autor_editar);
			
			System.out.println("IMAGEM ATUALIZADA COM SUCESSO!");
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
