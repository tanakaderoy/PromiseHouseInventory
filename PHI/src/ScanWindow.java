import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JButton;
import javax.swing.JTextField;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JTextPane;

public class ScanWindow {

	private JFrame frame;
	private JTextField txtSerialCode;
	private JButton btnMainMenu;

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
	public ScanWindow() {
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
		
		JButton btnScanIn = new JButton("Scan In");
		btnScanIn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String serialNum = txtSerialCode.getText();
				AddWindow.addWindow();
				frame.setVisible(false);
			}
		});
		btnScanIn.setBounds(56, 143, 112, 57);
		frame.getContentPane().add(btnScanIn);
		
		JButton btnScanOut = new JButton("Scan Out");
		btnScanOut.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String serialNum = txtSerialCode.getText();
				JOptionPane.showMessageDialog(null, serialNum + " has been scanned out.");
			}
		});
		btnScanOut.setBounds(253, 143, 112, 57);
		frame.getContentPane().add(btnScanOut);
		
		txtSerialCode = new JTextField();
		txtSerialCode.setText("Serial Number.....");
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
				windowMain.MainWindow();
				frame.setVisible(false);
			}
		});
		btnMainMenu.setBounds(154, 211, 112, 23);
		frame.getContentPane().add(btnMainMenu);
	}
}
