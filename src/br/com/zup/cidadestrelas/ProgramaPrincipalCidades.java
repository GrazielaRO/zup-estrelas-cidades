package br.com.zup.cidadestrelas;

import java.sql.Connection;
import java.sql.SQLException;
import java.text.Normalizer;
import java.util.Scanner;
import java.util.regex.Pattern;

import br.com.zup.DAO.CidadesDao;
import br.com.zup.POJO.CidadesPojo;
import br.com.zup.conectionfactory.ConnectionFactory;

public class ProgramaPrincipalCidades {

	public static void cabecalho() {
		System.out.println("------------------------------------------------------------");
		System.out.println("                   CIDADES DO BRASIL                        ");
		System.out.println("------------------------------------------------------------\n");
	}

	public static String retiraAcento(String str) {
		String nfdNormalizedString = Normalizer.normalize(str, Normalizer.Form.NFD);
		Pattern pattern = Pattern.compile("\\p{InCombiningDiacriticalMarks}+");
		return pattern.matcher(nfdNormalizedString).replaceAll("");
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
		
		int capital = 0;
		final String REGEX_NOME_CIDADE = "[a-zA-Z ]+";
		final String REGEX_CEP = "\\d{5}-\\d{3}";
		final String REGEX_SIGLA_ESTADO = "[a-zA-Z]+";
		final String REGEX_DATA = "(1|2)[0-9]{3}-[0-9][1-9]-[0-9][1-9]";
		
		sc.nextLine();
		System.out.print("\nInforme o nome da cidade: ");
		String nomeCidade = retiraAcento(sc.nextLine());
		
		while(!Pattern.matches(REGEX_NOME_CIDADE, nomeCidade)) {
			System.out.print("\nO nome da cidade deve conter apenas letras do alfabeto.\n\nDigite novamente o nome da cidade: ");
			nomeCidade = retiraAcento(sc.nextLine());
		}
		
		System.out.print("\nInforme o CEP (ex.: 39867-947): ");
		String cep = sc.next();
		
		while (!Pattern.matches(REGEX_CEP, cep)) {
			System.out.print("\nCEP incorreto!\n\nTente novamente (ex.: 39867-947): ");
			cep = sc.next();
		}
		
		System.out.print("\nInforme quantos habitantes tem a cidade: ");
		int numeroHabitantes = sc.nextInt();
		
		System.out.print("\nDigite 1 se a cidade for uma capital ou 0 caso não seja uma capital: ");
		String capitalInterior = sc.next();
		
		while (!capitalInterior.equals("1") && !capitalInterior.equals("0")) {
			System.out.print("\nOpção incorreta. Você precisa digitar 1 se a cidade for uma capital ou 0 se não for."
					+ "\n\nA cidade adicionada é uma capital? ");
			capitalInterior = sc.next();
		}
		
		if (capitalInterior.equals("1") || capitalInterior.equals("0")) {
			capital = Integer.valueOf(capitalInterior);
		}
		
		sc.nextLine();
		System.out.print("\nA qual a sigla do estado que pertence essa cidade (ex.: SP)? ");
		String estado = sc.nextLine().toUpperCase();
		
		while (!Pattern.matches(REGEX_SIGLA_ESTADO, estado) || estado.length() > 2) {
			System.out.print("\nVocê precisa informar a  sigla do estado.\n\nDigite novamente a que estado pertence a cidade: ");
			estado = sc.nextLine().toUpperCase();
		}
		
		System.out.print("\nInforme a renda per capita da cidade: ");
		float rendaPerCapita = sc.nextFloat();
		
		sc.nextLine();
		System.out.print("\nQual a data de fundação da cidade (ex.: AAAA-MM-DD): ");
		String dataFundacao = sc.next();
		
		while (!Pattern.matches(REGEX_DATA, dataFundacao)) {
			System.out.print("\nData incorreta!\n\nDigite a data no padrão correto (ex.: AAAA-MM-DD): ");
			dataFundacao = sc.next();
		}
		
		CidadesPojo cidade = new CidadesPojo(nomeCidade, cep, numeroHabitantes, capital, estado, rendaPerCapita, dataFundacao);
		
		CidadesDao cidadedao = new CidadesDao();
		
		cidadedao.insereCidade(cidade);
		
		System.out.println("\nCidade cadastrada com sucesso!\n");
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
