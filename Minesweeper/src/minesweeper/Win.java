package minesweeper;

import java.awt.FlowLayout;
import java.awt.Label;

import javax.swing.JFrame;

public class Win extends JFrame{
	Label winLabel;
	public Win() {
		winLabel = new Label("You win!");
		winLabel.setVisible(true);
		add(winLabel);
		setVisible(true);
		setLayout(new FlowLayout());
	}
}
