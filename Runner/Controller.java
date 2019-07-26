package Runner;

import java.awt.event.MouseEvent;


import java.awt.event.MouseListener;
import java.io.Serializable;

import GUI.Display;
import Resources.Name;
import Logic.*;
import Pieces.*;

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
	
	/**
	 * Method switches the turn to the other player
	 */
	public void switchTurn() {
		if (turn == Name.white) {
			turn = Name.black;
		} else {
			turn = Name.white;
		}
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
		board = new Board(white,black,this);
	}
	
	public void start() {
		board.update();
		
		while(gameComplete == false) {

		}
	}
	
	/**
	 * Executes a move from the selectedSquare to the pressed square
	 * @param pressedSquare the square to move the piece to
	 */
	public void executeMove(Square pressedSquare) {
		selectedSquare.getPiece().move(board, pressedSquare.getPosition());
		switchTurn();
	}
	
	@Override
	public void mousePressed(MouseEvent e) {
		squarePressed(e);
	}
	
	/**
	 * Calls relevant GUI methods for pawn promotion and parses the response.
	 * 
	 * @param owner the owner of the piece the player chooses to promote.
	 * @return the type of piece that the player chooses to promote to.
	 */
	public Piece promotePawn(Player owner) {
		Piece selectedPiece = null;
		
		String selectedType = display.drawPawnPromotion(owner);
		if (selectedType.equals("Queen")) {
			selectedPiece = new Queen(owner);
		} else if (selectedType.equals("Rook")) {
			selectedPiece = new Rook(owner);
		} else if (selectedType.equals("Bishop")) {
			selectedPiece = new Bishop(owner);
		} else if (selectedType.equals("Knight")) {
			selectedPiece = new Knight(owner);
		} 
		return selectedPiece;
	}
	
	public void squarePressed(MouseEvent e) {
		Square pressedSquare = board.getSquares()[e.getY() / Display.CHECKER_SIZE][e.getX() / Display.CHECKER_SIZE];
		if (selectedSquare != null && selectedSquare.getPiece() != null && selectedSquare.getPiece().getValidMoves().contains(pressedSquare)) {
			executeMove(pressedSquare);
		} else if (pressedSquare.getPiece() != null && pressedSquare.getPiece().getOwner().getName() == turn) {
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