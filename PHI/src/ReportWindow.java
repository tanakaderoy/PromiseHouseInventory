import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Vector;
import java.awt.event.ActionEvent;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;
import javax.swing.JScrollPane;

public class ReportWindow {

	private JFrame frame;
	private JTable tableDisplayItem;
	private JTextField txtSearch;
	private JScrollPane scrollPane;
	
	public ArrayList<Item> itemList() {
		ArrayList<Item> itemsList = new ArrayList<>();
		try {
			Class.forName("org.sqlite.JDBC");
			Connection con = DriverManager.getConnection("jdbc:sqlite:test.db");
			String query1 = "SELECT * FROM INVENTORY";
			Statement st = con.createStatement();
			ResultSet rs = st.executeQuery(query1);
			
			Item item;
			while(rs.next()) {
				item = new Item(rs.getInt("UPC"), rs.getString("PRODUCT_NAME"),rs.getDouble("PRICE"), rs.getInt("QUANTITY"), rs.getString("CATEGORY"), rs.getString("DATE"));
				
				itemsList.add(item);
			}
		} catch(Exception e) {
			JOptionPane.showMessageDialog(null, e);
		}
		return itemsList;
			
	}
	
	public void showItem() throws Exception {
		ArrayList<Item> list = itemList();
		DefaultTableModel model = (DefaultTableModel)tableDisplayItem.getModel();
		
		model.addColumn("UPC");
		model.addColumn("PRODUCT NAME");
		model.addColumn("Price");
		model.addColumn("QUANTITY");
		model.addColumn("Category");
		model.addColumn("DATE");
		Vector<Object> row;
		for(int i=0; i<list.size();i++) {
			row = new Vector<Object>();
			row.add(list.get(i).getUPC());
			row.add(list.get(i).getProductName());
			row.add(list.get(i).getPrice());
			row.add(list.get(i).getQuantinty());
			row.add(list.get(i).getCategory());
			row.add(list.get(i).getdate());
			
			//row[3] = list.get(i).getPrice();
			model.addRow(row);
		}
		TableToExcel tte = new TableToExcel(tableDisplayItem, null, "My Table");
		//optional -> tte.setCustomTitles(colTitles);
		File myFile = new File("test.xls");
		tte.generate(myFile);
	}

	/**
	 * Launch the application.
	 */
	public static void reportWindow() {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					ReportWindow window = new ReportWindow();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 * @throws Exception 
	 */
	public ReportWindow() throws Exception {
		initialize();
		showItem();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 1018, 586);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		JButton btnMainMenu = new JButton("Main Menu");
		btnMainMenu.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				WindowMain.MainWindow();
				frame.setVisible(false);
			}
		});
		btnMainMenu.setBounds(26, 41, 161, 29);
		frame.getContentPane().add(btnMainMenu);
		
		scrollPane = new JScrollPane();
		scrollPane.setBounds(36, 98, 885, 359);
		frame.getContentPane().add(scrollPane);
		
		tableDisplayItem = new JTable();
		scrollPane.setViewportView(tableDisplayItem);
		
		txtSearch = new JTextField();
		txtSearch.setText("Search Area");
		txtSearch.setBounds(348, 36, 236, 39);
		frame.getContentPane().add(txtSearch);
		txtSearch.setColumns(10);
		
		JButton btnSearch = new JButton("Search");
		btnSearch.setBounds(594, 35, 113, 41);
		frame.getContentPane().add(btnSearch);
	}
}
