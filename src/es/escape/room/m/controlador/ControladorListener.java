package es.escape.room.m.controlador;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ControladorListener implements ActionListener {

	private Controlador cnt;

	public ControladorListener(Controlador cnt) {
		this.cnt = cnt;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		if (e.getSource() == cnt.getVa().getBtn_tiempo()) {
			/* Se chequea si el boton de play esta activo o inactivo */
			if (cnt.isBtnTimeActive() == true) {
				cnt.pararCronometro();
			} else {
				cnt.iniciarCronometro();
			}
		} else if (e.getSource() == cnt.getVa().getBtn_reset_tiempo()) {
			cnt.resetCronometro();
		} else if (e.getSource() == cnt.getVa().getBtn_enviar_msg()) {
			cnt.actualizarMsg();
		} else if (e.getSource() == cnt.getVa().getBtn_limpiar_msg()) {
			cnt.limpiarMsg();
		} else if (e.getSource() == cnt.getVa().getBtn_ctr_sumar()) {
			cnt.sumarTiempo();
		} else if (e.getSource() == cnt.getVa().getBtn_ctr_restar()) {
			cnt.restarTiempo();
		} else if (e.getSource() == cnt.getVa().getBtnScreen()) {
			cnt.cambiarPantalla();
		} else if (e.getSource() == cnt.getVa().getBtn_cargar_img()) {
			cnt.cargarImg();
		} else if (e.getSource() == cnt.getVa().getBtn_limpiar_img()) {
			cnt.limpiarImg();
		}

	}

	/* Getters & Setters */
	public Controlador getCnt() {
		return cnt;
	}

	public void setCnt(Controlador cnt) {
		this.cnt = cnt;
	}

}
