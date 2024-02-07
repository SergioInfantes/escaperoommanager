package es.escape.room.m.vista;

import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.JPanel;
import java.awt.BorderLayout;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.JTextPane;

import java.awt.GridBagLayout;
import java.awt.Color;
import javax.swing.border.BevelBorder;
import javax.swing.border.Border;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyledDocument;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.GridBagConstraints;
import java.awt.Image;
import java.awt.Insets;
import java.awt.Toolkit;
import java.io.IOException;

import javax.swing.SwingConstants;
import javax.swing.ImageIcon;
import javax.swing.UIManager;
import javax.swing.JScrollPane;

import es.escape.room.m.modelo.RoundedBorder;
import es.escape.room.m.modelo.TextPrompt;
import es.escape.room.m.modelo.WrapEditorKit;

public class VentanaAdministrador {

	private JFrame frmEscapeRoom;
	private JTextArea ta_msg;
	private JPanel panel_tiempo, panel_mensaje;
	private JLabel lbl_tiempo;
	private JButton btn_tiempo, btn_reset_tiempo, btn_enviar_msg;
	private JTextPane ta_texto;
	private JLabel lbl_tiempo_rem;
	private JPanel panel_mensaje_desc;
	private JLabel lbl_texto_m;
	private JPanel subpanel_desc_mensaje_1;
	private JPanel subpanel_desc_mensaje_2;
	private JButton btn_limpiar_msg;
	private JPanel panel_ctr_tiempo;
	private JPanel panel_tiempo_restante;
	private JTextField tf_ctr_horas;
	private JLabel lbl_ctr_horas;
	private JTextField tf_ctr_minutos;
	private JLabel lbl_ctr_minutos;
	private JTextField tf_ctr_segundos;
	private JLabel lbl_ctr_segundos;
	private JButton btn_ctr_sumar;
	private JButton btn_ctr_restar;
	private JButton btn_cargar_img;
	private JLabel lbl_img;
	private JButton btn_limpiar_img;
	private TextPrompt ph_tf_msg;
	private GridBagConstraints gbc_lbl_img, gbc_panel_tiempo_restante,
			gbc_panel_ctr_tiempo;
	private JPanel panel_principal;
	private JPanel panel_mensaje_grid;
	private JScrollPane jsp_tf;
	private ImageIcon playImgIcon, pauseImgIcon;
	private Image imageBckg;
	private JButton btnScreen;

