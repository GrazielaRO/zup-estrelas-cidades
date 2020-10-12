package br.com.zup.Regex;

import java.text.Normalizer;
import java.util.Scanner;
import java.util.regex.Pattern;

public class Regex {

	private String nomeCidade;
	private String cep;
	private String siglaEstado;
	private String data;
	private String primeiraLetraCidade;
	private String numeroDeHabitantes;

	public Regex() {
		
		this.nomeCidade = "[a-zA-Z ]+";
		this.cep = "\\d{5}-\\d{3}";
		this.siglaEstado = "[a-zA-Z]{2}+";
		this.data = "(1|2)[0-9]{3}-[0-9][0-9]-[0-9][0-9]";
		this.primeiraLetraCidade = "[a-zA-Z]{1}+";
		this.numeroDeHabitantes = "[0-9]+";
	}
	
	public static String retiraAcento(String str) {
		String nfdNormalizedString = Normalizer.normalize(str, Normalizer.Form.NFD);
		Pattern pattern = Pattern.compile("\\p{InCombiningDiacriticalMarks}+");
		return pattern.matcher(nfdNormalizedString).replaceAll("");
	}
	
	public String validaNomeCidade (Scanner sc, String nomeCidade) {
		while (!Pattern.matches(this.nomeCidade, nomeCidade)) {
			System.out.print(
					"\nO nome da cidade deve conter apenas letras do alfabeto.\n\nDigite novamente o nome da cidade: ");
			nomeCidade = retiraAcento(sc.nextLine());
		}
		return nomeCidade;
	}

	public String validaCep (Scanner sc, String cep) {
		while (!Pattern.matches(this.cep, cep)) {
			System.out.print("\nCEP incorreto!\n\nTente novamente (ex.: 39867-947): ");
			cep = sc.next();
		}
		return cep;
	}
	
	public String validaCapitalInterior (Scanner sc, String capitalInterior) {
		while (!capitalInterior.equals("1") && !capitalInterior.equals("0")) {
			System.out.print("\nOpção incorreta. Você precisa digitar 1 se a cidade for uma capital ou 0 se não for."
					+ "\n\nDigite novamente uma das opções válidas: ");
			capitalInterior = sc.next();
		}
		return capitalInterior;
	}
	
	public String validaSiglaEstado (Scanner sc, String sigla) {
		while (!Pattern.matches(this.siglaEstado, sigla)) {
			System.out.print(
					"\nVocê precisa informar a  sigla do estado com apenas 2 letras e com letras do alfabeto."
					+ "\n\nDigite novamente a sigla do estado: ");
			sigla = sc.next().toUpperCase();
		}
		return sigla;
	}
	
	public String validaDataFundacao (Scanner sc, String dataFundacao) {
		while (!Pattern.matches(this.data, dataFundacao)) {
			System.out.print("\nData incorreta!\n\nDigite a data no padrão correto (ex.: AAAA-MM-DD): ");
			dataFundacao = sc.next();
		}
		return dataFundacao;
	}
	
	public String validaPrimeiraLetraCidade (Scanner sc, String letra) {
		while (!Pattern.matches(this.primeiraLetraCidade, letra)) {
			System.out.print(
					"\nVocê precisa informar apenas uma letra e que pertença ao alfabeto.\n\nDigite uma letra inicial para o nome das cidades: ");
			letra = sc.next().toUpperCase();
		}
		return letra;
	}
	
	public String validaNumeroHabitantes (Scanner sc, String numHabitantes) {
		while (!Pattern.matches(this.numeroDeHabitantes, numHabitantes)) {
			System.out.print("\nO número de habitantes deve ser um número inteiro, não é válido letras ou caracteres especiais."
					+ "\nDigite novamente o número de habitantes: ");
			numHabitantes = sc.next();
		}
		return numHabitantes;
	}
	public String getNumeroDeHabitantes() {
		return numeroDeHabitantes;
	}

	public String getSiglaEstado() {
		return siglaEstado;
	}
	
	
}
