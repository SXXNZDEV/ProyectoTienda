package co.edu.uptc.gui;

import java.awt.BorderLayout;

import javax.swing.*;
import javax.swing.border.TitledBorder;

public class PanelVentas extends JPanel {

	/**
	 * Texto de ventas
	 */
	private JTextArea txInformacion;

	/**
	 * Constructor de la clase PanelVentas.
	 * @param evento evento que se ejecutará cuando se ingrese un dato.
	 */
	public PanelVentas(Evento evento) {
		TitledBorder titulo = new TitledBorder("Linea de Texto de Ventas");
		titulo.setTitleJustification(TitledBorder.CENTER);
		setBorder(titulo);

		txInformacion= new JTextArea(30,30);

		JButton accion1= new JButton(Evento.CARGAR_VENTAS);
		accion1.addActionListener(evento);
		accion1.setActionCommand(Evento.CARGAR_VENTAS);
		setLayout(new BorderLayout());
		JScrollPane scrollPane = new JScrollPane(txInformacion);
		
		add(scrollPane,BorderLayout.CENTER);
		add(accion1,BorderLayout.SOUTH);
	}

	/**
	 * Metodo que obtiene los datos ingresados en el panel de ventas.
	 * @return datos ingresados en el panel de ventas.
	 */
	public String obtenerDatos() {
		return txInformacion.getText();
	}
}
