package minesweeper;

import java.awt.Button;
import java.awt.FlowLayout;
import java.awt.Label;
import java.awt.TextField;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

public class OptionsMenu extends JFrame {
	TextField optionMine;
	TextField optionSizeHeight;
	TextField optionSizeLength;
	Label sizeLabel;
	Label mineLabel;
	JButton OK;
	MinesweeperGUI m;
	int rows = 35;
	int cols = 35;
	int mCount = 150;
	
	public OptionsMenu(MinesweeperGUI m) {
		this.m = m;
		setLayout(new FlowLayout());
		setTitle("Options");
		setSize(300,150);
		setVisible(true);
		mineLabel = new Label("Number of Mines");
		mineLabel.setVisible(true);
		add(mineLabel);
		optionMine = new TextField(mCount + "", 2);
		optionMine.setEditable(true);
		optionMine.setVisible(true);
		add(optionMine);
		sizeLabel = new Label("Board Size (Recommended to keep length and width to be similar values)");
		sizeLabel.setVisible(true);
		add(sizeLabel);
		optionSizeHeight = new TextField(cols + "", 2);
		optionSizeLength = new TextField(rows + "", 2);
		optionSizeHeight.setEditable(true);
		optionSizeLength.setEditable(true);
		optionSizeHeight.setVisible(true);
		optionSizeLength.setVisible(true);
		add(optionSizeHeight);
		add(optionSizeLength);
		OK = new JButton("OK");
		OK.addActionListener(new OptionsListener());
		add(OK);
		disableBoard();
	}
	public class OptionsListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			int length = Integer.parseInt(optionSizeLength.getText());
			int height = Integer.parseInt(optionSizeHeight.getText());
			int mineCount = Integer.parseInt(optionMine.getText());
			if((length * height) < mineCount) {
				JOptionPane.showMessageDialog(m, "Number of mines cannot exceed number of cells!");
				dispose();
				OptionsMenu d = new OptionsMenu(m);
			}
			
			
			m.changeBoard(height, length, mineCount);
			rows = length;
			cols = height;
			mCount = mineCount;
			dispose();
		}
	}
	public void disableBoard()
	{
		for(int i = 0; i < m.getGrid().length; i++) {
			for(int j = 0; j < m.getGrid()[i].length; j++) {
				m.getGrid()[i][j].setEnabled(false);
			}
			
		}
	}
	public int getLength() {
		return rows;
	}
	public int getLength2() {
		return cols;
	}
	public int getMines() {
		return mCount;
	}
}


