package org.team3.PHI;
import java.awt.EventQueue;
import java.sql.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JTextField;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.io.Writer;
import java.awt.event.ActionEvent;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import com.toedter.calendar.JDateChooser;
import javax.swing.JComboBox;

public class AddWindow {

	private JFrame frame;
	private JTextField upcText;
	private JTextField productText;
	private JTextField quantityText;
	private JLabel txtpnUpcNumber;
	private JLabel txtpnProductName;
	private JLabel txtpnQuantity;
	private JLabel txtpnCategory;
	private JButton btnCancel;
	private JButton btnAdd;
	private JTextField priceTextField;
	private JComboBox comboBox;
	private Boolean updateOrInsert = false;
	private JDateChooser dateChooser;
	String serialNum = ScanWindow.getSerial();
	private JTextField addCategoryTextField;
	ArrayList<String> categories= new ArrayList<String>();

	/**
	 * Launch the application.
	 */
	public static void addWindow() {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					AddWindow window = new AddWindow();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	

	/**
	 * Create the application.
	 */
	public AddWindow() {
		initialize();
		if(serialNum.isEmpty()) {
			serialNum = "0";
			
		}
		
			autoFill();
		
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		
		frame = new JFrame();
		frame.setBounds(100, 100, 961, 622);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		
		
		
		
		
		upcText = new JTextField();
		upcText.setBounds(428, 68, 86, 20);
		
		frame.getContentPane().add(upcText);
		upcText.setColumns(10);
		
		productText = new JTextField();
		productText.setBounds(416, 153, 144, 20);
		frame.getContentPane().add(productText);
		productText.setColumns(10);
		
		quantityText = new JTextField();
		quantityText.setBounds(406, 239, 163, 22);
		frame.getContentPane().add(quantityText);
		quantityText.setColumns(10);
		
		txtpnUpcNumber = new JLabel();
		txtpnUpcNumber.setText("UPC NUMBER");
		txtpnUpcNumber.setBounds(416, 28, 188, 31);
		frame.getContentPane().add(txtpnUpcNumber);
		
		txtpnProductName = new JLabel();
		txtpnProductName.setText("PRODUCT NAME");
		txtpnProductName.setBounds(406, 104, 218, 38);
		frame.getContentPane().add(txtpnProductName);
		
		txtpnQuantity = new JLabel();
		txtpnQuantity.setText("QUANTITY");
		txtpnQuantity.setBounds(406, 188, 154, 39);
		frame.getContentPane().add(txtpnQuantity);
		File newFile = new File("Categories.txt");
		
		if(newFile.length() == 0) {
		try {
			
			newFile.createNewFile();
			
			addToCategoriesList("Supplies");
			addToCategoriesList("Food");
			addToCategoriesList("Toiletries");
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		}else {
			addFileLineToCat();
		}
		
		txtpnCategory = new JLabel("CATEGORY");
		txtpnCategory.setBounds(406, 277, 153, 31);
		frame.getContentPane().add(txtpnCategory);
		
		dateChooser = new JDateChooser();
		dateChooser.setBounds(406, 399, 181, 39);
		frame.getContentPane().add(dateChooser);
		
		String[] array = categories.toArray(new String[categories.size()]);
		
		comboBox = new JComboBox(array);
		comboBox.setBounds(406, 314, 163, 39);
		
		frame.getContentPane().add(comboBox);
		
		JLabel lblDate = new JLabel("DATE");
		lblDate.setBounds(406, 356, 115, 33);
		frame.getContentPane().add(lblDate);
		
	
		
		JLabel lblPrice = new JLabel("Price");
		lblPrice.setBounds(196, 107, 115, 33);
		frame.getContentPane().add(lblPrice);
		
		priceTextField = new JTextField();
		priceTextField.setBounds(141, 144, 236, 39);
		frame.getContentPane().add(priceTextField);
		priceTextField.setColumns(10);
		
		
		btnCancel = new JButton("CANCEL");
		btnCancel.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				frame.setVisible(false);
				WindowMain.MainWindow();
			}
		});
		btnCancel.setBounds(262, 470, 188, 20);
		frame.getContentPane().add(btnCancel);
		
		btnAdd = new JButton("ADD");
		btnAdd.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Connection con = null;
				Statement stmt = null;
				
