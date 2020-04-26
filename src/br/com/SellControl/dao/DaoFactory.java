package br.com.SellControl.dao;

import br.com.SellControl.db.DB;

public class DaoFactory {
	// This class is for create DAOS with more facility.
	
	//Here i return a clientDAO after opening a connection with at the DB.
	public static ClientDAO createClientDAO() {
		return new ClientDAO(DB.openConnection());
	}
}
