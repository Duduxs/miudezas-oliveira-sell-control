package br.com.SellControl.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import br.com.SellControl.db.DB;
import br.com.SellControl.model.entities.ItemSell;
import br.com.SellControl.model.entities.Product;
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

	// List all items from sell per id (sell).
	public List<ItemSell> selectAllItensById(int sell_id) {
		PreparedStatement ps = null;
		ResultSet rs = null;

		List<ItemSell> list = new ArrayList<>();
		StringBuilder query = new StringBuilder();

		try {
			query.append("select i.id, p.description, i.qtd, p.price, i.subtotal");
			query.append(" from tb_itensell as i inner join tb_product as p");
			query.append(" on (i.product_id = p.id)");
			query.append(" where i.sell_id = ?");

			ps = conn.prepareStatement(query.toString());
			ps.setInt(1, sell_id);
			rs = ps.executeQuery();

			while (rs.next()) {
				Product p = new Product();
				ItemSell i = new ItemSell();

				i.setId(rs.getInt("i.id"));
				p.setDescription(rs.getString("p.description"));
				i.setQuantity(rs.getInt("i.qtd"));
				p.setPrice(rs.getDouble("p.price"));
				i.setSubtotal(rs.getDouble("i.subtotal"));
				i.setProduct(p);

				list.add(i);
			}

		} catch (SQLException e) {
			throw new DbException(e.getMessage());
		} finally {
			DB.closePreparedStatement(ps);
			DB.closeResultSet(rs);
		}
		return list;

	}

}
