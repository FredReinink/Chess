public class RunChess {
	public static void main(String[] args) {
		Game game = new Game();
		Board board = new Board(game);
		game.start();
	}
}
