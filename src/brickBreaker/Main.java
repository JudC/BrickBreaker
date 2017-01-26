package brickBreaker;

import javax.swing.JFrame;

public class Main {

	public static void main(String[] args) {
			JFrame frame = new JFrame();
			Game game = new Game();
			frame.setSize(920,615);
			frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			frame.setTitle("Brick Ball");
			frame.setLocationRelativeTo(null);
			frame.setResizable(false);
			frame.setVisible(true);
			frame.add(game);
	}

}
