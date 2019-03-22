package org.team3.tests;

import static org.junit.Assert.*;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

import org.junit.Before;
import org.junit.Test;

public class MainPHITest {
	
	Connection con = null;
	Statement stmt = null;
	String sql = null;

    @Before
    public void before() {
    	try {
			Class.forName("org.sqlite.JDBC");
			con = DriverManager.getConnection("jdbc:sqlite:test.db");
			System.out.println("Opened database sucessfully");
			stmt = con.createStatement();
			 sql = "CREATE TABLE INVENTORY" +
							"(UPC INT PRIMARY KEY    NOT NULL," +
							"PRODUCT_NAME    TEXT    NOT NULL," +
							"PRICE      decimal(8,2) NOT NULL," +
							"QUANTITY         INT    NOT NULL," +
							"CATEGORY        TEXT    NOT NULL," +
							"DATE            INT    NOT NULL)" ;
			stmt.executeUpdate(sql);
			
		}catch(Exception e) {
			System.err.println(e.getClass().getName() + ": " + e.getMessage());
			System.out.println(e);
		}
    }



	@Test
	public void sqlEqual() {
		String expected = "CREATE TABLE INVENTORY" +
				"(UPC INT PRIMARY KEY    NOT NULL," +
				"PRODUCT_NAME    TEXT    NOT NULL," +
				"PRICE      decimal(8,2) NOT NULL," +
				"QUANTITY         INT    NOT NULL," +
				"CATEGORY        TEXT    NOT NULL," +
				"DATE            INT    NOT NULL)";
		
		
		assertEquals("Should equal to "+expected, expected, sql);
		
		
	}
	@Test
	public void connectionNotNUll() {
		assertNotNull("Connection Shouldnt be Null", con);
	}
	
	
	@Test
	public void statementNotNull() {
		assertNotNull(""+stmt.toString(),stmt);
	}
	@Test
	public void connectionIsClosed() {
		try {
			con.close();
			assertTrue("Connection should be closed",con.isClosed());
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	

}
