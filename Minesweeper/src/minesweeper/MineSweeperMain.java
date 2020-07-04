package minesweeper;
import javax.swing.*;
public class MineSweeperMain {
	private static Board b;
	
	
	public static void main(String[] args) {
		SwingUtilities.invokeLater(new Runnable() {
	         @Override
	         public void run() {
	            new MinesweeperGUI(35,35,150); // Let the constructor do the job
	         }
		});
	}
}
