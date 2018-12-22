import java.awt.Color;
import java.awt.Graphics;

import javax.swing.JFrame;
import javax.swing.JPanel;

public class Board extends JPanel{
	
	public static final int CHECKER_SIZE = 80;
	public static final int FRAME_WIDTH = CHECKER_SIZE * 8 + 16;
	public static final int FRAME_HEIGHT = CHECKER_SIZE * 8 + 40;
	
	private Game game;
	
    public Board (Game g){
    	game = g;
        JFrame frame = new JFrame();
        frame.setSize(FRAME_WIDTH,FRAME_HEIGHT);
        frame.getContentPane().add(this);
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }
    
    public void paint(Graphics g){
    	addMouseListener(game);

    	g.setColor(Color.LIGHT_GRAY);
    	g.fillRect(0, 0, CHECKER_SIZE * 8, CHECKER_SIZE * 8);
    	
    	g.setColor(Color.BLACK);
    	//i == rows j == columns
    	//Checkers are black when the row index is even and the column index is odd or the row index is odd and the column index is even
    	for (int i = 0; i < 8; i++) {
    		for (int j = 0; j < 8; j++) {
    			if ((i % 2 == 0 && j % 2 == 1) || (i % 2== 1 && j % 2 == 0)) {
    				g.fillRect(i*CHECKER_SIZE, j*CHECKER_SIZE, CHECKER_SIZE, CHECKER_SIZE);
    			}
    		}
    	}
    }
}