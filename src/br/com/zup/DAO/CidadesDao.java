package br.com.zup.DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import br.com.zup.POJO.CidadesPojo;
import br.com.zup.conectionfactory.ConnectionFactory;

public class CidadesDao {
	
	private Connection conn;
	
	public CidadesDao() {
		this.conn = new ConnectionFactory().getConnection();
	}

	public boolean insereCidade(CidadesPojo cidade) throws SQLException {
		
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
		return true;
		// tratar exceções
		}

}
