package utils;

import java.util.Random;

public enum Color {
	ROJO, AMARILLO, VERDE, AZUL, NEGRO;
	
	public static Color colorRandom() {
		Random r = new Random();
		return Color.values()[r.nextInt(Color.values().length)];
	}
}
