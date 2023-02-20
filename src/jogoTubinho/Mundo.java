package jogoTubinho;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;

public class Mundo {

	public static Tile[] tile;
	public static int WIDTH, HEIGTH;
	public static int TILE_SIZE = 32;

	public Mundo(String path) {
		try {
			BufferedImage mapa = ImageIO.read(getClass().getResource(path));
			int[] pixel = new int[mapa.getWidth() * mapa.getHeight()];
			WIDTH = mapa.getWidth();
			HEIGTH = mapa.getHeight();
			tile = new Tile[mapa.getWidth() * mapa.getHeight()];
			mapa.getRGB(0, 0, mapa.getWidth(), mapa.getHeight(), pixel, 0, mapa.getWidth());
			for (int xx = 0; xx < mapa.getWidth(); xx++) {
				for (int yy = 0; yy < mapa.getHeight(); yy++) {

					int pixelAtual = pixel[xx + (yy * mapa.getWidth())];
					tile[xx + (yy * WIDTH)] = new Chao(xx * 32, yy * 32, Tile.TILE_FLOOR);

					if (pixelAtual == 0xFF000000) {
						// chao
						tile[xx + (yy * WIDTH)] = new Chao(xx * 32, yy * 32, Tile.TILE_FLOOR);
					} else if (pixelAtual == 0xFFFFFFFF) {
						// Parede
						tile[xx + (yy * WIDTH)] = new Parede(xx * 32, yy * 32, Tile.TILE_WALL);

					} else if (pixelAtual == 0xFFFF0000) {
						// Jogador
						Jogo.jogador.setX(xx * 32);
						Jogo.jogador.setY(yy * 32);

					} else if (pixelAtual == 0xFF0026FF) {
						// inimigos
						Inimigo in = new Inimigo(xx * 32, yy * 32, 32, 32, Entidades.INIMIGO);
						Jogo.entidades.add(in);
						Jogo.inimigos.add(in);

					} else if (pixelAtual == 0xFFFFD800) {
						Jogo.entidades.add(new Arma(xx * 32, yy * 32, 32, 32, Entidades.ARMA));

					}

				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static boolean livre(int xprox, int yprox) {
		int x1 = xprox / TILE_SIZE;
		int y1 = yprox / TILE_SIZE;

		int x2 = (xprox + TILE_SIZE - 1) / TILE_SIZE;
		int y2 = yprox / TILE_SIZE;

		int x3 = xprox / TILE_SIZE;
		int y3 = (yprox + TILE_SIZE - 1) / TILE_SIZE;

		int x4 = (xprox + TILE_SIZE - 1) / TILE_SIZE;
		int y4 = (yprox + TILE_SIZE - 1) / TILE_SIZE;

		return !((tile[x1 + (y1 * Mundo.WIDTH)] instanceof Parede) 
				|| (tile[x2 + (y2 * Mundo.WIDTH)] instanceof Parede)
				|| (tile[x3 + (y3 * Mundo.WIDTH)] instanceof Parede)
				|| (tile[x4 + (y4 * Mundo.WIDTH)] instanceof Parede));

	}
	
	public static void reiniciarLevel(String level) {
		Jogo.entidades.clear();
		Jogo.inimigos.clear();
		Jogo.entidades = new ArrayList<Entidades>();
		Jogo.inimigos = new ArrayList<Inimigo>();
		Jogo.spritesheet = new SpriteSheet("/spritesheet.png");
		Jogo.jogador = new Jogador(0, 0, 32, 32, Jogo.spritesheet.pegarSprite(32, 32, 16, 16));
		Jogo.entidades.add(Jogo.jogador);
		Jogo.mundo = new Mundo("/"+level);
		return;
	}

	public void renderizar(Graphics g) {

		int num = 32;

		int xstart = Camera.x / num;
		int ystart = Camera.y / num;

		int xFinal = xstart + (Jogo.WIDTH * Jogo.SCALE / num);
		int yFinal = ystart + (Jogo.HEIGTH * Jogo.SCALE / num);

		for (int xx = xstart; xx <= xFinal; xx++) {
			for (int yy = ystart; yy <= yFinal; yy++) {
				if (xx < 0 || yy < 0 || xx >= WIDTH || yy >= HEIGTH) {
					continue;
				}
				Tile telha = tile[xx + (yy * WIDTH)];
				telha.renderizar(g);
			}

		}
	}

}
