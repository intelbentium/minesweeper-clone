package minesweeper;
import java.util.Scanner;

import javax.swing.JOptionPane;

public class Board {
	private final int NUM_ROWS;
	private final int NUM_COLS;
	private Cell[][] board;
	private final int NUM_MINES;
	
	private static final int LOSE = -1;
	private static final int ONGOING = 0;
	private static final int WIN = 1;
	private int gameState;
	private boolean isFirstMove;
	
	public Board(int rows, int cols, int numMines) {
		isFirstMove = true;
		NUM_ROWS = rows;
		NUM_COLS = cols;
		NUM_MINES = numMines;
		board = new Cell[NUM_ROWS][NUM_COLS];
		for(int i = 0; i < NUM_ROWS; i++) {
			for(int j = 0; j < NUM_COLS; j++) {
				board[i][j] = new Cell();
			}
		}
		gameState = ONGOING;
		initializeMines();
	}
	
	public void playTextGame() { //starts text version of game
		Scanner scan = new Scanner(System.in);
		System.out.println(toString());
		System.out.println("Type in row, then column coordinate");
		int rowcoord = scan.nextInt();
		int colcoord = scan.nextInt();
		select(rowcoord, colcoord);
		if(win()) {
			System.out.println("you cleared all the mines");
		}
		while(!endGame()) {
			System.out.println("Type in row, then column coordinate");
			rowcoord = scan.nextInt();
			colcoord = scan.nextInt();
			select(rowcoord, colcoord);
			if(win()) {
				System.out.println("you cleared all the mines");
				break;
			}
		}
		scan.close();
	}
	
	public void shuffleMines(boolean[] a) { //shuffles mines
		for(int i = a.length - 1; i > 0; i--) {
			int rand = (int) (Math.random() * (i + 1));
			boolean temp = a[i];
			a[i] = a[rand];
			a[rand] = temp;
		}
		
	}
	
	public void initializeMines() { //places mines side by side from the start
		boolean[] minePlacement1D = new boolean[NUM_ROWS * NUM_COLS];
		for(int i = 0; i < NUM_MINES; i++) {
			minePlacement1D[i] = true;
		}
		shuffleMines(minePlacement1D);
		
		int index1D = 0;
		for(int i = 0; i < NUM_ROWS; i++) {
			for(int j = 0; j < NUM_COLS; j++) {
				if(minePlacement1D[index1D]) {
					board[i][j].setMine();
				}
				index1D++;
			}
		}
	}
	
	public void firstMove(int row, int col) { //makes sure that game will not lose on first try
		isFirstMove = false;
		int empty = (NUM_ROWS * NUM_COLS) - NUM_MINES; 
		int emptyCount = 0;
		int rand = (int) (Math.random() * empty);
		if(board[row][col].isMine()) {
			for(int i = 0; i < NUM_ROWS; i++) {
				for(int j = 0; j < NUM_COLS; j++) {
					if(board[i][j].isMine() == false) {
						if(emptyCount == rand) {
							board[row][col].removeMine();
							board[i][j].setMine();
							placeNums();
							select(row, col);
							return;
						}
						emptyCount++;
					}
				}
			}
		}
		placeNums();
	}
	public void placeNums() { //runs through board and finds mines 
		for(int row = 0; row < board.length; row++) {
			for(int col = 0; col < board[0].length; col++) {
				if(board[row][col].isMine()) {
					addAdjacient(row, col);
				}	
			}
		}
	}
	public void addAdjacient(int row, int col) { //places numbers based on the surrounding mines
		for(int r = row - 1; r <= row + 1; r++) {
			for(int c = col - 1; c <= col + 1; c++) {
				if((r >= 0 && r < NUM_ROWS) && (c >= 0 && c < NUM_COLS && board[r][c].isMine() == false)) {
					board[r][c].addNum();
				}
			}
		}
	}
	public String toStringOld() { //basic version of printed board
		String output = "";
		for(int row = 0; row < board.length; row++) {
			for(int col = 0; col < board[0].length; col++) {
				output += board[row][col].toString() + " ";	
			}
			output += "\n";
		}
		return output;
	}
	public void select(int row, int col) { //runs a turn of the game
		if(isFirstMove == true) {
			firstMove(row, col);
		}
		if(board[row][col].mineCount() == 0 && !board[row][col].isMine()) {
			removeZeroes(row, col);
		}
		
		else if(board[row][col].isMine()) {
			board[row][col].setVisibility();
			gameState = LOSE;
			lose();
		} 
		else {
			board[row][col].setVisibility();
		}

		//System.out.println(toString());
		updateWin();
	}
	public boolean endGame() {
		if(gameState != ONGOING) {
			return true;
		}
		return false;
	}
	public void removeZeroes(int row, int col) { //removes zeroes that are beside other zeroes
		if(board[row][col].isVisible()) {
			return;
		}
		board[row][col].setVisibility();
		if(board[row][col].mineCount() != 0) {
			return;
		}
		for(int r = row - 1; r <= row +1; r++) {
			for(int c = col - 1; c <= col + 1; c++) {
				if(r >= 0 && r < board.length && c >= 0 && c < board[0].length) {
					removeZeroes(r, c);
				}
			}
		}
	}
	public void updateWin() {
		for(int row = 0; row < NUM_ROWS; row++) {
			for(int col = 0; col < NUM_COLS; col++) {
				if(!board[row][col].isVisible() && !board[row][col].isMine()) {
					return;
				}
			}
		}
		gameState = WIN;
	}
	public boolean win() {
		if(gameState == WIN) {
			return true;
		}
		else {
			return false;
		}
	}
	public int getState() {
		return gameState;
	}
	public boolean lose() {
		if(gameState == LOSE) {
			System.out.println("you hit a mine");
			return true;
		}
		else {
			return false;
		}
	}
	public Cell getCell(int row, int col) {
		return board[row][col];
	}
	@Override
	public String toString() { //prints detailed board for actual play
		String output = "";
		//Creates the first line.
		output += "\t" + " ";
		for(int col = 0; col < NUM_COLS; col++) {
				String intString = col + "";
				if(intString.length() == 1) {
					output +="|" + "  " + intString + "  "; 
				}
				else if(intString.length() == 2) {
					output +="|" + " " + intString + " ";
				}
				else if(intString.length() == 3) {
					output +="|" + intString;
				}
		}
		output += "|\n";
		
		String intString; 
		for(int row = 0; row < NUM_ROWS; row++) {
			output +=  "----------";
			for(int col = 0; col < NUM_COLS; col++) {
				output += "-----+";
			}
			output += "\n";
			intString = row + "";
			
			if(intString.length() == 1) {
				output += "    " + row + "" + "    "  + "|";
			}
			else if(intString.length() == 2) {
				output += "   " + row + "" + "   " + "|";
			}
			else if(intString.length() == 3) {
				output += "  " + row + "" + "  " + "|";
			
			}
			
			for(int c = 0; c < NUM_COLS; c++) {
				output += "  " + board[row][c] + "  " + "|";
			}
			output += "\n";
			}
		return output;
	}
	
	public String toStringRevealed() { //prints out simplistic board with revealed elements for testing
		String output = "";
		for(int i = 0; i < NUM_ROWS; i++) {
			for(int j = 0; j < NUM_COLS; j++) {
				output += board[i][j].toStringRevealed() + "\t";
			}
			output += "\n";
		}
		return output;
	}
}

	
