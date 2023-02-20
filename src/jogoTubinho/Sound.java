package jogoTubinho;

import java.applet.Applet;
import java.applet.AudioClip;

public class Sound {

	private AudioClip clip;
	 
	public static final Sound musicaFundo = new Sound("/TESTE.wav");
	public static final Sound caminharSom = new Sound("/caminhar.wav");
	public static final Sound colisaoSom = new Sound("/colisao.wav");

	private Sound(String name) {
		try {

			clip = Applet.newAudioClip(Sound.class.getResource(name));
		} catch (Throwable e) {
		}

	}

	public void play() {
		try {

			new Thread() {
				public void run() {
					clip.play();
				}
			}.start();

		} catch (Throwable e) {
		}
	}
	public void loop() {
		try {
			
			new Thread() {
				public void run() {
					clip.play();
				}
			}.start();
			
		} catch (Throwable e) {
		}
	}
}