	/**
	 * Create the application.
	 */
	public VentanaAdministrador() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	@SuppressWarnings("serial")
	private void initialize() {

		// Calculate percentage of screen
		Toolkit tk = Toolkit.getDefaultToolkit();
		int xSize = ((int) tk.getScreenSize().getWidth());
		int ySize = ((int) tk.getScreenSize().getHeight());
		int height = (int) (Math.round(ySize * 0.82)) + 100;
		int width = (int) (Math.round(xSize * 0.5)) + 100;

		// Initialize image icon (Usar en controlador)
		playImgIcon = new ImageIcon(
				VentanaAdministrador.class.getResource("/img/playTime.png"));
		pauseImgIcon = new ImageIcon(
				VentanaAdministrador.class.getResource("/img/pauseTime.png"));

		frmEscapeRoom = new JFrame();
		frmEscapeRoom.setIconImage(Toolkit.getDefaultToolkit().getImage(
				VentanaAdministrador.class.getResource("/img/icon_er.png")));
		frmEscapeRoom.setTitle("Escape Room");
		// frame.pack();
		frmEscapeRoom.setBounds(50, 0, width, height);
		frmEscapeRoom.setPreferredSize(new Dimension(width, height));
		frmEscapeRoom.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frmEscapeRoom.getContentPane().setLayout(new BorderLayout(0, 0));
		frmEscapeRoom.setResizable(false);
		
		// Agregar imagen de fondo al JPanel
		try {
			// Se crea la imagen y se ajusta al tamaño del panel
			Image imagen = ImageIO.read(VentanaUsuario.class
					.getResource("/img/background.jpg"));
			imageBckg = imagen.getScaledInstance(width, height,
					Image.SCALE_DEFAULT);

		} catch (IOException ex) {
			// handle exception...
			System.out.println(ex);
		}
		panel_principal = new JPanel() {

			@Override
			protected void paintComponent(Graphics g) {
				super.paintComponent(g);
				g.drawImage(imageBckg, 0, 0, this); // see javadoc for more info
													// on the parameters
			}
		};
		frmEscapeRoom.getContentPane()
				.add(panel_principal, BorderLayout.CENTER);
		GridBagLayout gbl_panel_principal = new GridBagLayout();
		gbl_panel_principal.columnWidths = new int[] { 610, 0 };
		gbl_panel_principal.rowHeights = new int[] { 170, 30, 120 };
		gbl_panel_principal.columnWeights = new double[] { 0.0,
				Double.MIN_VALUE };
		gbl_panel_principal.rowWeights = new double[] { 0.0, 0.0, 0.0, 0.0 };
		panel_principal.setLayout(gbl_panel_principal);

		panel_tiempo = new JPanel();
		panel_tiempo.setOpaque(false);
		panel_tiempo.setBorder(new BevelBorder(BevelBorder.LOWERED, Color.DARK_GRAY, null, null, null));
		GridBagConstraints gbc_panel_tiempo = new GridBagConstraints();
		gbc_panel_tiempo.weightx = 1.0;
		gbc_panel_tiempo.weighty = 0.05;
		gbc_panel_tiempo.fill = GridBagConstraints.BOTH;
		gbc_panel_tiempo.insets = new Insets(0, 0, 5, 0);
		gbc_panel_tiempo.gridx = 0;
		gbc_panel_tiempo.gridy = 2;
		panel_principal.add(panel_tiempo, gbc_panel_tiempo);
		GridBagLayout gbl_panel_tiempo = new GridBagLayout();
		gbl_panel_tiempo.columnWidths = new int[] { 200, 0, 0 };
		gbl_panel_tiempo.rowHeights = new int[] { 80 };
		gbl_panel_tiempo.columnWeights = new double[] { 0.0, 0.0,
				Double.MIN_VALUE };
		gbl_panel_tiempo.rowWeights = new double[] { 0.0 };
		panel_tiempo.setLayout(gbl_panel_tiempo);

		panel_tiempo_restante = new JPanel();
		panel_tiempo_restante.setOpaque(false);
		gbc_panel_tiempo_restante = new GridBagConstraints();
		gbc_panel_tiempo_restante.anchor = GridBagConstraints.WEST;
		gbc_panel_tiempo_restante.weightx = 0.5;
		gbc_panel_tiempo_restante.weighty = 1.0;
		gbc_panel_tiempo_restante.fill = GridBagConstraints.BOTH;
		gbc_panel_tiempo_restante.gridx = 0;
		gbc_panel_tiempo_restante.gridy = 0;
		panel_tiempo.add(panel_tiempo_restante, gbc_panel_tiempo_restante);
		GridBagLayout gbl_panel_tiempo_restante = new GridBagLayout();
		gbl_panel_tiempo_restante.columnWidths = new int[] { 86, 161, 30, 31 };
		gbl_panel_tiempo_restante.rowHeights = new int[] { 37 };
		gbl_panel_tiempo_restante.columnWeights = new double[] { 0.0, 0.0, 0.0,
				0.0 };
		gbl_panel_tiempo_restante.rowWeights = new double[] { 0.0, 0.0 };
		panel_tiempo_restante.setLayout(gbl_panel_tiempo_restante);

		lbl_tiempo_rem = new JLabel("Tiempo restante:");
		lbl_tiempo_rem.setForeground(Color.WHITE);
		lbl_tiempo_rem.setFont(new Font("Sylfaen", Font.BOLD, 25));
		GridBagConstraints gbc_lbl_tiempo_rem = new GridBagConstraints();
		gbc_lbl_tiempo_rem.fill = GridBagConstraints.BOTH;
		gbc_lbl_tiempo_rem.insets = new Insets(5, 0, 0, 5);
		gbc_lbl_tiempo_rem.gridx = 1;
		gbc_lbl_tiempo_rem.gridy = 0;
		panel_tiempo_restante.add(lbl_tiempo_rem, gbc_lbl_tiempo_rem);

		lbl_tiempo = new JLabel("");
		lbl_tiempo.setForeground(Color.WHITE);
		lbl_tiempo.setFont(new Font("Sylfaen", Font.BOLD, 27));
		GridBagConstraints gbc_lbl_tiempo = new GridBagConstraints();
		gbc_lbl_tiempo.insets = new Insets(5, 0, 0, 5);
		gbc_lbl_tiempo.gridx = 1;
		gbc_lbl_tiempo.gridy = 1;
		panel_tiempo_restante.add(lbl_tiempo, gbc_lbl_tiempo);

		btn_tiempo = new JButton("");
		btn_tiempo.setToolTipText("PLAY");
		btn_tiempo.setIcon(playImgIcon);
		btn_tiempo.setBorder(new RoundedBorder(5));
		btn_tiempo.setBackground(UIManager.getColor("Button.highlight"));
		GridBagConstraints gbc_btn_tiempo = new GridBagConstraints();
		gbc_btn_tiempo.fill = GridBagConstraints.BOTH;
		gbc_btn_tiempo.anchor = GridBagConstraints.NORTHWEST;
		gbc_btn_tiempo.insets = new Insets(5, 0, 0, 0);
		gbc_btn_tiempo.gridx = 3;
		gbc_btn_tiempo.gridy = 0;
		panel_tiempo_restante.add(btn_tiempo, gbc_btn_tiempo);

		btn_reset_tiempo = new JButton("");
		btn_reset_tiempo.setToolTipText("RESET");
		btn_reset_tiempo.setIcon(new ImageIcon(VentanaAdministrador.class
				.getResource("/img/stopTime.png")));
		btn_reset_tiempo.setBorder(new RoundedBorder(5));
		btn_reset_tiempo.setBackground(UIManager.getColor("Button.highlight"));
		GridBagConstraints gbc_btn_reset_tiempo = new GridBagConstraints();
		gbc_btn_reset_tiempo.fill = GridBagConstraints.BOTH;
		gbc_btn_reset_tiempo.insets = new Insets(5, 0, 0, 0);
		gbc_btn_reset_tiempo.anchor = GridBagConstraints.NORTHWEST;
		gbc_btn_reset_tiempo.gridx = 3;
		gbc_btn_reset_tiempo.gridy = 1;
		panel_tiempo_restante.add(btn_reset_tiempo, gbc_btn_reset_tiempo);

		panel_ctr_tiempo = new JPanel();
		panel_ctr_tiempo.setOpaque(false);
		gbc_panel_ctr_tiempo = new GridBagConstraints();
		gbc_panel_ctr_tiempo.weightx = 0.5;
		gbc_panel_ctr_tiempo.weighty = 1.0;
		gbc_panel_ctr_tiempo.fill = GridBagConstraints.BOTH;
		gbc_panel_ctr_tiempo.gridx = 1;
		gbc_panel_ctr_tiempo.gridy = 0;
		panel_tiempo.add(panel_ctr_tiempo, gbc_panel_ctr_tiempo);
		GridBagLayout gbl_panel_ctr_tiempo = new GridBagLayout();
		gbl_panel_ctr_tiempo.columnWidths = new int[] { 39, 34, 41, 34, 0 };
		gbl_panel_ctr_tiempo.rowHeights = new int[] { 37 };
		gbl_panel_ctr_tiempo.columnWeights = new double[] { 0.0, 0.0, 0.0, 0.0,
				0.0 };
		gbl_panel_ctr_tiempo.rowWeights = new double[] { 0.0, 0.0, 0.0 };
		panel_ctr_tiempo.setLayout(gbl_panel_ctr_tiempo);

		tf_ctr_horas = new JTextField();
		tf_ctr_horas.setFont(new Font("Tahoma", Font.PLAIN, 17));
		tf_ctr_horas.setText("00");
		GridBagConstraints gbc_tf_ctr_horas = new GridBagConstraints();
		gbc_tf_ctr_horas.fill = GridBagConstraints.BOTH;
		gbc_tf_ctr_horas.anchor = GridBagConstraints.WEST;
		gbc_tf_ctr_horas.insets = new Insets(5, 0, 5, 5);
		gbc_tf_ctr_horas.gridx = 0;
		gbc_tf_ctr_horas.gridy = 0;
		panel_ctr_tiempo.add(tf_ctr_horas, gbc_tf_ctr_horas);
		tf_ctr_horas.setColumns(2);

		lbl_ctr_horas = new JLabel("horas");
		lbl_ctr_horas.setForeground(Color.WHITE);
		lbl_ctr_horas.setFont(new Font("Tahoma", Font.PLAIN, 17));
		GridBagConstraints gbc_lbl_ctr_horas = new GridBagConstraints();
		gbc_lbl_ctr_horas.anchor = GridBagConstraints.WEST;
		gbc_lbl_ctr_horas.insets = new Insets(0, 0, 5, 5);
		gbc_lbl_ctr_horas.gridx = 1;
		gbc_lbl_ctr_horas.gridy = 0;
		panel_ctr_tiempo.add(lbl_ctr_horas, gbc_lbl_ctr_horas);
		
		btnScreen = new JButton("FULL");
		GridBagConstraints gbc_btnScreen = new GridBagConstraints();
		gbc_btnScreen.insets = new Insets(0, 0, 5, 0);
		gbc_btnScreen.gridx = 3;
		gbc_btnScreen.gridy = 2;
		panel_ctr_tiempo.add(btnScreen, gbc_btnScreen);

		tf_ctr_minutos = new JTextField();
		tf_ctr_minutos.setFont(new Font("Tahoma", Font.PLAIN, 17));
		tf_ctr_minutos.setText("00");
		GridBagConstraints gbc_tf_ctr_minutos = new GridBagConstraints();
		gbc_tf_ctr_minutos.fill = GridBagConstraints.BOTH;
		gbc_tf_ctr_minutos.anchor = GridBagConstraints.WEST;
		gbc_tf_ctr_minutos.insets = new Insets(5, 0, 5, 5);
		gbc_tf_ctr_minutos.gridx = 0;
		gbc_tf_ctr_minutos.gridy = 1;
		panel_ctr_tiempo.add(tf_ctr_minutos, gbc_tf_ctr_minutos);
		tf_ctr_minutos.setColumns(2);

		lbl_ctr_minutos = new JLabel("minutos");
		lbl_ctr_minutos.setForeground(Color.WHITE);
		lbl_ctr_minutos.setFont(new Font("Tahoma", Font.PLAIN, 17));
		GridBagConstraints gbc_lbl_ctr_minutos = new GridBagConstraints();
		gbc_lbl_ctr_minutos.weighty = 0.2;
		gbc_lbl_ctr_minutos.anchor = GridBagConstraints.WEST;
		gbc_lbl_ctr_minutos.insets = new Insets(0, 0, 5, 5);
		gbc_lbl_ctr_minutos.gridx = 1;
		gbc_lbl_ctr_minutos.gridy = 1;
		panel_ctr_tiempo.add(lbl_ctr_minutos, gbc_lbl_ctr_minutos);

		tf_ctr_segundos = new JTextField();
		tf_ctr_segundos.setFont(new Font("Tahoma", Font.PLAIN, 17));
		tf_ctr_segundos.setText("00");
		GridBagConstraints gbc_tf_ctr_segundos = new GridBagConstraints();
		gbc_tf_ctr_segundos.fill = GridBagConstraints.BOTH;
		gbc_tf_ctr_segundos.anchor = GridBagConstraints.WEST;
		gbc_tf_ctr_segundos.insets = new Insets(5, 0, 0, 5);
		gbc_tf_ctr_segundos.gridx = 0;
		gbc_tf_ctr_segundos.gridy = 2;
		panel_ctr_tiempo.add(tf_ctr_segundos, gbc_tf_ctr_segundos);
		tf_ctr_segundos.setColumns(2);

		lbl_ctr_segundos = new JLabel("segundos");
		lbl_ctr_segundos.setForeground(Color.WHITE);
		lbl_ctr_segundos.setFont(new Font("Tahoma", Font.PLAIN, 17));
		GridBagConstraints gbc_lbl_ctr_segundos = new GridBagConstraints();
		gbc_lbl_ctr_segundos.weighty = 0.2;
		gbc_lbl_ctr_segundos.anchor = GridBagConstraints.WEST;
		gbc_lbl_ctr_segundos.insets = new Insets(0, 0, 0, 5);
		gbc_lbl_ctr_segundos.gridx = 1;
		gbc_lbl_ctr_segundos.gridy = 2;
		panel_ctr_tiempo.add(lbl_ctr_segundos, gbc_lbl_ctr_segundos);

		btn_ctr_sumar = new JButton("");
		btn_ctr_sumar.setIcon(new ImageIcon(VentanaAdministrador.class
				.getResource("/img/sumarTime.PNG")));
		btn_ctr_sumar.setToolTipText("Agregar tiempo");
		btn_ctr_sumar.setBorder(new RoundedBorder(5));
		btn_ctr_sumar.setBackground(UIManager.getColor("Button.highlight"));
		GridBagConstraints gbc_btn_ctr_sumar = new GridBagConstraints();
		gbc_btn_ctr_sumar.fill = GridBagConstraints.BOTH;
		gbc_btn_ctr_sumar.insets = new Insets(5, 0, 5, 5);
		gbc_btn_ctr_sumar.gridx = 3;
		gbc_btn_ctr_sumar.gridy = 0;
		panel_ctr_tiempo.add(btn_ctr_sumar, gbc_btn_ctr_sumar);

		btn_ctr_restar = new JButton("");
		btn_ctr_restar.setIcon(new ImageIcon(VentanaAdministrador.class
				.getResource("/img/restarTime.PNG")));
		btn_ctr_restar.setToolTipText("Quitar tiempo");
		btn_ctr_restar.setBorder(new RoundedBorder(5));
		btn_ctr_restar.setBackground(UIManager.getColor("Button.highlight"));
		GridBagConstraints gbc_btn_ctr_restar = new GridBagConstraints();
		gbc_btn_ctr_restar.fill = GridBagConstraints.BOTH;
		gbc_btn_ctr_restar.insets = new Insets(5, 0, 5, 5);
		gbc_btn_ctr_restar.gridx = 3;
		gbc_btn_ctr_restar.gridy = 1;
		panel_ctr_tiempo.add(btn_ctr_restar, gbc_btn_ctr_restar);

		panel_mensaje_desc = new JPanel();
		panel_mensaje_desc.setOpaque(false);
		panel_mensaje_desc.setBorder(new BevelBorder(BevelBorder.LOWERED, Color.DARK_GRAY, null, null, null));
		GridBagConstraints gbc_panel_mensaje_desc = new GridBagConstraints();
		gbc_panel_mensaje_desc.gridheight = 2;
		gbc_panel_mensaje_desc.weightx = 1.0;
		gbc_panel_mensaje_desc.weighty = 0.9;
		gbc_panel_mensaje_desc.fill = GridBagConstraints.BOTH;
		gbc_panel_mensaje_desc.gridx = 0;
		gbc_panel_mensaje_desc.gridy = 0;
		panel_principal.add(panel_mensaje_desc, gbc_panel_mensaje_desc);
		panel_mensaje_desc.setLayout(new BorderLayout(0, 0));
		
		panel_mensaje_grid = new JPanel();
		panel_mensaje_grid.setOpaque(false);
		panel_mensaje_desc.add(panel_mensaje_grid, BorderLayout.CENTER);
		GridBagLayout gbl_panel_mensaje_grid = new GridBagLayout();
		gbl_panel_mensaje_grid.columnWidths = new int[] { 32, 584, 0 };
		gbl_panel_mensaje_grid.rowHeights = new int[] { 20, 120, 20 };
		gbl_panel_mensaje_grid.columnWeights = new double[] { 0.0, 0.0,
				Double.MIN_VALUE };
		gbl_panel_mensaje_grid.rowWeights = new double[] { 0.0, 0.0 };
		panel_mensaje_grid.setLayout(gbl_panel_mensaje_grid);

		subpanel_desc_mensaje_1 = new JPanel();
		subpanel_desc_mensaje_1.setOpaque(false);
		GridBagConstraints gbc_subpanel_desc_mensaje_1 = new GridBagConstraints();
		gbc_subpanel_desc_mensaje_1.weightx = 1.0;
		gbc_subpanel_desc_mensaje_1.fill = GridBagConstraints.BOTH;
		gbc_subpanel_desc_mensaje_1.weighty = 0.1;
		gbc_subpanel_desc_mensaje_1.gridx = 1;
		gbc_subpanel_desc_mensaje_1.gridy = 0;
		panel_mensaje_grid.add(subpanel_desc_mensaje_1,
				gbc_subpanel_desc_mensaje_1);
		GridBagLayout gbl_subpanel_desc_mensaje_1 = new GridBagLayout();
		gbl_subpanel_desc_mensaje_1.columnWidths = new int[] { 235, 113, 0 };
		gbl_subpanel_desc_mensaje_1.rowHeights = new int[] { 14 };
		gbl_subpanel_desc_mensaje_1.columnWeights = new double[] { 0.0, 0.0,
				Double.MIN_VALUE };
		gbl_subpanel_desc_mensaje_1.rowWeights = new double[] { 0.0 };
		subpanel_desc_mensaje_1.setLayout(gbl_subpanel_desc_mensaje_1);

		lbl_texto_m = new JLabel("Info Ventana de Usuario");
		lbl_texto_m.setHorizontalAlignment(SwingConstants.CENTER);
		lbl_texto_m.setFont(new Font("Sylfaen", Font.BOLD, 30));
		lbl_texto_m.setForeground(Color.WHITE);
		GridBagConstraints gbc_lbl_texto_m = new GridBagConstraints();
		gbc_lbl_texto_m.weighty = 1.0;
		gbc_lbl_texto_m.weightx = 1.0;
		gbc_lbl_texto_m.fill = GridBagConstraints.BOTH;
		gbc_lbl_texto_m.gridx = 0;
		gbc_lbl_texto_m.gridy = 0;
		subpanel_desc_mensaje_1.add(lbl_texto_m, gbc_lbl_texto_m);

		subpanel_desc_mensaje_2 = new JPanel();
		subpanel_desc_mensaje_2.setOpaque(false);
		GridBagConstraints gbc_subpanel_desc_mensaje_2 = new GridBagConstraints();
		gbc_subpanel_desc_mensaje_2.weightx = 1.0;
		gbc_subpanel_desc_mensaje_2.fill = GridBagConstraints.BOTH;
		gbc_subpanel_desc_mensaje_2.weighty = 0.8;
		gbc_subpanel_desc_mensaje_2.gridx = 1;
		gbc_subpanel_desc_mensaje_2.gridy = 1;
		panel_mensaje_grid.add(subpanel_desc_mensaje_2,
				gbc_subpanel_desc_mensaje_2);
		GridBagLayout gbl_subpanel_desc_mensaje_2 = new GridBagLayout();
		gbl_subpanel_desc_mensaje_2.columnWidths = new int[] { 584, 0, 0, 0, 0 };
		gbl_subpanel_desc_mensaje_2.rowHeights = new int[] { 56, 56, 30, 30, 30 };
		gbl_subpanel_desc_mensaje_2.columnWeights = new double[] { 0.0, 0.0,
				0.0, 0.0, Double.MIN_VALUE };
		gbl_subpanel_desc_mensaje_2.rowWeights = new double[] { 0.0, 0.0, 0.0,
				0.0, 0.0, Double.MIN_VALUE };
		subpanel_desc_mensaje_2.setLayout(gbl_subpanel_desc_mensaje_2);

		ta_texto = new JTextPane();
		ta_texto.setForeground(Color.WHITE);
		ta_texto.setFont(new Font("Tempus Sans ITC", Font.PLAIN, 27));
		ta_texto.setOpaque(false);
		ta_texto.setEditable(false);
		ta_texto.setFocusable(false);
		// Arreglar problema con palabras largas
		ta_texto.setEditorKit(new WrapEditorKit());
		// Centrar texto
		StyledDocument doc = ta_texto.getStyledDocument();
		SimpleAttributeSet center = new SimpleAttributeSet();
		StyleConstants.setAlignment(center, StyleConstants.ALIGN_CENTER);
		doc.setParagraphAttributes(0, doc.getLength(), center, false);

		GridBagConstraints gbc_lbl_texto = new GridBagConstraints();
		gbc_lbl_texto.weightx = 1.0;
		gbc_lbl_texto.weighty = 0.1;
		gbc_lbl_texto.fill = GridBagConstraints.BOTH;
		gbc_lbl_texto.insets = new Insets(0, 0, 5, 20);
		gbc_lbl_texto.gridx = 0;
		gbc_lbl_texto.gridy = 0;
		subpanel_desc_mensaje_2.add(ta_texto, gbc_lbl_texto);

		lbl_img = new JLabel("");
		lbl_img.setHorizontalAlignment(SwingConstants.CENTER);
		gbc_lbl_img = new GridBagConstraints();
		gbc_lbl_img.gridheight = 5;
		gbc_lbl_img.insets = new Insets(0, 0, 0, 20);
		gbc_lbl_img.weightx = 1.0;
		gbc_lbl_img.weighty = 0.9;
		gbc_lbl_img.fill = GridBagConstraints.BOTH;
		gbc_lbl_img.gridx = 0;
		gbc_lbl_img.gridy = 1;
		subpanel_desc_mensaje_2.add(lbl_img, gbc_lbl_img);

		panel_mensaje = new JPanel();
		panel_mensaje.setBorder(new BevelBorder(BevelBorder.LOWERED, Color.DARK_GRAY, null, null, null));
		panel_mensaje.setOpaque(false);
		GridBagConstraints gbc_panel_mensaje = new GridBagConstraints();
		gbc_panel_mensaje.fill = GridBagConstraints.BOTH;
		gbc_panel_mensaje.weightx = 1.0;
		gbc_panel_mensaje.weighty = 0.05;
		gbc_panel_mensaje.gridx = 0;
		gbc_panel_mensaje.gridy = 3;
		panel_principal.add(panel_mensaje, gbc_panel_mensaje);
		GridBagLayout gbl_panel_mensaje = new GridBagLayout();
		gbl_panel_mensaje.columnWidths = new int[] { 301, 301, 0, 0, 0 };
		gbl_panel_mensaje.rowHeights = new int[] { 41, 41, 41, 0 };
		gbl_panel_mensaje.columnWeights = new double[] { 0.0, 0.0, 0.0, 0.0,
				Double.MIN_VALUE };
		gbl_panel_mensaje.rowWeights = new double[] { 0.0, 0.0, 0.0,
				Double.MIN_VALUE };
		panel_mensaje.setLayout(gbl_panel_mensaje);

		ta_msg = new JTextArea();
		ta_msg.setFont(new Font("Tahoma", Font.PLAIN, 17));
		ta_msg.setRows(3);
		ta_msg.setWrapStyleWord(true);
		ta_msg.setLineWrap(true);

		jsp_tf = new JScrollPane(ta_msg);
		GridBagConstraints gbc_jsp_tf = new GridBagConstraints();
		gbc_jsp_tf.weighty = 0.5;
		gbc_jsp_tf.weightx = 0.6;
		gbc_jsp_tf.gridwidth = 2;
		gbc_jsp_tf.gridheight = 2;
		gbc_jsp_tf.fill = GridBagConstraints.BOTH;
		gbc_jsp_tf.insets = new Insets(0, 5, 5, 5);
		gbc_jsp_tf.gridx = 0;
		gbc_jsp_tf.gridy = 0;
		panel_mensaje.add(jsp_tf, gbc_jsp_tf);
		ta_msg.setToolTipText("");
		ph_tf_msg = new TextPrompt("Escribe aqui...", ta_msg);
		ph_tf_msg.setFont(new Font("Monospaced", Font.PLAIN, 17));
		ta_msg.setColumns(50);
		ta_msg.add(ph_tf_msg);

		btn_enviar_msg = new JButton("");
		GridBagConstraints gbc_btn_enviar_msg = new GridBagConstraints();
		gbc_btn_enviar_msg.weightx = 0.1;
		gbc_btn_enviar_msg.fill = GridBagConstraints.BOTH;
		gbc_btn_enviar_msg.gridx = 2;
		gbc_btn_enviar_msg.gridy = 0;
		panel_mensaje.add(btn_enviar_msg, gbc_btn_enviar_msg);
		btn_enviar_msg.setIcon(new ImageIcon(VentanaAdministrador.class
				.getResource("/img/sendMsg.png")));
		btn_enviar_msg.setToolTipText("Enviar Texto");
		btn_enviar_msg.setBorder(new RoundedBorder(5));
		btn_enviar_msg.setBackground(UIManager.getColor("Button.light"));

		btn_cargar_img = new JButton("");
		GridBagConstraints gbc_btn_cargar_img = new GridBagConstraints();
		gbc_btn_cargar_img.weightx = 0.1;
		gbc_btn_cargar_img.fill = GridBagConstraints.BOTH;
		gbc_btn_cargar_img.gridx = 2;
		gbc_btn_cargar_img.gridy = 1;
		panel_mensaje.add(btn_cargar_img, gbc_btn_cargar_img);
		btn_cargar_img.setIcon(new ImageIcon(VentanaAdministrador.class
				.getResource("/img/selectImg.png")));
		btn_cargar_img.setToolTipText("Seleccionar imagen");
		btn_cargar_img.setBorder(new RoundedBorder(5));
		btn_cargar_img.setBackground(UIManager.getColor("Button.highlight"));

		btn_limpiar_msg = new JButton("");
		GridBagConstraints gbc_btn_limpiar_msg = new GridBagConstraints();
		gbc_btn_limpiar_msg.weightx = 0.1;
		gbc_btn_limpiar_msg.fill = GridBagConstraints.BOTH;
		gbc_btn_limpiar_msg.gridx = 3;
		gbc_btn_limpiar_msg.gridy = 0;
		panel_mensaje.add(btn_limpiar_msg, gbc_btn_limpiar_msg);
		btn_limpiar_msg.setIcon(new ImageIcon(VentanaAdministrador.class
				.getResource("/img/clearMsg.PNG")));
		btn_limpiar_msg.setToolTipText("Limpiar texto");
		btn_limpiar_msg.setBorder(new RoundedBorder(5));
		btn_limpiar_msg.setBackground(UIManager.getColor("Button.light"));

		btn_limpiar_img = new JButton("");
		GridBagConstraints gbc_btn_limpiar_img = new GridBagConstraints();
		gbc_btn_limpiar_img.weightx = 0.1;
		gbc_btn_limpiar_img.fill = GridBagConstraints.BOTH;
		gbc_btn_limpiar_img.gridx = 3;
		gbc_btn_limpiar_img.gridy = 1;
		panel_mensaje.add(btn_limpiar_img, gbc_btn_limpiar_img);
		btn_limpiar_img.setIcon(new ImageIcon(VentanaAdministrador.class
				.getResource("/img/clearImg.PNG")));
		btn_limpiar_img.setToolTipText("Limpiar imagen");
		btn_limpiar_img.setBorder(new RoundedBorder(5));
		btn_limpiar_img.setBackground(UIManager.getColor("Button.light"));

		frmEscapeRoom.pack();
		// verBordes();
	}

