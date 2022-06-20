package exceptions;

import java.util.Arrays;
import java.util.List;

public class ExecutionsExceptions extends Exception {
	public static final int ERROR_Creacion = 0;
	public static final int ERROR_Lectura = 1;
	public static final int ERROR_Escritura = 2;
	public static final int ERROR_Cerrando = 3;
	
	private int value;
	
	public ExecutionsExceptions (int value) {
		this.value = value;
	}
	
	private List<String> message = Arrays.asList(
			"<< Error creando el fichero >>",
			"<< Error de lectura de fichero >>",
			"<< Error de escritura de fichero >>",
			"<< Error cerrando el fichero >>"
			);
			
	
	@Override
	public String getMessage() {
		return message.get(value);
	}
}
