package org.team3.PHI;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;

public class WindowMain {

	public static JFrame frame;
	public static JButton btnScanProduct = new JButton("Scan Product");
	public static JButton btnViewInventory = new JButton("View Inventory");
	public static JButton btnInventoryReports = new JButton("Inventory Reports");

	/**
	 * Launch the application.
	 */
	public static void MainWindow() {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					WindowMain window = new WindowMain();
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
	public WindowMain() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 997, 572);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		
		btnScanProduct.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				ScanWindow.scanWindow();
				frame.setVisible(false);
			}
		});
		btnScanProduct.setBounds(151, 89, 315, 39);
		frame.getContentPane().add(btnScanProduct);
		
		btnViewInventory.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				 
				InventoryWindow.inventoryWindow();
				frame.setVisible(false);
			}
		});
		btnViewInventory.setBounds(151, 139, 315, 44);
		frame.getContentPane().add(btnViewInventory);
		btnInventoryReports.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				ReportWindow.reportWindow();
				frame.setVisible(false);
			}
		});
		btnInventoryReports.setBounds(151, 194, 315, 39);
		frame.getContentPane().add(btnInventoryReports);
	}
}
