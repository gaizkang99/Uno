package utils;

import java.util.Random;

public enum Numero {
	CERO, UNO, DOS, TRES, CUATRO, CINCO, SEIS, SIETE, OCHO, NUEVE, CAMBIO, MASDOS, SALTO, CAMBIOCOLOR, MASCUATRO;
	
	public static Numero numeroRandomColor() {
		Random r = new Random();
		return Numero.values()[r.nextInt(13)];
	}
	public static Numero numeroRandomNegro() {
		Random r = new Random();
		return Numero.values()[r.nextInt(2)+13];
	}
	
}
