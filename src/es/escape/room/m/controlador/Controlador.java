package es.escape.room.m.controlador;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.Image;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.Action;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;

import es.escape.room.m.modelo.Cronometro;
import es.escape.room.m.modelo.ImagePreviewPanel;
import es.escape.room.m.modelo.JDialog_Alert;
import es.escape.room.m.vista.VentanaAdministrador;
import es.escape.room.m.vista.VentanaUsuario;

public class Controlador {

	private Cronometro cron;
	private VentanaUsuario vu;
	private VentanaAdministrador va;
	private CronometroLabel_Controller clabel_cont;
	private ControladorListener cnt_Listener;
	private boolean btnTimeActive;
	private boolean isShown0Alert;
	private boolean isFullScreen;
	private JFileChooser fc;
	private ImagePreviewPanel preview;
	private JDialog_Alert alert;

	/**
	 * Se inician los objetos del controlador
	 */
	public Controlador(int horas, int minutos, int segundos, int segundosPocoTiempo, 
			String rutaPistas) {
		cron = new Cronometro(horas, minutos, segundos);
		vu = new VentanaUsuario();
		va = new VentanaAdministrador();
		fc = new JFileChooser();
		
		//Ruta por defecto JFileChooser
		File curDir = new File(rutaPistas);
		if (curDir.exists()){
			fc.setCurrentDirectory(curDir);
		}
		//Ver detalles de la imagen
		Action details = fc.getActionMap().get("viewTypeDetails");
		details.actionPerformed(null);
		//Previsualizador de imagenes
		preview = new ImagePreviewPanel();
		fc.setAccessory(preview);
		fc.addPropertyChangeListener(preview);

		/* Iniciar Ventana de Usuario */
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					vu.getFrame().setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});

		/* Iniciar Ventana de Administrador */
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					va.getFrame().setVisible(true);
					va.getFrame().requestFocus();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});

		/*
		 * Flag que indica si el boton de tiempo esta a Play o Stop 
		 * - Stop = false, Play = True
		 */
		btnTimeActive = false;
		
		/* Flag que indica si debe de mostrarse la alerta cuando el boton llega a 0 
		 El boton solamente debe de mostrarse una vez*/
		isShown0Alert = true;
		/* Flag que indica si la pantalla esta completa */
		this.isFullScreen=false;
		
		/* Controlador del label */
		clabel_cont = new CronometroLabel_Controller(this, segundosPocoTiempo);

		/* Iniciar los label de tiempo por primera vez */
		getVu().getLbl_tiempo().setText(this.getCron().toString());
		getVa().getLbl_tiempo().setText(this.getCron().toString());

		/* Agregar el listener a los botones */
		cnt_Listener = new ControladorListener(this);
		this.getVa().getBtn_enviar_msg().addActionListener(cnt_Listener);
		this.getVa().getBtn_reset_tiempo().addActionListener(cnt_Listener);
		this.getVa().getBtn_tiempo().addActionListener(cnt_Listener);
		this.getVa().getBtn_limpiar_msg().addActionListener(cnt_Listener);
		this.getVa().getBtn_ctr_sumar().addActionListener(cnt_Listener);
		this.getVa().getBtn_ctr_restar().addActionListener(cnt_Listener);
		this.getVa().getBtnScreen().addActionListener(cnt_Listener);
		this.getVa().getBtn_cargar_img().addActionListener(cnt_Listener);
		this.getVa().getBtn_limpiar_img().addActionListener(cnt_Listener);

		/* Iniciar los Threads */
		cron.start();
		clabel_cont.start();

	}

	/**
	 * Actualizar el Label dinamicamente, generando un nuevo hilo
	 */
	public void iniciarCronometro() {
		/* Iniciar el temporizador */
		this.getCron().startCron();
		/* Indica que el boton de tiempo esta activo - Boton a PAUSE */
		this.setBtnTimeActive(true);
		this.getVa().getBtn_tiempo().setIcon(this.getVa().getPauseImgIcon());
		this.getVa().getBtn_tiempo().setToolTipText("PAUSE");
	}

	/**
	 * Parar el cronometro momentaneamente
	 */
	public void pararCronometro() {
		/* Para el cronometro */
		this.getCron().stopCron();
		/* Indica que el boton de tiempo esta inactivo - Boton a PLAY */
		this.setBtnTimeActive(false);
		this.getVa().getBtn_tiempo().setIcon(this.getVa().getPlayImgIcon());
		this.getVa().getBtn_tiempo().setToolTipText("PLAY");
	}

	/**
	 * Deja de actualizar el label del tiempo
	 */
	public void resetCronometro() {

		/* Reinicia y para el cronometro */
		this.getCron().restartCron();
		
		//Se actualiza la variable de control del mensaje cuando el tiempo es 0 
		this.setShown0Alert(true);
		
		/* Indica que el boton de tiempo esta inactivo - Boton a PLAY */
		this.setBtnTimeActive(false);
		this.getVa().getBtn_tiempo().setIcon(this.getVa().getPlayImgIcon());
		this.getVa().getBtn_tiempo().setToolTipText("PLAY");
		
		//Se deja como color blanco (por defecto) el texto en ambas pantallas
			// Se hace antes de limpiar el mensaje porque si no, no funciona... 
		this.getVu().getTa_texto().setForeground(Color.WHITE);
		this.getVa().getTa_texto().setForeground(Color.WHITE);
		
		this.getVu().getTa_texto().setFont(new Font("Tempus Sans ITC", Font.PLAIN, 39));
		
		//Limpiamos las pantallas
		this.limpiarMsg();
		this.limpiarImg();
		
	}

	/**
	 * Actualizar el mensaje de las pantallas a partir del texto seleccionado
	 */
	public void actualizarMsg() {
		this.getVu().getTa_texto().setText(this.getVa().getTf_msg().getText());
		this.getVa().getTa_texto().setText(this.getVa().getTf_msg().getText());
		// Dejamos el textArea con la config por defecto
		this.getVa().getTf_msg().setText("");
		this.getVa().getTf_msg().setRows(3);
	}

	/**
	 * Limpiar el mensaje de las pantallas
	 */
	public void limpiarMsg() {
		this.getVu().getTa_texto().setText("");
		this.getVa().getTa_texto().setText("");
	}

	/**
	 * Sumar tiempo al cronometro a partir de los textfield
	 */
	public void sumarTiempo() {
		try {
			int horas = Integer.parseInt(this.getVa().getTf_ctr_horas()
					.getText());
			int minutos = Integer.parseInt(this.getVa().getTf_ctr_minutos()
					.getText());
			int segundos = Integer.parseInt(this.getVa().getTf_ctr_segundos()
					.getText());

			int segundosTotales = horas * 3600 + minutos * 60 + segundos;

			this.getCron().sumarTiempo(segundosTotales);

			// Se resetean los textos
			this.getVa().getTf_ctr_horas().setText("00");
			this.getVa().getTf_ctr_minutos().setText("00");
			this.getVa().getTf_ctr_segundos().setText("00");

			// Se muestra la alerta
			alert = new JDialog_Alert(this.getVa().getFrame(),
					"Se han añadido " + horas + " horas, " + minutos
							+ " minutos y " + segundos + " segundos");
			
			//Se actualiza la variable de control del mensaje cuando el tiempo es 0 
			this.setShown0Alert(true);
		} catch (NumberFormatException e) {
			alert = new JDialog_Alert(this.getVa().getFrame(),
					"El dato introducido para horas, minutos y segundos "
							+ "debe de ser un número entero.");
		}
	}

	/**
	 * Restar tiempo al cronometro a partir de los textfield
	 */
	public void restarTiempo() {
		try {
			int horas = Integer.parseInt(this.getVa().getTf_ctr_horas()
					.getText());
			int minutos = Integer.parseInt(this.getVa().getTf_ctr_minutos()
					.getText());
			int segundos = Integer.parseInt(this.getVa().getTf_ctr_segundos()
					.getText());

			int segundosTotales = horas * 3600 + minutos * 60 + segundos;

			this.getCron().restarTiempo(segundosTotales);

			// Se resetean los textos
			this.getVa().getTf_ctr_horas().setText("00");
			this.getVa().getTf_ctr_minutos().setText("00");
			this.getVa().getTf_ctr_segundos().setText("00");

			// Se muestra la alerta
			alert = new JDialog_Alert(this.getVa().getFrame(),
					"Se han quitado " + horas + " horas, " + minutos
							+ " minutos y " + segundos + " segundos");
			
			//Se actualiza la variable de control del mensaje cuando el tiempo es 0 
			this.setShown0Alert(true);
			
		} catch (NumberFormatException e) {
			e.printStackTrace();
			alert = new JDialog_Alert(this.getVa().getFrame(),
					"El dato introducido para horas, minutos y segundos "
							+ "debe de ser un número entero.");
		}
	}

	/**
	 * Actulizar el estado de la pantalla de usuario. 
	 * Para ello, utiliza el JFrame Base y el JFrame Full Screen asignando los objectos
	 */
	public void cambiarPantalla() {
		//Si es fullScreen, cambia a la pantalla base. 
		//En caso contrario, cambia a pantalla completa
		if (isFullScreen){
			this.getVu().getFrame().dispose();
			this.getVu().getFrame().setUndecorated(false);
			this.getVu().getFrame().setVisible(true);
			this.getVa().getBtnScreen().setText("FULL");
			this.isFullScreen=false;
		}else{
			this.getVu().getFrame().dispose();
			this.getVu().getFrame().setUndecorated(true);
			this.getVu().getFrame().setVisible(true);
			this.getVa().getBtnScreen().setText("BASE");
			this.isFullScreen=true;
		}
		
	}
	
	/**
	 * Metodo utilizado para cargar una imagen en los JLabel de mensaje.
	 */
	public void cargarImg() {
		fc.setDialogTitle("Buscar imagen");

		// Se comprueba que el usuario haya clicado en el boton aceptar
		if (fc.showOpenDialog(this.getVa().getFrame()) == JFileChooser.APPROVE_OPTION) {
			File fileImg = new File(fc.getSelectedFile().toString());
			try {
				// Se ajusta al tamaño de los label
				Image imagen = ImageIO.read(fileImg);
				
				//Error si el fichero no es una imagen
				if (imagen != null){
					// Se crean las imagenes para la ventana de administracion y
					// usuario
					Image imageLblVa = imagen.getScaledInstance(this.getVa()
							.getLbl_img().getWidth(), this.getVa().getLbl_img()
							.getHeight(), Image.SCALE_DEFAULT);
					Image imageLblVu = imagen.getScaledInstance(this.getVu()
							.getLbl_img().getWidth(), this.getVu().getLbl_img()
							.getHeight(), Image.SCALE_DEFAULT);

					ImageIcon imgIcVa = new ImageIcon(imageLblVa);
					ImageIcon imgIcVu = new ImageIcon(imageLblVu);
					
					//Se guarda la imagen anterior de la ventana de administracion
					Icon imgIcVa_Old = this.getVa().getLbl_img().getIcon();
					//Se actualiza la ventana de administracion
					this.getVa().getLbl_img().setIcon(imgIcVa);
					
					//Se utiliza un JOptionPane para elegir si se desea cargar la imagen
					int n = JOptionPane.showConfirmDialog(
                            this.getVa().getFrame(), "¿Quieres cargar la imagen?",
                            "",
                            JOptionPane.YES_NO_OPTION);
					if (n == JOptionPane.YES_OPTION) {
						//Se carga la nueva imagen
						this.getVu().getLbl_img().setIcon(imgIcVu);
					} else if (n == JOptionPane.NO_OPTION) {
						//Se mantiene la imagen actual en la ventana de administracion
						this.getVa().getLbl_img().setIcon(imgIcVa_Old);
					} 			
					
				}else{
					alert = new JDialog_Alert(this.getVa().getFrame(),
							"No se ha podido cargar la imagen.");
				}
				
			} catch (IOException e) {
				// En caso de error, se indica que no se puede cargar
				e.printStackTrace();
				alert = new JDialog_Alert(this.getVa().getFrame(),
						"No se ha podido cargar la imagen.");
			}

		}
	}

	/**
	 * Limpiar el mensaje de las pantallas
	 */
	public void limpiarImg() {
		this.getVu().getLbl_img().setIcon(null);
		this.getVa().getLbl_img().setIcon(null);
	}

	/* Getters & Setters */

	public VentanaUsuario getVu() {
		return vu;
	}

	public VentanaAdministrador getVa() {
		return va;
	}

	public CronometroLabel_Controller getClabel_cont() {
		return this.clabel_cont;
	}

	public void setClabel_cont(CronometroLabel_Controller clabel_cont) {
		this.clabel_cont = clabel_cont;
	}

	public void setVu(VentanaUsuario vu) {
		this.vu = vu;
	}

	public ControladorListener getCnt_Listener() {
		return this.cnt_Listener;
	}

	public void setCnt_Listener(ControladorListener cnt_Listener) {
		this.cnt_Listener = cnt_Listener;
	}

	public boolean isBtnTimeActive() {
		return btnTimeActive;
	}

	public void setBtnTimeActive(boolean btnTimeActive) {
		this.btnTimeActive = btnTimeActive;
	}

	public void setVa(VentanaAdministrador va) {
		this.va = va;
	}

	public Cronometro getCron() {
		return cron;
	}

	public void setCron(Cronometro cron) {
		this.cron = cron;
	}

	public JFileChooser getFc() {
		return fc;
	}

	public void setFc(JFileChooser fc) {
		this.fc = fc;
	}

	public JDialog_Alert getAlert() {
		return alert;
	}

	public void setAlert(JDialog_Alert alert) {
		this.alert = alert;
	}

	public boolean isShown0Alert() {
		return isShown0Alert;
	}

	public void setShown0Alert(boolean isShown0Alert) {
		this.isShown0Alert = isShown0Alert;
	}

}
