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
		
		System.out.println("");
		
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
				if (playerOneTurn || !singlePlayerMode) {
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
					xChoice = board.AIMove().getX();
					yChoice = board.AIMove().getY();
					
					System.out.println("AI picked column: " + xChoice);
					System.out.println("             row: " + yChoice);
				}
			} while (!board.isValidMove(xChoice, yChoice));
			
			board.place(symbol, xChoice, yChoice);
			
			playerOneTurn ^= true;
			
			System.out.println(board);
		}
		
		System.out.println(symbol + " wins!");
		
		keyboard.close();
	}
}
