/**
 * 
 */
package org.team3.PHI;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JButton;
import javax.swing.JTable;
import javax.swing.JScrollPane;
import javax.swing.JPanel;
import javax.swing.BoxLayout;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableModel;

import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;
import java.awt.event.ActionEvent;
import javax.swing.Box;
import java.awt.Component;
import java.awt.Dimension;

/**
 * @author tanaka.mazivanhanga
 *
 */
public class WindowMain3 {
	JFrame frame;  
	private JTable tableDisplayItem;
	private JTextField txtSearchUpc;
	private JTextField txtStartdate;
	private JTextField txtEnddate;
	NumberFormat currencyFormatter = NumberFormat.getCurrencyInstance();
	private JTextField txtUpc;
	private JTextField textField;
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
	public WindowMain3() throws Exception {
		initialize();
		showItem();

	}

	/**
	 * @wbp.parser.entryPoint
	 */
	public void initialize(){  
		frame=new JFrame();
		frame.getContentPane().setLayout(new BorderLayout(0, 0));
		
		JPanel eastPane = new JPanel();
		frame.getContentPane().add(eastPane, BorderLayout.EAST);
		eastPane.setLayout(new BoxLayout(eastPane, BoxLayout.PAGE_AXIS));
		
		Box verticalBox = Box.createVerticalBox();
		eastPane.add(verticalBox);
		
		JButton btnNewButton = new JButton("New button");
		verticalBox.add(btnNewButton);
		
		Component verticalStrut = Box.createVerticalStrut(20);
		verticalBox.add(verticalStrut);
		
		txtUpc = new JTextField();
		verticalBox.add(txtUpc);
		txtUpc.setText("UPC");
		txtUpc.setColumns(10);
		
		Component rigidArea = Box.createRigidArea(new Dimension(20, 70));
		verticalBox.add(rigidArea);
		
		Box horizontalBox = Box.createHorizontalBox();
		eastPane.add(horizontalBox);
		
		textField = new JTextField();
		horizontalBox.add(textField);
		textField.setColumns(10);
		
		JButton btnNewButton_1 = new JButton("New button");
		eastPane.add(btnNewButton_1);
		
		JScrollPane scrollPane = new JScrollPane();
		frame.getContentPane().add(scrollPane, BorderLayout.CENTER);
		
		tableDisplayItem = new JTable();
		scrollPane.setViewportView(tableDisplayItem);
		
		JPanel northPanel = new JPanel();
		frame.getContentPane().add(northPanel, BorderLayout.NORTH);
		northPanel.setLayout(new BoxLayout(northPanel, BoxLayout.LINE_AXIS));
		
		Component horizontalStrut_2 = Box.createHorizontalStrut(20);
		northPanel.add(horizontalStrut_2);
		
		txtSearchUpc = new JTextField();
		txtSearchUpc.setText("Search UPC");
		northPanel.add(txtSearchUpc);
		txtSearchUpc.setColumns(10);
		
		JButton button = new JButton("Search");
		button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		
		Component horizontalStrut_1 = Box.createHorizontalStrut(20);
		northPanel.add(horizontalStrut_1);
		button.setHorizontalAlignment(SwingConstants.LEADING);
		northPanel.add(button);
		
		Component horizontalStrut = Box.createHorizontalStrut(20);
		northPanel.add(horizontalStrut);
		
		txtStartdate = new JTextField();
		txtStartdate.setText("startDate");
		northPanel.add(txtStartdate);
		txtStartdate.setColumns(10);
		
		txtEnddate = new JTextField();
		txtEnddate.setText("endDate");
		northPanel.add(txtEnddate);
		txtEnddate.setColumns(10);
		
		JButton btnFilterByDate = new JButton("Filter By Date");
		northPanel.add(btnFilterByDate);
	}
	double RoundTo2Decimals(double val) {
		DecimalFormat df2 = new DecimalFormat("###.##");
		return Double.valueOf(df2.format(val));
	}


}
