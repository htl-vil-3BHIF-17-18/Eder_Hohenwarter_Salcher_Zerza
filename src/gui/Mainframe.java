package gui;

import java.awt.BorderLayout;
import java.awt.Dimension;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JMenuBar;

public class Mainframe extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1181889914322477954L;

	private JMenuBar menubar = null;
	private JButton btnRefresh = null;
	
	public Mainframe(String title) {
		super(title);
		this.setMinimumSize(new Dimension(800,600));
		this.setPreferredSize(new Dimension(800,600));
		initializeControls();
		this.pack();
		this.setVisible(true);
	}
	private void initializeControls() {
		this.setLayout(new BorderLayout());
		menubar = new JMenuBar();
		btnRefresh = new JButton("Refresh");
		btnRefresh.setActionCommand("refresh");
		
		menubar.add(btnRefresh);
		this.add(menubar,BorderLayout.PAGE_START);
	}
}
