package es.escape.room.m.controlador;

import java.awt.Color;
import java.awt.Font;

import es.escape.room.m.modelo.JDialog_Alert;

/**
 * Clase controladora para los label de tiempo. Estos label deben de manejarse
 * en un hilo aparte, para evitar bloquear la aplicacion. Por ello, se utiliza
 * un Thread.
 * 
 * @author Juanlu
 * 
 */
public class CronometroLabel_Controller extends Thread {

	private Controlador cont;
	private boolean run;
	private int segundosPocoTiempo;/* Tiempo que utiliza para pintar los label a rojo
								 	  indicando que queda poco tiempo
									*/
	/**
	 * Clase de inicio de CronometroLabel_Controller al que le llega el 
	 * controlador y los segundos para alertar de que queda poco tiempo
	 * @param cont
	 * @param segundosPocoTiempo
	 */
	public CronometroLabel_Controller(Controlador cont, int segundosPocoTiempo) {
		this.cont = cont;
		this.segundosPocoTiempo = segundosPocoTiempo;
		this.run = true;
	}

	/**
	 * Inicia el hilo - Envia el texto del cronometro constantemente a los label
	 * mientras la variable sea true
	 */
	public void run() {
		//Se crea la fuente de Game Over
		Font fntGameOver = new Font("Tempus Sans ITC", Font.BOLD, 80);
		
		// Comprtueba si el flag esta activo
		while (this.isRun()) {
			// Actualiza los valores de los label
			this.getCont().getVu().getLbl_tiempo()
					.setText(this.getCont().getCron().toString());
			this.getCont().getVa().getLbl_tiempo()
					.setText(this.getCont().getCron().toString());

			// Se comprueba si el tiempo es mayor de un límite fijado para
			// cambiarle el color a los label
			if (this.getCont().getCron().getSegundos() > segundosPocoTiempo) {
				this.getCont().getVu().getLbl_tiempo().setForeground(Color.WHITE);
				this.getCont().getVa().getLbl_tiempo().setForeground(Color.WHITE);
			} else {
				this.getCont().getVu().getLbl_tiempo().setForeground(Color.RED);
				this.getCont().getVa().getLbl_tiempo().setForeground(Color.RED);
			}

			// Se comprueba si el tiempo es 0 y se actualiza el boton, lo hace
			// solamente una vez
			// utilizando la variable isShown0Alert
			if (this.getCont().getCron().tiempoEs0()
					&& this.getCont().isShown0Alert()) {
				/* Indica que el boton de tiempo esta inactivo - Boton a PLAY */
				this.getCont().setBtnTimeActive(false);
				this.getCont().getVa().getBtn_tiempo()
						.setIcon(this.getCont().getVa().getPlayImgIcon());
				this.getCont().getVa().getBtn_tiempo().setToolTipText("PLAY");

				// Se muestra la alerta
				this.getCont().setAlert(
						new JDialog_Alert(this.getCont().getVa().getFrame(),
								"Se ha acabado el tiempo"));
				
				//Se limpian los textos e imagenes
				this.getCont().limpiarMsg();
				this.getCont().limpiarImg();
				
				//Se muestra en las ventanas el mensaje - Game Over - 
				this.getCont().getVu().getTa_texto().setText("Game Over");
				this.getCont().getVa().getTa_texto().setText("Game Over");
				this.getCont().getVu().getTa_texto().setForeground(Color.RED);
				this.getCont().getVa().getTa_texto().setForeground(Color.RED);
				
				this.getCont().getVu().getTa_texto().setFont(fntGameOver);
				
				// No vuelve a entrar en el if de nuevo, para ello es necesario
				// poner esta variable a true
				this.getCont().setShown0Alert(false);

			}
		}

	}

	/**
	 * Stop the thread
	 * */
	public void stopThread() {
		this.setRun(false);
	}

	/* Getters & Setters */
	public boolean isRun() {
		return run;
	}

	public Controlador getCont() {
		return cont;
	}

	public void setCont(Controlador cont) {
		this.cont = cont;
	}

	public void setRun(boolean run) {
		this.run = run;
	}
}