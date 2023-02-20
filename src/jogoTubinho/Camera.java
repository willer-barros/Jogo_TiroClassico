package jogoTubinho;

public class Camera {
	
	
	public static int x = 0;
	public static int y = 0;
	
	public static int clamp(int atual, int minimo, int maximo) {
	if(atual < minimo) {
		atual = minimo;
	}
		if(atual > maximo) {
			atual = maximo;
		}
		
		return atual;
	}

}
