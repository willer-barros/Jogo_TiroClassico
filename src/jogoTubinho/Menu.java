package jogoTubinho;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;

public class Menu {

	public  boolean cima, baixo, enter;
	public boolean pause = false;

	public String[] opcoes = { "Novo Jogo", "Carregar", "Sair" };
	public int opcaoAtual = 0;
	public int opcaoMax = opcoes.length - 1;

	public void logica() {
		Sound.musicaFundo.loop();
		if (cima) {
			cima = false;
			opcaoAtual--;
			if (opcaoAtual < 0) {
				opcaoAtual = opcaoMax;
			}
		}
		if (baixo) {
			baixo = false;
			opcaoAtual++;
			if (opcaoAtual > opcaoMax) {
				opcaoAtual = 0;
			}
		}
		if (enter) {
			enter = false;
			if (opcoes[opcaoAtual] == "Novo jogo" || opcoes[opcaoAtual] == "continuar") {
				Jogo.estadoDeJogo = "NORMAL";
				pause = false;
			}
		}
	}

	public void renderizar(Graphics g) {
		Graphics2D g2 = (Graphics2D) g;
		g2.setColor(new Color(0, 0, 0, 150));
		g2.fillRect(0, 0, (Jogo.WIDTH * Jogo.SCALE), (Jogo.HEIGTH * Jogo.SCALE));
		// titulo do jogo
		g.setColor(Color.red);
		g.setFont(new Font("arial", Font.BOLD, 36));
		g.drawString(">>Atirador<<", (Jogo.WIDTH * Jogo.SCALE - 100) / 2, (Jogo.HEIGTH * Jogo.SCALE - 550) / 2);
		// menu do jogo
		g.setColor(Color.white);
		g.setFont(new Font("arial", Font.BOLD, 24));
		if (pause == false)
			g.drawString("Novo Jogo", (Jogo.WIDTH * Jogo.SCALE - 100) / 2, (Jogo.HEIGTH * Jogo.SCALE - 470) / 2);
		else
			g.drawString("Resumir", (Jogo.WIDTH * Jogo.SCALE - 100) / 2, (Jogo.HEIGTH * Jogo.SCALE - 470) / 2);
		g.drawString("Carregar", (Jogo.WIDTH * Jogo.SCALE - 100) / 2, (Jogo.HEIGTH * Jogo.SCALE - 410) / 2);
		g.drawString("Sair", (Jogo.WIDTH * Jogo.SCALE - 100) / 2, (Jogo.HEIGTH * Jogo.SCALE - 350) / 2);

		if (opcoes[opcaoAtual] == "Novo Jogo") {
			g.drawString(">", (Jogo.WIDTH * Jogo.SCALE) / 2 - 70, 165);
		} else if (opcoes[opcaoAtual] == "Carregar") {
			g.drawString(">", (Jogo.WIDTH * Jogo.SCALE) / 2 - 70, 195);
		} else if (opcoes[opcaoAtual] == "Sair") {
			g.drawString(">", (Jogo.WIDTH * Jogo.SCALE) / 2 - 70, 225);
		}

	}

}
