package br.com.zup.cidadestrelas;

import java.sql.Connection;
import java.sql.SQLException;
import java.text.Normalizer;
import java.util.List;
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
		System.out.println("\n(1) - Incluir cidade");
		System.out.println("(2) - Excluir cidade");
		System.out.println("(3) - Alterar dados cidade");
		System.out.println("(4) - Consultar cidade pelo CEP");
		System.out.println("(5) - Consultar cidade iniciada pela mesma letra");
		System.out.println("(6) - Listar cidades pelo estado");
		System.out.println("(7) - Contar cidades do mesmo estado");
		System.out.println("(8) - Listar cidades capitais estaduais ou não");
		System.out.println("(9) - Listar cidades cadastradas");
		System.out.println("(0) - Sair do programa\n");

	}

	public static void incluirCidade(Scanner sc) {

		int capital = 0;
		final String REGEX_NOME_CIDADE = "[a-zA-Z ]+";
		final String REGEX_CEP = "\\d{5}-\\d{3}";
		final String REGEX_SIGLA_ESTADO = "[a-zA-Z]+";
		final String REGEX_DATA = "(1|2)[0-9]{3}-[0-9][0-9]-[0-9][0-9]";

		sc.nextLine();
		System.out.print("\nInforme o nome da cidade: ");
		String nomeCidade = retiraAcento(sc.nextLine());

		while (!Pattern.matches(REGEX_NOME_CIDADE, nomeCidade)) {
			System.out.print(
					"\nO nome da cidade deve conter apenas letras do alfabeto.\n\nDigite novamente o nome da cidade: ");
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
			System.out.print(
					"\nVocê precisa informar a  sigla do estado.\n\nDigite novamente a que estado pertence a cidade: ");
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

		CidadesPojo cidade = new CidadesPojo(nomeCidade, cep, numeroHabitantes, capital, estado, rendaPerCapita,
				dataFundacao);

		CidadesDao cidadedao = new CidadesDao();

		cidadedao.insereCidade(cidade);

	}

	public static void excluirCidade(Scanner sc) throws SQLException {

		final String REGEX_CEP = "\\d{5}-\\d{3}";
		System.out.print("\nInforme o CEP da cidade que deseja excluir: ");
		String cep = sc.next();

		while (!Pattern.matches(REGEX_CEP, cep)) {
			System.out.print("\nCEP incorreto!\n\nTente novamente (ex.: 39867-947): ");
			cep = sc.next();
		}

		CidadesDao cidadedao = new CidadesDao();

		cidadedao.deletaCidade(cep);

	}

	public static void consultarCidadePeloCep(Scanner sc) {

		final String REGEX_CEP = "\\d{5}-\\d{3}";
		System.out.print("\nInforme o CEP que deseja consultar (ex.: 39867-947): ");
		String cep = sc.next();

		while (!Pattern.matches(REGEX_CEP, cep)) {
			System.out.print("\nCEP incorreto!\n\nTente novamente (ex.: 39867-947): ");
			cep = sc.next();
		}

		CidadesDao cidadedao = new CidadesDao();

		List<CidadesPojo> cidadesBD = cidadedao.listaCidadePorCEP(cep);

		System.out.printf("\nDados de endereço do CEP %s: \n\n", cep);
		for (CidadesPojo cidades : cidadesBD) {
			System.out.println(cidades);
		}

	}

	public static void consultarCidadesIniciadasComMesmaLetra(Scanner sc) {

		final String REGEX_PRIMEIRA_LETRA_CIDADE = "[a-zA-Z]+";
		System.out.print("\nDigite a letra pela qual o nome das cidades deve começar: ");
		String letra = sc.next().toUpperCase();

		while (!Pattern.matches(REGEX_PRIMEIRA_LETRA_CIDADE, letra) || letra.length() > 1) {
			System.out.print(
					"\nVocê precisa informar a  sigla do estado.\n\nDigite novamente a que estado pertence a cidade: ");
			letra = sc.nextLine().toUpperCase();
		}

		CidadesDao cidadedao = new CidadesDao();

		List<CidadesPojo> cidadesBD = cidadedao.listaCidadesComMesmoInicio(letra);

		System.out.printf("\nLista das cidades cadastradas inciciadas com a letra %s:\n\n", letra);
		for (CidadesPojo cidades : cidadesBD) {
			System.out.println(cidades);
		}
	}

	public static void listarCidadesPeloEstado(Scanner sc) {

		final String REGEX_SIGLA_ESTADO = "[a-zA-Z]+";
		System.out.print("\nDigite a sigla do estado: ");
		String sigla = sc.next().toUpperCase();

		while (!Pattern.matches(REGEX_SIGLA_ESTADO, sigla) || sigla.length() > 2) {
			System.out.print(
					"\nVocê precisa informar a  sigla do estado.\n\nDigite novamente a que estado pertence a cidade: ");
			sigla = sc.nextLine().toUpperCase();
		}

		CidadesDao cidadedao = new CidadesDao();
		List<CidadesPojo> cidadesBD = cidadedao.listaCidadesPelaSiglaEstado(sigla);

		System.out.printf("\nLista das cidades cadastradas pertencentes ao Estado de %s:\n\n", sigla);
		for (CidadesPojo cidades : cidadesBD) {
			System.out.println(cidades);
		}
	}

	public static void contaCidadesDoMesmoEstado(Scanner sc) {

		final String REGEX_SIGLA_ESTADO = "[a-zA-Z]+";

		System.out.print("\nInforme o estado: ");
		String estado = sc.next().toUpperCase();

		while (!Pattern.matches(REGEX_SIGLA_ESTADO, estado) || estado.length() > 2) {
			System.out.print(
					"\nVocê precisa informar a  sigla do estado.\n\nDigite novamente a que estado pertence a cidade: ");
			estado = sc.nextLine().toUpperCase();
		}

		CidadesDao cidadedao = new CidadesDao();
		System.out.printf("\nQuantidade de cidades no estado de %s: \n", estado);
		System.out.println(cidadedao.contaQuantidadeDeCidadesDoEstado(estado));
	}

	public static void listarCidadesCapitaisOuNao(Scanner sc) {

		int capital = 0;
		System.out.print("\nDigite 1 para listar as cidades que são capitais ou 0 para as que não são: ");
		String capitalInterior = sc.next();

		while (!capitalInterior.equals("1") && !capitalInterior.equals("0")) {
			System.out.print(
					"\nOpção incorreta. Você precisa digitar 1 para listar as cidades que são capitais ou 0 para as que não são."
							+ "\n\nDigite a opção novamente: ");
			capitalInterior = sc.next();
		}

		if (capitalInterior.equals("1") || capitalInterior.equals("0")) {
			capital = Integer.valueOf(capitalInterior);
		}

		CidadesDao cidadedao = new CidadesDao();
		List<CidadesPojo> cidadesBD = cidadedao.listaCidadesCapitaisOuNao(capital);

		if (capital == 1) {

			System.out.println("\nRelação das cidades que são capitais: \n");
			for (CidadesPojo cidades : cidadesBD) {
				System.out.println(cidades);
			}
		} else {
			System.out.println("\nRelação das cidades que não são capitais estaduais: \n");
			for (CidadesPojo cidades : cidadesBD) {
				System.out.println(cidades);
			}
		}

	}

	public static void listarCidadesCadastradas() {

		CidadesDao cidadedao = new CidadesDao();
		List<CidadesPojo> cidadesBD = cidadedao.listaCidades();

		System.out.println("\nRelação de todas as cidades cadastradas: \n");
		for (CidadesPojo cidades : cidadesBD) {
			System.out.println(cidades);
		}
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

				excluirCidade(sc);

				break;

			case "3":

				break;

			case "4":

				consultarCidadePeloCep(sc);

				break;

			case "5":

				consultarCidadesIniciadasComMesmaLetra(sc);

				break;

			case "6":

				listarCidadesPeloEstado(sc);

				break;

			case "7":

				contaCidadesDoMesmoEstado(sc);

				break;

			case "8":

				listarCidadesCapitaisOuNao(sc);

				break;

			case "9":

				listarCidadesCadastradas();

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
