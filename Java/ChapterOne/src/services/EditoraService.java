package services;

import java.io.IOException;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Scanner;

import dao.EditoraDAO;
import model.Editora;
import model.Utils;

public class EditoraService {
	
	Scanner ler = new Scanner(System.in);
	Scanner lerNome = new Scanner(System.in).useDelimiter("\n");
	Utils utils = new Utils();
	EditoraDAO editoraDAO = new EditoraDAO();
	
	public void listarEditoras(HashMap<Integer, Editora> listaEditoras) {
	    for (Editora editora : listaEditoras.values()) {
	        System.out.println(editora.exibirEditora());
	    }
	}
	
	public void cadastrarEditora(int id_editora, HashMap<Integer, Editora> listaEditoras) throws SQLException, IOException {
		Editora nova_editora = new Editora();
		
		// SETANDO O ID DA EDITORA (OK)
		nova_editora.setId_editora(id_editora);
		
		// SETANDO O NOME DA EDITORA (OK)
		System.out.println(id_editora + ". DIGITE O NOME DA EDITORA: ");
		nova_editora.setNome_editora(lerNome.nextLine());
		
		// SETANDO O ENDEREÇO DA EDITORA (OK)
		System.out.println(id_editora + ". DIGITE O ENDEREÇO DA EDITORA: ");
		nova_editora.setEndereco_editora(lerNome.nextLine());
		
		// SETANDO O TELEFONE DA EDITORA (OK)
		System.out.println(id_editora + ". DIGITE O TELEFONE DA EDITORA (EXEMPLO: 11983050165): ");
		String telefone = ler.next();
		String telefone_formatado = utils.formatarTelefone(telefone);
		nova_editora.setTelefone_editora(telefone_formatado);
		
		if (editoraDAO.insert(nova_editora)) {
			// ADICIONANDA EDITORA NA LISTA DE EDITORAES (OK)
			listaEditoras.put(nova_editora.getId_editora(), nova_editora);
			
			System.out.println("EDITORA CADASTRADA COM SUCESSO!");
		}
		
		else {
			System.out.println("FALHA AO CADASTRAR A CATEGORIA!");
			utils.voltarMenu();
		}
	}
	
	public void editarNome(Editora editora_editar) throws IOException, SQLException {
		if (utils.confirmarAcao("EDITAR NOME DA EDITORA " + editora_editar.getId_editora() + ". " + editora_editar.getNome_editora())) {
			System.out.println(editora_editar.getId_editora() + ". DIGITE O NOVO NOME DA EDITORA " + editora_editar.getNome_editora() + ": ");
			String nome = lerNome.nextLine();
			nome = utils.validarPreenchimentoString(editora_editar.getId_editora() + ". DIGITE O NOVO NOME DA EDITORA " + editora_editar.getNome_editora() + ": ", nome);
			
			Editora editora_antiga = new Editora(editora_editar.getId_editora(), editora_editar.getNome_editora(), editora_editar.getEndereco_editora(), editora_editar.getTelefone_editora());
			
			// EDITANDA EDITORA NA LISTA DE EDITORAES (OK)
			editora_editar.setNome_editora(nome);
			
			if (editoraDAO.update(editora_editar)) {
				System.out.println("NOME ATUALIZADO COM SUCESSO!");
			}
			
			else {
				editora_editar.setNome_editora(editora_antiga.getNome_editora());
				System.out.println("FALHA AO ATUALIZAR O NOME!");
			}
		}
	}
	
	public void editarEndereco(Editora editora_editar) throws IOException, SQLException {
		if (utils.confirmarAcao("EDITAR ENDEREÇO DA EDITORA " + editora_editar.getId_editora() + ". " + editora_editar.getNome_editora())) {
			System.out.println(editora_editar.getId_editora() + ". DIGITE O NOVO ENDEREÇO DA EDITORA " + editora_editar.getNome_editora() + ": ");
			String endereco = lerNome.nextLine();
			endereco = utils.validarPreenchimentoString(editora_editar.getId_editora() + ". DIGITE O NOVO ENDEREÇO DA EDITORA " + editora_editar.getNome_editora() + ": ", endereco);
			
			Editora editora_antiga = new Editora(editora_editar.getId_editora(), editora_editar.getNome_editora(), editora_editar.getEndereco_editora(), editora_editar.getTelefone_editora());
			
			// EDITANDA EDITORA NA LISTA DE EDITORAES (OK)
			editora_editar.setEndereco_editora(endereco);
			
			if (editoraDAO.update(editora_editar)) {
				System.out.println("ENDEREÇO ATUALIZADO COM SUCESSO!");
			}
			
			else {
				editora_editar.setEndereco_editora(editora_antiga.getEndereco_editora());
				System.out.println("FALHA AO ATUALIZAR O ENDEREÇO!");
			}
		}
	}
	
	public void editarTelefone(Editora editora_editar) throws IOException, SQLException {
		if (utils.confirmarAcao("EDITAR TELEFONE DA EDITORA " + editora_editar.getId_editora() + ". " + editora_editar.getNome_editora())) {
			System.out.println(editora_editar.getId_editora() + ". DIGITE O NOVO TELEFONE DA EDITORA " + editora_editar.getNome_editora() + " (EXEMPLO: 11983050165): ");
			String telefone = ler.next();
			telefone = utils.validarPreenchimentoString(editora_editar.getId_editora() + ". DIGITE O NOVO TELEFONE DA EDITORA " + editora_editar.getNome_editora() + ": ", telefone);
			String telefone_formatado = utils.formatarTelefone(telefone);
			
			Editora editora_antiga = new Editora(editora_editar.getId_editora(), editora_editar.getNome_editora(), editora_editar.getEndereco_editora(), editora_editar.getTelefone_editora());
			
			// EDITANDA EDITORA NA LISTA DE EDITORAES (OK)
			editora_editar.setTelefone_editora(telefone_formatado);
			
			if (editoraDAO.update(editora_editar)) {
				System.out.println("TELEFONE ATUALIZADO COM SUCESSO!");
			}
			
			else {
				editora_editar.setTelefone_editora(editora_antiga.getTelefone_editora());
				System.out.println("FALHA AO ATUALIZAR O TELEFONE!");
			}
		}
	}

	public void deletarEditora(int id_editora, HashMap<Integer, Editora> listaEditoras) throws SQLException {
		try {
			if (utils.confirmarAcao("EXCLUIR EDITORA")) {
				// FAZENDO DELETE DA EDITORA NO BANCO DE DADOS (OK)
				if (editoraDAO.delete(id_editora)) {
					// REMOVENDA EDITORA DA LISTA DE EDITORAES (OK)
					listaEditoras.remove(id_editora);
					System.out.println("EDITORA EXCLUÍDA COM SUCESSO!");
				}
				
				else {
					System.out.println("FALHA AO EXCLUIR A EDITORA!");
					utils.voltarMenu();
				}
			}
			
		} catch (IOException e) {
			e.printStackTrace();
			
		}
	}

}
