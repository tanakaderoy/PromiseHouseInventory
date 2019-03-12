import java.awt.EventQueue;
import java.sql.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;

import javax.swing.JFrame;
import javax.swing.JTextField;
import javax.swing.JTextPane;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JLabel;
import com.toedter.calendar.JDateChooser;

public class AddWindow {

	private JFrame frame;
	private JTextField upcText;
	private JTextField productText;
	private JTextField quantityText;
	private JTextField categoryText;
	private JLabel txtpnUpcNumber;
	private JLabel txtpnProductName;
	private JLabel txtpnQuantity;
	private JLabel txtpnCategory;
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
		frame.setBounds(100, 100, 961, 621);
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
		
		categoryText = new JTextField();
		categoryText.setBounds(406, 324, 144, 22);
		frame.getContentPane().add(categoryText);
		categoryText.setColumns(10);
		
		txtpnUpcNumber = new JLabel();
		txtpnUpcNumber.setText("UPC NUMBER");
		txtpnUpcNumber.setBounds(416, 28, 106, 31);
		frame.getContentPane().add(txtpnUpcNumber);
		
		txtpnProductName = new JLabel();
		txtpnProductName.setText("PRODUCT NAME");
		txtpnProductName.setBounds(406, 104, 154, 38);
		frame.getContentPane().add(txtpnProductName);
		
		txtpnQuantity = new JLabel();
		txtpnQuantity.setText("QUANTITY");
		txtpnQuantity.setBounds(406, 188, 154, 39);
		frame.getContentPane().add(txtpnQuantity);
		
		txtpnCategory = new JLabel("CATEGORY");
		txtpnCategory.setBounds(406, 277, 153, 31);
		frame.getContentPane().add(txtpnCategory);
		
		JLabel lblDate = new JLabel("DATE");
		lblDate.setBounds(406, 356, 115, 33);
		frame.getContentPane().add(lblDate);
		
		JDateChooser dateChooser = new JDateChooser();
		dateChooser.setBounds(406, 399, 181, 39);
		frame.getContentPane().add(dateChooser);
		
		
		btnCancel = new JButton("CANCEL");
		btnCancel.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				frame.setVisible(false);
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
					String sql = "INSERT INTO INVENTORY (UPC, PRODUCT_NAME, QUANTITY, CATEGORY, DATE)" +
									"VALUES ("+upcText.getText()+",'"+productText.getText()+"',"+
									quantityText.getText()+",'"+categoryText.getText()+"',"+"date((julianday("+"'"+fmt.format(dateChooser.getDate())+"'"+")))"+");";
					
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
			}
		});
		btnAdd.setBounds(575, 469, 163, 20);
		frame.getContentPane().add(btnAdd);
		
		
		
		
	}
}
