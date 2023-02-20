package jogoTubinho;

import java.awt.Color;
import java.awt.Graphics;

public class Ui {

	public void renderizar(Graphics g) {
		g.setColor(Color.red);
		g.fillRect(8, 8, 100, 8);
		g.setColor(Color.green);
		g.fillRect(8, 8, (int) ((Jogo.jogador.vida / Jogo.jogador.vidaMax) * 100), 8);
	}

}
