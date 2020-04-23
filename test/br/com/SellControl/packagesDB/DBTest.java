package br.com.SellControl.packagesDB;

import static org.junit.Assert.assertEquals;

import java.util.Properties;

import org.junit.jupiter.api.Test;

import br.com.SellControl.db.DB;

class DBTest {

	// Exceptions will not be tested.
	
	
	@Test
	void testDbReadProperties() {
		Properties x = DB.readProperties();
		String url = x.getProperty("dburl");
		assertEquals(url, "jdbc:mysql://localhost:3306/bdsell");
		
	}
	
	


}
