package manager;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Scanner;

import dao.Dao;
import model.Cartas;
import model.Jugador;
import utils.Color;
import utils.Numero;

public class Manager {
	private Dao d;
	private Scanner sc;
	private Jugador j;
	private Cartas carta;
	private ArrayList<Cartas> c;
	
	public Manager() {
		d = new Dao();
		sc = new Scanner(System.in);
		c = new ArrayList<Cartas>();
	}
		public void init() {
			
			try {
				//Loggin de jugadores 
				if(inicioSesion()) {
					System.out.println("Has entrado!!");
					iniciaPartida();
				}else {
					System.out.println("Las credenciales son incorrectas. Prueba otra vez: ");
					inicioSesion();
				}
			}catch(SQLException e){
				System.out.println("Error en la conexión con la BBDD");
				e.printStackTrace();
			}
		}
		
		
		//Pide las credenciales de un jugador y comprueba si esta registrado
		private boolean inicioSesion() throws SQLException {
			String name, pass;
			System.out.println("Usuario: ");
			name = sc.next();
			System.out.println("Contraseña: ");
			pass = sc.next();
			d.conectar();
			j = d.inicioSesion(name, pass);
			d.desconectar();
			if(j == null) return false;
			return true;
		}
		
		
		//Iniciada la sesion empieza la partida en este metodo
		private void iniciaPartida() throws SQLException {
			if(muestraUltimaCarta()!=false){
				System.out.println("La ultima carta jugada es: " + carta.toString());
				muestraManoJugador();
				comprueba(carta);
			}else {
				System.out.println("Iniciando la partida tira la primera carta: ");
				muestraManoJugador();
				jugar();
			}
		}
		
		//Devuelve la ultima carta jugada en la partida
		private boolean muestraUltimaCarta() throws SQLException {
			d.conectar();
			carta = d.muestraUltimaCarta();
			d.desconectar();
			if(carta!= null) return true;
			return false;	
		}
		
		//Muestra en un arraylist la mano del jugador por la id del jugador 
		private void muestraManoJugador() throws SQLException {
			int id = j.getId();
			d.conectar();
			if(d.manoJugador(id).isEmpty()) {
				robarCartas(7);
				muestraManoJugador();
			}else {
				c = d.manoJugador(id);
			}
			d.desconectar();
		}
		
		//Mostramos todas las cartas que el jugador tiene en la mano y damos opcion de robar
		private int opciones() {
			int opc = 0;
			System.out.println("-1 - Robar carta");
			for(int i=0;i<c.size();i++) {
				System.out.println(i + " - " + c.get(i).toString());
			}
			System.out.println("Tira una carta: ");
			opc = sc.nextInt();
			return opc;
		}
		
		
		//Crea una carta aleatoria
		private Cartas crearCarta() throws SQLException {
			String color = Color.colorRandom().toString();
			String numero = "";
			Cartas cartaNueva;
			if (!(color.equalsIgnoreCase("negro"))) {
				numero = Numero.numeroRandomColor().toString();
			}else {
				numero = Numero.numeroRandomNegro().toString();
			}
			d.conectar();
			cartaNueva = d.insertarCarta(d.idUltimaCarta()+1, j.getId(), numero, color);
			d.desconectar();
			return cartaNueva;
		}
		
		//Roba cartas del mazo indicando el numero de cartas que queremos robar por parametro
		private void robarCartas(int f) throws SQLException {
			d.conectar();
			for(int i=0; i<f; i++) {
				Cartas robada = crearCarta();
				c.add(robada);
				System.out.println("Has robado la siguiente carta: " + robada.toString());
			}
			d.desconectar();
		}
		
		//Comprueba cual es la ultima carta que se ha jugado 
		private void comprueba(Cartas c) throws SQLException {
			String numeroCarta = c.getNumero();
			d.conectar();
			if(numeroCarta.equalsIgnoreCase("masdos")) {
				robarCartas(2);
				jugar();
			}else if(numeroCarta.equalsIgnoreCase("mascuatro")){
				robarCartas(4);
				jugar();
			}else if(numeroCarta.equalsIgnoreCase("salto") || numeroCarta.equalsIgnoreCase("cambio")) {
				d.eliminarCarta(c.getId());
				System.exit(0);
			}else {
				jugar();
			}
			d.desconectar();
		}
		
		//Función que reproduce el juego, demanda una carta a tirar y la compara con la ultima carta lanzada
		//para comprobar que sigue siendo del mismo color que la anterior. Si no tienes cartas robas 1.
		private void jugar() throws SQLException{
			int opc = opciones();
			if(opc>=-1) {
					while(opc==-1) {
						robarCartas(1);
						opc = opciones();
					}
				if(opc!=-1) {
					if(muestraUltimaCarta()!=false){
						d.conectar();
						String colorUltimaCarta = carta.getColor();
						String colorCartaEscogida = c.get(opc).getColor();
						System.out.println("Has lanzado --> " + c.get(opc).toString());
						if(colorUltimaCarta.equalsIgnoreCase("negro") || colorCartaEscogida.equalsIgnoreCase("negro")) {
							d.cartaJugada(c.get(opc).getId());
							c.remove(opc);
						}else if (colorUltimaCarta.equalsIgnoreCase(colorCartaEscogida)) {
							d.cartaJugada(c.get(opc).getId());
							c.remove(opc);
						}else {
							System.out.println("No puedes tirar esta carta");
							jugar();
						}
						d.desconectar();
					}else {
						d.conectar();
						System.out.println("Has lanzado --> " + c.get(opc).toString());
						d.cartaJugada(c.get(opc).getId());
						c.remove(opc);
						d.desconectar();
					}
					//funcion para acabar el juego 
					d.desconectar();
				}else {
					System.out.println("Numero no válido. Vuelve a probar.");
					jugar();
				}
			}
			if(c.size()<1) {
				System.out.println("El jugador: " + j.getUsuario() + " ha ganado la partida.");
				d.conectar();
				d.partidaGanada(j.getId(), j.getGanadas(), j.getPartidas());
				d.desconectar();
			}
	}
}
