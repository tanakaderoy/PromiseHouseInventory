import java.awt.Desktop;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Vector;
import java.awt.event.ActionEvent;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.RowFilter;
import javax.swing.RowFilter.ComparisonType;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;
import javax.xml.stream.events.StartDocument;
import javax.swing.JScrollPane;
import java.awt.Font;
import javax.swing.JLabel;
import com.toedter.calendar.JDateChooser;

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
		model.setRowCount(0);
		tableDisplayItem.setRowHeight(30);
		tableDisplayItem.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
		TableColumnAdjuster tca = new TableColumnAdjuster(tableDisplayItem);
		tca.adjustColumns();
		
		model.addColumn("UPC");
		model.addColumn("PRODUCT NAME");
		model.addColumn("Price");
		model.addColumn("QUANTITY");
		model.addColumn("Category");
		model.addColumn("DATE");
		model.addColumn("Total Price");
		List<Double> totalPrice = new ArrayList<Double>();
		for(int i=0; i<list.size();i++) {
			totalPrice.add(list.get(i).getPrice());
			
		}
		double sum = totalPrice.stream().reduce(0.0, Double::sum);
		Vector<Object> row;
		for(int i=0; i<list.size();i++) {
			row = new Vector<Object>();
			row.add(list.get(i).getUPC());
			row.add(list.get(i).getProductName());
			row.add(list.get(i).getPrice());
			row.add(list.get(i).getQuantinty());
			row.add(list.get(i).getCategory());
			row.add(list.get(i).getdate());
			
			
			row.add(sum);
			
			//row[3] = list.get(i).getPrice();
			model.addRow(row);
		}
		
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
		frame.setBounds(100, 100, 1832, 815);
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
		scrollPane.setBounds(37, 157, 1735, 570);
		frame.getContentPane().add(scrollPane);
		
		tableDisplayItem = new JTable();
		tableDisplayItem.setFont(new Font("Tahoma", Font.PLAIN, 22));
		scrollPane.setViewportView(tableDisplayItem);
		
		
		txtSearch = new JTextField();
		TextPrompt txtSearchPrompt = new TextPrompt("Search by UPC or Product Name", txtSearch);
		txtSearch.setBounds(248, 36, 236, 39);
		frame.getContentPane().add(txtSearch);
		txtSearch.setColumns(10);
		txtSearch.getDocument().addDocumentListener(new DocumentListener() {
			
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
		btnSearch.setBounds(510, 35, 113, 41);
		frame.getContentPane().add(btnSearch);
		btnSearch.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				newFilter();
				
			}
		});
		
		JButton btnGenerateReport = new JButton("Generate Report");
		btnGenerateReport.setFont(new Font("Tahoma", Font.PLAIN, 20));
		btnGenerateReport.setBounds(730, 35, 191, 41);
		
		frame.getContentPane().add(btnGenerateReport);
		
		JLabel lblStartDate = new JLabel("Start Date");
		lblStartDate.setBounds(77, 96, 120, 33);
		frame.getContentPane().add(lblStartDate);
		
		DateFormat fmt = new SimpleDateFormat("yyyy-MM-dd");
		JDateChooser startDateChooser = new JDateChooser();
		startDateChooser.setBounds(204, 100, 181, 29);
		frame.getContentPane().add(startDateChooser);
		
		
		JLabel lblEndDate = new JLabel("End Date");
		lblEndDate.setBounds(411, 96, 115, 33);
		frame.getContentPane().add(lblEndDate);
		
		JButton btnFilter = new JButton("Filter");
		btnFilter.setBounds(740, 88, 99, 41);
		frame.getContentPane().add(btnFilter);
		
		JDateChooser endDateChooser = new JDateChooser();
		endDateChooser.setBounds(533, 96, 181, 29);
		frame.getContentPane().add(endDateChooser);
		
		btnFilter.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				String sDate;
				String eDate;
				if(startDateChooser.getDate() == null && endDateChooser.getDate() == null) {
					sDate = "";
					eDate = "";
				}else {
					sDate = ""+fmt.format(startDateChooser.getDate())+"";
					eDate = ""+fmt.format(endDateChooser.getDate())+"";
					
				}
				
				filter(sDate,eDate);
				
			}
		});
