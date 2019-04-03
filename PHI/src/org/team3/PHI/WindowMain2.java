package org.team3.PHI;
import java.awt.BorderLayout;
import java.awt.Desktop;

import org.json.JSONException;
import org.json.JSONObject;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.KeyEvent;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.KeyStroke;
import javax.swing.RowFilter;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;

import com.toedter.calendar.JDateChooser;
import javax.swing.ImageIcon;
import java.awt.Toolkit;

public class WindowMain2 {

	public static JFrame frame;
	public static JButton btnScanProduct = new JButton("Scan Product");
	public static JButton btnViewInventory = new JButton("View Inventory");
	public static JButton btnInventoryReports = new JButton("Inventory Reports");
	private static JTable tableDisplayItem;
	private JTextField txtSearch;
	private JScrollPane scrollPane;
	static NumberFormat currencyFormatter = NumberFormat.getCurrencyInstance();
	TextPrompt txtSearchPrompt;

	/**
	 * Launch the application.
	 */
	public static void MainWindow() {
		EventQueue.invokeLater(new Runnable() {
			@SuppressWarnings("static-access")
			public void run() {
				try {
					WindowMain2 window = new WindowMain2();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	public static ArrayList<Item> itemList() {
		ArrayList<Item> itemsList = new ArrayList<>();
		try {
			Class.forName("org.sqlite.JDBC");
			Connection con = DriverManager.getConnection("jdbc:sqlite:test.db");
			String query1 = "SELECT * FROM INVENTORY " + 
					"ORDER BY `DATE` desc;";
			Statement st = con.createStatement();
			ResultSet rs = st.executeQuery(query1);

			Item item;
			while(rs.next()) {
				item = new Item(rs.getString("UPC"), rs.getString("PRODUCT_NAME"),rs.getDouble("PRICE"), rs.getInt("QUANTITY"), rs.getString("CATEGORY"), rs.getString("DATE"));

				itemsList.add(item);
			}
			st.executeUpdate(query1);
			st.close();
			con.close();
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
		tableDisplayItem.setAutoCreateRowSorter(true);

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
		double sum = RoundTo2Decimals(totalPrice.stream().reduce(0.0, Double::sum));
		Vector<Object> row;
		/*
		 * TODO Change this to something more readable
		 */
		DateFormat fmt = new SimpleDateFormat("yyyy-MM-dd");
		DateFormat fmt1 = new SimpleDateFormat("MM-dd-yyyy");

		for(int i=0; i<list.size();i++) {
			row = new Vector<Object>();
			row.add(list.get(i).getUPC());
			row.add(list.get(i).getProductName());
			row.add(currencyFormatter.format(list.get(i).getPrice()));
			row.add(list.get(i).getQuantity());
			row.add(list.get(i).getCategory());
			String date = ""+fmt1.format(fmt.parse(list.get(i).getDate()))+"";
			row.add(date);


			row.add(currencyFormatter.format(sum));

			//row[3] = list.get(i).getPrice();
			model.addRow(row);
		}

	}


	/**
	 * Create the application.
	 * @throws Exception 
	 */
	public WindowMain2() throws Exception {
		initialize();
		showItem();

	}




	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		// setup menu
		JMenuBar menuBar = new JMenuBar();
		JMenu menu = new JMenu("File");
		menu.setMnemonic(KeyEvent.VK_F);
		JMenuItem menuItem = new JMenuItem("Exit");
		JMenuItem generateExcel = new JMenuItem("Generate Excel");


		generateExcel.setMnemonic(KeyEvent.VK_G);
		menuItem.setMnemonic(KeyEvent.VK_X);       
		menuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_F4, KeyEvent.ALT_MASK));
		menuItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				System.exit(0);
			}
		});
		menu.add(generateExcel);
		menu.add(menuItem);
		menuBar.add(menu);

		frame = new JFrame();
		frame.setJMenuBar(menuBar);
		frame.setIconImage(Toolkit.getDefaultToolkit().getImage(WindowMain2.class.getResource("/org/team3/PHI/otterbein.jpg")));
		frame.setBounds(100, 100, 1832, 803);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);


