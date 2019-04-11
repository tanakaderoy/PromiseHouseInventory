/**
 * 
 */
package org.team3.PHI;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Desktop;
import java.awt.EventQueue;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.IOException;
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

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
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

/**
 * @author tanaka.mazivanhanga
 *
 */
public class WindowMain3 {
	JFrame frame;  
	private JTable tableDisplayItem;
	private JTextField searchTextField;

	NumberFormat currencyFormatter = NumberFormat.getCurrencyInstance();
	private static JTextField upcTextField;
	private JTextField textField;
	private TextPrompt searchTextPrompt;
	private TextPrompt UPCTextPrompt;

	private static final int GAP = 20;
	private static final int MARGIN = 25;
	public static  String getSerial() {
		String serialNum = upcTextField.getText().replaceAll("\\s+", "");




		return serialNum;

	}
	
	public static void MainWindow() {
		EventQueue.invokeLater(new Runnable() {

			public void run() {
				try {
					WindowMain3 window = new WindowMain3();
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
				item = new Item(rs.getString("UPC"), rs.getString("PRODUCT_NAME"),rs.getDouble("PRICE"),  rs.getInt("Weight"), rs.getInt("QUANTITY"), rs.getString("DONOR"), rs.getString("CATEGORY"), rs.getString("DATE"));

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
		model.addColumn("Weight");
		model.addColumn("QUANTITY");
		model.addColumn("Donor");
		model.addColumn("Category");
		model.addColumn("DATE");
		model.addColumn("Total Price");
		List<Double> totalPrice = new ArrayList<Double>();
		for(int i=0; i<list.size();i++) {
			totalPrice.add(list.get(i).getPrice());

		}
		double sum = (totalPrice.stream().reduce(0.0, Double::sum));
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
			row.add(list.get(i).getWeight());
			row.add(list.get(i).getQuantity());
			row.add(list.get(i).getDonor());
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
	public WindowMain3() throws Exception {
		initialize();
		showItem();

	}

	/**
	 * @wbp.parser.entryPoint
	 */
	public void initialize(){  

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

		frame=new JFrame("PHI");
		frame.setJMenuBar(menuBar);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setIconImage(Toolkit.getDefaultToolkit().getImage(WindowMain3.class.getResource("/org/team3/PHI/otterbein.jpg")));
		frame.getContentPane().setLayout(new BorderLayout());


		JPanel eastPanel = new JPanel();
		eastPanel.setLayout(new BoxLayout(eastPanel, BoxLayout.PAGE_AXIS));
		eastPanel.setBorder(BorderFactory.createEmptyBorder(MARGIN, MARGIN, MARGIN, MARGIN));
		frame.getContentPane().add(eastPanel, BorderLayout.EAST);




		upcTextField = new JTextField();
		upcTextField.setAlignmentX(Component.CENTER_ALIGNMENT);

		eastPanel.add(upcTextField);
		UPCTextPrompt = new TextPrompt("Scan UPC" , upcTextField);

		upcTextField.setColumns(15);
		upcTextField.setMaximumSize(upcTextField.getPreferredSize());
		//Component rigidArea = Box.createRigidArea(new Dimension(20, 70));
		//verticalBox.add(rigidArea);

		eastPanel.add(Box.createVerticalStrut(GAP));

		JPanel buttonPanel = new JPanel(new FlowLayout());
		buttonPanel.setAlignmentX(Component.CENTER_ALIGNMENT);


		JButton scanInButton = new JButton("Scan In");
		scanInButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				AddWindow.addWindow();

			}
		});
		buttonPanel.add(scanInButton);


		JButton scanOutButton = new JButton("Scan Out");

		buttonPanel.add(scanOutButton);

		eastPanel.add(buttonPanel);

		eastPanel.add(Box.createVerticalGlue());


		JPanel centerPanel = new JPanel(new GridLayout(1,2));



		JScrollPane scrollPane = new JScrollPane();
		centerPanel.setBorder( BorderFactory.createTitledBorder("Inventory"));
		centerPanel.add(scrollPane);
		frame.getContentPane().add(centerPanel, BorderLayout.CENTER);

		tableDisplayItem = new JTable();
		JButton deleteButton = new JButton("Delete");
		deleteButton.setEnabled(false);

		deleteButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				deleteSelectedRow();

			}
		});

		tableDisplayItem.addFocusListener(new FocusListener() {
			//DefaultTableModel model = (DefaultTableModel)tableDisplayItem.getModel();

			@Override
			public void focusLost(FocusEvent e) {
				// TODO Auto-generated method stub
				if(!deleteButton.getModel().isPressed()) {
					tableDisplayItem.getSelectionModel().clearSelection();
				}

			}

			@Override
			public void focusGained(FocusEvent e) {
				// TODO Auto-generated method stub

			}
		});
		scrollPane.setViewportView(tableDisplayItem);

		JPanel northPanel = new JPanel();
		frame.getContentPane().add(northPanel, BorderLayout.NORTH);
		northPanel.setLayout(new BoxLayout(northPanel, BoxLayout.LINE_AXIS));
		northPanel.setBorder(BorderFactory.createEmptyBorder(MARGIN, MARGIN, MARGIN, MARGIN));

		searchTextField = new JTextField();
		searchTextPrompt = new TextPrompt("Search by UPC or Product Name", searchTextField);

