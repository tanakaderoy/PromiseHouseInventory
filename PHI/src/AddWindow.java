import java.awt.EventQueue;
import java.sql.*;

import javax.swing.JFrame;
import javax.swing.JTextField;
import javax.swing.JTextPane;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class AddWindow {

	private JFrame frame;
	private JTextField upcText;
	private JTextField productText;
	private JTextField quantityText;
	private JTextField categoryText;
	private JTextPane txtpnUpcNumber;
	private JTextPane txtpnProductName;
	private JTextPane txtpnQuantity;
	private JTextPane txtpnCategory;
	private JButton btnCancel;
	private JButton btnAdd;

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
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 450, 300);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		
		
		upcText = new JTextField();
		upcText.setBounds(171, 53, 86, 20);
		frame.getContentPane().add(upcText);
		upcText.setColumns(10);
		
		productText = new JTextField();
		productText.setBounds(171, 103, 86, 20);
		frame.getContentPane().add(productText);
		productText.setColumns(10);
		
		quantityText = new JTextField();
		quantityText.setBounds(171, 153, 86, 20);
		frame.getContentPane().add(quantityText);
		quantityText.setColumns(10);
		
		categoryText = new JTextField();
		categoryText.setBounds(171, 207, 86, 20);
		frame.getContentPane().add(categoryText);
		categoryText.setColumns(10);
		
		txtpnUpcNumber = new JTextPane();
		txtpnUpcNumber.setText("UPC NUMBER");
		txtpnUpcNumber.setBounds(176, 34, 101, 20);
		frame.getContentPane().add(txtpnUpcNumber);
		
		txtpnProductName = new JTextPane();
		txtpnProductName.setText("PRODUCT NAME");
		txtpnProductName.setBounds(171, 84, 106, 20);
		frame.getContentPane().add(txtpnProductName);
		
		txtpnQuantity = new JTextPane();
		txtpnQuantity.setText("QUANTITY");
		txtpnQuantity.setBounds(181, 134, 76, 20);
		frame.getContentPane().add(txtpnQuantity);
		
		txtpnCategory = new JTextPane();
		txtpnCategory.setText("CATEGORY");
		txtpnCategory.setBounds(181, 188, 76, 20);
		frame.getContentPane().add(txtpnCategory);
		
		btnCancel = new JButton("CANCEL");
		btnCancel.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				frame.setVisible(false);
			}
		});
		btnCancel.setBounds(102, 238, 89, 23);
		frame.getContentPane().add(btnCancel);
		
		btnAdd = new JButton("ADD");
		btnAdd.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Connection con = null;
				Statement stmt = null;
				try {
					Class.forName("org.sqlite.JDBC");
					con = DriverManager.getConnection("jdbc:sqlite:test.db");
					con.setAutoCommit(false);
					System.out.println("Opened Database Sucessfuly");
					
					stmt = con.createStatement();
					String sql = "INSERT INTO INVENTORY (UPC, PRODUCT_NAME, QUANTITY, CATEGORY)" +
									"VALUES ("+upcText.getText()+",'"+productText.getText()+"',"+
									quantityText.getText()+",'"+categoryText.getText()+"');";
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
			}
		});
		btnAdd.setBounds(237, 238, 89, 23);
		frame.getContentPane().add(btnAdd);
		
		
	}

}
