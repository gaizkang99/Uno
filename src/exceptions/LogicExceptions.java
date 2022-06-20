package exceptions;

import java.util.Arrays;
import java.util.List;

public class LogicExceptions extends Exception {
	public static final int Conexion_fallida = 0;
	public static final int Desconexion_fallida = 1;
	
	
	private int value;
	
	public LogicExceptions (int value) {
		this.value = value;
	}
	
	private List<String> message = Arrays.asList(
			"<< La conexión con la base de datos es erronea >>",
			"<< No se ha podido desconectar de la base de datos >>"
			);
			
	
	@Override
	public String getMessage() {
		return message.get(value);
	}
}
