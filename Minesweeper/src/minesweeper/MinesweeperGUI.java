package minesweeper;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.net.URL;

import javax.swing.*;


public class MinesweeperGUI extends JFrame {
	JMenuBar menuBar;
	JMenu menu;
	JMenuItem menuItem;
	JMenuItem menuItem1;
	JMenuItem menuItem2;
	JPopupMenu popup;
	Board b;
	JButton empty;
	JButton hidden;
	JButton mine;
	JButton flag;
	JButton one;
	JButton two;
	JButton three;
	JButton four;
	JButton five;
	JButton six;
	JButton seven;
	JButton eight;
	ImageIcon[]Pics;
	Container cp;
	OptionsMenu m;
	private static final int EMPTY = 0;
	private static final int HIDDEN = 9;
	private static final int MINE = 10;
	private static final int FLAG = 11;
	
	private JButton[][] grid;

	public MinesweeperGUI(int numRows, int numCols, int numMines) {
		b = new Board(numRows, numCols, numMines);
		cp = getContentPane();
		setTitle("Minesweeper");
		cp.setLayout(new GridLayout(numRows, numCols));
		grid = new JButton[numRows][numCols];
		menuBar = new JMenuBar();
		menu = new JMenu("Options/Help");
		menuBar.add(menu);
		menuBar.add(menu);
		menu.add(menuBar);
		menuItem= new JMenuItem("Change Properties");
		menuItem1 = new JMenuItem("Controls");
		menuItem2 = new JMenuItem("Reset Board");
		menu.add(menuItem);
		menu.add(menuItem1);
		menu.add(menuItem2);
		setJMenuBar(menuBar);
		menuItem.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				m = new OptionsMenu(getGUI());
			}
		});
		menuItem1.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				Help h = new Help();
			}
		});
		menuItem2.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					changeBoard(m.getLength(), m.getLength2(), m.getMines());
				}
				catch (NullPointerException n) {
					changeBoard(35, 35, 150);
				}
			}
		});
		Pics = getImages();
		for(int i = 0; i < 12; i++) {
			
		}
		
		for(int row = 0; row < numRows; row++) {
			for(int col = 0; col < numCols; col++) {
				grid[row][col] = new JButton();
				grid[row][col].setIcon(Pics[9]);
				grid[row][col].addMouseListener(new CellListener(b, row, col));
				cp.add(grid[row][col]);
			}
		}
		setSize(numRows * 20, numCols * 20);
		setVisible(true);
	}
	
	public MinesweeperGUI getGUI() {
		return this;
	}
	public JButton[][] getGrid() {
		return grid;
	}
	
	private ImageIcon[] getImages() {
		ImageIcon[] Icons = new ImageIcon[12];
		ImageIcon empty = new ImageIcon(getClass().getResource("/resources/Empty.png"));
		Icons[EMPTY] = empty;
		ImageIcon hidden = new ImageIcon(getClass().getResource("/resources/Hidden.png"));
		Icons[HIDDEN] = hidden;
		ImageIcon mine = new ImageIcon(getClass().getResource("/resources/Mine.png"));
		Icons[MINE] = mine;
		ImageIcon flag = new ImageIcon(getClass().getResource("/resources/Flag.png"));
		Icons[FLAG] = flag;
		for(int i = 1; i < 9; i++) {
			ImageIcon num = new ImageIcon(getClass().getResource("/resources/" + i + ".png"));
			Icons[i] = num;
		}
		return Icons;
	}
	public class CellListener implements MouseListener {
		Board board;
		int row;
		int col;
		
		public CellListener(Board b, int r, int c) {
			row = r;
			col = c;
			board = b;
		}
		public void updateBoardIcons(int row, int col) {
			for(int x = 0;  x < grid.length; x++) {
				for(int y = 0; y < grid[0].length; y++) {
					if(board.getCell(x, y).isVisible()) {
						switch(board.getCell(x, y).mineCount()) {
							case 0:
								grid[x][y].setEnabled(false);
								grid[x][y].setDisabledIcon(Pics[EMPTY]);
								break;	
							case 1:
								grid[x][y].setEnabled(false);
								grid[x][y].setDisabledIcon(Pics[1]);
								break;
							case 2:
								grid[x][y].setEnabled(false);
								grid[x][y].setDisabledIcon(Pics[2]);
								break;
							case 3:
								grid[x][y].setEnabled(false);
								grid[x][y].setDisabledIcon(Pics[3]);
								break;
							case 4:
								grid[x][y].setEnabled(false);
								grid[x][y].setDisabledIcon(Pics[4]);
								break;
							case 5:
								grid[x][y].setEnabled(false);
								grid[x][y].setDisabledIcon(Pics[5]);
								break;
							case 6:
								grid[x][y].setEnabled(false);
								grid[x][y].setDisabledIcon(Pics[6]);
								break;
							case 7:
								grid[x][y].setEnabled(false);
								grid[x][y].setDisabledIcon(Pics[7]);
								break;
							case 8:
								grid[x][y].setEnabled(false);
								grid[x][y].setDisabledIcon(Pics[8]);
								break;
								
						}
					
						if(board.getCell(x, y).isMine()) {
							grid[x][y].setDisabledIcon(Pics[MINE]);
							grid[x][y].setIcon(Pics[MINE]);
						}	
					}
				}
			}
		}
		
		public void endGame() {
			for(int x = 0; x < grid.length; x++) {
				for(int y = 0; y < grid[0].length; y++) {
					if(b.getCell(x,y).isVisible() == false && b.getCell(x, y).isMine() == false) {
						return;
					}
				}
			}
		JOptionPane.showMessageDialog(m, "You win!");
		}
		public void shutdown() {
			for(int x = 0; x < grid.length; x++) {
				for(int y = 0; y < grid[0].length; y++) {
					grid[x][y].setEnabled(false);
					revealAllMines();
				}
			}
		}
		public void revealAllMines() {
			for(int x = 0; x < grid.length; x++) {
				for(int y = 0; y < grid[0].length; y++) {
					if(board.getCell(x, y).isMine()) {
						board.getCell(x, y).setVisibility();
					}
				}
			}
		}
		@Override
		public void mouseClicked(MouseEvent e) {
			if(e.getButton() == 1 && board.getCell(row, col).isFlagged() == false) {
				board.select(row, col);
				if(board.getCell(row, col).isMine()) {
					shutdown();
				}
				if(board.getCell(row, col).mineCount() == EMPTY ) {
					board.removeZeroes(row,col);
				}
				updateBoardIcons(row, col);
				endGame();
			}
			else if(e.getButton() == 3) {
				if(board.getCell(row, col).isFlagged()) {
					board.getCell(row, col).removeFlag();
					grid[row][col].setIcon(Pics[HIDDEN]);
				}
				else {
					board.getCell(row, col).placeFlag();
					grid[row][col].setIcon(Pics[FLAG]);
					
				}
			}
		}
		@Override
		public void mousePressed(MouseEvent e) {
			// TODO Auto-generated method stub
			
		}
		@Override
		public void mouseReleased(MouseEvent e) {
			// TODO Auto-generated method stub
			
		}
		@Override
		public void mouseEntered(MouseEvent e) {
			// TODO Auto-generated method stub
			
		}
		@Override
		public void mouseExited(MouseEvent e) {
			// TODO Auto-generated method stub
			
		}
	}
	public void changeBoard(int rows, int cols, int mineCount) {
		cp.removeAll();
		b = new Board(rows, cols, mineCount);
		System.out.println("a");
		setLayout(new GridLayout(rows, cols));
		grid = new JButton[rows][cols];
		for(int row = 0; row < rows; row++) {
			for(int col = 0; col < cols; col++) {
				grid[row][col] = new JButton();
				grid[row][col].setIcon(Pics[HIDDEN]);
				grid[row][col].addMouseListener(new CellListener(b, row, col));
				cp.add(grid[row][col]);
				setSize(cols * 20, rows * 20);
			}
		}
		revalidate();
		
	}
}
		
	
	
	


