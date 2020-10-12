package br.com.zup.cidadestrelas;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.Scanner;
import java.util.regex.Pattern;

import br.com.zup.DAO.CidadesDao;
import br.com.zup.POJO.CidadesPojo;
import br.com.zup.Regex.Regex;
import br.com.zup.conectionfactory.ConnectionFactory;

public class ProgramaPrincipalCidades {

	public static void cabecalho() {
		System.out.println("------------------------------------------------------------");
		System.out.println("                   CIDADES DO BRASIL                        ");
		System.out.println("------------------------------------------------------------\n");
	}

	public static void menuPrincipal(Scanner sc) {
		System.out.println("\n(1) - Incluir cidade");
		System.out.println("(2) - Excluir cidade");
		System.out.println("(3) - Consultar cidade pelo CEP");
		System.out.println("(4) - Consultar cidade iniciada pela mesma letra");
		System.out.println("(5) - Listar cidades pelo estado");
		System.out.println("(6) - Contar cidades do mesmo estado");
		System.out.println("(7) - Listar cidades capitais estaduais ou não");
		System.out.println("(8) - Listar cidades cadastradas");
		System.out.println("(0) - Sair do programa\n");

	}

	public static void incluirCidade(Regex regex, CidadesDao cidadedao, Scanner sc) {

		int capital = 0;
		int numeroHabitantes = 0;

		sc.nextLine();
		System.out.print("\nInforme o nome da cidade: ");
		String nomeCidade = sc.nextLine();

		regex.validaNomeCidade(sc, nomeCidade);

		System.out.print("\nInforme o CEP (ex.: 39867-947): ");
		String cep = sc.next();

		regex.validaCep(sc, cep);
		
		System.out.print("\nInforme quantos habitantes tem a cidade: ");
		String numHabitantes = sc.next();
		
		regex.validaNumeroHabitantes(sc, numHabitantes);
		
		if (Pattern.matches(regex.getNumeroDeHabitantes(), numHabitantes)) {
			numeroHabitantes = Integer.valueOf(numHabitantes);
		}

		System.out.print("\nDigite 1 se a cidade for uma capital ou 0 caso não seja uma capital: ");
		String capitalInterior = sc.next();

		regex.validaCapitalInterior(sc, capitalInterior);

		if (capitalInterior.equals("1") || capitalInterior.equals("0")) {
			capital = Integer.valueOf(capitalInterior);
		}

		sc.nextLine();
		System.out.print("\nA qual a sigla do estado que pertence essa cidade (ex.: SP)? ");
		String siglaEstado = sc.nextLine().toUpperCase();

		regex.validaSiglaEstado(sc, siglaEstado);

		System.out.print("\nInforme a renda per capita da cidade: ");
		float rendaPerCapita = sc.nextFloat();

		sc.nextLine();
		System.out.print("\nQual a data de fundação da cidade (ex.: AAAA-MM-DD): ");
		String dataFundacao = sc.next();

		regex.validaDataFundacao(sc, dataFundacao);

		CidadesPojo cidade = new CidadesPojo(nomeCidade, cep, numeroHabitantes, capital, siglaEstado, rendaPerCapita,
				dataFundacao);
		
		cidadedao.insereCidade(cidade);

	}

	public static void excluirCidade(Regex regex, CidadesDao cidadedao, Scanner sc) throws SQLException {

		System.out.print("\nInforme o CEP da cidade que deseja excluir: ");
		String cep = sc.next();

		regex.validaCep(sc, cep);

		cidadedao.deletaCidade(cep);

	}

	public static void consultarCidadePeloCep(Regex regex, CidadesDao cidadedao, Scanner sc) {

		System.out.print("\nInforme o CEP que deseja consultar (ex.: 39867-947): ");
		String cep = sc.next();

		regex.validaCep(sc, cep);

		List<CidadesPojo> cidadesBD = cidadedao.listaCidadePorCEP(cep);

		System.out.printf("\nDados de endereço do CEP %s: \n\n", cep);
		for (CidadesPojo cidades : cidadesBD) {
			System.out.println(cidades);
		}

	}

	public static void consultarCidadesIniciadasComMesmaLetra(Regex regex, CidadesDao cidadedao, Scanner sc) {

		System.out.print("\nDigite a letra pela qual o nome das cidades deve começar: ");
		String letra = sc.next().toUpperCase();

		regex.validaPrimeiraLetraCidade(sc, letra);

		List<CidadesPojo> cidadesBD = cidadedao.listaCidadesComMesmoInicio(letra);

		System.out.printf("\nLista das cidades cadastradas inciciadas com a letra %s:\n\n", letra);
		for (CidadesPojo cidades : cidadesBD) {
			System.out.println(cidades);
		}
	}

	public static void listarCidadesPeloEstado(Regex regex, CidadesDao cidadedao, Scanner sc) {

		System.out.print("\nDigite a sigla do estado: ");
		String sigla = sc.next().toUpperCase();

		regex.validaSiglaEstado(sc, sigla);

		List<CidadesPojo> cidadesBD = cidadedao.listaCidadesPelaSiglaEstado(sigla);

		System.out.printf("\nLista das cidades cadastradas pertencentes ao Estado de %s:\n\n", sigla);
		for (CidadesPojo cidades : cidadesBD) {
			System.out.println(cidades);
		}
	}

	public static void contaCidadesDoMesmoEstado(Regex regex, CidadesDao cidadedao, Scanner sc) {

		System.out.print("\nInforme o estado: ");
		String siglaEstado = sc.next().toUpperCase();

		regex.validaSiglaEstado(sc, siglaEstado);

		System.out.printf("\nQuantidade de cidades no estado de %s: ", siglaEstado);
		System.out.println(cidadedao.contaQuantidadeDeCidadesDoEstado(siglaEstado));
	}

	public static void listarCidadesCapitaisOuNao(Regex regex, CidadesDao cidadedao, Scanner sc) {

		int capital = 0;
		System.out.print("\nDigite 1 para listar as cidades que são capitais ou 0 para as que não são: ");
		String capitalInterior = sc.next();

		regex.validaCapitalInterior(sc, capitalInterior);

		if (capitalInterior.equals("1") || capitalInterior.equals("0")) {
			capital = Integer.valueOf(capitalInterior);
		}

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
		Regex regex = new Regex();
		CidadesDao cidadedao = new CidadesDao();
		Scanner sc = new Scanner(System.in);
		
		String opcao = "";

		cabecalho();

		do {

			menuPrincipal(sc);
			System.out.print("Escolha uma das opções acima: ");
			opcao = sc.next();

			switch (opcao) {
			case "1":

				incluirCidade(regex, cidadedao, sc);

				break;

			case "2":

				excluirCidade(regex, cidadedao, sc);

				break;

			case "3":

				consultarCidadePeloCep(regex, cidadedao, sc);

				break;

			case "4":

				consultarCidadesIniciadasComMesmaLetra(regex, cidadedao, sc);

				break;

			case "5":

				listarCidadesPeloEstado(regex, cidadedao, sc);

				break;

			case "6":

				contaCidadesDoMesmoEstado(regex, cidadedao, sc);

				break;

			case "7":

				listarCidadesCapitaisOuNao(regex, cidadedao, sc);

				break;

			case "8":

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
