package org.team3.PHI;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.KeyStroke;
import java.awt.Toolkit;

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
			@SuppressWarnings("static-access")
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
		
        
        // setup menu
        JMenuBar menuBar = new JMenuBar();
        JMenu menu = new JMenu("File");
        menu.setMnemonic(KeyEvent.VK_F);
        JMenuItem menuItem = new JMenuItem("Exit");
        menuItem.setMnemonic(KeyEvent.VK_X);
        menuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_F4, KeyEvent.ALT_MASK));
        menuItem.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent event) {
                System.exit(0);
            }
        });
        menu.add(menuItem);
        menuBar.add(menu);
        

		
		
		frame = new JFrame();
		frame.setIconImage(Toolkit.getDefaultToolkit().getImage(WindowMain.class.getResource("/images/otterbein.png")));
		frame.setJMenuBar(menuBar);
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
