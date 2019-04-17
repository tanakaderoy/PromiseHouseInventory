package org.team3.PHI;
import java.sql.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;

import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

public class MainPHI {

	public static void main(String[] args) {
		
		try {
			com.jtattoo.plaf.acryl.AcrylLookAndFeel.setTheme("Red");
            // Set System Look&Feel
			UIManager.setLookAndFeel("com.jtattoo.plaf.acryl.AcrylLookAndFeel");
    } 
    catch (UnsupportedLookAndFeelException e) {
       // handle exception
    }
    catch (ClassNotFoundException e) {
       // handle exception
    }
    catch (InstantiationException e) {
       // handle exception
    }
    catch (IllegalAccessException e) {
       // handle exception
    }

     //Create and show the GUI.

		// TODO Auto-generated method stub
		WindowMain.MainWindow();
		System.out.println("HeyFellas!");
		Connection con = null;
		Statement stmt = null;
		try {
			Class.forName("org.sqlite.JDBC");
			con = DriverManager.getConnection("jdbc:sqlite:test.db");
			System.out.println("Opened database sucessfully");
			stmt = con.createStatement();
			String sql = "-- TABLE\r\n" + 
					
					"CREATE TABLE IF NOT EXISTS HISTORY (historyID INTEGER PRIMARY KEY AUTOINCREMENT,CHANGE varchar(10), Id  INTEGER,UPC varchar(50)    NOT NULL,PRODUCT_NAME    TEXT    NOT NULL,PRICE      decimal(8,2) NOT NULL,WEIGHT          INT     NOT NULL,QUANTITY         INT    NOT NULL,DONOR           TEXT    NOT NULL,CATEGORY        TEXT    NOT NULL,DATE            INT    NOT NULL);\r\n" + 
					"CREATE TABLE IF NOT EXISTS INVENTORY(Id  INTEGER PRIMARY KEY AUTOINCREMENT,UPC varchar(50)    NOT NULL,PRODUCT_NAME    TEXT    NOT NULL,PRICE      decimal(8,2) NOT NULL,WEIGHT          INT     NOT NULL,QUANTITY         INT    NOT NULL,DONOR           TEXT    NOT NULL,CATEGORY        TEXT    NOT NULL,DATE            INT    NOT NULL);\r\n" + 
					"\r\n" + 
					" \r\n" + 
					"-- INDEX\r\n" + 
					" \r\n" + 
					"-- TRIGGER\r\n" + 
					
					"CREATE TRIGGER IF NOT EXISTS DELETE_LOG AFTER DELETE \r\n" + 
					"ON INVENTORY\r\n" + 
					"BEGIN\r\n" + 
					"   INSERT INTO HISTORY(Id,CHANGE,UPC,PRODUCT_NAME,PRICE,WEIGHT,QUANTITY,DONOR ,CATEGORY ,`DATE`) VALUES (OLD.Id,'DELETE',OLD.UPC,OLD.PRODUCT_NAME,OLD.PRICE,OLD.WEIGHT,-1,OLD.DONOR,OLD.CATEGORY, datetime('now'));\r\n" + 
					"\r\n" + 
					"END;\r\n" + 
					"CREATE TRIGGER IF NOT EXISTS INSERT_LOG AFTER INSERT \r\n" + 
					"ON INVENTORY\r\n" + 
					"BEGIN\r\n" + 
					"   INSERT INTO HISTORY(Id,CHANGE,UPC,PRODUCT_NAME,PRICE,WEIGHT,QUANTITY,DONOR ,CATEGORY ,`DATE`) VALUES (new.Id,'INSERT',new.UPC,NEW.PRODUCT_NAME,new.PRICE,NEW.WEIGHT,new.QUANTITY,new.DONOR,NEW.CATEGORY, date((julianday('NOW'))));\r\n" + 
					"END;\r\n" ;
			System.out.println(sql);
			stmt.executeUpdate(sql);
			stmt.close();
			con.close();
		}catch(Exception e) {
			System.err.println(e.getClass().getName() + ": " + e.getMessage());
			System.out.println(e);
		}
		
		
		try {


			
			Class.forName("org.sqlite.JDBC");
			con = DriverManager.getConnection("jdbc:sqlite:test.db");
			con.setAutoCommit(false);
			System.out.println("Opened Database Sucessfuly");

			stmt = con.createStatement();
			String sql;

			
				sql = "drop view if exists INVENTORYVIEW;"
						+ " CREATE VIEW IF NOT EXISTS INVENTORYVIEW \r\n" + 
						"AS \r\n" + 
						"SELECT\r\n" + 
						" Id,UPC,PRODUCT_NAME,\r\n" + 
						"PRICE,SUM(QUANTITY) AS QUANTITY,\r\n" + 
						"WEIGHT, DONOR,CATEGORY,`DATE` \r\n" + 
						"FROM HISTORY;";
			System.out.println(sql);
			stmt.executeUpdate(sql);
			stmt.close();
			con.commit();
			con.close();
		
		System.out.println("Table created successfully");
		
	}catch(Exception e) {
		System.err.println(e.getClass().getName() + ": " + e.getMessage());
		System.out.println(e);
	}

}
}



