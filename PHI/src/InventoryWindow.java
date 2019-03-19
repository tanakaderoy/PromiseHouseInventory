import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Vector;
import java.awt.event.ActionEvent;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;
import javax.swing.JTextField;
import javax.swing.RowFilter;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

public class InventoryWindow {

	private JFrame frame;
	private JTable inventoryTable;
	private JTextField searchTextField;
	private NumberFormat currencyFormatter = NumberFormat.getCurrencyInstance();
	
	
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
			row.add(currencyFormatter.format(list.get(i).getPrice()));
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
		btnMainMenu.setBounds(57, 44, 170, 26);
		frame.getContentPane().add(btnMainMenu);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(57, 125, 1735, 334);
		frame.getContentPane().add(scrollPane);
		
		inventoryTable = new JTable();
		scrollPane.setViewportView(inventoryTable);
		
		JButton btnAdd = new JButton("Add");
		btnAdd.setBounds(1683, 40, 91, 30);
		frame.getContentPane().add(btnAdd);
		
		searchTextField = new JTextField();
		searchTextField.setBounds(790, 36, 418, 31);
		TextPrompt txtSearchPrompt = new TextPrompt("Search by UPC or Product Name", searchTextField);
		frame.getContentPane().add(searchTextField);
		searchTextField.setColumns(10);
searchTextField.getDocument().addDocumentListener(new DocumentListener() {
			
			@Override
			public void removeUpdate(DocumentEvent e) {
				// TODO Auto-generated method stub
				newFilter();
				
			}
			
			@Override
			public void insertUpdate(DocumentEvent e) {
				// TODO Auto-generated method stub
				newFilter();
				
			}
			
			@Override
			public void changedUpdate(DocumentEvent e) {
				// TODO Auto-generated method stub
				newFilter();
				
			}
		});
		JButton btnSearch = new JButton("Search");
		btnSearch.setBounds(1217, 35, 113, 32);
		frame.getContentPane().add(btnSearch);
btnSearch.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				newFilter();
				
			}
		});
		
		
	}
	public void newFilter() {
		DefaultTableModel model = (DefaultTableModel)inventoryTable.getModel();
		TableRowSorter<DefaultTableModel> sorter = new TableRowSorter<DefaultTableModel>(model);
		
		inventoryTable.setRowSorter(sorter);
	    RowFilter<DefaultTableModel, Object> rf = null;
	    //If current expression doesn't parse, don't update.
	    try {
	        rf = RowFilter.orFilter(Arrays.asList(RowFilter.regexFilter("(?i)" + searchTextField.getText(),0),
	        	    RowFilter.regexFilter("(?i)" + searchTextField.getText(), 1)));
	        System.out.println(rf);
	        System.out.println(searchTextField.getText());
	    } catch (java.util.regex.PatternSyntaxException e) {
	        return;
	    }
	    sorter.setRowFilter(rf);
	}
	
	
}
