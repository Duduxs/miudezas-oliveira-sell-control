package br.com.SellControl.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import br.com.SellControl.db.DB;
import br.com.SellControl.model.entities.Sell;
import br.com.SellControl.model.exception.DbException;

public class SellDAO {

	private Connection conn = null;

	public SellDAO(Connection conn) {
		this.conn = conn;
	}

	public void insert(Sell sell) {

		PreparedStatement ps = null;

		try {
			String query = "insert into tb_sell (client_id,sell_date,sell_total,comments) values (?,?,?,?)";

			ps = conn.prepareStatement(query);

			ps.setInt(1, sell.getClient().getId());
			ps.setString(2, sell.getDateSell());
			ps.setDouble(3, sell.getTotalSell());
			ps.setString(4, sell.getObs());

			int rows = ps.executeUpdate();

			if (rows <= 0)
				throw new DbException("Error, no rows affected!");

		} catch (SQLException e) {
			throw new DbException(e.getMessage());

		} finally {
			DB.closePreparedStatement(ps);
		}

	}

	// Return the ID last sell
	public int selectLastSell() {
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {

			int idsell = 0;

			String query = "select max(id) id from tb_sell";
			ps = conn.prepareStatement(query);
			rs = ps.executeQuery();

			if (rs.next()) {
				Sell s = new Sell();
				s.setId(rs.getInt("id"));
				idsell = s.getId();
			}
			return idsell;

		} catch (SQLException e) {
			throw new DbException(e.getMessage());
		} finally {
			DB.closePreparedStatement(ps);
		}
	}
}
