package br.com.zup.cidadestrelas;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Scanner;

import br.com.zup.DAO.CidadesDao;
import br.com.zup.POJO.CidadesPojo;
import br.com.zup.conectionfactory.ConnectionFactory;

public class ProgramaPrincipalCidades {

	public static void cabecalho() {
		System.out.println("------------------------------------------------------------");
		System.out.println("                   CIDADES DO BRASIL                        ");
		System.out.println("------------------------------------------------------------\n");
	}

	public static void menuPrincipal(Scanner sc) {
		System.out.println("(1) - Incluir cidade");
		System.out.println("(2) - Excluir cidade");
		System.out.println("(3) - Alterar dados cidade");
		System.out.println("(4) - Consultar cidade");
		System.out.println("(5) - Listar cidades cadastradas");
		System.out.println("(0) - Sair do programa\n");
		
	}

	public static void incluirCidade(Scanner sc) throws SQLException {
		
		sc.nextLine();
		System.out.print("\nInforme o nome da cidade: ");
		String nome = sc.nextLine();
		
		System.out.print("Informe o CEP: ");
		String cep = sc.nextLine();
		
		System.out.print("Informe quantos habitantes tem a cidade: ");
		int numeroHabitantes = sc.nextInt();
		
		System.out.print("Essa cidade é uma capital? Digite 1 pra sim e 0 pra não: ");
		int capital = sc.nextInt();
		
		sc.nextLine();
		System.out.print("A qual estado pertence essa cidade: ");
		String estado = sc.nextLine();
		
		System.out.print("Qual a renda per capita: ");
		float rendaPerCapita = sc.nextFloat();
		
		sc.nextLine();
		System.out.print("Qual a data de fundação da cidade: ");
		String dataFundacao = sc.next();
		
		CidadesPojo cidade = new CidadesPojo(nome, cep, numeroHabitantes, capital, estado, rendaPerCapita, dataFundacao);
		
		CidadesDao cidadedao = new CidadesDao();
		
		cidadedao.insereCidade(cidade);
	}
	
	public static void main(String[] args) throws SQLException {

		Connection conn = new ConnectionFactory().getConnection();
		Scanner sc = new Scanner(System.in);
		String opcao = "";

		cabecalho();
		

		do {
			
			menuPrincipal(sc);
			System.out.print("Escolha uma das opções acima: ");
			opcao = sc.next();

			switch (opcao) {
			case "1":
				
				incluirCidade(sc);
				
				break;

			case "2":

				break;

			case "3":

				break;

			case "4":

				break;

			case "5":

				break;

			case "0":

				System.out.println("\nObrigada por utilizar nosso sistema!\n");

				break;

			default:

				System.out.println("\nOpção inválida. Tente novamente uma opção de 0 a 2.\n");

				break;
			}
		} while (!opcao.equals("0"));

		sc.close();
		conn.close();
	}

}
