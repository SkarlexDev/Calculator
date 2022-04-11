package main;

import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.text.DecimalFormat;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JTextField;

import main.Util.Util;
import main.custom.CustomTextField;
import main.custom.DefaultFrame;

public class Calculator implements ActionListener {

	public static void main(String[] args) {
		new Calculator();
	}

	private DefaultFrame window;
	private JTextField current;
	private JTextField history;
	private JPanel panel;
	private JPanel view;
	private JPanel mainPanel;

	private JButton addButton, subButton, mulButton, divButton, decButton, eqButton, delButton, clrButton, negButton,
			clCurrent, percentButton, divide1Button, doubleButton, radButton;
	private JButton[] functionButton = new JButton[14];
	private JButton[] numberButtons = new JButton[10];

	private char operator;
	private double n1 = 0, n2 = 0, result = 0, store = 0;
	private String tempStore = null;
	private boolean canreset = false;
	private boolean keep = false;
	private boolean start = false;
	private boolean decButtonProtect = false;
	private boolean keepeq = false;
	
	DecimalFormat numberFormat = new DecimalFormat("0.###");

	public Calculator() {

		window = new DefaultFrame("Calculator");
		createView();
		createMainPanel();
		window.add(mainPanel);
		window.setVisible(true);

	}

	public void createMainPanel() {

		mainPanel = new JPanel();
		mainPanel.setLayout(new GridBagLayout());
		GridBagConstraints viewGrid = new GridBagConstraints();
		viewGrid.anchor = GridBagConstraints.PAGE_START;
		viewGrid.fill = GridBagConstraints.BOTH;
		viewGrid.insets = new Insets(0, 0, 0, 0);
		viewGrid.weightx = 1.0;
		mainPanel.add(view, viewGrid);
		viewGrid.gridy = 1;
		viewGrid.weighty = 1.0;
		mainPanel.add(panel, viewGrid);
	}

	

	public void createView() {
		view = new JPanel();
		view.setLayout(new GridLayout(2, 0, 0, 0));
		history = new CustomTextField();
		current = new CustomTextField("0");
		view.add(history);
		view.add(current);
		createPanel();
	}

	public void createPanel() {
		createButtons();
		panel = new JPanel();
		panel.setBackground(Color.DARK_GRAY);
		panel.setLayout(new GridLayout(6, 4, 1, 1));
		
		panel.add(functionButton[10]);
		panel.add(clCurrent);
		panel.add(clrButton);
		panel.add(delButton);
		panel.add(divide1Button);
		panel.add(doubleButton);
		panel.add(radButton);
		panel.add(divButton);
		panel.add(numberButtons[7]);
		panel.add(numberButtons[8]);
		panel.add(numberButtons[9]);
		panel.add(mulButton);
		panel.add(numberButtons[4]);
		panel.add(numberButtons[5]);
		panel.add(numberButtons[6]);
		panel.add(subButton);
		panel.add(numberButtons[1]);
		panel.add(numberButtons[2]);
		panel.add(numberButtons[3]);
		panel.add(addButton);
		panel.add(negButton);
		panel.add(numberButtons[0]);
		panel.add(decButton);
		panel.add(eqButton);

	}

