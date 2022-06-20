package dao;

import java.sql.*;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;

import model.Cartas;
import model.Jugador;
import utils.Constantes;

public class Dao {
	
	private Connection conexion;
	
	//Conexión y desconexión con la base de datos 
	public void conectar() throws SQLException {
		String url = Constantes.Connection;
		String usuario = Constantes.Name;
		String contrasena = Constantes.Password;
			//Conexion
			conexion = DriverManager.getConnection(url, usuario, contrasena);
	}
	public void desconectar() throws SQLException {
		if(conexion != null) {
				conexion.close();
		}
	}
	
	public Jugador inicioSesion(String user,String pass) throws SQLException{	
		Jugador j = null;
		try (PreparedStatement ps = conexion.prepareStatement(Constantes.Inicio_Sesion)) { 
    	  	ps.setString(1,user);
    	  	ps.setString(2, pass);
            try (ResultSet rs = ps.executeQuery()) {
            	if (rs.next()) {
            		j = new Jugador();
            		j.setId(rs.getInt("id"));
	                j.setUsuario(rs.getString("usuario"));
	                j.setContrasena(rs.getString("password"));
	                j.setNombre(rs.getString("nombre"));
	                j.setPartidas(rs.getInt("partidas"));
	                j.setGanadas(rs.getInt("ganadas"));
            	}
            }
        }
		return j;
	}
	
	public Cartas muestraUltimaCarta() throws SQLException{
		Cartas c = null;
		try (PreparedStatement ps = conexion.prepareStatement(Constantes.Ultima_Carta)) {
            try (ResultSet rs = ps.executeQuery()) {
            	if (rs.next()) {
            		c = new Cartas();
            		c.setId(rs.getInt("id"));
            		c.setId_jugador(rs.getInt("id_jugador"));
            		c.setNumero(rs.getString("numero"));
            		c.setColor(rs.getString("color"));	
            	}
            }
        }
		return c;
	}
	
	public int idUltimaCarta() throws SQLException{
		int i = 0;
		try(PreparedStatement ps = conexion.prepareStatement(Constantes.Id_UltimaCarta)){
			try(ResultSet rs = ps.executeQuery()){
				if(rs.next()) {
					i = rs.getInt(1);
				}
			}
		}
		return i;
	}
	
	public ArrayList<Cartas> manoJugador(int id) throws SQLException{
		ArrayList<Cartas> c = new ArrayList<Cartas>();
		try(PreparedStatement ps = conexion.prepareStatement(Constantes.Mano_Jugador)){
			ps.setInt(1, id);
			try(ResultSet rs = ps.executeQuery()){
				while(rs.next()) {
					Cartas carta = new Cartas();
					carta.setId(rs.getInt("id"));
            		carta.setId_jugador(rs.getInt("id_jugador"));
            		carta.setNumero(rs.getString("numero"));
            		carta.setColor(rs.getString("color"));
            		c.add(carta);
				}
			}
		}
		return c;
	}
	
	
	public Cartas insertarCarta(int id, int id_jugador, String numero, String color) throws SQLException{
		Cartas carta = null;
		try(PreparedStatement ps = conexion.prepareStatement(Constantes.Insertar_Carta)){
			ps.setInt(1, id);
			ps.setInt(2, id_jugador);
			ps.setString(3, numero);
			ps.setString(4, color);
			ps.executeUpdate();
			carta = new Cartas();
			carta.setId(id);
            carta.setId_jugador(id_jugador);
            carta.setNumero(numero);
            carta.setColor(color);
		}
		return carta;
	}
	
	public Cartas insertarCartaTablero(int id, String numero, String color) throws SQLException{
		Cartas carta = null;
		try(PreparedStatement ps = conexion.prepareStatement(Constantes.Insertar_Carta_Tablero)){
			ps.setInt(1, id);
			ps.setString(2, numero);
			ps.setString(3, color);
			ps.executeUpdate();
			carta = new Cartas();
			carta.setId(id);
            carta.setNumero(numero);
            carta.setColor(color);
		}
		try(PreparedStatement ps = conexion.prepareStatement(Constantes.Llenar_Tablero)){
			ps.setInt(1, id);
			ps.executeUpdate();
		}
		return carta;
	}
	
	public void eliminarCarta(int id) throws SQLException{
		try(PreparedStatement ps = conexion.prepareStatement(Constantes.Eliminar_Carta_Partida)){
			ps.setInt(1, id);
			ps.executeUpdate();
		}try(PreparedStatement ps = conexion.prepareStatement(Constantes.Eliminar_Carta)){
			ps.setInt(1, id);
			ps.executeUpdate();
		}
	}
	
	public void cartaJugada(int id) throws SQLException{
		try(PreparedStatement ps = conexion.prepareStatement(Constantes.Actualizar_Jugador)){
			ps.setInt(1, id);
			ps.executeUpdate();
		}
		try(PreparedStatement ps = conexion.prepareStatement(Constantes.Llenar_Tablero)){
			ps.setInt(1, id);
			ps.executeUpdate();
		}
	}
	
	public void partidaGanada(int id, int ganadas, int partidas) throws SQLException{
		try(PreparedStatement ps = conexion.prepareStatement(Constantes.Actualizar_Ganadas)){
			ps.setInt(1, ganadas+1);
			ps.setInt(2, id);
			ps.executeUpdate();
		}
		try(PreparedStatement ps = conexion.prepareStatement(Constantes.Actualizar_Partidas)){
			ps.setInt(1, partidas+1);
			ps.executeUpdate();
		}
		try(PreparedStatement ps = conexion.prepareStatement(Constantes.Eliminar_Partida)){
			ps.executeUpdate();
		}
		try(PreparedStatement ps = conexion.prepareStatement(Constantes.Eliminar_Cartas)){
			ps.executeUpdate();
		}
	}
}
