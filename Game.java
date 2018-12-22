import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class Game implements MouseListener{
	
	boolean gameComplete = false;
	enum Player {
		WHITE, BLACK
	}
	
	public void start() {
		Player turn = Player.WHITE;

		while(gameComplete == false);
	}
	
	@Override
	public void mousePressed(MouseEvent e) {
		squarePressed(e);

	}
	public void squarePressed(MouseEvent e) {
		System.out.println((e.getX() / Board.CHECKER_SIZE) + " " + e.getY() / Board.CHECKER_SIZE);
	}

	
	
	@Override
	public void mouseReleased(MouseEvent e) {

		// TODO Auto-generated method stub

	}
	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseEntered(MouseEvent e) {


	}

	@Override
	public void mouseExited(MouseEvent e) {
}
}