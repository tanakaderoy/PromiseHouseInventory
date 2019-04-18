package org.team3.PHI;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.text.ParseException;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

import org.json.JSONException;
import org.json.JSONObject;

@SuppressWarnings("unused")
public class ScanWindow {

	private JFrame frame;
	public static JTextField txtSerialCode;
	private JButton btnMainMenu;
	private TextPrompt txtSearchPrompt;

	/**
	 * Launch the application.
	 */
	public static void scanWindow() {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					ScanWindow window = new ScanWindow();
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
	public static String getSerial() {
		String serialNum = txtSerialCode.getText().replaceAll("\\s+", "");




		return serialNum;

	}
	public ScanWindow() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 450, 300);
		frame.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
		frame.getContentPane().setLayout(null);

		JButton btnScanIn = new JButton("Scan In");
		btnScanIn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				AddWindow.addWindow();
				frame.setVisible(false);
			}
		});
		btnScanIn.setBounds(56, 143, 112, 57);
		frame.getContentPane().add(btnScanIn);

		JButton btnScanOut = new JButton("Scan Out");
		btnScanOut.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				deleteFromDatabase();
				frame.setVisible(false);
				try {
					WindowMain.tableUpdated();
				} catch (ParseException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
		btnScanOut.setBounds(253, 143, 112, 57);
		frame.getContentPane().add(btnScanOut);

		txtSerialCode = new JTextField();

		txtSearchPrompt = new TextPrompt("Scan UPC", txtSerialCode);

		txtSerialCode.setToolTipText("Serial Number.....");
		txtSerialCode.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

			}
		});
		txtSerialCode.setBounds(95, 74, 241, 23);
		frame.getContentPane().add(txtSerialCode);
		txtSerialCode.setColumns(10);

		btnMainMenu = new JButton("Main Menu");
		btnMainMenu.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				frame.setVisible(false);
			}
		});
		btnMainMenu.setBounds(154, 211, 112, 23);
		frame.getContentPane().add(btnMainMenu);
	}
	public void deleteFromDatabase() {

		System.out.println(txtSerialCode.getText());


		try {
			Class.forName("org.sqlite.JDBC");
			Connection conn = (Connection) DriverManager.getConnection("jdbc:sqlite:test.db");
			PreparedStatement ps = conn.prepareStatement("delete from INVENTORY where UPC="+txtSerialCode.getText()+" ");
			ps.executeUpdate();
			ps.close();
			conn.close();
		}
		catch (Exception w) {
			JOptionPane.showMessageDialog(null, "Connection Error!");

		}

	}

}
