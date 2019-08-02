package Runner;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import GUI.Display;
import Resources.Name;
import Logic.*;
import Pieces.*;

/**
 * Manager class. Handles GUI/logic communication and overall game states. 
 * 
 * @author Fred Reinink
 */
public class Controller implements MouseListener{
	
	private Display display = null;
	private Player white;
	private Player black;
	private Board board;
	private Name turn;
	private Square selectedSquare; //The current user selected square
	
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
	
	public void setDisplay(Display d) {
		display = d;
	}
	
	public Controller() {
		turn = Name.white;
		createPlayers();
		createBoard();
	}
	
	public void createPlayers() {
		white = new Player(Name.white, true);
		black = new Player(Name.black, true);
	}
	
	public void createBoard() {
		board = new Board(white,black,this);
	}
	
	/**
	 * Switches the turn to the other player
	 */
	public void switchTurn() {
		if (turn == Name.white) {
			turn = Name.black;
		} else {
			turn = Name.white;
		}
	}
	
	/**
	 * Starts the game.
	 */
	public void start() {
		board.update();
	}
	
	/**
	 * Restarts the game.
	 */
	public void restart() {
		createPlayers();
		createBoard();
		display.setBoard(board);
		selectedSquare = null;
		turn = Name.black; //switchTurn() is called immediately after and so after a restart it's still whites turn first.
		start();
	}
	
	/**
	 * Moves the piece on the selected square to the new selected square.
	 * 
	 * @param newSelectedSquare the square to move the piece to.
	 */
	public void executeMove(Square newSelectedSquare) {
		selectedSquare.getPiece().move(board, newSelectedSquare.getPosition());
		board.update();
		switchTurn();
	}
	
	@Override
	public void mousePressed(MouseEvent event) {
		int row = Display.transformFrameCoordinate(event.getY());
		int column = Display.transformFrameCoordinate(event.getX());
		
		squarePressed(row, column);
	}
	
	/**
	 * If the previously selected square contains a piece and the newly selected square is a square that the piece is able to move to, moves the piece to the new square.
	 * If the newly selected square contains a piece that can move on the current turn, displays it's valid moves.
	 * 
	 * @param row the row of the square pressed.
	 * @param column the column of the square pressed.
	 */
	public void squarePressed(int row, int column) {
		
		Square[][] squares = board.getSquares();
		Square newSelectedSquare = squares[row][column];
		
		if (selectedSquare != null && selectedSquare.getPiece() != null && selectedSquare.getPiece().getValidMoves().contains(newSelectedSquare)) {
			executeMove(newSelectedSquare);
		} else if (newSelectedSquare.getPiece() != null && newSelectedSquare.getPiece().getOwner().getName() == turn) {
			setSelectedSquare(newSelectedSquare);
		}
		
		display.update();
	}
	
	/**
	 * Ends the game and calls relevant GUI methods to display the specified player as the winner. Closes the game if the player decides to quit, restarts otherwise.
	 * 
	 * @param winner the Name of the winner
	 */
	public void checkmate(Player winner) {
		if (!display.continueAfterCheckmate(winner)){
			display.closeGame();
		} else {
			restart();
		}
	}
	
	/**
	 * Ends the game and calls relevant GUI methods to display a draw. Closes the game if the player decides to quit, restarts otherwise.
	 */
	public void draw(String typeOfDraw) {
		if (!display.continueAfterDraw(typeOfDraw)){
			display.closeGame();
		} else {
			restart();
		}
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

	@Override
	public void mouseClicked(MouseEvent e) {
	}

	@Override
	public void mouseEntered(MouseEvent e) {
	}

	@Override
	public void mouseExited(MouseEvent e) {
	}

	@Override
	public void mouseReleased(MouseEvent e) {
	}
}