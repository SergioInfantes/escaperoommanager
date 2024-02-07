package es.escape.room.m.vista;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextPane;

import java.awt.BorderLayout;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import javax.swing.border.Border;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyledDocument;

import es.escape.room.m.modelo.WrapEditorKit;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Image;
import java.awt.Insets;
import java.awt.Font;
import java.awt.Toolkit;
import java.io.IOException;

public class VentanaUsuario {

	private JFrame frame;
	private JPanel panel_tiempo, panel_gbl_texto;
	private JLabel lbl_tiempo;
	private JLabel lbl_img;
	private JPanel panel_principal;
	private JTextPane ta_texto;
	private Image imageBckg;

	/**
	 * Create the application.
	 */
	public VentanaUsuario() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	@SuppressWarnings("serial")
	private void initialize() {
		// Calculate size of screen
		Toolkit tk = Toolkit.getDefaultToolkit();
		int xSize = ((int) tk.getScreenSize().getWidth());
		int ySize = ((int) tk.getScreenSize().getHeight());
		int height = (int) (Math.round(ySize)) + 100;
		int width = (int) (Math.round(xSize)) + 100;

		frame = new JFrame();
		frame.setIconImage(Toolkit.getDefaultToolkit().getImage(
				VentanaUsuario.class.getResource("/img/icon_er.png")));
		frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
		// frame.setUndecorated(true); - Esto sirve para que no aparezcan los
		// botones de cerrar,etc
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(new BorderLayout(0, 0));

		// Agregar imagen de fondo al JPanel
		try {
			// Se crea la imagen y se ajusta al tamaño del panel
			Image imagen = ImageIO.read(VentanaUsuario.class
					.getResource("/img/background.jpg"));
			imageBckg = imagen.getScaledInstance(width,
			height, Image.SCALE_DEFAULT);

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

		frame.getContentPane().add(panel_principal);
		GridBagLayout gbl_panel_principal = new GridBagLayout();
		gbl_panel_principal.columnWeights = new double[] { 1.0 };
		gbl_panel_principal.rowWeights = new double[] { 0.0, 0.0, 1.0 };
		panel_principal.setLayout(gbl_panel_principal);

		panel_tiempo = new JPanel();
		panel_tiempo.setOpaque(false);
		GridBagConstraints gbc_panel_tiempo = new GridBagConstraints();
		gbc_panel_tiempo.weighty = 0.05;
		gbc_panel_tiempo.gridwidth = 0;
		gbc_panel_tiempo.fill = GridBagConstraints.BOTH;
		gbc_panel_tiempo.anchor = GridBagConstraints.NORTH;
		gbc_panel_tiempo.gridx = 0;
		gbc_panel_tiempo.gridy = 0;
		panel_principal.add(panel_tiempo, gbc_panel_tiempo);
		GridBagLayout gbl_panel_tiempo = new GridBagLayout();
		gbl_panel_tiempo.columnWidths = new int[] {434};
		gbl_panel_tiempo.rowHeights = new int[] {15};
		gbl_panel_tiempo.columnWeights = new double[] { 0.0 };
		gbl_panel_tiempo.rowWeights = new double[] { 0.0, 0.0 };
		panel_tiempo.setLayout(gbl_panel_tiempo);

		lbl_tiempo = new JLabel("");
		lbl_tiempo.setForeground(Color.WHITE);
		lbl_tiempo.setFont(new Font("Tempus Sans ITC", Font.BOLD, 100));
		lbl_tiempo.setHorizontalAlignment(SwingConstants.CENTER);
		GridBagConstraints gbc_lbl_tiempo = new GridBagConstraints();
		gbc_lbl_tiempo.gridwidth = 0;
		gbc_lbl_tiempo.fill = GridBagConstraints.BOTH;
		gbc_lbl_tiempo.gridx = 0;
		gbc_lbl_tiempo.gridy = 1;
		panel_tiempo.add(lbl_tiempo, gbc_lbl_tiempo);

		GridBagConstraints gbc_panel_texto = new GridBagConstraints();
		gbc_panel_texto.gridheight = 2;
		gbc_panel_texto.weighty = 0.95;
		gbc_panel_texto.insets = new Insets(0, 0, 0, 5);
		gbc_panel_texto.fill = GridBagConstraints.BOTH;
		gbc_panel_texto.gridx = 0;
		gbc_panel_texto.gridy = 1;
		

		panel_gbl_texto = new JPanel();
		panel_gbl_texto.setBounds(5, 5, 449, 259);
		panel_gbl_texto.setOpaque(false);
		GridBagLayout gbl_panel_gbl_texto = new GridBagLayout();
		gbl_panel_gbl_texto.columnWidths = new int[] {30, 150};
		gbl_panel_gbl_texto.rowHeights = new int[] {80, 159};
		gbl_panel_gbl_texto.columnWeights = new double[] { 0.0, 0.0 };
		gbl_panel_gbl_texto.rowWeights = new double[] { 0.0, 0.0 };
		panel_gbl_texto.setLayout(gbl_panel_gbl_texto);

		ta_texto = new JTextPane();
		ta_texto.setForeground(Color.WHITE);
		ta_texto.setEditable(false);
		ta_texto.setOpaque(false);
		ta_texto.setFont(new Font("Tempus Sans ITC", Font.PLAIN, 80));
		ta_texto.setFocusable(false);
		ta_texto.setSize(200, 200);
		// Arreglar problema con palabras largas
		ta_texto.setEditorKit(new WrapEditorKit());
		// Centrar texto
		StyledDocument doc = ta_texto.getStyledDocument();
		SimpleAttributeSet center = new SimpleAttributeSet();
		StyleConstants.setAlignment(center, StyleConstants.ALIGN_CENTER);
		doc.setParagraphAttributes(0, doc.getLength(), center, false);

		GridBagConstraints gbc_lbl_texto = new GridBagConstraints();
		gbc_lbl_texto.insets = new Insets(0, 15, 0, 15);
		gbc_lbl_texto.weighty = 20.0;
		gbc_lbl_texto.weightx = 1.0;
		gbc_lbl_texto.fill = GridBagConstraints.BOTH;
		gbc_lbl_texto.gridx = 1;
		gbc_lbl_texto.gridy = 0;
		panel_gbl_texto.add(ta_texto, gbc_lbl_texto);

		lbl_img = new JLabel("");
		lbl_img.setOpaque(false);
		lbl_img.setHorizontalAlignment(SwingConstants.CENTER);
		GridBagConstraints gbc_lbl_img = new GridBagConstraints();
		gbc_lbl_img.insets = new Insets(0, 15, 0, 15);
		gbc_lbl_img.weighty = 80.0;
		gbc_lbl_img.weightx = 1.0;
		gbc_lbl_img.fill = GridBagConstraints.BOTH;
		gbc_lbl_img.gridx = 1;
		gbc_lbl_img.gridy = 1;
		panel_gbl_texto.add(lbl_img, gbc_lbl_img);
		panel_principal.add(panel_gbl_texto, gbc_panel_texto);
		frame.pack();
		//verBordes();

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
		panel_gbl_texto.setBorder(borderPanel);
		panel_tiempo.setBorder(borderPanel);
		panel_principal.setBorder(borderPanelP);
	}
	
	public JPanel getPanel_principal() {
		return panel_principal;
	}

	public void setPanel_principal(JPanel panel_principal) {
		this.panel_principal = panel_principal;
	}

	public JFrame getFrame() {
		return frame;
	}

	public void setFrame(JFrame frame) {
		this.frame = frame;
	}

	public JPanel getPanel_tiempo() {
		return panel_tiempo;
	}

	public void setPanel_tiempo(JPanel panel_tiempo) {
		this.panel_tiempo = panel_tiempo;
	}

	public JPanel getPanel_texto() {
		return panel_gbl_texto;
	}

	public void setPanel_texto(JPanel panel_texto) {
		this.panel_gbl_texto = panel_texto;
	}

	public JLabel getLbl_tiempo() {
		return lbl_tiempo;
	}

	public void setLbl_tiempo(JLabel lbl_tiempo) {
		this.lbl_tiempo = lbl_tiempo;
	}

	public JTextPane getTa_texto() {
		return ta_texto;
	}

	public void setTa_texto(JTextPane ta_texto) {
		this.ta_texto = ta_texto;
	}

	public JLabel getLbl_img() {
		return lbl_img;
	}

	public void setLbl_img(JLabel lbl_img) {
		this.lbl_img = lbl_img;
	}

}
