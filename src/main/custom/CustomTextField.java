package main.custom;

import java.awt.Color;

import javax.swing.BorderFactory;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import main.Util.Util;

public class CustomTextField extends JTextField{

	private static final long serialVersionUID = 1L;

	public CustomTextField(){
		history();
	}
	
	public CustomTextField(String e){
		current(e);
	}
	
	public void history() {
		setBorder(BorderFactory.createEmptyBorder(20, 0, 0, 5));
		setFont(Util.font_small);
		setEditable(false);
		setHorizontalAlignment(SwingConstants.RIGHT);
		setForeground(Color.WHITE);
		setBackground(Color.BLACK);
	}
	
	public void current(String e) {
		setText(e);
		setBorder(BorderFactory.createEmptyBorder(20, 20, 10, 5));
		setFont(Util.font_big);
		setEditable(false);
		setHorizontalAlignment(SwingConstants.RIGHT);
		setForeground(Color.WHITE);
		setBackground(Color.BLACK);
	}
	
}
