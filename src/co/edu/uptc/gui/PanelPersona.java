package co.edu.uptc.gui;

import java.awt.BorderLayout;

import javax.swing.*;
import javax.swing.border.TitledBorder;

public class PanelPersona extends JPanel {

	private JTextArea txInformacion;
	
	public PanelPersona(Evento evento) {
		TitledBorder titulo = new TitledBorder("Linea de Texto de Vendedor");
		titulo.setTitleJustification(TitledBorder.CENTER);
		setBorder(titulo);
		txInformacion= new JTextArea(60,30);
		
		JButton accion1= new JButton(Evento.CARGAR_PERSONAS);
		accion1.addActionListener(evento);
		accion1.setActionCommand(Evento.CARGAR_PERSONAS);
		setLayout(new BorderLayout());
		JScrollPane scrollPane = new JScrollPane(txInformacion);
		
		add(scrollPane, BorderLayout.CENTER);
		add(accion1, BorderLayout.SOUTH);
	}
	
	public String obtenerDatos() {
		return txInformacion.getText();
	}
}
