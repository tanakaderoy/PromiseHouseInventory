import java.sql.*;

public class MainPHI {

	public static void main(String[] args) {
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
			String sql = "CREATE TABLE INVENTORY" +
							"(UPC INT PRIMARY KEY    NOT NULL," +
							"PRODUCT_NAME    TEXT    NOT NULL," +
							"QUANTITY         INT    NOT NULL," +
							"CATEGORY        TEXT    NOT NULL)";
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
