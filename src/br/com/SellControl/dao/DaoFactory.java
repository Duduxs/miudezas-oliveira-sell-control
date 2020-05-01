package br.com.SellControl.dao;

import br.com.SellControl.db.DB;

public class DaoFactory {
	// This class is for create DAOS with more facility.

	// Here i return a clientDAO after opening a connection with at the DB.
	public static ClientDAO createClientDAO() {
		return new ClientDAO(DB.openConnection());
	}

	// Here i return a employeeDAO after opening a connection with at the DB.
	public static EmployeeDAO createEmployeeDAO() {
		return new EmployeeDAO(DB.openConnection());
	}

	// Here i return a providerDAO after opening a connection with at the DB.
	public static ProviderDAO createProviderDAO() {
		return new ProviderDAO(DB.openConnection());
	}

	// Here i return a productDAO after opening a connection with at the DB.
	public static ProductDAO createProductDAO() {
		return new ProductDAO(DB.openConnection());
	}
	
	// Here i return a SellDAO after opening a connection with at the DB.
	public static SellDAO createSellDAO() {
		return new SellDAO(DB.openConnection());
	}
}