	/**
	 * Ver los bordes para debugear
	 */
	public void verBordes() {
		Border borderLbl = BorderFactory.createLineBorder(Color.BLUE, 5);
		Border borderPanel = BorderFactory.createLineBorder(Color.RED, 5);
		Border borderPanelP = BorderFactory.createLineBorder(Color.GREEN, 5);
		// set the border of this component
		ta_texto.setBorder(borderLbl);
		lbl_img.setBorder(borderLbl);
		panel_mensaje_desc.setBorder(borderPanelP);
		panel_mensaje_grid.setBorder(borderPanel);
		panel_tiempo.setBorder(borderPanel);
		panel_principal.setBorder(borderPanelP);
	}

	// Getters & Setters

	public JFrame getFrame() {
		return frmEscapeRoom;
	}

	public void setFrame(JFrame frame) {
		this.frmEscapeRoom = frame;
	}

	public JTextArea getTf_msg() {
		return ta_msg;
	}

	public void setTf_msg(JTextArea tf_msg) {
		this.ta_msg = tf_msg;
	}

	public JPanel getPanel_tiempo() {
		return panel_tiempo;
	}

	public void setPanel_tiempo(JPanel panel_tiempo) {
		this.panel_tiempo = panel_tiempo;
	}

	public JPanel getPanel_mensaje() {
		return panel_mensaje;
	}