		btnScanProduct.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				ScanWindow.scanWindow();

			}
		});
		btnScanProduct.setBounds(1497, 202, 315, 39);
		frame.getContentPane().add(btnScanProduct);






		tableDisplayItem = new JTable();
		tableDisplayItem.setBounds(0, 157, 1487, 607);
		tableDisplayItem.setFont(new Font("Tahoma", Font.PLAIN, 22));
		JButton btnDelete = new JButton("Delete");
		tableDisplayItem.addFocusListener(new FocusListener() {
			//DefaultTableModel model = (DefaultTableModel)tableDisplayItem.getModel();

			@Override
			public void focusLost(FocusEvent e) {
				// TODO Auto-generated method stub
				if(!btnDelete.getModel().isPressed()) {
					tableDisplayItem.getSelectionModel().clearSelection();
				}

			}

			@Override
			public void focusGained(FocusEvent e) {
				// TODO Auto-generated method stub

			}
		});
		scrollPane = new JScrollPane();

		scrollPane.setBounds(0, 157, 1487, 607);
		frame.getContentPane().add(scrollPane, BorderLayout.CENTER);
		scrollPane.setViewportView(tableDisplayItem);


		txtSearch = new JTextField();
		txtSearchPrompt = new TextPrompt("Search by UPC or Product Name", txtSearch);
		txtSearch.setBounds(248, 36, 407, 39);
		frame.getContentPane().add(txtSearch);
		txtSearch.setColumns(10);
		txtSearch.getDocument().addDocumentListener(new DocumentListener() {

			@Override
			public void removeUpdate(DocumentEvent e) {
				// TODO Auto-generated method stub
				textFilter();

			}

			@Override
			public void insertUpdate(DocumentEvent e) {
				// TODO Auto-generated method stub
				textFilter();

			}

			@Override
			public void changedUpdate(DocumentEvent e) {
				// TODO Auto-generated method stub
				textFilter();

			}
		});


		JButton btnSearch = new JButton("Search");
		btnSearch.setBounds(669, 35, 113, 35);
		frame.getContentPane().add(btnSearch);
		btnSearch.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				textFilter();

			}
		});

		JButton btnGenerateReport = new JButton("Generate Report");
		btnGenerateReport.setFont(new Font("Tahoma", Font.PLAIN, 20));
		btnGenerateReport.setBounds(881, 36, 191, 37);

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

				try {
					dateRangeFilter(sDate,eDate);
				} catch (ParseException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}

			}
		});

		btnDelete.setBounds(1599, 265, 113, 33);
		btnDelete.setEnabled(false);
		frame.getContentPane().add(btnDelete);
		btnDelete.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				deleteSelectedRow();

			}
		});
		tableDisplayItem.getSelectionModel().addListSelectionListener(new ListSelectionListener(){

			@Override
			public void valueChanged(ListSelectionEvent arg0) {
				// TODO Auto-generated method stub
				int row = tableDisplayItem.getSelectedRow();
				if(row>=0) {
					btnDelete.setEnabled(true);
				}else {
					btnDelete.setEnabled(false);
				}

			}
		});
		generateExcel.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				btnGenerateReport.doClick();


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
	public void textFilter() {
		DefaultTableModel model = (DefaultTableModel)tableDisplayItem.getModel();
		TableRowSorter<DefaultTableModel> sorter = new TableRowSorter<DefaultTableModel>(model);

		tableDisplayItem.setRowSorter(sorter);
		RowFilter<DefaultTableModel, Object> rf = null;
		//If current expression doesn't parse, don't update.
		try {
			rf = RowFilter.orFilter(Arrays.asList(RowFilter.regexFilter("^" +txtSearch.getText()+"$",0),
					RowFilter.regexFilter("(?i)" +txtSearch.getText(), 1)));
			System.out.println(rf);
			System.out.println(txtSearch.getText());
		} catch (java.util.regex.PatternSyntaxException e) {
			return;
		}
		sorter.setRowFilter(rf);
	}
	public static void tableUpdated() throws ParseException {
		ArrayList<Item> list = itemList();
		DefaultTableModel model = (DefaultTableModel)tableDisplayItem.getModel();
		model.setRowCount(0);
		tableDisplayItem.setRowHeight(30);
		tableDisplayItem.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
		TableColumnAdjuster tca = new TableColumnAdjuster(tableDisplayItem);
		tca.adjustColumns();

		List<Double> totalPrice = new ArrayList<Double>();
		for(int i=0; i<list.size();i++) {
			totalPrice.add(list.get(i).getPrice());

		}
		double sum = RoundTo2Decimals(totalPrice.stream().reduce(0.0, Double::sum));
		Vector<Object> row;
		/*
		 * TODO Change this to something more readable
		 */
		DateFormat fmt = new SimpleDateFormat("yyyy-MM-dd");
		DateFormat fmt1 = new SimpleDateFormat("MM-dd-yyyy");

		for(int i=0; i<list.size();i++) {
			row = new Vector<Object>();
			row.add(list.get(i).getUPC());
			row.add(list.get(i).getProductName());
			row.add(currencyFormatter.format(list.get(i).getPrice()));
			row.add(list.get(i).getQuantity());
			row.add(list.get(i).getCategory());
			String date = ""+fmt1.format(fmt.parse(list.get(i).getDate()))+"";
			row.add(date);


			row.add(currencyFormatter.format(sum));

			//row[3] = list.get(i).getPrice();
			model.addRow(row);
		}
		model.fireTableDataChanged();



	}
	public void deleteSelectedRow() {
		int row = tableDisplayItem.getSelectedRow();
		DefaultTableModel model= (DefaultTableModel)tableDisplayItem.getModel();

		String selected = model.getValueAt(row, 0).toString();
		System.out.println(selected);

		if (row >= 0) {

			model.removeRow(row);

			try {
				Class.forName("org.sqlite.JDBC");
				Connection conn = (Connection) DriverManager.getConnection("jdbc:sqlite:test.db");
				PreparedStatement ps = conn.prepareStatement("delete from INVENTORY where UPC='"+selected+"' ");
				ps.executeUpdate();
				ps.close();
				conn.close();
			}
			catch (Exception w) {
				JOptionPane.showMessageDialog(tableDisplayItem, "Connection Error!");
			}          
		}

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
						"where `DATE` between  '"+startDate+"' and '"+endDate+"'"+
						"ORDER BY `DATE` desc;";
			}
			System.out.println(query1);
			Statement st = con.createStatement();
			ResultSet rs = st.executeQuery(query1);

			Item item;
			while(rs.next()) {
				item = new Item(rs.getString("UPC"), rs.getString("PRODUCT_NAME"),rs.getDouble("PRICE"), rs.getInt("QUANTITY"), rs.getString("CATEGORY"), rs.getString("DATE"));

				itemsList2.add(item);
			}
			st.executeUpdate(query1);
			st.close();
			con.close();
		} catch(Exception e) {
			JOptionPane.showMessageDialog(null, e);
		}
		return itemsList2;

	}
	static double RoundTo2Decimals(double val) {
		DecimalFormat df2 = new DecimalFormat("###.##");
		return Double.valueOf(df2.format(val));
	}

	public void dateRangeFilter(String startDateText, String endDateText) throws ParseException {



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
		double sum = RoundTo2Decimals(totalPrice.stream().reduce(0.0, Double::sum));
		DateFormat fmt = new SimpleDateFormat("yyyy-MM-dd");
		DateFormat fmt1 = new SimpleDateFormat("MM-dd-yyyy");


		Vector<Object> row;
		for(int i=0; i<list2.size();i++) {
			row = new Vector<Object>();
			row.add(list2.get(i).getUPC());
			row.add(list2.get(i).getProductName());
			row.add(currencyFormatter.format(list2.get(i).getPrice()));
			row.add(list2.get(i).getQuantity());
			row.add(list2.get(i).getCategory());
			String date = ""+fmt1.format(fmt.parse(list2.get(i).getDate()))+"";
			row.add(date);
			row.add(currencyFormatter.format(sum));

			//row[3] = list.get(i).getPrice();
			model1.addRow(row);
		}
	}
}
