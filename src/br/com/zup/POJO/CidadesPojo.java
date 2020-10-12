package br.com.zup.POJO;

import java.sql.Date;

public class CidadesPojo {
	
	private String nome;
	private String cep;
	private int numeroHabitantes;
	private int capital;
	private String estado;
	private float rendaPerCapita;
	private String dataDeFundacao;
	
	public CidadesPojo (String nome, String cep, int numeroHabitantes, int capital, String estado, float rendaPerCapita,
			String dataDeFundacao) {
		this.nome = nome;
		this.cep = cep;
		this.numeroHabitantes = numeroHabitantes;
		this.capital = capital;
		this.estado = estado;
		this.rendaPerCapita = rendaPerCapita;
		this.dataDeFundacao = dataDeFundacao;
	}
	
	public CidadesPojo() {
		
	}
	
	public String toString() {
		return "Nome = " + nome + "| CEP = " + cep + "| Numero de habitantes = " + numeroHabitantes + "| Capital = "
				+ capital + "| Estado = " + estado + "| Renda per capta = " + rendaPerCapita + "| Data de fundacao = "
				+ dataDeFundacao + "";
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getCep() {
		return cep;
	}

	public void setCep(String cep) {
		this.cep = cep;
	}

	public int getNumeroHabitantes() {
		return numeroHabitantes;
	}

	public void setNumeroHabitantes(int numeroHabitantes) {
		this.numeroHabitantes = numeroHabitantes;
	}

	public int getCapital() {
		return capital;
	}

	public void setCapital(int capital) {
		this.capital = capital;
	}

	public String getEstado() {
		return estado;
	}

	public void setEstado(String estado) {
		this.estado = estado;
	}

	public float getRendaPerCapita() {
		return rendaPerCapita;
	}

	public void setRendaPerCapita(float rendaPerCapita) {
		this.rendaPerCapita = rendaPerCapita;
	}

	public String getDataDeFundacao() {
		return dataDeFundacao;
	}

	public void setDataDeFundacao(String dataDeFundacao) {
		this.dataDeFundacao = dataDeFundacao;
	}
	
	
	
}
