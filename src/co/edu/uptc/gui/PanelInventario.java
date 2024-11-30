package co.edu.uptc.gui;

import java.awt.BorderLayout;

import javax.swing.*;
import javax.swing.border.TitledBorder;

public class PanelInventario extends JPanel {

	/**
	 * Texto de Inventario
	 */
	private JTextArea txInformacion;

	/**
	 * Constructor de la clase PanelInventario
	 * @param evento evento que se ejecutará cuando se ingrese un dato.
	 */
	public PanelInventario(Evento evento) {
		TitledBorder titulo = new TitledBorder("Linea de Texto de Inventario");
		titulo.setTitleJustification(TitledBorder.CENTER);
		setBorder(titulo);
		txInformacion= new JTextArea(30,30);
		
		JButton accion1= new JButton(Evento.CARGAR);
		accion1.addActionListener(evento);
		accion1.setActionCommand(Evento.CARGAR);
		setLayout(new BorderLayout());

		JScrollPane scrollPane = new JScrollPane(txInformacion);

		add(scrollPane,BorderLayout.CENTER);
		add(accion1,BorderLayout.SOUTH);
	}

	/**
	 * Metodo que obtiene los datos ingresados en el panel de Inventario.
	 * @return datos ingresados en el panel de Inventario.
	 */
	public String obtenerDatos() {
		return txInformacion.getText();
	}
}
