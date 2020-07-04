package minesweeper;
import java.awt.*;

import javax.swing.JFrame;

public class Help extends JFrame{
	Label left;
	Label right;
	
	public Help() {
		left = new Label("Left Click: Reveal cell");
		right = new Label("Right Click: Flag cell");
		add(left);
		add(right);
		setVisible(true);
		setLayout(new FlowLayout());
		setSize(200,100);
	}
}
