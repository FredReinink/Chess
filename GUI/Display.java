package GUI;
import Logic.ChessUtility;
import Logic.Player;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.UIManager;

import Logic.Board;
import Logic.Square;
import Pieces.*;
import Resources.Name;
import Runner.Controller;

public class Display extends JPanel{

	public static final int CHECKER_SIZE = 80;
	public static final int FRAME_WIDTH_OFFSET = 6;
	public static final int FRAME_HEIGHT_OFFSET = 29;
	public static final int FRAME_WIDTH = CHECKER_SIZE * 8 + FRAME_WIDTH_OFFSET;
	public static final int FRAME_HEIGHT = CHECKER_SIZE * 8 + FRAME_HEIGHT_OFFSET;

	public static final String BROWN_RGB_VALUE = "10046464";
	public static final Color WHITE_SQUARE_COLOUR = Color.LIGHT_GRAY;
	public static final Color BLACK_SQUARE_COLOUR = Color.decode(BROWN_RGB_VALUE); 


	private Controller controller;
	private Graphics g;
	private Board board;
	private JFrame frame;

	public void setBoard(Board board) {
		this.board = board;
	}

	public Display (Controller c){
		controller = c;
		addMouseListener(controller);
		frame = new JFrame();
		frame.setSize(FRAME_WIDTH,FRAME_HEIGHT);
		frame.getContentPane().add(this);
		frame.setLocationRelativeTo(null);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setTitle("Chess");
		frame.setResizable(false);
		frame.setVisible(true);
	}

	public void paint(Graphics g){
		this.g = g;

		g.setColor(WHITE_SQUARE_COLOUR);
		g.fillRect(0, 0, CHECKER_SIZE * 8, CHECKER_SIZE * 8);

		g.setColor(BLACK_SQUARE_COLOUR);
		//Paints the checkers. Checkers are black when the row index is even and the column index is odd or the row index is odd and the column index is even
		//i == rows j == columns
		for (int i = 0; i < 8; i++) {
			for (int j = 0; j < 8; j++) {
				if ((i % 2 == 0 && j % 2 == 1) || (i % 2 == 1 && j % 2 == 0)) {
					g.fillRect(i*CHECKER_SIZE, j*CHECKER_SIZE, CHECKER_SIZE, CHECKER_SIZE);
				}
			}
		}

		drawAllPieces();
		drawSquareHighlights();
	}

	/**
	 * Draws square highlghting corresponding to a selected piece's valid moves.
	 */
	public void drawSquareHighlights(){
		Square selectedSquare = controller.getSelectedSquare();

		try {
			System.out.println("====================================================================\n");
			System.out.println("the selected piece is " + selectedSquare.getPiece() + " at position Row: " + selectedSquare.getRow() + " Column: " + selectedSquare.getColumn());
			ArrayList<Square> possibleMoves = selectedSquare.getPiece().getPossibleMoves();
			ArrayList<Square> validMoves = selectedSquare.getPiece().getValidMoves();


			System.out.println("this piece has " + possibleMoves.size() + " possible moves");
			System.out.println("this piece has " + validMoves.size() + " valid moves");

			for (Square s : validMoves) {
				g.setColor(Color.YELLOW);
				g.drawRect(s.getColumn()*CHECKER_SIZE, s.getRow()*CHECKER_SIZE, CHECKER_SIZE, CHECKER_SIZE);
				//make rectangle thicker
				g.drawRect((s.getColumn()*CHECKER_SIZE)+1, (s.getRow()*CHECKER_SIZE)+1, CHECKER_SIZE-2, CHECKER_SIZE-2);
				g.drawRect((s.getColumn()*CHECKER_SIZE)+2, (s.getRow()*CHECKER_SIZE)+2, CHECKER_SIZE-4, CHECKER_SIZE-4);
			}
		} catch (NullPointerException e) {
			//do nothing. Null pointer here means selectedSquare has not been initialized yet.
		}
	}

	/**
	 * Draws all pieces on the board
	 */
	public void drawAllPieces() {
		Square[][] squares = board.getSquares();

		for (int i = 0; i < 8; i++) {
			for (int j = 0; j < 8; j++) {
				Piece piece = squares[i][j].getPiece();
				if (piece != null) {
					drawPiece(ChessUtility.findFile(piece),squares[i][j]);
				}
			}
		}
	}

	/**
	 * Draws the image contained by the file on the specified square
	 * 
	 * @param pieceFile the file containing the image to draw
	 * @param square the square to draw the image on
	 */
	public void drawPiece(File pieceFile, Square square) {
		Image pieceImage;
		pieceImage = getScaledImage(pieceFile);
		g.drawImage(pieceImage, transformCoordinate(square.getColumn()), transformCoordinate(square.getRow()), null);
	}

