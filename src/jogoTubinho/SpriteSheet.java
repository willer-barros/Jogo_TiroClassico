package jogoTubinho;

import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

public class SpriteSheet {

	private BufferedImage spritesheet;

	public SpriteSheet(String path) {
		try {
			spritesheet = ImageIO.read(getClass().getResource(path));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public BufferedImage pegarSprite(int x, int y, int width, int height) {
		return spritesheet.getSubimage(x, y, width, height);
	}

}
