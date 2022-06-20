package utils;

public class Constantes {
	//Constantes para la conexion
		public static final String Schema_name = "dam2tm06uf2p1";
		public static final String Connection = "jdbc:mysql://localhost:3306/" + Schema_name
				+ "?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC&useSSL=false";
		public static final String Name = "root";
		public static final String Password = "DAM1T_M03";
		
		//Queries SQL
		public static final String Inicio_Sesion = "select * from jugador where usuario=? and password=?";
		public static final String Ultima_Carta = "select c.* from carta c left join partida p on p.id_carta = c.id where p.id = (select max(id) from partida)";
		public static final String Id_UltimaCarta = "select max(id) from carta";
		public static final String Mano_Jugador = "select * from carta where id_jugador=?"; 
		public static final String Insertar_Carta = "insert into carta values(?,?,?,?)";
		public static final String Insertar_Carta_Tablero = "insert into carta (id, numero, color) values(?,?,?)";
		public static final String Llenar_Tablero = "insert into partida (id_carta) values (?)";
		public static final String Eliminar_Carta = "delete from carta where id=?";
		public static final String Eliminar_Carta_Partida = "delete from partida where id_carta=?";
		public static final String Actualizar_Jugador = "update carta set id_jugador=null where id=?";
		public static final String Actualizar_Partidas = "update jugador set partidas=?";
		public static final String Actualizar_Ganadas = "update jugador set ganadas=? where id=?";
		public static final String Eliminar_Cartas = "delete from carta";
		public static final String Eliminar_Partida = "delete from partida";
}