	public void setPanel_mensaje(JPanel panel_mensaje) {
		this.panel_mensaje = panel_mensaje;
	}

	public JLabel getLbl_tiempo() {
		return lbl_tiempo;
	}

	public void setLbl_tiempo(JLabel lbl_tiempo) {
		this.lbl_tiempo = lbl_tiempo;
	}

	public JButton getBtn_tiempo() {
		return btn_tiempo;
	}

	public void setBtn_tiempo(JButton btn_tiempo) {
		this.btn_tiempo = btn_tiempo;
	}

	public JButton getBtn_reset_tiempo() {
		return btn_reset_tiempo;
	}

	public void setBtn_reset_tiempo(JButton btn_reset_tiempo) {
		this.btn_reset_tiempo = btn_reset_tiempo;
	}

	public JButton getBtn_enviar_msg() {
		return btn_enviar_msg;
	}

	public void setBtn_enviar_msg(JButton btn_enviar_msg) {
		this.btn_enviar_msg = btn_enviar_msg;
	}

	public JTextPane getTa_texto() {
		return ta_texto;
	}

	public void setTa_texto(JTextPane ta_texto) {
		this.ta_texto = ta_texto;
	}

	public JLabel getLbl_tiempo_rem() {
		return lbl_tiempo_rem;
	}

