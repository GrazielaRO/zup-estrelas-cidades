package br.com.zup.Regex;

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
		this.siglaEstado = "[a-zA-Z]+";
		this.data = "(1|2)[0-9]{3}-[0-9][0-9]-[0-9][0-9]";
		this.primeiraLetraCidade = "[a-zA-Z]+";
		this.numeroDeHabitantes = "[0-9]+";
	}
	
	public void validaNomeCidade (Scanner sc, String nomeCidade) {
		while (!Pattern.matches(this.nomeCidade, nomeCidade)) {
			System.out.print(
					"\nO nome da cidade deve conter apenas letras do alfabeto.\n\nDigite novamente o nome da cidade: ");
			nomeCidade = sc.nextLine();
		}
	}

	public void validaCep (Scanner sc, String cep) {
		while (!Pattern.matches(this.cep, cep)) {
			System.out.print("\nCEP incorreto!\n\nTente novamente (ex.: 39867-947): ");
			cep = sc.next();
		}
	}
	
	public void validaCapitalInterior (Scanner sc, String capitalInterior) {
		while (!capitalInterior.equals("1") && !capitalInterior.equals("0")) {
			System.out.print("\nOpção incorreta. Você precisa digitar 1 se a cidade for uma capital ou 0 se não for."
					+ "\n\nDigite novamente uma das opções válidas: ");
			capitalInterior = sc.next();
		}
	}
	
	public void validaSiglaEstado (Scanner sc, String sigla) {
		while (!Pattern.matches(this.siglaEstado, sigla) || sigla.length() > 2) {
			System.out.print(
					"\nVocê precisa informar a  sigla do estado com apenas 2 letras e com letras do alfabeto."
					+ "\n\nDigite novamente a que estado pertence a cidade: ");
			sigla = sc.nextLine().toUpperCase();
		}
	}
	
	public void validaDataFundacao (Scanner sc, String dataFundacao) {
		while (!Pattern.matches(this.data, dataFundacao)) {
			System.out.print("\nData incorreta!\n\nDigite a data no padrão correto (ex.: AAAA-MM-DD): ");
			dataFundacao = sc.next();
		}
	}
	
	public void validaPrimeiraLetraCidade (Scanner sc, String letra) {
		while (!Pattern.matches(this.primeiraLetraCidade, letra) || letra.length() > 1) {
			System.out.print(
					"\nVocê precisa informar a  sigla do estado.\n\nDigite novamente a que estado pertence a cidade: ");
			letra = sc.nextLine().toUpperCase();
		}
	}
	
	public void validaNumeroHabitantes (Scanner sc, String numHabitantes) {
		while (!Pattern.matches(this.numeroDeHabitantes, numHabitantes)) {
			System.out.print("\nO número de habitantes deve ser um número inteiro, não é válido letras ou caracteres especiais."
					+ "\nDigite novamente o número de habitantes: ");
			numHabitantes = sc.next();
		}
	}
	public String getNumeroDeHabitantes() {
		return numeroDeHabitantes;
	}
}
