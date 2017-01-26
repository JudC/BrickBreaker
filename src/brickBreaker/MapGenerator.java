package brickBreaker;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.util.Random;


public class MapGenerator {
	public int map[][];
	public int brickWidth;
	public int brickHeight;
	Random rand = new Random();
	// Java 'Color' class takes 3 floats, from 0 to 1.
	float r = rand.nextFloat();
	float g = rand.nextFloat();
	float b = rand.nextFloat();
	
	Color randomColor = new Color(r, g, b);
	
	public MapGenerator (int row, int col) {
		map = new int [row][col];
		for (int i = 0; i<map.length; i++){
			for (int j=0; j< map[0].length;j++){
				map[i][j] = 1;
			}
		}
		
		brickWidth = 640/col;
		brickHeight = 150/row;
	}
	public void draw(Graphics2D g) {
		for (int i = 0; i<map.length; i++){
			for (int j=0; j< map[0].length;j++){
				if (map[i][j] > 0){
					g.setColor(randomColor);
					g.fillRect(j * brickWidth + 30, i * brickHeight + 30, brickWidth, brickHeight);
					
					g.setStroke(new BasicStroke(5));
					g.setColor(Color.white);
					g.drawRect(j * brickWidth + 30, i * brickHeight + 30, brickWidth, brickHeight);
				}
			}
		}
	}
	public void setBrickValue(int value, int row, int col){
		map[row][col] = value;
	}
}

