package jogoTubinho;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

public class Tile {

	public static BufferedImage TILE_FLOOR = Jogo.spritesheet.pegarSprite(0, 64, 32, 32);
	public static BufferedImage TILE_WALL = Jogo.spritesheet.pegarSprite(32, 64, 32, 32);

	public BufferedImage sprite;

	private int x, y;

	public Tile(int x, int y, BufferedImage sprite) {
		this.x = x;
		this.y = y;
		this.sprite = sprite;
	}

	public void renderizar(Graphics g) {
		g.drawImage(sprite, x - Camera.x, y - Camera.y, null);
	}

}