	/**
	 * Returns the image contained by the specified file. The Image is scaled to fit within the defined checker size.
	 * 
	 * @param imageFile the file containing the image.
	 * @return A scaled version of the image.
	 */
	public Image getScaledImage(File imageFile) {
		Image scaledImage = null;
		try {
			scaledImage = ImageIO.read(imageFile).getScaledInstance(CHECKER_SIZE, CHECKER_SIZE, 0);
		} catch (IOException e) {
			System.out.println("Error reading image");
			e.printStackTrace();
		}
		return scaledImage;
	}
	
	
	/**
	 * Creates a JOptionPane that displays the winner of the game and lets the user decide whether to restart or quit.
	 * 
	 * Method ref: https://docs.oracle.com/javase/tutorial/uiswing/components/dialog.html
	 * 
	 * @param winner the player who won
	 * @return whether the user wants to continue playing. False otherwise
	 */
	public boolean continueAfterCheckmate(Player winner) {
		String winnerString = "";
		if (winner.getName() == Name.white) {
			winnerString = "White";
		} else if (winner.getName() == Name.black) {
			winnerString = "Black";
		}
		
		ImageIcon winnerIcon = new ImageIcon(getScaledImage(ChessUtility.findFile(new King(winner))));
		
		Object[] options = {"Restart",
		"Quit"};
		int n = JOptionPane.showOptionDialog(frame,
				winnerString + " Wins!",
				"Checkmate!",
				JOptionPane.YES_NO_OPTION,
				JOptionPane.PLAIN_MESSAGE,
				winnerIcon,
				options,
				null);
		
		Boolean continueGame = false;
		if (n == 0) {
			continueGame = true;
		} 
		System.out.println(n);
		return continueGame;
	}

	/**
	 * Creates a dialog box that allows the user to select a piece to promote a pawn to.
	 * 
	 * @param owner the player that owns the pawn to be promoted
	 * @return The type of piece the user selects ("Queen", "Rook", "Bishop", or "Knight")
	 */
	public String drawPawnPromotion(Player owner) {
		ImageIcon queenImage = new ImageIcon(getScaledImage(ChessUtility.findFile(new Queen(owner))));
		ImageIcon rookImage = new ImageIcon(getScaledImage(ChessUtility.findFile(new Rook(owner))));
		ImageIcon bishopImage = new ImageIcon(getScaledImage(ChessUtility.findFile(new Bishop(owner))));
		ImageIcon knightImage = new ImageIcon(getScaledImage(ChessUtility.findFile(new Knight(owner))));

		JOptionPane promotionPane = new JOptionPane();
		JButton queenButton = getButton(promotionPane, "Queen", queenImage, WHITE_SQUARE_COLOUR);
		JButton rookButton = getButton(promotionPane, "Rook", rookImage, BLACK_SQUARE_COLOUR);
		JButton bishopButton = getButton(promotionPane, "Bishop", bishopImage, WHITE_SQUARE_COLOUR);
		JButton knightButton = getButton(promotionPane, "Knight", knightImage, BLACK_SQUARE_COLOUR);
		
		promotionPane.setOptions(new Object[] {queenButton, rookButton, bishopButton, knightButton});
		promotionPane.setMessage(null);

		JDialog dialog = promotionPane.createDialog(frame, "Promote to");
		dialog.setDefaultCloseOperation(JDialog.DO_NOTHING_ON_CLOSE);
		dialog.setAlwaysOnTop(true);
		dialog.setVisible(true);

		String selectedType = (String) promotionPane.getValue();
		System.out.println(selectedType);
		return selectedType;
	}

	/**
	 * Helper method for drawPawnPromotion. Creates a JButton with properties corresponding to this function's parameters. Adds this JButton to the specified JOptionPane.
	 * 
	 * @param optionPane The option pane to add the button to
	 * @param buttonValue The string to assign the button's action command as
	 * @param image The image to draw on the button
	 * @param backGroundColour The background colour of the button
	 * @return the created button
	 */
	public JButton getButton(JOptionPane optionPane, String buttonValue, ImageIcon image, Color backGroundColour) {
		JButton button = new JButton(image);
		button.setBackground(backGroundColour);
		button.setActionCommand(buttonValue);
		ActionListener actionListener = new ActionListener() {
			public void actionPerformed(ActionEvent actionEvent) {
				optionPane.setValue(button.getActionCommand());
			}
		};
		button.addActionListener(actionListener);
		return button;
	}

	/**
	 * Transforms a board coordinate into an absolute coordinate on the JFrame
	 * @param coord the board coordinate to transform
	 * @return returns the absolute position of the coord
	 */
	public int transformCoordinate(int coord) {
		return coord * CHECKER_SIZE;
	}
	
	/**
	 * Sends a close window event to the game's JFrame
	 */
	public void closeGame() {
		frame.dispatchEvent(new WindowEvent(frame, WindowEvent.WINDOW_CLOSING));
	}

}