btnGenerateReport.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				String sDate;
				String eDate;
				if(startDateChooser.getDate() == null && endDateChooser.getDate() == null) {
					sDate = JOptionPane.showInputDialog("enter start date for report");
					eDate = JOptionPane.showInputDialog("enter end date for report");
				}else {
					sDate = ""+fmt.format(startDateChooser.getDate())+"";
					eDate = ""+fmt.format(endDateChooser.getDate())+"";
					
				}
				// TODO Auto-generated method stub
				TableToExcel tte = new TableToExcel(tableDisplayItem, null, "My Table");
				//optional -> tte.setCustomTitles(colTitles);
				File myFile = new File(""+sDate+"-"+eDate+".xls");
				try {
					tte.generate(myFile);
				} catch (Exception e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				try {
					Desktop.getDesktop().open(myFile);
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				
			}
		});
		
		
		
	}
	
	public void newFilter() {
		DefaultTableModel model = (DefaultTableModel)tableDisplayItem.getModel();
		TableRowSorter<DefaultTableModel> sorter = new TableRowSorter<DefaultTableModel>(model);
		
		tableDisplayItem.setRowSorter(sorter);
	    RowFilter<DefaultTableModel, Object> rf = null;
	    //If current expression doesn't parse, don't update.
	    try {
	        rf = RowFilter.orFilter(Arrays.asList(RowFilter.regexFilter(txtSearch.getText(),0),
	        	    RowFilter.regexFilter(txtSearch.getText(), 1)));
	        System.out.println(rf);
	        System.out.println(txtSearch.getText());
	    } catch (java.util.regex.PatternSyntaxException e) {
	        return;
	    }
	    sorter.setRowFilter(rf);
	}
	
	public ArrayList<Item> filterItemList(String startDate, String endDate) {
		ArrayList<Item> itemsList2 = new ArrayList<>();
		try {
			Class.forName("org.sqlite.JDBC");
			Connection con = DriverManager.getConnection("jdbc:sqlite:test.db");
			String query1;
			if(startDate.isEmpty() && endDate.isEmpty() ) {
				query1 = "SELECT * FROM 'INVENTORY' ";
						
			}else {
				
				query1 = "SELECT * FROM 'INVENTORY' " + 
						"where `DATE` between  '"+startDate+"' and '"+endDate+"';";
			}
			System.out.println(query1);
			Statement st = con.createStatement();
			ResultSet rs = st.executeQuery(query1);
			
			Item item;
			while(rs.next()) {
				item = new Item(rs.getInt("UPC"), rs.getString("PRODUCT_NAME"),rs.getDouble("PRICE"), rs.getInt("QUANTITY"), rs.getString("CATEGORY"), rs.getString("DATE"));
				
				itemsList2.add(item);
			}
		} catch(Exception e) {
			JOptionPane.showMessageDialog(null, e);
		}
		return itemsList2;
			
	}
	
	public void filter(String startDateText, String endDateText) {
		
		
		
		ArrayList<Item> list2 = filterItemList(startDateText, endDateText);
		DefaultTableModel model1 = (DefaultTableModel)tableDisplayItem.getModel();
		
		model1.setRowCount(0);
		tableDisplayItem.setRowHeight(30);
		tableDisplayItem.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
		TableColumnAdjuster tca = new TableColumnAdjuster(tableDisplayItem);
		tca.adjustColumns();
		
		List<Double> totalPrice = new ArrayList<Double>();
		for(int i=0; i<list2.size();i++) {
			totalPrice.add(list2.get(i).getPrice());
			
		}
		double sum = totalPrice.stream().reduce(0.0, Double::sum);
		
		Vector<Object> row;
		for(int i=0; i<list2.size();i++) {
			row = new Vector<Object>();
			row.add(list2.get(i).getUPC());
			row.add(list2.get(i).getProductName());
			row.add(list2.get(i).getPrice());
			row.add(list2.get(i).getQuantinty());
			row.add(list2.get(i).getCategory());
			row.add(list2.get(i).getdate());
			row.add(sum);
			
			//row[3] = list.get(i).getPrice();
			model1.addRow(row);
		}
	    }
}
