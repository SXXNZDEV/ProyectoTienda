package co.edu.uptc.gui;

import java.awt.BorderLayout;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JOptionPane;


import co.edu.uptc.dto.ReporteInventarioDTO;
import co.edu.uptc.dto.ReporteMasVendidoDTO;
import co.edu.uptc.dto.ReporteVendedorDTO;
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
		setDefaultCloseOperation(EXIT_ON_CLOSE);
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
			JOptionPane.showMessageDialog(null, "Inventario cargado");
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
			JOptionPane.showMessageDialog(null, "Vendedores cargados");
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, e.getMessage());
		}
	}

	public void cargarVentas() throws IllegalArgumentException {
		String ventas = infoVentas.obtenerDatos();
		try {
			nuevaTienda.cargarVentas(ventas);
			JOptionPane.showMessageDialog(null,"Ventas Cargadas");
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, e.getMessage());
		}
	}

	public void generarInformeInventario() {
		JOptionPane.showMessageDialog(this, "Creando el informe del inventario...");
		List<ReporteInventarioDTO> reporte;
		DialogoLista nuevo = new DialogoLista();
		Object[] titulos = {"#Celulares", "Total Precio Base", "Total Precio Ventas ", "Total impuestos", "Total Comisiones", "Total ganancias"};
		try {
			reporte = nuevaTienda.calcularTotalInventario();
			nuevo.crearTablaInventario(titulos, reporte);
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, e.getMessage());
		}
	}

	public void generarReporteVentas() throws IllegalArgumentException {
		DialogoLista nuevo = new DialogoLista();
		List<ReporteVendedorDTO> reporte;
		Object[] titulos = {"Tipo #ID", "Nombre", "#Cuenta", "Tipo", "TotalComision", "Total Celulares"};
		try {
			reporte = nuevaTienda.generarReporteVentas();
			nuevo.crearTablaVendedor(titulos, reporte);
		} catch (IllegalArgumentException e) {
			JOptionPane.showMessageDialog(null, e.getMessage());
		}
	}

	public void generarReporteMasVendidos() throws IllegalArgumentException {
		DialogoLista nuevo = new DialogoLista();
		try {
			Object[] titulos = {"Marca +Vendida", "Total Ventas +Marca", "Linea +Vendida", "Total Ventas +Linea"};
			ReporteMasVendidoDTO linea = nuevaTienda.reporteMasVendidoLinea();
			ReporteMasVendidoDTO marca = nuevaTienda.reporteMasVendidoMarca();
			nuevo.crearTablaMasVendidos(titulos, linea, marca);
		} catch (IllegalArgumentException e) {
			JOptionPane.showMessageDialog(null, e.getMessage());
		}
	}

	public void generarReporteIVA() throws IllegalArgumentException {
		DialogoLista nuevo = new DialogoLista();
		try {
			String txt = nuevaTienda.reportIVA();
			//nuevo.agregrarTexto(txt);
			nuevo.setVisible(true);
		} catch (IllegalArgumentException e) {
			JOptionPane.showMessageDialog(null, e.getMessage());
		}
	}

	public void salir() {
		System.exit(0);
	}
}
