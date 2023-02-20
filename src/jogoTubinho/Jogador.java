package jogoTubinho;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

public class Jogador extends Entidades {

	public boolean direita, esquerda, cima, baixo;
	public int direita_dir = 0;
	public int esquerda_dir = 1;
	public int dir = direita_dir;

	public int velocidade = 2;

	public int frames = 0, maxFrames = 30, index = 0, maxIndex = 2;
	public boolean mover = false;
	public BufferedImage[] direitaJogador;
	public BufferedImage[] esquerdaJogador;
	private BufferedImage danoJogador;
	private boolean arma = false;

	public boolean estaComDano;
	private int danoFrames = 0;

	public double vida = 100, vidaMax = 100;

	public boolean shoot = false;

	public Jogador(int x, int y, int width, int height, BufferedImage sprite) {
		super(x, y, width, height, sprite);

		direitaJogador = new BufferedImage[3];
		esquerdaJogador = new BufferedImage[3];
		danoJogador = Jogo.spritesheet.pegarSprite(96, 32, 32, 32);

		for (int i = 0; i < 3; i++) {
			direitaJogador[i] = Jogo.spritesheet.pegarSprite(0 + (i * 32), 0, 32, 32);
		}
		for (int i = 0; i < 3; i++) {
			esquerdaJogador[i] = Jogo.spritesheet.pegarSprite(0 + (i * 32), 32, 32, 32);
		}

	}

	public void logica() {
		mover = false;
		if (direita && Mundo.livre(x + velocidade, y)) {
			mover = true;
			dir = direita_dir;
			x += velocidade;
		} else if (esquerda && Mundo.livre(x - velocidade, y)) {
			mover = true;
			dir = esquerda_dir;
			x -= velocidade;
		}
		if (cima && Mundo.livre(x, y - velocidade)) {
			mover = true;
			y -= velocidade;
		} else if (baixo && Mundo.livre(x, y + velocidade)) {
			mover = true;
			y += velocidade;
		}

		if (mover) {
			frames++;
			if (frames == maxFrames) {
				frames = 0;
				index++;
				if (index > maxIndex) {
					index = 0;
				}
			}
		}
		pegarArma();

		if (estaComDano) {
			danoFrames++;
			if (danoFrames == 8) {
				danoFrames = 0;
				estaComDano = false;
			}

		}

		if (shoot) {
			shoot = false;
			if (arma) {
				int dx = 0;
				int px = 0;
				int py = 14;
				if (dir == direita_dir) {
					dx = 1;
					px = 20;
				} else {
					dx = -1;
					px = 4;
				}
				Tiro tiros = new Tiro(this.getX() + px, this.getY() + py, 4, 4, null, dx, 0);
				Jogo.tiro.add(tiros);
			}
		}

		if (vida <= 0) {
			Jogo.estadoDeJogo = "GAME_OVER";

		}

		Camera.x = Camera.clamp(this.getX() - (Jogo.WIDTH / 2), 0, Mundo.WIDTH * 8 - 32 - Jogo.WIDTH);
		Camera.y = Camera.clamp(this.getY() - (Jogo.HEIGTH / 2), 0, Mundo.HEIGTH * 15 - Jogo.HEIGTH);
	}

	public void pegarArma() {
		for (int i = 0; i < Jogo.entidades.size(); i++) {
			Entidades atual = Jogo.entidades.get(i);
			if (atual instanceof Arma) {
				if (Entidades.colisao(this, atual)) {
					arma = true;
					// System.out.println("Pegou a arma");
					Jogo.entidades.remove(atual);
				}
			}
		}
	}

	public void renderizar(Graphics g) {
		if (!estaComDano) {
			if (dir == direita_dir) {
				g.drawImage(direitaJogador[index], this.getX() - Camera.x, this.getY() - Camera.y, null);
			} else if (dir == esquerda_dir) {
				g.drawImage(esquerdaJogador[index], this.getX() - Camera.x, this.getY() - Camera.y, null);

			}
		} else {
			g.drawImage(danoJogador, this.getX() - Camera.x, this.getY() - Camera.y, null);
		}
	}
}
