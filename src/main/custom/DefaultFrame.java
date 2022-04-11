package main.custom;

import javax.swing.JFrame;

public class DefaultFrame extends JFrame {

	private static final long serialVersionUID = 1L;

	public int width = 350, height = 550;

	public DefaultFrame() {
		setup();
	}

	public DefaultFrame(String e) {
		setTitle(e);
		setup();
	}

	public void setup() {
		setSize(width, height);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLocationRelativeTo(null);
		setResizable(true);
		setUndecorated(false);
		setVisible(true);
	}

}
