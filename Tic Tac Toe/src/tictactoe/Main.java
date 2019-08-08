/*	Main.java
 *  Creator: Vance Vaughan
 *  Created: 7 August 2019
 *  Last Modified: 8 August 2019
 */

package tictactoe;

import java.util.Scanner;

public class Main {
	public static void main(String args[]) {
		Board board = new Board();
		boolean singlePlayerMode = true;
		boolean playerOneTurn = true;
		char symbol = ' ';
		int xChoice = 0;
		int yChoice = 0;
		Scanner keyboard = new Scanner(System.in);
		String input = "";

		System.out.println("Tic Tac Toe");

		System.out.print("Enter # of players (1 or 2): ");
		input = keyboard.nextLine().replaceAll("[^1-2]", "");
		if (input.equals("2")) {
			singlePlayerMode = false;
		}

		do {
			// either X or O starts
			if (!singlePlayerMode){
				playerOneTurn = (Math.random() > 0.5);
			}

			System.out.println(board);

			for (int i = 0; (board.gameOver() == '\n'); i++) {
				// pick new symbol
				if (playerOneTurn) {
					symbol = 'X';
				} else {
					symbol = 'O';
				}

				System.out.println("Ply " + (i + 1));
				System.out.println(symbol + "'s turn");

				do {
					// ensures valid human move
					if (!singlePlayerMode || playerOneTurn) {
						System.out.print("Pick column (1-3): ");
						input = keyboard.nextLine().replaceAll("[^1-3]", "");
						xChoice = Integer.parseInt(input) - 1;

						System.out.print("Pick row (1-3): ");
						input = keyboard.nextLine().replaceAll("[^1-3]", "");
						yChoice = Integer.parseInt(input) - 1;

						if (!board.isValidMove(xChoice, yChoice)) {
							System.out.println("Invalid move");
						}
					} else {
						Square best = board.AIMove();
						xChoice = best.getX();
						yChoice = best.getY();
					}
				} while (!board.isValidMove(xChoice, yChoice) && (board.gameOver() == '\n'));

				board.place(symbol, xChoice, yChoice);

				playerOneTurn ^= true;

				System.out.println(board);
			}

			if (board.gameOver() == '-') {
				System.out.println("Draw.");
			} else {
				System.out.println(symbol + " wins!");
			}
			
			board.resetBoard();
			
			System.out.print("Do you wish to play another round (yes/no): ");
			input = keyboard.nextLine().replaceAll("[^enosy]", "");
			System.out.println("-----------------------------------");
		} while (input.contains("yes"));

		keyboard.close();
	}
}
