package org.team3.PHI;
import java.sql.*;

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
		WindowMain2.MainWindow();
		System.out.println("HeyFellas!");
		Connection con = null;
		Statement stmt = null;
		try {
			Class.forName("org.sqlite.JDBC");
			con = DriverManager.getConnection("jdbc:sqlite:test.db");
			System.out.println("Opened database sucessfully");
			stmt = con.createStatement();
			String sql = "CREATE TABLE INVENTORY" +
							"(UPC varchar(50) PRIMARY KEY    NOT NULL," +
							"PRODUCT_NAME    TEXT    NOT NULL," +
							"PRICE      decimal(8,2) NOT NULL," +
							"WEIGHT          INT     NOT NULL,"+
							"QUANTITY         INT    NOT NULL," +
							"DONOR           TEXT    NOT NULL,"+
							"CATEGORY        TEXT    NOT NULL," +
							"DATE            INT    NOT NULL)" ;
			stmt.executeUpdate(sql);
			stmt.close();
			con.close();
		}catch(Exception e) {
			System.err.println(e.getClass().getName() + ": " + e.getMessage());
			System.out.println(e);
		}
		System.out.println("Table created successfully");
		
	}

}



