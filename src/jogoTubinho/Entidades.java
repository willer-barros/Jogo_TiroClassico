package jogoTubinho;

import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;

public class Entidades {

	public static BufferedImage INIMIGO = Jogo.spritesheet.pegarSprite(96, 0, 32, 32);
	public static BufferedImage ARMA = Jogo.spritesheet.pegarSprite(64, 64, 32, 32);
	public static BufferedImage FEEDBACK_INIMIGO = Jogo.spritesheet.pegarSprite(160, 0, 32, 32);

	public int x;
	public int y;
	public int width;
	public int height;
	public BufferedImage sprite;

	private int xmasc, ymasc, widthmasc, heightmasc;

	public Entidades(int x, int y, int width, int height, BufferedImage sprite) {
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
		this.sprite = sprite;

		this.xmasc = 0;
		this.ymasc = 0;
		this.widthmasc = width;
		this.heightmasc = height;
	}

	public void setMasc(int xmasc, int ymasc, int widthmasc, int heightmasc) {
		this.xmasc = xmasc;
		this.ymasc = ymasc;
		this.widthmasc = widthmasc;
		this.heightmasc = heightmasc;
		;
	}

	public void setX(int newX) {
		this.x = newX;
	}

	public void setY(int newY) {
		this.y = newY;
	}

	public int getX() {
		return (int) this.x;
	}

	public int getY() {
		return (int) this.y;
	}

	public int getWidth() {
		return this.width;
	}

	public int getHeight() {
		return this.height;
	}

	public void logica() {

	}

	public static boolean colisao(Entidades e1, Entidades e2) {
		Rectangle e1masc = new Rectangle(e1.getX() + e1.xmasc, e1.getY() + e1.ymasc, e1.widthmasc, e1.heightmasc);
		Rectangle e2masc = new Rectangle(e2.getX() + e2.xmasc, e2.getY() + e2.ymasc, e2.widthmasc, e2.heightmasc);

		return e1masc.intersects(e2masc);
	}

	public void renderizar(Graphics g) {
		g.drawImage(sprite, getX() - Camera.x, getY() - Camera.y, null);

	}

}
