import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JButton;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class WindowMain {

	private JFrame frame;

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
		
		JButton btnScanProduct = new JButton("Scan Product");
		btnScanProduct.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				ScanWindow.scanWindow();
				frame.setVisible(false);
			}
		});
		btnScanProduct.setBounds(151, 89, 315, 39);
		frame.getContentPane().add(btnScanProduct);
		
		JButton btnViewInventory = new JButton("View Inventory");
		btnViewInventory.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JOptionPane.showMessageDialog(null, "View Inventory");
				InventoryWindow.inventoryWindow();
				frame.setVisible(false);
			}
		});
		btnViewInventory.setBounds(151, 139, 315, 44);
		frame.getContentPane().add(btnViewInventory);
		
		JButton btnInventoryReports = new JButton("Inventory Reports");
		btnInventoryReports.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JOptionPane.showMessageDialog(null, "Inventory Reports");
				ReportWindow.reportWindow();
				frame.setVisible(false);
			}
		});
		btnInventoryReports.setBounds(151, 194, 315, 39);
		frame.getContentPane().add(btnInventoryReports);
	}
}
