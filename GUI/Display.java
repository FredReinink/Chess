package GUI;
import Logic.ChessUtility;

import Logic.Player;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import Logic.Board;
import Logic.Square;
import Pieces.*;
import Resources.Name;
import Runner.Controller;

/**
 * This class handles all GUI related functions.
 * 
 * @author Fred Reinink
 * With Ref: https://docs.oracle.com/javase/tutorial/uiswing/components/dialog.html
 *
 */
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

	/**
	 * Sets the instance of board the GUI will display.
	 */
	public void setBoard(Board board) {
		this.board = board;
	}
	
	/**
	 * Repaints the JFrame.
	 */
	public void update() {
		revalidate();
		repaint();
	}

	/**
	 * Sole constructor. Creates and initializes the JFrame with needed parameters.
	 * 
	 * @param controller the instance of controller to set as the JFrame's actionListener.
	 */
	public Display (Controller controller){
		this.controller = controller;
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
		for (int i = 0; i < Board.NUM_ROWS; i++) {
			for (int j = 0; j < Board.NUM_COLS; j++) {
				if ((i % 2 == 0 && j % 2 == 1) || (i % 2 == 1 && j % 2 == 0)) {
					g.fillRect(i*CHECKER_SIZE, j*CHECKER_SIZE, CHECKER_SIZE, CHECKER_SIZE);
				}
			}
		}

		drawAllPieces();
		drawSquareHighlights();
	}

	/**
	 * Draws the square highlights around a selected piece's valid moves.
	 */
	public void drawSquareHighlights(){
		Square selectedSquare = controller.getSelectedSquare();

		try {
			
			ArrayList<Square> validMoves = selectedSquare.getPiece().getValidMoves();
			
			for (Square s : validMoves) {
				int leftEdge = s.getColumn() * CHECKER_SIZE;
				int topEdge = s.getRow() * CHECKER_SIZE;
				int checkerSize = CHECKER_SIZE;
				
				g.setColor(Color.YELLOW);
				g.drawRect(leftEdge, topEdge, checkerSize, checkerSize);
				
				//make rectangle thicker
				leftEdge++;
				topEdge++;
				checkerSize -= 2;
				g.drawRect(leftEdge, topEdge, checkerSize, checkerSize);
				
				leftEdge++;
				topEdge++;
				checkerSize -= 2;
				g.drawRect(leftEdge, topEdge, checkerSize, checkerSize);
			}
		} catch (NullPointerException e) {
			//do nothing. Null pointer here means selectedSquare has not been initialized yet.
		}
	}

	/**
	 * Draws all pieces on the board according to their pieceImage and position on the board.
	 */
	public void drawAllPieces() {
		Square[][] squares = board.getSquares();

		for (int i = 0; i < Board.NUM_ROWS; i++) {
			for (int j = 0; j < Board.NUM_COLS; j++) {
				Square squareToDraw = squares[i][j];
				Piece pieceToDraw = squareToDraw.getPiece();
				
				if (pieceToDraw != null) {
					File fileToDraw = ChessUtility.findFile(pieceToDraw);
					drawImage(fileToDraw, squareToDraw);
				}
			}
		}
	}

	/**
	 * Draws the image contained by the specified file on the specified square,
	 * 
	 * @param fileToDraw the file containing the image to draw.
	 * @param squareToDraw the square to draw the image on.
	 */
	public void drawImage(File fileToDraw, Square squareToDraw) {
		Image pieceImage = getScaledImage(fileToDraw);
		
		int x = transformBoardCoordinate(squareToDraw.getColumn());
		int y = transformBoardCoordinate(squareToDraw.getRow());
		g.drawImage(pieceImage, x, y, null);
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
	 * @param winner the player who won.
	 * @return true if the user wants to continue playing. False otherwise.
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
		if (n == 0) { //0 corresponds to the "Restart" option in the JOptionPane.
			continueGame = true;
		} 
		return continueGame;
	}
	
	/**
	 * Creates a JOptionPane that shows a draw and asks the user if they would like to restart or quit.
	 * 
	 * Method ref: https://docs.oracle.com/javase/tutorial/uiswing/components/dialog.html
	 * 
	 * @param typeOfDraw the type of draw to display to the player.
	 * @return true if the user wants to continue playing. False otherwise.
	 */
	public boolean continueAfterDraw(String typeOfDraw) {
		Object[] options = {"Restart",
		"Quit"};
		int n = JOptionPane.showOptionDialog(frame,
				"Draw! " + typeOfDraw,
				"Draw!",
				JOptionPane.YES_NO_OPTION,
				JOptionPane.PLAIN_MESSAGE,
				null,
				options,
				null);
		
		Boolean continueGame = false;
		if (n == 0) { //0 corresponds to the "Restart" option in the JOptionPane.
			continueGame = true;
		} 
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
		return selectedType;
	}

	/**
	 * Helper method for drawPawnPromotion. Creates a JButton with properties corresponding to this function's parameters. Adds this JButton to the specified JOptionPane.
	 * 
	 * @param optionPane The option pane to add the button to.
	 * @param buttonValue The string to assign the button's action command as.
	 * @param image The image to draw on the button.
	 * @param backGroundColour The background colour of the button.
	 * @return the created button.
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
	 * Transforms a board coordinate (row # or column #) into an absolute coordinate on the JFrame.
	 * Ex: Row 2, Column 3 on the board could be x = 160, y = 240 on the JFrame.
	 * 
	 * @param coord the board coordinate to transform.
	 * @return the JFrame coordinate corresponding to the board coordinate.
	 */
	public static int transformBoardCoordinate(int coord) {
		return coord * CHECKER_SIZE;
	}

	/**
	 * Transforms a JFrame coordinate (x, y) into a board coordinate (row # or column #).
	 * Ex: x = 160, y = 240 on the JFrame could be Row 2, Column 3 on the board.
	 * 
	 * @param coord the JFrame coordinate to transform.
	 * @return the board coordinate corresponding to the JFrame coordinate.
	 */
	public static int transformFrameCoordinate(int coord) {
		return coord / CHECKER_SIZE;
	}
	
	/**
	 * Closes the game window.
	 */
	public void closeGame() {
		frame.dispatchEvent(new WindowEvent(frame, WindowEvent.WINDOW_CLOSING));
	}

}