		northPanel.add(searchTextField);
		searchTextField.setColumns(15);
		searchTextField.setMaximumSize(searchTextField.getPreferredSize());
		searchTextField.getDocument().addDocumentListener(new DocumentListener() {

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


		JButton searchButton = new JButton("Search");
		searchButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				textFilter();

			}
		});

		northPanel.add(Box.createHorizontalStrut(GAP));


		northPanel.add(searchButton);

		northPanel.add(Box.createHorizontalStrut(2*GAP));
		northPanel.add(Box.createHorizontalGlue());

		JLabel lblStartDate = new JLabel("Start Date");


		DateFormat fmt = new SimpleDateFormat("yyyy-MM-dd");
		JDateChooser startDateChooser = new JDateChooser();
		startDateChooser.setMaximumSize(startDateChooser.getPreferredSize());





		lblStartDate.setMaximumSize(lblStartDate.getPreferredSize());
		northPanel.add(lblStartDate);
		northPanel.add(startDateChooser);

		northPanel.add(Box.createHorizontalStrut(GAP));


		JLabel lblEndDate = new JLabel("End Date");



		JDateChooser endDateChooser = new JDateChooser();

		endDateChooser.setMaximumSize(endDateChooser.getPreferredSize());
		lblEndDate.setMaximumSize(lblEndDate.getPreferredSize());
		northPanel.add(lblEndDate);
		northPanel.add(endDateChooser);

		northPanel.add(Box.createHorizontalStrut(GAP));

		JButton btnFilterByDate = new JButton("Filter By Date");
		btnFilterByDate.addActionListener(new ActionListener() {

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


		tableDisplayItem.getSelectionModel().addListSelectionListener(new ListSelectionListener(){

			@Override
			public void valueChanged(ListSelectionEvent arg0) {
				// TODO Auto-generated method stub
				int row = tableDisplayItem.getSelectedRow();
				if(row>=0) {
					deleteButton.setEnabled(true);
				}else {
					deleteButton.setEnabled(false);
				}

			}
		});
		JButton generateReportButton = new JButton("Generate Excel");
		generateExcel.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				generateReportButton.doClick();


			}
		});
		generateReportButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				String sDate;
				String eDate;
				String fileName;
				TableToExcel tte = new TableToExcel(tableDisplayItem, null, "My Table");

				if(startDateChooser.getDate() == null && endDateChooser.getDate() == null) {
					fileName = JOptionPane.showInputDialog("enter filename for report");



					//optional -> tte.setCustomTitles(colTitles);
					File myFile = new File(""+fileName+".xls");
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

				}else {
					sDate = ""+fmt.format(startDateChooser.getDate())+"";
					eDate = ""+fmt.format(endDateChooser.getDate())+"";
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
				// TODO Auto-generated method stub


			}
		});


		eastPanel.add(generateReportButton);
		northPanel.add(btnFilterByDate);
		northPanel.add(deleteButton);


		frame.addFocusListener(new FocusListener() {

			@Override
			public void focusLost(FocusEvent e) {
				// TODO Auto-generated method stub
				try {
					tableUpdated();
				} catch (ParseException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}

			}

			@Override
			public void focusGained(FocusEvent e) {
				// TODO Auto-generated method stub
				if(frame.isActive()) {
					try {
						tableUpdated();
					} catch (ParseException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				}

			}
		});

		frame.pack();

		frame.setMinimumSize(frame.getPreferredSize());
	}

	public void textFilter() {
		DefaultTableModel model = (DefaultTableModel)tableDisplayItem.getModel();
		TableRowSorter<DefaultTableModel> sorter = new TableRowSorter<DefaultTableModel>(model);

		tableDisplayItem.setRowSorter(sorter);
		RowFilter<DefaultTableModel, Object> rf = null;
		//If current expression doesn't parse, don't update.
		try {
			rf = RowFilter.orFilter(Arrays.asList(RowFilter.regexFilter("^" +searchTextField.getText()+"$",0),
					RowFilter.regexFilter("(?i)" +searchTextField.getText(), 1)));
			System.out.println(rf);
			System.out.println(searchTextField.getText());
		} catch (java.util.regex.PatternSyntaxException e) {
			return;
		}
		sorter.setRowFilter(rf);
	}
	public void tableUpdated() throws ParseException {
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
		String sum = RoundTo2Decimals(totalPrice.stream().reduce(0.0, Double::sum));
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
			row.add(list.get(i).getWeight());
			row.add(list.get(i).getQuantity());
			row.add(list.get(i).getDonor());
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
				item = new Item(rs.getString("UPC"), rs.getString("PRODUCT_NAME"),rs.getDouble("PRICE"),  rs.getInt("Weight"), rs.getInt("QUANTITY"), rs.getString("DONOR"), rs.getString("CATEGORY"), rs.getString("DATE"));

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
		String sum = RoundTo2Decimals(totalPrice.stream().reduce(0.0, Double::sum));
		DateFormat fmt = new SimpleDateFormat("yyyy-MM-dd");
		DateFormat fmt1 = new SimpleDateFormat("MM-dd-yyyy");


		Vector<Object> row;
		for(int i=0; i<list2.size();i++) {
			row = new Vector<Object>();
			row.add(list2.get(i).getUPC());
			row.add(list2.get(i).getProductName());
			row.add(currencyFormatter.format(list2.get(i).getPrice()));
			row.add(list2.get(i).getWeight());
			row.add(list2.get(i).getQuantity());
			row.add(list2.get(i).getDonor());
			row.add(list2.get(i).getCategory());
			String date = ""+fmt1.format(fmt.parse(list2.get(i).getDate()))+"";
			row.add(date);
			row.add(currencyFormatter.format(sum));

			//row[3] = list.get(i).getPrice();
			model1.addRow(row);
		}
	}



	String RoundTo2Decimals(double val) {
		//	DecimalFormat df2 = new DecimalFormat("###.##");
		//	return Double.valueOf(df2.format(val));
		return String.format("%.2d", val);
	}
	
	


}