	public void createButtons() {

		functionButton[0] = addButton = new JButton("+");
		functionButton[1] = subButton = new JButton("-");
		functionButton[2] = mulButton = new JButton("X");
		functionButton[3] = divButton = new JButton("/");
		functionButton[4] = decButton = new JButton(".");
		functionButton[5] = eqButton = new JButton("=");
		functionButton[6] = delButton = new JButton("←");
		functionButton[7] = clrButton = new JButton("C");
		functionButton[8] = negButton = new JButton("+/-");
		functionButton[9] = clCurrent = new JButton("CE");
		functionButton[10] = percentButton = new JButton("%");
		functionButton[11] = divide1Button = new JButton("1/x");
		functionButton[12] = doubleButton = new JButton("X2");
		functionButton[13] = radButton = new JButton("√x");

		for (int i = 0; i < functionButton.length; i++) {
			final int finalI = i;
			if (i == 5) {
				functionButton[i].addActionListener(this);
				functionButton[i].setFont(Util.font_small);
				functionButton[i].setFocusable(false);
				functionButton[i].setBackground(Util.blue1);
				functionButton[i].setForeground(Color.white);
				functionButton[i].setBorderPainted(false);
				functionButton[i].addMouseListener(new MouseAdapter() {
					public void mouseEntered(MouseEvent evt) {
						functionButton[finalI].setBackground(Util.blue2);
					}

					public void mouseExited(MouseEvent evt) {
						functionButton[finalI].setBackground(Util.blue1);
					}
				});
				continue;
			}
			functionButton[i].addActionListener(this);
			functionButton[i].setFont(Util.font_small);
			functionButton[i].setFocusable(false);
			functionButton[i].setBackground(Color.BLACK);
			functionButton[i].setForeground(Color.white);
			functionButton[i].setBorderPainted(false);
			functionButton[i].addMouseListener(new MouseAdapter() {
				public void mouseEntered(MouseEvent evt) {
					functionButton[finalI].setBackground(Color.DARK_GRAY);
				}

				public void mouseExited(MouseEvent evt) {
					functionButton[finalI].setBackground(Color.BLACK);
				}
			});

		}

		for (int i = 0; i < 10; i++) {
			final int finalI = i;
			numberButtons[i] = new JButton(String.valueOf(i));
			numberButtons[i].addActionListener(this);
			numberButtons[i].setFont(Util.font_small);
			numberButtons[i].setFocusable(false);
			numberButtons[i].setBackground(Color.BLACK);
			numberButtons[i].setForeground(Color.white);
			numberButtons[i].setBorderPainted(false);
			numberButtons[i].addMouseListener(new MouseAdapter() {
				public void mouseEntered(MouseEvent evt) {
					numberButtons[finalI].setBackground(Color.DARK_GRAY);
				}

				public void mouseExited(MouseEvent evt) {
					numberButtons[finalI].setBackground(Color.BLACK);
				}
			});
		}

	}

	@Override
	public void actionPerformed(ActionEvent e) {
		
		for (int i = 0; i < 10; i++) {
			if (e.getSource() == numberButtons[i]) {
				buttonNum(i);
			}
		}

		// calculate
		if (e.getSource() == eqButton) {
			calculate();
		} else {
			keep = false;
		}

		if (e.getSource() == decButton) {
			if (!decButtonProtect) {
				current.setText(current.getText().concat("."));
				decButtonProtect = true;
			}
			tempStore = null;
			canreset = false;
		}
		if (e.getSource() == addButton) {
			n1 = Double.parseDouble(current.getText());
			operator = '+';
			setHistory();
			store();
		}
		if (e.getSource() == subButton) {
			n1 = Double.parseDouble(current.getText());
			operator = '-';
			setHistory();
			store();
		}

		if (e.getSource() == mulButton) {
			n1 = Double.parseDouble(current.getText());
			operator = '*';
			setHistory();
			store();
		}
		if (e.getSource() == divButton) {
			n1 = Double.parseDouble(current.getText());
			operator = '/';
			setHistory();
			store();
		}

		if (e.getSource() == clrButton) {
			current.setText("0");
			history.setText("");
			start = false;
			store();
		}
		if (e.getSource() == delButton) {
			store();
			if (current.getText().length() == 1) {
				current.setText("0");
				start = false;
				return;
			}
			String temp = current.getText();
			current.setText("");
			for (int i = 0; i < temp.length() - 1; i++) {
				current.setText(current.getText() + temp.charAt(i));
			}
		}

		if (e.getSource() == negButton) {
			double temp = Double.parseDouble(current.getText());
			temp *= -1;
			if (temp % 1 == 0) {
				current.setText(String.valueOf((int) temp));
			} else {
				current.setText(String.valueOf(temp));
			}
			operator = '?';
			store();
		}

		if (e.getSource() == clCurrent) {
			current.setText("0");
			start = false;
			canreset = true;
			decButtonProtect = false;
			store();
		}

		if (e.getSource() == percentButton) {
			if (history.getText().isEmpty()) {
				return;
			}
			double n3 = Double.parseDouble(history.getText().replaceAll("[^\\d.]", ""));
			n1 = Double.parseDouble(current.getText());
			result = (n3 / 100) * n1;
			current.setText(String.valueOf(result));
			n2 = result;
			history.setText(history.getText() + " " + operator + " " + current.getText());
			store();

		}

		if (e.getSource() == divide1Button) {
			double n3 = Double.parseDouble(current.getText());
			result = 1 / n3;
			if (n3 % 1 == 0) {
				history.setText("1/" + (int) n3);
			} else {
				history.setText("1/" + n3);
			}
			current.setText(String.valueOf(numberFormat.format(result)));
			store();
		}

		if (e.getSource() == doubleButton) {
			double n3 = Double.parseDouble(current.getText());
			result = Math.pow(n3, 2);
			if (n3 % 1 == 0) {
				history.setText("sqr(" + String.valueOf((int) n3) + ")");
				current.setText(String.valueOf((int) result));
			} else {
				history.setText("sqr(" + String.valueOf(n3) + ")");
				current.setText(String.valueOf(numberFormat.format(result)));
			}
			operator = ' ';
			keepeq = true;
			store();
		}
		
		if(e.getSource() == radButton) {
			double n3 = Double.parseDouble(current.getText());
			result = Math.sqrt(n3);
			if (n3 % 1 == 0) {
				history.setText("√(" + String.valueOf((int) n3) + ")");
			} else {
				history.setText("√(" + String.valueOf(n3) + ")");
				current.setText(String.valueOf(numberFormat.format(result)));
			}
			if(result%1 == 0) {
				current.setText(String.valueOf((int) result));
			}else {
				current.setText(String.valueOf(numberFormat.format(result)));
			}
			operator = ' ';
			store();
		}

	}

