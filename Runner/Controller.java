package Runner;

import java.awt.event.MouseEvent;

import java.awt.event.MouseListener;

import GUI.Display;
import Resources.Name;
import Logic.*;
import Pieces.Piece;

public class Controller implements MouseListener{
	
	private Display display = null;
	private boolean gameComplete = false;
	private Player white;
	private Player black;
	private Board board;
	private Name turn;
	private Square selectedSquare;
	
	public void setSelectedSquare(Square s) {
		selectedSquare = s;
	}
	
	public Square getSelectedSquare() {
		return selectedSquare;
	}
	
	public Name getTurn() {
		return turn;
	}
	
	public Board getBoard() {
		return board;
	}
	
	public Controller() {
		createPlayers();
		createBoard();
		turn = Name.white;
	}

	public void setDisplay(Display d) {
		display = d;
	}
	
	/**
	 * Creates two new player objects
	 */
	public void createPlayers() {
		white = new Player(Name.white);
		black = new Player(Name.black);
	}
	
	public void createBoard() {
		board = new Board(white,black);
	}
	
	public void start() {
		board.update();
		
		while(gameComplete == false) {

		}
	}
	
	@Override
	public void mousePressed(MouseEvent e) {
		squarePressed(e);
	}
	
	public void squarePressed(MouseEvent e) {
		Square pressedSquare = board.getSquares()[e.getY() / Display.CHECKER_SIZE][e.getX() / Display.CHECKER_SIZE];
		if (selectedSquare != null && selectedSquare.getPiece() != null && selectedSquare.getPiece().getValidMoves().contains(pressedSquare)) {
			selectedSquare.getPiece().move(board, pressedSquare.getPosition());
		} else {
			setSelectedSquare(pressedSquare);
		}
		board.update();
		display.revalidate();
		display.repaint();
	}
	
	@Override
	public void mouseReleased(MouseEvent e) {
	}
	
	@Override
	public void mouseClicked(MouseEvent e) {
	}

	@Override
	public void mouseEntered(MouseEvent e) {
	}

	@Override
	public void mouseExited(MouseEvent e) {
	}
}