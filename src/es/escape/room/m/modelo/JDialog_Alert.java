package es.escape.room.m.modelo;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import java.awt.Color;
import java.awt.Font;
import java.awt.Frame;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.Timer;

/**
 * Se crea un JDialog para abrir una ventana con un mensaje informativo
 * durante un tiempo determinado. 
 */
public class JDialog_Alert extends JFrame implements ActionListener {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7099142106577075689L;
	private JLabel label;
	private Timer timer;
	private static final int TIEMPO = 1250;

	/**
	 * Se crea la alerta con un tiempo definido
	 * 
	 * @param parent
	 * @param msg
	 */
	public JDialog_Alert(Frame parent, String msg) {
		label = new JLabel(msg);
		timer = new Timer(TIEMPO, this); // Timer in seconds
		this.setUndecorated(true);/*
								 * Esto debe de hacerse ANTES de agregar el
								 * label. de otra manera, da error
								 */
		


		// Formato
		this.getContentPane().setBackground(new Color(255, 255, 102));
		label.setFont(new Font("Sylfaen", Font.BOLD, 25));
		//Add label
		this.add(label);
		
		this.pack();
		this.setLocationRelativeTo(null);
		this.setResizable(false);
		
		// Se inicia un thread para no trastocar las otras interfaces
		new Thread() {
			@Override
			public void run() {
				timer.start();
				setVisible(true);
			}
		}.start();
	}

	public void actionPerformed(ActionEvent e) {
		this.dispose();
	}
}
