package jogoTubinho;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import javax.swing.JFrame;

public class Jogo extends Canvas implements Runnable, KeyListener {

	/**
	 * 
	 */

	private static final long serialVersionUID = 1L;
	public JFrame frame;
	private Thread thread;
	public static final int WIDTH = 200;
	public static final int HEIGTH = 160;
	public static final int SCALE = 5;
	public boolean rodando;

	@SuppressWarnings("unused")
	private BufferedImage image;

	public static Mundo mundo;
	public static Jogador jogador;
	public static Random rand;
	public static List<Entidades> entidades;
	public static List<Inimigo> inimigos;
	public static List<Tiro> tiro;
	public static SpriteSheet spritesheet;
	public static double vida = 100;
	public static double maxVida = 100;
	public static Ui ui;
	private int CUR_LEVEL = 1, MAX_LEVEL = 2;
	public static String estadoDeJogo = "MENU";
	private boolean showMessageGameOver = true;
	private int framesGameOver = 0;
	private boolean reiniciarJogo = false;

	public static Menu menu;

	public Jogo() {
		Sound.musicaFundo.loop();
		rand = new Random();
		addKeyListener(this);
		setPreferredSize(new Dimension(WIDTH * SCALE, HEIGTH * SCALE));
		Inicializar();
		// inicializar objetos
		ui = new Ui();
		image = new BufferedImage(WIDTH, HEIGTH, BufferedImage.TYPE_INT_RGB);
		entidades = new ArrayList<Entidades>();
		inimigos = new ArrayList<Inimigo>();
		tiro = new ArrayList<Tiro>();
		spritesheet = new SpriteSheet("/spritesheet.png");
		jogador = new Jogador(0, 0, 32, 32, spritesheet.pegarSprite(32, 32, 16, 16));
		entidades.add(jogador);
		mundo = new Mundo("/level1.png");
		menu = new Menu();
	}

	public void Inicializar() {
		frame = new JFrame("Jogo 01");
		frame.add(this);
		frame.setResizable(false);
		frame.pack();
		frame.setLocationRelativeTo(null);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
	}

	public synchronized void start() {
		rodando = true;
		thread = new Thread(this);
		thread.start();
	}

	public synchronized void stop() {
		rodando = false;
		try {
			thread.join();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	public void logica() {
		if (estadoDeJogo == "NORMAL") {
			reiniciarJogo = false;
			for (int i = 0; i < entidades.size(); i++) {
				Entidades e = entidades.get(i);
				e.logica();
			}

			for (int i = 0; i < tiro.size(); i++) {
				tiro.get(i).logica();
				;
			}

			if (inimigos.size() == 0) {
				// System.out.println("proximo nivel");
				CUR_LEVEL++;
				if (CUR_LEVEL > MAX_LEVEL) {
					CUR_LEVEL = 1;
				}
				String novoMundo = "level" + CUR_LEVEL + ".png";
				Mundo.reiniciarLevel(novoMundo);
			}
		} else if (estadoDeJogo == "GAME_OVER") {
			framesGameOver++;
			if (framesGameOver == 30) {
				framesGameOver = 0;
				if (showMessageGameOver)
					showMessageGameOver = false;
				else
					showMessageGameOver = true;
			}
		}

		if (reiniciarJogo) {
			reiniciarJogo = false;
			estadoDeJogo = "NORMAL";
			CUR_LEVEL = 1;
			String novoMundo = "level" + CUR_LEVEL + ".png";
			Mundo.reiniciarLevel(novoMundo);
		}
		menu.logica();
	}

	public void renderizar() {
		BufferStrategy bs = getBufferStrategy();
		if (bs == null) {
			createBufferStrategy(3);
			return;
		}
		Graphics g = bs.getDrawGraphics();
		g.setColor(new Color(0, 0, 0));
		g.fillRect(0, 0, WIDTH * SCALE, HEIGTH * SCALE);
		mundo.renderizar(g);
		ui.renderizar(g);
		for (int i = 0; i < entidades.size(); i++) {
			Entidades e = entidades.get(i);
			e.renderizar(g);
		}
		for (int i = 0; i < tiro.size(); i++) {
			tiro.get(i).renderizar(g);
		}
		if (estadoDeJogo == "GAME_OVER") {
			Graphics2D g2 = (Graphics2D) g;
			g2.setColor(new Color(0, 0, 0, 150));
			g2.fillRect(0, 0, WIDTH * SCALE, HEIGTH * SCALE);
			g2.setFont(new Font("arial", Font.BOLD, 50));
			g2.setColor(Color.white);
			g2.drawString("Game Over!!!", (WIDTH * SCALE - 200) / 2, (HEIGTH * SCALE) / 2);

			g2.setFont(new Font("arial", Font.BOLD, 30));
			g2.setColor(Color.white);
			if (showMessageGameOver)
				g2.drawString("-> Pressione 'Enter' para Continuar <-", (WIDTH * SCALE - 450) / 2,
						(HEIGTH * SCALE + 100) / 2);

		} else if (estadoDeJogo == "MENU") {
			menu.renderizar(g);
		}
		bs.show();
	}

	public static void main(String[] args) {
		Jogo jogo = new Jogo();
		jogo.start();
	}

	public void run() {
		long tempo = System.nanoTime();
		double quantidadeFrames = 60.0;
		double nanoSegundo = 1000000000 / quantidadeFrames;
		double delta = 0;

		requestFocus();
		while (rodando) {
			long agora = System.nanoTime();
			delta += (agora - tempo) / nanoSegundo;

			if (delta >= 1) {
				logica();
				renderizar();
			}
		}
		stop();
	}

	public void keyTyped(KeyEvent e) {

	}

	public void keyPressed(KeyEvent e) {
		if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
			jogador.direita = true;
		} else if (e.getKeyCode() == KeyEvent.VK_LEFT) {
			jogador.esquerda = true;
		}
		if (e.getKeyCode() == KeyEvent.VK_UP) {
			jogador.cima = true;

			if (estadoDeJogo == "MENU") {
				menu.cima = true;
			}
		} else if (e.getKeyCode() == KeyEvent.VK_DOWN) {
			jogador.baixo = true;
			if (estadoDeJogo == "MENU") {
				menu.baixo = true;
			}

		}
		if (e.getKeyCode() == KeyEvent.VK_A) {
			jogador.shoot = true;
		}
		if (e.getKeyCode() == KeyEvent.VK_ENTER) {
			this.reiniciarJogo = true;
			if (estadoDeJogo == "MENU")
				menu.enter = true;
		}
		
		if(e.getKeyCode() == KeyEvent.VK_SPACE) {
			estadoDeJogo = "MENU";
			menu.pause = true;
		}
	}

	public void keyReleased(KeyEvent e) {
		if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
			jogador.direita = false;
		} else if (e.getKeyCode() == KeyEvent.VK_LEFT) {
			jogador.esquerda = false;
		}

		if (e.getKeyCode() == KeyEvent.VK_UP) {
			jogador.cima = false;
		} else if (e.getKeyCode() == KeyEvent.VK_DOWN) {
			jogador.baixo = false;
		}
	}
}
