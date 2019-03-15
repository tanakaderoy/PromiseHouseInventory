import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Vector;
import java.awt.event.ActionEvent;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

public class InventoryWindow {

	private JFrame frame;
	private JTable inventoryTable;
	
	
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
	
	public void showItem() {
		ArrayList<Item> list = itemList();
		DefaultTableModel model = (DefaultTableModel)inventoryTable.getModel();

		
		model.setRowCount(0);
		inventoryTable.setRowHeight(30);
		inventoryTable.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
		TableColumnAdjuster tca = new TableColumnAdjuster(inventoryTable);
		tca.adjustColumns();
		
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
	}

	

	/**
	 * Launch the application.
	 */
	public static void inventoryWindow() {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					
					InventoryWindow window = new InventoryWindow();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.	 */
	public InventoryWindow() {
		initialize();
		showItem();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		
		frame = new JFrame();
		frame.setBounds(100, 100, 1832, 575);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		JButton btnMainMenu = new JButton("Main Menu");
		btnMainMenu.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				
				WindowMain.MainWindow();
				frame.setVisible(false);
			}
		});
		btnMainMenu.setBounds(57, 44, 170, 23);
		frame.getContentPane().add(btnMainMenu);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(57, 125, 1735, 334);
		frame.getContentPane().add(scrollPane);
		
		inventoryTable = new JTable();
		scrollPane.setViewportView(inventoryTable);
		
		
	}
}