	public void setLbl_tiempo_rem(JLabel lbl_tiempo_rem) {
		this.lbl_tiempo_rem = lbl_tiempo_rem;
	}

	public JPanel getPanel_mensaje_desc() {
		return panel_mensaje_desc;
	}

	public void setPanel_mensaje_desc(JPanel panel_mensaje_desc) {
		this.panel_mensaje_desc = panel_mensaje_desc;
	}

	public JLabel getLbl_texto_m() {
		return lbl_texto_m;
	}

	public void setLbl_texto_m(JLabel lbl_texto_m) {
		this.lbl_texto_m = lbl_texto_m;
	}

	public JPanel getSubpanel_mensaje_1() {
		return subpanel_desc_mensaje_1;
	}

	public void setSubpanel_mensaje_1(JPanel subpanel_mensaje_1) {
		this.subpanel_desc_mensaje_1 = subpanel_mensaje_1;
	}

	public JPanel getSubpanel_mensaje_2() {
		return subpanel_desc_mensaje_2;
	}

	public void setSubpanel_mensaje_2(JPanel subpanel_mensaje_2) {
		this.subpanel_desc_mensaje_2 = subpanel_mensaje_2;
	}

	public JButton getBtn_limpiar_msg() {
		return btn_limpiar_msg;
	}

