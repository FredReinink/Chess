package Runner;

import GUI.Display;

public class ChessApplet {
	public static void main(String[] args) {
		Controller controller = new Controller();
		Display display = new Display(controller);
		display.setBoard(controller.getBoard());
		controller.setDisplay(display);
		controller.start();
	}
}
