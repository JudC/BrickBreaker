package brickBreaker;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import javax.swing.Timer;
import javax.swing.JPanel;

public class Game extends JPanel implements KeyListener, ActionListener{
	private boolean play = false;
	private boolean end = false; 
	private boolean movesRight = false;
	private boolean movesLeft = false;
	private int level = 1;
	private int score = 0;
	private int mapY = 3;
	private int mapX = 6;
	private int totalBricks = mapY*mapX;

	private Timer timer;
	private int delay = 8;

	private int playerX = 297;
	private int ballposX = 340;
	private int ballposY = 535;
	private double ballXdir = -3;
	private double ballYdir = -7;
	private double bounceAngle =  Math.toRadians(80);
	private MapGenerator map;

	public Game() {
		map = new MapGenerator(mapY,mapX);
		addKeyListener(this);
		setFocusable(true);
		setFocusTraversalKeysEnabled(true);
		timer = new Timer(delay, this);
		timer.start();
	}

	public void paint(Graphics g){
		//background
		g.setColor(Color.white);
		g.fillRect(1, 1, 700, 592);
		
		// score background
		g.setColor(Color.lightGray);
		g.fillRect(690, 1, 300, 592);

		// drawing map
		map.draw((Graphics2D) g);

		// borders
		g.setColor(Color.lightGray);
		g.fill3DRect(0,560, 695, 20, true);
		
		g.setColor(Color.gray);
		g.fill3DRect(0, 0, 20, 600, true);
		g.fill3DRect(675, 0, 20, 600, true);
		g.fill3DRect(0, 0, 692, 20, true);

		// scores
		g.setColor(Color.black);
		g.setFont(new Font("sansserif", Font.BOLD, 20));
		g.drawString("Bricks Destroyed: "+ score, 705, 50);
		g.drawString("ball: "+ ballposX, 705, 170);
		g.drawString("paddle:" + (ballposX+15/2 - (playerX+50))/50.0, 705, 150);
		
		// level
		g.setColor(Color.black);
		g.setFont(new Font("sansserif", Font.PLAIN, 20));
		g.drawString("Level: "+ level, 705, 100);

		// the panel
		g.setColor(Color.red);
		g.fill3DRect(playerX, 550, 100, 10, true);

		// the ball
		g.setColor(Color.black);
		g.fillOval(ballposX, ballposY, 15, 15);

		if(totalBricks <= 0) {
			end = true;
			play = false;
			g.setColor(Color.black);
			g.setFont(new Font("sansserif", Font.PLAIN, 40));
			g.drawString("You won! Bricks destroyed: "+score, 90, 250);

			g.setColor(Color.red);
			g.setFont(new Font("sansserif", Font.PLAIN, 30));
			g.drawString("Press Enter to restart", 210, 350);
			g.drawString("Press Space to go to next level", 150, 400);
		}
		if(ballposY > 560) {
			end = true;
			play = false;
			g.setColor(Color.black);
			g.setFont(new Font("sansserif", Font.BOLD, 30));
			g.drawString("Game Over :( Bricks destroyed: "+ score, 100, 300);

			g.setColor(Color.red);
			g.setFont(new Font("sansserif", Font.PLAIN, 30));
			g.drawString("Press Enter to restart", 200, 370);
		}

		g.dispose();
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		timer.start();
		if (movesLeft && !end){
			if (playerX <=20){
				playerX = 20;
			}else {
				play = true;
				playerX-=5;
			}
		}
		if (movesRight && !end){
			if (playerX >=575){
				playerX = 575;
			}else{
				play = true;
				playerX+=5;
			}
		}
		if(play) {
			Rectangle paddlePos = new Rectangle(playerX, 550, 100, 10);
			Rectangle ballPos = new Rectangle (ballposX, ballposY, 15, 15);
			
			if(ballPos.intersects(paddlePos)){
				double relativeIntersection = java.lang.Math.abs((ballposX+15/2 - (playerX+50))/50);
				ballYdir = -relativeIntersection*4;
			}
			A: for(int i = 0; i<map.map.length; i++){
				for (int j = 0; j <map.map[0].length; j++){
					if (map.map[i][j] > 0){
						int brickX = j*map.brickWidth + 30;
						int brickY = i*map.brickHeight + 30;
						int brickWidth = map.brickWidth;
						int brickHeight = map.brickHeight;

						Rectangle brick = new Rectangle(brickX, brickY, brickWidth, brickHeight-5);

						if (ballPos.intersects(brick)){
							map.setBrickValue(0, i, j);
							totalBricks--;
							score += 1;

							if(ballposX + 19 <= brick.x || ballposX + 1 >= brick.x + brick.width){
								ballXdir = -ballXdir;
							} else {
								ballYdir = -ballYdir;
							}

							break A;
						}
					}

				}
			}

			ballposX += ballXdir;
			ballposY += ballYdir;

			if(ballposX < 20){
				ballXdir = -ballXdir;
			}
			if(ballposY < 20){
				ballYdir = -ballYdir;
			}
			if(ballposX > 660){
				ballXdir = -ballXdir;
			}

		}

		repaint();

	}

	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub

	}
	@Override
	public void keyReleased(KeyEvent e) {
		if(e.getKeyCode() == KeyEvent.VK_RIGHT){
			movesRight = false;
		}
		if(e.getKeyCode() == KeyEvent.VK_LEFT) {
			movesLeft = false;
		}
	}
	@Override
	public void keyPressed(KeyEvent e) {
		if(e.getKeyCode() == KeyEvent.VK_RIGHT){
			movesRight = true;
		}
		if(e.getKeyCode() == KeyEvent.VK_LEFT) {
			movesLeft = true;
		}
		if(e.getKeyCode() == KeyEvent.VK_ENTER){
			if(!play){
				end = false;
				ballposX = 340; ballposY = 535;
				ballXdir = -3;
				ballYdir = -3;
				playerX = 310;
				level = 1;
				score = 0;
				mapX = 6;
				mapY = 3;
				totalBricks = mapX*mapY;
				map = new MapGenerator(mapY, mapX);

				repaint();
			}
		}
		if(e.getKeyCode() == KeyEvent.VK_SPACE){
			if(!play && totalBricks <=0){
				ballposX = 350; ballposY = 530;
				if (ballXdir >= 0){
					ballXdir = -ballXdir-1;
				}else{
					ballXdir -=1;
				}

				if (ballYdir >= 0){
					ballYdir = -ballYdir-1;
				}else{
					ballYdir -=1;
				}
				playerX = 297;
				score = score;
				end = false;
				level+=1;
				mapX += 1;
				mapY += 1;
				totalBricks = mapX*mapY;
				map = new MapGenerator(mapY,mapX);

				repaint();
			}
		}

	}

}

