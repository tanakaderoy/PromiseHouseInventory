import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class InventoryWindow {

	private JFrame frame;

	/**
	 * Launch the application.
	 */
	public static void inventoryWindow() {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					InventoryWindow window = new InventoryWindow();
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
	public InventoryWindow() {
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
		
		JButton btnMainMenu = new JButton("Main Menu");
		btnMainMenu.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				windowMain.MainWindow();
				frame.setVisible(false);
			}
		});
		btnMainMenu.setBounds(145, 203, 116, 23);
		frame.getContentPane().add(btnMainMenu);
		
		
	}
}
