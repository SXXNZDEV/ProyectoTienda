package co.edu.uptc.gui;

import javax.swing.JButton;
import javax.swing.JPanel;

public class PanelBotones extends JPanel {

	/**
	 * Constructor de la clase PanelBotones
	 * @param evento se ejecutará cuando se presióne el botón correspondiente.
	 */
	public PanelBotones(Evento evento) {
		JButton accion1= new JButton(Evento.STOCK);
		accion1.addActionListener(evento);
		accion1.setActionCommand(Evento.STOCK);
		
		JButton accion2= new JButton(Evento.VENTAS);
		accion2.addActionListener(evento);
		accion2.setActionCommand(Evento.VENTAS);
		
		JButton accion3= new JButton(Evento.MAS_VENDIDO);
		accion3.addActionListener(evento);
		accion3.setActionCommand(Evento.MAS_VENDIDO);
		
		JButton accion4= new JButton(Evento.IMPUESTOS);
		accion4.addActionListener(evento);
		accion4.setActionCommand(Evento.IMPUESTOS);
		
		JButton accion5= new JButton(Evento.SALIR);
		accion5.addActionListener(evento);
		accion5.setActionCommand(Evento.SALIR);

		JButton accion6 = new JButton(Evento.INGRESAR);
		accion6.addActionListener(evento);
		accion6.setActionCommand(Evento.INGRESAR);

		add(accion6);
		add(accion1);
		add(accion2);
		add(accion3);
		add(accion4);
		add(accion5);
	}
}