				try {
					DateFormat fmt = new SimpleDateFormat("yyyy-MM-dd");
					Class.forName("org.sqlite.JDBC");
					con = DriverManager.getConnection("jdbc:sqlite:test.db");
					con.setAutoCommit(false);
					System.out.println("Opened Database Sucessfuly");
					
					stmt = con.createStatement();
					String sql;
					if(updateOrInsert != false) {
						
					sql = "INSERT INTO INVENTORY (UPC, PRODUCT_NAME,PRICE, QUANTITY, CATEGORY, DATE)" +
									"VALUES ("+upcText.getText()+",'"+productText.getText()+"',"+"'"+priceTextField.getText()+"',"+
									quantityText.getText()+",'"+comboBox.getSelectedItem().toString()+"',"+"date((julianday("+"'"+fmt.format(dateChooser.getDate())+"'"+")))"+");";
					}else {
				sql = "UPDATE INVENTORY SET PRODUCT_Name = '"+productText.getText()+"', PRICE = "+priceTextField.getText()+", QUANTITY = "+quantityText.getText()+" WHERE UPC = "+upcText.getText()+"; ";					
					}
					System.out.println(sql);
					stmt.executeUpdate(sql);
					stmt.close();
					con.commit();
					con.close();
				}catch(Exception ee) {
					System.err.println(ee.getClass().getName() + ": " + ee.getMessage());
					System.exit(0);
				}
				System.out.println("Product has been added sucessfully!");
				frame.setVisible(false);
				WindowMain.MainWindow();
			}
			
			
			
			
		});
		btnAdd.setBounds(575, 469, 163, 20);
		frame.getContentPane().add(btnAdd);
		
		addCategoryTextField = new JTextField();
		addCategoryTextField.setBounds(579, 323, 121, 20);
		frame.getContentPane().add(addCategoryTextField);
		addCategoryTextField.setColumns(10);
		TextPrompt txtSearchPrompt = new TextPrompt("Add Extra Categories", addCategoryTextField);
		
		JButton btnAddCategory = new JButton("Add Category");
		btnAddCategory.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
				if(!addCategoryTextField.getText().isEmpty()) {
				comboBox.addItem(addCategoryTextField.getText());
				}
				try {
					addToCategoriesList(addCategoryTextField.getText());
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				addCategoryTextField.setText(null);
			}
		});
		btnAddCategory.setBounds(724, 322, 105, 23);
		frame.getContentPane().add(btnAddCategory);
		
		JButton btnBack = new JButton("Back");
		btnBack.setBounds(26, 23, 171, 41);
		frame.getContentPane().add(btnBack);
		btnBack.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				ScanWindow.scanWindow();
				frame.setVisible(false);
			}
		});
		
		
		
		
		
		
		
		
	}
	public void addToCategoriesList(String cat) throws IOException{
		Writer writer = null;
		if(!cat.equals("")) {
		try {
			writer = new BufferedWriter(new FileWriter("categories.txt", true));
			
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		writer.append("\r\n"+cat);
		
		writer.close();

		addFileLineToCat();
		}
	}
	public void addFileLineToCat() {
		BufferedReader reader;
		try {
			reader = new BufferedReader(new FileReader("categories.txt"));
			String line = reader.readLine();
			while (line != null) {
				
				// read next line
				if((!line.equals("") && !categories.contains(line))) {
					System.out.println(line);
					categories.add(line);
				}
				line=reader.readLine();
			}
			reader.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	public void toggleUpdateOrInsert() {
		if(updateOrInsert == true) {
			updateOrInsert = false;
		}else {
			updateOrInsert = true;
		}
	}
	public void autoFill() {
		ArrayList<Item> itemsList2 = new ArrayList<>();
		try {
			Class.forName("org.sqlite.JDBC");
			Connection con = DriverManager.getConnection("jdbc:sqlite:test.db");
			con.setAutoCommit(false);
			String query1;
		
				query1 = "SELECT * FROM 'INVENTORY' WHERE UPC ="+serialNum+"";
						
			
			System.out.println(query1);
			Statement st = con.createStatement();
			ResultSet rs = st.executeQuery(query1);
			
			Item item;
			if(rs.next()) {
				item = new Item(rs.getInt("UPC"), rs.getString("PRODUCT_NAME"),rs.getDouble("PRICE"), rs.getInt("QUANTITY"), rs.getString("CATEGORY"), rs.getString("DATE"));
				
				itemsList2.add(item);
			}else {
				
			}
			if( !itemsList2.isEmpty()) {
				upcText.setText(""+itemsList2.get(0).getUPC()+"");
				productText.setText(itemsList2.get(0).getProductName());
				priceTextField.setText(itemsList2.get(0).getPrice().toString());
				comboBox.setSelectedItem(""+itemsList2.get(0).getCategory()+"");
				quantityText.setText(Integer.toString(itemsList2.get(0).getQuantinty()));
				String date= (itemsList2.get(0).getdate());
				Date date2 = new SimpleDateFormat("yyyy-MM-dd").parse(date);
				dateChooser.setDateFormatString("MM-dd-yyyy");
				dateChooser.setDate(date2);
				
				
				
				updateOrInsert = false;
			}else {
				upcText.setText(serialNum);
				toggleUpdateOrInsert();
			}
			st.executeUpdate(query1);
			st.close();
			con.commit();
			con.close();

		} catch(Exception e) {
			JOptionPane.showMessageDialog(null, e);
			
		} 
		
	}
	}

