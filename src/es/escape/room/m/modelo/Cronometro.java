package es.escape.room.m.modelo;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

import javax.swing.Timer;

public class Cronometro extends Thread {

	private Timer cronometro; // Declaramos un objeto Timer con el nombre de
								// Reloj.
	int segundos; // Una variable para manejar la cuenta regresiva.
	private int segundosInicio; // Para recordar los segundos en caso de
								// reiniciar la cuenta regresiva.
	private int delay; // Cada cúantos milisegundos nuestro Timer hará
						// una acción,

	// en este caso, se actualiza cada segundo.

	public Cronometro(int horas, int minutos, int segundos_n) {
		/* Se convierten los segundos */
		this.segundos = horas * 3600 + minutos * 60 + segundos_n;
		/* Se guardan los segundos iniciales en caso de reseteo */
		this.segundosInicio = this.segundos;
		this.delay = 1000;

		ActionListener taskPerformer = new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent evt) {
				// Comprueba si segundos es igual a cero para detener el Timer.
				if (segundos <= 0) {
					cronometro.stop(); // Detiene el timer y se deja de ejecutar
										// ésto.
				} else {
					segundos--; // Reduce la cantidad de segundos.
				}
			}
		}; // Fin de la declaración del ActionListener.

		cronometro = new Timer(delay, taskPerformer);
		// cronometro.setRepeats(false);
	}

	/**
	 * Iniciar cronometro
	 */
	public void startCron() {
		cronometro.start();
	}

	/**
	 * Parar cronometro
	 */
	public void stopCron() {
		cronometro.stop();
	}

	/**
	 * Reiniciar cronometro
	 */
	public void restartCron() {
		this.segundos = this.segundosInicio;
		cronometro.restart();
		cronometro.stop();
	}

	/**
	 * Sumar tiempo al cronometro
	 * 
	 * @param segs
	 */
	public void sumarTiempo(int segs) {
		this.segundos = this.segundos + segs;
	}

	/**
	 * Restar tiempo al cronometro. Si los segundos son menores a 0, se quedan
	 * en 0.
	 * 
	 * @param segs
	 */
	public void restarTiempo(int segs) {
		this.segundos = this.segundos - segs;
		if (this.segundos < 0)
			this.segundos = 0;
	}

	/**
	 * Se comprueba si el cronometro se ha quedado sin tiempo
	 * 
	 * @return
	 */
	public boolean tiempoEs0() {
		boolean rtn = false;
		if (this.getSegundos() == 0)
			rtn = true;
		return rtn;
	}

	@Override
	public String toString() {
		/* Se devuelve la hora en HH:mm:ss */
		Date d = new Date(segundos * 1000L);
		SimpleDateFormat df = new SimpleDateFormat("HH:mm:ss"); // HH for 0-23
		df.setTimeZone(TimeZone.getTimeZone("GMT"));
		String time = df.format(d);

		return time;
	}

	// Getters & Setters

	public Timer getCronometro() {
		return cronometro;
	}

	public void setCronometro(Timer cronometro) {
		this.cronometro = cronometro;
	}

	public int getSegundos() {
		return segundos;
	}

	public void setSegundos(int segundos) {
		this.segundos = segundos;
	}

	public int getSegundosInicio() {
		return segundosInicio;
	}

	public void setSegundosInicio(int segundosInicio) {
		this.segundosInicio = segundosInicio;
	}

	public int getDelay() {
		return delay;
	}

	public void setDelay(int delay) {
		this.delay = delay;
	}

}
