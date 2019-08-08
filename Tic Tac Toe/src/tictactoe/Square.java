package tictactoe;

public class Square {
	private char value;
	private int x;
	private int y;
	
	Square() {
		value = ' ';
		x = 0;
		y = 0;
	}
	
	Square(char value, int x, int y) {
		this.value = value;
		this.x = x;
		this.y = y;
	}
	
	public boolean isEmpty() {
		return (value == ' ');
	}
	public char getValue() {
		return value;
	}
	public void setValue(char value) {
		this.value = value;
	}
	public int getX() {
		return x;
	}
	public void setX(int x) {
		this.x = x;
	}
	public int getY() {
		return y;
	}
	public void setY(int y) {
		this.y = y;
	}
	@Override
	public String toString() {
		return "v: " + value + " x: " + x + " y: " + y + "\n";
	}
}
