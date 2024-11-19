package co.edu.uptc.gui;

import java.awt.BorderLayout;
import java.text.NumberFormat;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

import co.edu.uptc.dto.ReporteInventarioDTO;
import co.edu.uptc.negocio.Tienda;

public class VentanaPrincipal extends JFrame {

	private PanelInventario info;
	private PanelVentas infoVentas;
	private PanelBotones botones;
	private PanelPersona persona;
	
	private Tienda nuevaTienda;
	
	public VentanaPrincipal(){
		setTitle("Mi Tienda");
		setSize(1100,600);
		
		Evento evento= new Evento(this);
		info= new PanelInventario(evento);
		infoVentas= new PanelVentas(evento);
		botones = new PanelBotones(evento);
		persona = new PanelPersona(evento);
		
		setLayout(new BorderLayout());
		add(info,BorderLayout.WEST);
		add(persona,BorderLayout.CENTER);
		add(infoVentas,BorderLayout.EAST);
		add(botones,BorderLayout.SOUTH);
		
		nuevaTienda= new Tienda();
	}
	public static void main(String[] args) {
		VentanaPrincipal nueva= new VentanaPrincipal();
		nueva.setVisible(true);
	}
	
	public void cargarInfoInventario() {
		//TODO implementar logica para separa información
		// trae lo que hay en textArea en String
		String variable=info.obtenerDatos();
		try {
			nuevaTienda.cargarInventario(variable);
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, e.getMessage());
		}
	}

	
	public void cargarInfoVendedor() {
		//TODO implementar logica para separa información
		// trae lo que hay en textArea en String
		String vendedor = persona.obtenerDatos();
		try {
			nuevaTienda.cargarVendedor(vendedor);
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, e.getMessage());
		}
	}

	public void cargarVentas() throws IllegalArgumentException {
		String ventas = infoVentas.obtenerDatos();
		try {
			nuevaTienda.cargarVentas(ventas);
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, e.getMessage());
		}
	}

	public void generarInformeInventario() {
		JOptionPane.showMessageDialog(this, "Crear infome de inventario" );
		ReporteInventarioDTO nuevoReporte= nuevaTienda.calcularTotalInventario();
		DialogoLista nuevo= new DialogoLista();
		NumberFormat format = NumberFormat.getCurrencyInstance();
		format.setMinimumFractionDigits(0);
		StringBuilder sb = new StringBuilder(String.format("%-20s | %-20s | %-20s | %-20s | %-20s | %-20s\n", "Total Celulares", "Total Precio Base", "Total Precio Ventas ", "Total impuestos", "Total Comisiones", "Total ganancias\n"));
		sb.append(String.format("%-20d | %-20s | %-20s | %-20s | %-20s | %-20s", nuevoReporte.getTotalProductos(), format.format(nuevoReporte.getPrecioBase()), format.format(nuevoReporte.getTotalPrecioVenta()), format.format(nuevoReporte.getTotalImpuesto()), format.format(nuevoReporte.getTotalComisiones()), format.format(nuevoReporte.getTotalGanancias())));
		nuevo.agregrarTexto(sb.toString());
		nuevo.setVisible(true);
	}

	public void generarReporteVentas() throws IllegalArgumentException {
		DialogoLista nuevo = new DialogoLista();
		try {
			String txt = nuevaTienda.generarReporteVentas();
			nuevo.agregrarTexto(txt);
			nuevo.setVisible(true);
		} catch (IllegalArgumentException e) {
			JOptionPane.showMessageDialog(null, e.getMessage());
		}
	}

	public void generarReporteMasVendidos() throws IllegalArgumentException {
		DialogoLista nuevo = new DialogoLista();
		try {
			String txt = nuevaTienda.generarReporteMasVendidos();
			nuevo.agregrarTexto(txt);
			nuevo.setVisible(true);
		} catch (IllegalArgumentException e) {
			JOptionPane.showMessageDialog(null, e.getMessage());
		}
	}

	public void generarReporteIVA() throws IllegalArgumentException {
		DialogoLista nuevo = new DialogoLista();
		try {
			String txt = nuevaTienda.reportIVA();
			nuevo.agregrarTexto(txt);
			nuevo.setVisible(true);
		} catch (IllegalArgumentException e) {
			JOptionPane.showMessageDialog(null, e.getMessage());
		}
	}

	public void salir() {
		System.exit(0);
	}
}
