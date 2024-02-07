package es.escape.room.m.controlador;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;

public class MainEscapeRoomM {

	private static Controlador cont;

	/**
	 * @param args
	 */
	public static void main(String[] args) {

		try {
			// Se recogen los valores iniciales para configurar la APP a partir del
			// fichero properties
			Properties p = new Properties();
			p.load(new FileReader("res/config.properties"));
			int horas = Integer.parseInt(p.getProperty("horas"));
			int minutos = Integer.parseInt(p.getProperty("minutos"));
			int segundos = Integer.parseInt(p.getProperty("segundos"));
			int segundosPocoTiempo = Integer.parseInt(p
					.getProperty("segundosPocoTiempo"));
			String rutaPistas = p.getProperty("rutaPistas");

			// Iniciamos el controlador de manejo de cronometro y pantalla
			cont = new Controlador(horas, minutos, segundos,
					segundosPocoTiempo, rutaPistas);
			
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public Controlador getCont() {
		return cont;
	}

	public void setCont(Controlador cont) {
		MainEscapeRoomM.cont = cont;
	}

}
