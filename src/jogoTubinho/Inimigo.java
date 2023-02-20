package jogoTubinho;

import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;

public class Inimigo extends Entidades {

	private int velocidade = 1;
	public int frames = 0, maxFrames = 15, index = 0, maxIndex = 1;
	public BufferedImage[] spriteInimigo;
	public static int tile_size = 32;
	private int vidaInimigo = 3;

	private boolean estaComDano = false;
	private int danoFrames = 2, danoAtual = 0;

	public Inimigo(int x, int y, int width, int height, BufferedImage sprite) {
		super(x, y, width, height, null);

		spriteInimigo = new BufferedImage[2];
		spriteInimigo[0] = Jogo.spritesheet.pegarSprite(96, 0, tile_size, tile_size);
		spriteInimigo[1] = Jogo.spritesheet.pegarSprite(128, 0, tile_size, tile_size);
	}

	public void logica() {
		if (colisaoComJogador() == false) {
			if (x < Jogo.jogador.getX() && Mundo.livre(x + velocidade, y) && !colisao(x + velocidade, y)) {
				x += velocidade;
			} else if (x > Jogo.jogador.getX() && Mundo.livre(x - velocidade, y) && !colisao(x - velocidade, y)) {
				x -= velocidade;
			}
			if (y < Jogo.jogador.getY() && Mundo.livre(x, y + velocidade) && !colisao(x, y + velocidade)) {
				y += velocidade;
			} else if (y > Jogo.jogador.getY() && Mundo.livre(x, y - velocidade) && !colisao(x, y - velocidade)) {
				y -= velocidade;
			}
		} else {
			if (Jogo.rand.nextInt(100) < 10) {
				Sound.colisaoSom.play();
				Jogo.jogador.vida -= Jogo.rand.nextInt(3);
				Jogo.jogador.estaComDano = true;
				if (Jogo.jogador.vida <= 0) {
					// System.exit(1);
				}
				System.out.println("Vida: " + Jogo.jogador.vida);
			}
		}

		frames++;
		if (frames == maxFrames) {
			frames = 0;
			index++;
			if (index > maxIndex) {
				index = 0;
			}

			colisaoInimigoComTiro();
			if (vidaInimigo <= 0) {
				destroiInimigo();
				return;

			}
			if (estaComDano) {
				danoAtual++;
				if (danoAtual == danoFrames) {
					danoAtual = 0;
					estaComDano = false;
				}
			}
		}

	}

	public void destroiInimigo() {
		Jogo.inimigos.remove(this);
		Jogo.entidades.remove(this);
		
	}

	public void colisaoInimigoComTiro() {
		for (int i = 0; i < Jogo.tiro.size(); i++) {
			Entidades e = Jogo.tiro.get(i);
			if (e instanceof Tiro) {
				if (Entidades.colisao(this, e)) {
					estaComDano = true;
					vidaInimigo--;
					Jogo.tiro.remove(i);
					return;
				}
			}
		}
	}

	public boolean colisaoComJogador() {
		Rectangle inimigoAlvo = new Rectangle(this.getX(), this.getY(), Mundo.TILE_SIZE, Mundo.TILE_SIZE);
		Rectangle jogador = new Rectangle(Jogo.jogador.getX(), Jogo.jogador.getY(), Mundo.TILE_SIZE, Mundo.TILE_SIZE);

		return inimigoAlvo.intersects(jogador);
	}

	public boolean colisao(int xprox, int yprox) {
		Rectangle inimigoAtual = new Rectangle(xprox, yprox, Mundo.TILE_SIZE, Mundo.TILE_SIZE);
		for (int i = 0; i < Jogo.inimigos.size(); i++) {
			Inimigo ii = Jogo.inimigos.get(i);
			if (ii == this)
				continue;
			Rectangle inimigoAlvo = new Rectangle(ii.x, ii.y, Mundo.TILE_SIZE, Mundo.TILE_SIZE);
			if (inimigoAlvo.intersects(inimigoAtual)) {
				return true;
			}
		}
		return false;
	}

	public void renderizar(Graphics g) {
		if (!estaComDano) {
			g.drawImage(spriteInimigo[index], this.getX() - Camera.x, this.getY() - Camera.y, null);
		} else {
			g.drawImage(Entidades.FEEDBACK_INIMIGO, this.getX() - Camera.x, this.getY() - Camera.y, null);

		}
	}

}
