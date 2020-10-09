package br.com.zup.DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import br.com.zup.POJO.CidadesPojo;
import br.com.zup.conectionfactory.ConnectionFactory;

public class CidadesDao {

	private Connection conn;

	public CidadesDao() {
		this.conn = new ConnectionFactory().getConnection();
	}

	public void insereCidade(CidadesPojo cidade) {
		try {
			String sql = "insert into cidade"
					+ "(nome, CEP, numero_habitantes, capital, estado, renda_per_capta, data_de_fundacao)"
					+ "values (?, ?, ?, ?, ?, ?, ?)";

			PreparedStatement stmt = this.conn.prepareStatement(sql);

			stmt.setString(1, cidade.getNome());
			stmt.setString(2, cidade.getCep());
			stmt.setInt(3, cidade.getNumeroHabitantes());
			stmt.setInt(4, cidade.getCapital());
			stmt.setString(5, cidade.getEstado());
			stmt.setFloat(6, cidade.getRendaPerCapita());
			stmt.setString(7, cidade.getDataDeFundacao());

			stmt.execute();
			stmt.close();

			System.out.println("\nCidade cadastrada com sucesso!\n");

		} catch (SQLException e) {
			System.out.println(
					"\nNão foi possível cadastrar a cidade.\nVerifique se as informações foram inseridas corretamente.\n");
			System.out.println(e.getMessage());
		}
	}
	
	public List<CidadesPojo> listaCidades(){
		
		List<CidadesPojo> cidades = new ArrayList<CidadesPojo>();
		
		String sqlConsulta = "select * from cidade";
		
		try {
			PreparedStatement stmt = conn.prepareStatement(sqlConsulta);
			
			ResultSet rs = stmt.executeQuery();
			
			while (rs.next()) {
				CidadesPojo cidade = new CidadesPojo();
				
				cidade.setNome(rs.getString("nome"));
				cidade.setCep(rs.getString("CEP"));
				cidade.setNumeroHabitantes(rs.getInt("numero_habitantes"));
				cidade.setCapital(rs.getInt("capital"));
				cidade.setEstado(rs.getString("estado"));
				cidade.setRendaPerCapita(rs.getFloat("renda_per_capta"));
				cidade.setDataDeFundacao(rs.getString("data_de_fundacao"));
				
				cidades.add(cidade);
			}
			
			
		} catch (SQLException e) {
			System.out.println("\nOcorreu um erro ao executar a consulta. Tente novamente.\n");
			System.out.println(e.getMessage());
		}
		return cidades;
	}
	
	public List<CidadesPojo> listaCidadePorCEP(String cep){
		
		List<CidadesPojo> cidades = new ArrayList<CidadesPojo>();
		
		String sqlConsulta = "select * from cidade where cep = ?";
		
		try {
			PreparedStatement stmt = conn.prepareStatement(sqlConsulta);
			
			stmt.setString(1, cep);
			
			ResultSet rs = stmt.executeQuery();
			
			while (rs.next()) {
				CidadesPojo cidade = new CidadesPojo();
				
				cidade.setNome(rs.getString("nome"));
				cidade.setCep(rs.getString("CEP"));
				cidade.setNumeroHabitantes(rs.getInt("numero_habitantes"));
				cidade.setCapital(rs.getInt("capital"));
				cidade.setEstado(rs.getString("estado"));
				cidade.setRendaPerCapita(rs.getFloat("renda_per_capta"));
				cidade.setDataDeFundacao(rs.getString("data_de_fundacao"));
				
				cidades.add(cidade);
			}
		} catch (SQLException e) {
			System.out.println("\nErro ao efetuar a consulta!\n");
			System.out.println(e.getMessage());;
		}
		return cidades;
	}

	public void deletaCidade(String cep){

		try {
			
			String sql = "delete from cidade where cep = ?";

			PreparedStatement stmt = this.conn.prepareStatement(sql);
			
			stmt.setString(1, cep);

			stmt.execute();
			stmt.close();

			System.out.println("\nCidade excluída com sucesso!\n");

		} catch (SQLException e) {
			System.out.println("\nNão foi possível deletar a cidade. Verifique se o CEP informado está correto.");
			System.out.println(e.getMessage());
		}
	}
}
