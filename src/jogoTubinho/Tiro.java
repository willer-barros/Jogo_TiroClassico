package jogoTubinho;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;

public class Tiro extends Arma {

	private int dx;
	private int dy;
	private int velocidade = 4;

	private int vidaTiro = 70, curVidaTiro = 0;

	public Tiro(int x, int y, int width, int height, BufferedImage sprite, int dx, int dy) {
		super(x, y, width, height, sprite);

		this.dx = dx;
		this.dy = dy;
		
	}

	public void logica() {
		x += dx * velocidade;
		y += dy * velocidade;
		curVidaTiro++;
		if (vidaTiro == curVidaTiro) {
			Jogo.tiro.remove(this);
			return;
		}

	}

	public void renderizar(Graphics g) {
		g.setColor(Color.yellow);
		g.fillOval(this.getX() - Camera.x, this.getY() - Camera.y, width, height);

	}

}