	public void setBtn_limpiar_msg(JButton btn_limpiar_msg) {
		this.btn_limpiar_msg = btn_limpiar_msg;
	}

	public JTextField getTf_ctr_horas() {
		return tf_ctr_horas;
	}

	public void setTf_ctr_horas(JTextField tf_ctr_horas) {
		this.tf_ctr_horas = tf_ctr_horas;
	}

	public JLabel getLbl_ctr_horas() {
		return lbl_ctr_horas;
	}

	public void setLbl_ctr_horas(JLabel lbl_ctr_horas) {
		this.lbl_ctr_horas = lbl_ctr_horas;
	}

	public JTextField getTf_ctr_minutos() {
		return tf_ctr_minutos;
	}

	public void setTf_ctr_minutos(JTextField tf_ctr_minutos) {
		this.tf_ctr_minutos = tf_ctr_minutos;
	}

	public JLabel getLbl_ctr_minutos() {
		return lbl_ctr_minutos;
	}

	public void setLbl_ctr_minutos(JLabel lbl_ctr_minutos) {
		this.lbl_ctr_minutos = lbl_ctr_minutos;
	}

	public JTextField getTf_ctr_segundos() {
		return tf_ctr_segundos;
	}

	public void setTf_ctr_segundos(JTextField tf_ctr_segundos) {
		this.tf_ctr_segundos = tf_ctr_segundos;
	}