	public void buttonNum(int i) {
		if (start) {
			// prevent 0000
			if ((current.getText() + numberButtons[i].getText()).equals("0" + numberButtons[i].getText())) {
				current.setText(String.valueOf(i));
				start = false;
				return;
			}
			if (canreset) {
				current.setText(String.valueOf(i));
				canreset = false;
				return;
			}
			if (tempStore != null) {
				current.setText(String.valueOf(i));
				tempStore = null;
			} else {
				current.setText(current.getText().concat(String.valueOf(i)));
			}
		} else {
			current.setText(String.valueOf(i));
			if (!(current.getText() + numberButtons[i].getText()).equals("0" + numberButtons[i].getText())) {
				start = true;
			}
			canreset = false;
			tempStore = null;
		}
		keepeq = false;
	}

	public void calculate() {
		if (keepeq) {
			if (history.getText().contains("sqr")) {
				history.setText(current.getText() + " = ");
			}
			return;
		}
		if (keep) {
			n2 = store;
		} else {
			n2 = Double.parseDouble(current.getText());

		}
		switch (operator) {
		case '+':
			result = n1 + n2;
			break;

		case '-':
			result = n1 - n2;
			break;

		case '*':
			result = n1 * n2;
			break;

		case '/':
			result = n1 / n2;
			break;

		}
		if(String.valueOf(numberFormat.format(result)).length()>10) {
			return;
		}
		if (keep) {
			if (store % 1 == 0 && n1 % 1 == 0) {
				history.setText(String.valueOf((int) n1) + " " + operator + " " + String.valueOf((int) store) + " = ");
			} else if (n1 % 1 == 0) {
				history.setText(String.valueOf((int) n1) + " " + operator + " " + String.valueOf((int) store) + " = ");
			} else if (store % 1 == 0) {
				history.setText(String.valueOf(numberFormat.format(n1)) + " " + operator + " " + String.valueOf((int) store) + " = ");
			} else {
				history.setText(String.valueOf(numberFormat.format(n1)) + " " + operator + " " + String.valueOf(store) + " = ");
			}
		} else {
			if (n2 % 1 == 0) {
				history.setText(history.getText() + " " + String.valueOf((int) n2) + " = ");
			} else {
				history.setText(history.getText() + " " + String.valueOf(numberFormat.format(n2)) + " = ");
			}
			store = n2;
		}
		if (result % 1 == 0) {
			current.setText(String.valueOf((int) result));
		} else {
			current.setText(String.valueOf(numberFormat.format(result)));
		}
		n1 = result;
		canreset = true;
		keep = true;
		decButtonProtect = true;
	}

	public void store() {
		tempStore = current.getText();
		decButtonProtect = false;
	}
	
	public void setHistory() {
		history.setText(current.getText() + " " + operator);
	}

}
