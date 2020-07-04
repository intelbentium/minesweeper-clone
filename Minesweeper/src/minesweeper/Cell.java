package minesweeper;

public class Cell {
	private boolean isVisible;
	private boolean isMine;
	private int mineCount;
	private boolean flagged;
	
	public Cell() {
		isVisible = false;
		isMine = false;
		mineCount = 0;
	}
	public void setMine() {
		isMine = true;
	}
	public void setVisibility() {
		isVisible = true;
	}
	public boolean isMine() {
		return isMine;
	}
	public boolean isVisible() {
		return isVisible;
	}
	public int mineCount() {
		return mineCount;
	}
	public void addNum() {
		mineCount++;
	}
	public void removeMine() {
		isMine = false;
	}
	public boolean isFlagged() {
		return flagged;
	}
	public void placeFlag() {
		flagged = true;
	}
	public void removeFlag() {
		flagged = false;
	}
	@Override
	public String toString() { //toString for actual use
		if(isVisible) {
			if(isMine) {
				return "*";
			}
			else {
				return "" + mineCount;
			}
		}
		return "#";
	}
	
	
	public String toStringRevealed() { //toString for testing
		if(isMine) {
			return "*";
		}
		else  {
			return "" +mineCount;
		}
	}
}