	public JLabel getLbl_ctr_segundos() {
		return lbl_ctr_segundos;
	}

	public void setLbl_ctr_segundos(JLabel lbl_ctr_segundos) {
		this.lbl_ctr_segundos = lbl_ctr_segundos;
	}

	public JButton getBtn_ctr_sumar() {
		return btn_ctr_sumar;
	}

	public void setBtn_ctr_sumar(JButton btn_ctr_sumar) {
		this.btn_ctr_sumar = btn_ctr_sumar;
	}

	public JButton getBtn_ctr_restar() {
		return btn_ctr_restar;
	}

	public void setBtn_ctr_restar(JButton btn_ctr_restar) {
		this.btn_ctr_restar = btn_ctr_restar;
	}

	public JButton getBtn_cargar_img() {
		return btn_cargar_img;
	}

	public void setBtn_cargar_img(JButton btn_cargar_img) {
		this.btn_cargar_img = btn_cargar_img;
	}

	public JLabel getLbl_img() {
		return lbl_img;
	}

	public void setLbl_img(JLabel lbl_img) {
		this.lbl_img = lbl_img;
	}

	public JButton getBtn_limpiar_img() {
		return btn_limpiar_img;
	}

	public void setBtn_limpiar_img(JButton btn_limpiar_img) {
		this.btn_limpiar_img = btn_limpiar_img;
	}

	public JFrame getFrmEscapeRoom() {
		return frmEscapeRoom;
	}

	public void setFrmEscapeRoom(JFrame frmEscapeRoom) {
		this.frmEscapeRoom = frmEscapeRoom;
	}

	public TextPrompt getPh_tf_msg() {
		return ph_tf_msg;
	}

	public void setPh_tf_msg(TextPrompt ph_tf_msg) {
		this.ph_tf_msg = ph_tf_msg;
	}

	public ImageIcon getPlayImgIcon() {
		return playImgIcon;
	}

	public void setPlayImgIcon(ImageIcon playImgIcon) {
		this.playImgIcon = playImgIcon;
	}

	public ImageIcon getPauseImgIcon() {
		return pauseImgIcon;
	}

	public void setPauseImgIcon(ImageIcon pauseImgIcon) {
		this.pauseImgIcon = pauseImgIcon;
	}

	public JButton getBtnScreen() {
		return btnScreen;
	}

	public void setBtnScreen(JButton btnScreen) {
		this.btnScreen = btnScreen;
	}

}
