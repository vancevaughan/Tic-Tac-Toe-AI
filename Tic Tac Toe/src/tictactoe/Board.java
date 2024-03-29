/*	Board.java
 *  Creator: Vance Vaughan
 *  Created: 7 August 2019
 *  Last Modified: 8 August 2019
 */

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
				board.get(i).get(j).setValue('_');
			}
		}

		isNewBoard = true;
	}

	public boolean isNewBoard() {
		return isNewBoard;
	}

	/* checks to see if the game is over
	 *
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
			return board.get(0).get(0).getValue();
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

		if (isFull()) {
			return '-';
		}

		return '\n';
	}

	public boolean isFull() {
		for (int i = 0; i < ySize; i++) {
			for (int j = 0; j < xSize; j++) {
				if (isValidMove(j, i)) {
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

		if (board.get(y).get(x).getValue() == '_') {
			return true;
		} else {
			return false;
		}
	}

	// returns square with best score
	public Square AIMove() {
		int bestI = -1;
		int bestJ = -1;
		int bestScore = -100;
		int moveScore = -100;
		
		// check score values for all empty spaces
		for (int i = 0; i < ySize; i++) {
			for (int j = 0; j < xSize; j++) {		
				if (isValidMove(j, i)) {
					place('O', j, i);
					
					moveScore = minimax(false, 0);
					
					this.board.get(i).get(j).setValue('_');
					
					if (moveScore == 10) {
						return new Square('O', j, i);
					}
					
					if (moveScore > bestScore) {
						bestI = i;
						bestJ = j;
						bestScore = moveScore;
					}
				}
			}
		}

		// return square with best score
		return new Square('O', bestJ, bestI);
	}

	public int minimax(boolean isMax, int depth) {
		int stateScore = evaluate();
		
		// either gone too deep or game is set
		if (depth == 10) {
			return stateScore;
		} 
		
		if (stateScore == 10) {
			return 10 - depth;
		}
		
		if (stateScore == -10) {
			return -10 + depth;
		}
		
		if (isFull()) {
			return 0;
		}
		
		if (isMax) {
			int bestScore = -100;
			
			for (int i = 0; i < ySize; i++) {
				for (int j = 0; j < xSize; j++) {
					if (isValidMove(j, i)) {
						place('O', j, i);
						
						bestScore = Math.max(bestScore, minimax(!isMax, depth + 1));
						
						this.board.get(i).get(j).setValue('_');
						
						if (bestScore == 1) {
							return 1;
						}						
					}
				}
			}
			
			return bestScore;
		} else {
			int bestScore = 100;
			
			for (int i = 0; i < ySize; i++) {
				for (int j = 0; j < xSize; j++) {
					if (isValidMove(j, i)) {
						place('X', j, i);
						
						bestScore = Math.min(bestScore, minimax(!isMax, depth + 1));
						
						this.board.get(i).get(j).setValue('_');
						
						if (bestScore == -1) {
							return -1;
						}
						
					}
				}
			}
			
			return bestScore;
		}
	}

	/*	returns:	1: O has won
	 * 			   -1: X has won
	 * 				0: game not complete or draw
	 */
	public int evaluate() {
		if (gameOver() == 'O') {
			return 10;
		} else if (gameOver() == 'X') {
			return -10;
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
