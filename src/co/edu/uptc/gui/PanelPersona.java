package co.edu.uptc.gui;

import java.awt.BorderLayout;

import javax.swing.*;
import javax.swing.border.TitledBorder;

public class PanelPersona extends JPanel {

	/**
	 * Texto de vendedor
	 */
	private JTextArea txInformacion;

	/**
	 * Constructor de la clase PanelPersona.
	 * @param evento evento que se ejecutará cuando se ingrese un dato.
	 */
	public PanelPersona(Evento evento) {
		TitledBorder titulo = new TitledBorder("Linea de Texto de Vendedor");
		titulo.setTitleJustification(TitledBorder.CENTER);
		setBorder(titulo);
		txInformacion= new JTextArea(30,30);
		
		JButton accion1= new JButton(Evento.CARGAR_PERSONAS);
		accion1.addActionListener(evento);
		accion1.setActionCommand(Evento.CARGAR_PERSONAS);
		setLayout(new BorderLayout());
		JScrollPane scrollPane = new JScrollPane(txInformacion);
		
		add(scrollPane, BorderLayout.CENTER);
		add(accion1, BorderLayout.SOUTH);
	}

	/**
	 * Metodo que obtiene los datos ingresados en el panel de vendedor.
	 * @return datos ingresados en el panel de vendedor.
	 */
	public String obtenerDatos() {
		return txInformacion.getText();
	}
}
