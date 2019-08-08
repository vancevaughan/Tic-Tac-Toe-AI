package tictactoe;

import java.util.ArrayList;

public class Board {
	private ArrayList<ArrayList<Square>> board;
	private boolean isNewBoard;
	private int xSize;
	private int ySize;

	Board() {
		board = new ArrayList<ArrayList<Square>>();

		xSize = 3;
		ySize = 3;

		resetBoard();
		isNewBoard = true;
	}

	public ArrayList<ArrayList<Square>> getBoard() {
		return board;
	}

	public void resetBoard() {
		for (int i = 0; i < ySize; i++) {
			board.add(new ArrayList<Square>());

			for (int j = 0; j < xSize; j++) {
				board.get(i).add(new Square());

				board.get(i).get(j).setX(j);
				board.get(i).get(j).setY(i);
				board.get(i).get(j).setValue(' ');
			}
		}

		isNewBoard = true;
	}

	public boolean isNewBoard() {
		return isNewBoard;
	}

	// checks to see if there is a 3 in a row
	/*
	 * returns: X: X win 
	 * 			O: O win 
	 * 			-: tie 
	 * 		   \n: game not over
	 */

	public char gameOver() {
		if (isNewBoard()) {
			return '\n';
		}

		// check for horizontal 3 in a row
		if (!board.get(0).get(0).isEmpty() && board.get(0).get(0).getValue() == board.get(0).get(1).getValue()
				&& board.get(0).get(1).getValue() == board.get(0).get(2).getValue()) {
			return board.get(0).get(0).getValue();
		} else if (!board.get(1).get(0).isEmpty() && board.get(1).get(0).getValue() == board.get(1).get(1).getValue()
				&& board.get(1).get(1).getValue() == board.get(1).get(2).getValue()) {
			return board.get(1).get(0).getValue();
		} else if (!board.get(2).get(0).isEmpty() && board.get(2).get(0).getValue() == board.get(2).get(1).getValue()
				&& board.get(2).get(1).getValue() == board.get(2).get(2).getValue()) {
			return board.get(2).get(0).getValue();
		}

		// check for vertical 3 in a row
		if (!board.get(0).get(0).isEmpty() && board.get(0).get(0).getValue() == board.get(1).get(0).getValue()
				&& board.get(1).get(0).getValue() == board.get(2).get(0).getValue()) {
			return board.get(0).get(1).getValue();
		} else if (!board.get(0).get(1).isEmpty() && board.get(0).get(1).getValue() == board.get(1).get(1).getValue()
				&& board.get(1).get(1).getValue() == board.get(2).get(1).getValue()) {
			return board.get(0).get(1).getValue();
		} else if (!board.get(0).get(2).isEmpty() && board.get(0).get(2).getValue() == board.get(1).get(2).getValue()
				&& board.get(1).get(2).getValue() == board.get(2).get(2).getValue()) {
			return board.get(0).get(2).getValue();
		}

		// check for diagonal 3 in a row
		if (!board.get(0).get(0).isEmpty() && board.get(0).get(0).getValue() == board.get(1).get(1).getValue()
				&& board.get(1).get(1).getValue() == board.get(2).get(2).getValue()) {
			return board.get(0).get(0).getValue();
		} else if (!board.get(2).get(0).isEmpty() && board.get(2).get(0).getValue() == board.get(1).get(1).getValue()
				&& board.get(1).get(1).getValue() == board.get(0).get(2).getValue()) {
			return board.get(2).get(0).getValue();
		}

		if (this.isFull()) {
			return '-';
		}

		return '\n';
	}

	public boolean isFull() {
		for (int i = 0; i < ySize; i++) {
			for (int j = 0; j < xSize; j++) {
				if (board.get(i).get(j).getValue() == ' ') {
					return false;
				}
			}
		}

		return true;
	}

	public void place(char symbol, int x, int y) {
		isNewBoard = false;

		board.get(y).get(x).setValue(symbol);
	}

	public boolean isValidMove(int x, int y) {
		if (x > 2 || x < 0 || y > 2 || y < 0) {
			return false;
		}

		if (board.get(y).get(x).isEmpty()) {
			return true;
		} else {
			return false;
		}
	}

	// returns square with best score
	public Square AIMove() {
		int bestI = 0;
		int bestJ = 0;
		int bestScore = -10;
		int moveScore = -10;
		
		// check score values for all empty spaces
		for (int i = 0; i < ySize; i++) {
			for (int j = 0; j < xSize; j++) {
				if (board.get(i).get(j).getValue() == ' ') {
					this.place('O', j, i);
					
					moveScore = minimax();
					
					if (moveScore > bestScore) {
						bestI = i;
						bestJ = j;
						bestScore = moveScore;
					}
					
					this.board.get(i).get(j).setValue(' ');
					
					
				}
			}
		}

		// return square with best score
		return new Square('O', bestI, bestJ);
	}

	public int minimax() {
		int stateScore = evaluate();
		
		return 0;
	}

	public int evaluate() {
		if (this.gameOver() == 'X') {
			return 1;
		} else if (this.gameOver() == 'O') {
			return -1;
		} else {
			return 0;
		}
	}

	@Override
	public String toString() {
		String output = "";

		for (int i = 0; i < 3; i++) {
			for (int j = 0; j < 3; j++) {
				output += "|" + board.get(i).get(j).getValue();
			}
			output += "|\n";
		}

		return output;
	}
}
