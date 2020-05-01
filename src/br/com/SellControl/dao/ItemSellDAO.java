package br.com.SellControl.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import br.com.SellControl.db.DB;
import br.com.SellControl.model.entities.ItemSell;
import br.com.SellControl.model.exception.DbException;

public class ItemSellDAO {

	private Connection conn = null;

	public ItemSellDAO(Connection conn) {
		this.conn = conn;
	}

	public void insert(ItemSell ItemSell) {

		PreparedStatement ps = null;

		try {
			String query = "insert into tb_itensell (sell_id,product_id,qtd,subtotal) values (?,?,?,?)";

			ps = conn.prepareStatement(query);

			ps.setInt(1, ItemSell.getSell().getId());
			ps.setInt(2, ItemSell.getProduct().getId());
			ps.setInt(3, ItemSell.getQuantity());
			ps.setDouble(4, ItemSell.getSubtotal());

			int rows = ps.executeUpdate();

			if (rows <= 0)
				throw new DbException("Error, no rows affected!");

		} catch (SQLException e) {
			throw new DbException(e.getMessage());

		} finally {
			DB.closePreparedStatement(ps);
		}

	}

}
