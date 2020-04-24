package br.com.SellControl.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import br.com.SellControl.db.DB;
import br.com.SellControl.db.DbException;
import br.com.SellControl.model.entities.Client;

public class ClientDAO {

	private Connection conn = null;

	public ClientDAO(Connection conn) {
		this.conn = conn;
	}

	public void insert(Client client) {

		PreparedStatement ps = null;
		
		try {
			StringBuilder query = new StringBuilder();
			query.append(
					"insert into tb_client (name,cpf,email,phone,cellphone,cep,address,number,complement,neighborhood,city,state)");
			query.append("values (?,?,?,?,?,?,?,?,?,?,?,?)");
			
			 ps = conn.prepareStatement(query.toString());
			
			makeClient(ps,client);
			int rows = ps.executeUpdate();
			
			if(rows > 0) {
				System.out.println("\n" + rows + " rows affected!");
			}else {
				throw new DbException("Error, no rows affected!");
			}
		
		} catch (SQLException e) {
			throw new DbException(e.getMessage());
		} finally {
			DB.closePreparedStatement(ps);
		}

	}

	public Client makeClient(PreparedStatement ps, Client client) throws SQLException {

		ps.setString(1, client.getName());
		ps.setString(2, client.getCpf());
		ps.setString(3, client.getEmail());
		ps.setString(4, client.getPhone());
		ps.setString(5, client.getCellphone());
		ps.setString(6, client.getCep());
		ps.setString(7, client.getAddress());
		ps.setInt(8, client.getNumber());
		ps.setString(9, client.getComplement());
		ps.setString(10, client.getNeighborhood());
		ps.setString(11, client.getCity());
		ps.setString(12, client.getState());
		return client;

	}

}