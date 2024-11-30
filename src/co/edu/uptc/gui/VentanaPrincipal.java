package co.edu.uptc.gui;

import java.awt.*;
import java.util.List;

import javax.swing.*;


import co.edu.uptc.dto.ReporteInventarioDTO;
import co.edu.uptc.dto.ReporteIvaDTO;
import co.edu.uptc.dto.ReporteMasVendidoDTO;
import co.edu.uptc.dto.ReporteVendedorDTO;
import co.edu.uptc.negocio.Tienda;

public class VentanaPrincipal extends JFrame {

    /**
     * Panel de pantallas
     */
    private PanelPantallas panelPantallas;

    /**
     * Panel de botones
     */
    private PanelBotones panelBotones;

    /**
     * Evento
     */
    private Evento evento;

    /**
     * Instancia de la clase Tienda
     */
    private Tienda nuevaTienda;

    /**
     * Constructor de la clase VentanaPrincipal
     */
    public VentanaPrincipal() {
        setTitle("Mi Tienda");
        setSize(1100, 600);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        this.nuevaTienda = new Tienda();

        // Crea los paneles y el evento
        evento = new Evento(this);
        panelPantallas = new PanelPantallas(this, evento);
        panelBotones = new PanelBotones(evento);

        // Agrega el panel de botones y el panel de contenido
        setLayout(new BorderLayout());
        add(panelPantallas, BorderLayout.CENTER);
        add(panelBotones, BorderLayout.SOUTH);
        pantallaPrincipal();
    }

    /**
     * Metodo principal
     *
     * @param args argumentos
     */
    public static void main(String[] args) {
        VentanaPrincipal ventana = new VentanaPrincipal();
        ventana.setVisible(true);
    }

    /**
     * Metodo que muestra la pantalla principal de ingreso de datos.
     */
    public void pantallaPrincipal() {
        panelPantallas.pantalla();
        panelPantallas.setVisible(true);
    }

    /**
     * Metodo que carga el inventario ingresado.
     */
    public void cargarInfoInventario() {
        String variable = panelPantallas.getPanelInventario().obtenerDatos();
        try {
            nuevaTienda.cargarInventario(variable);
            JOptionPane.showMessageDialog(null, "Inventario cargado");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
        }
    }

    /**
     * Metodo que carga el vendedor ingresado.
     */
    public void cargarInfoVendedor() {

        String vendedor = panelPantallas.getPanelPersona().obtenerDatos();
        try {
            nuevaTienda.cargarVendedor(vendedor);
            JOptionPane.showMessageDialog(null, "Vendedores cargados");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
        }
    }

    /**
     * Metodo que carga las ventas ingresadas.
     */
    public void cargarVentas() {
        String ventas = panelPantallas.getPanelVentas().obtenerDatos();
        try {
            nuevaTienda.cargarVentas(ventas);
            JOptionPane.showMessageDialog(null, "Ventas Cargadas");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
        }
    }

    /**
     * Metodo que genera el informe del inventario.
     */
    public void generarInformeInventario() {
        JOptionPane.showMessageDialog(this, "Creando el informe del inventario...");
        List<ReporteInventarioDTO> reporte;
        Object[] titulos = {"#Celulares", "Total Precio Base", "Total Precio Ventas ", "Total impuestos", "Total Comisiones", "Total ganancias"};
        try {
            reporte = nuevaTienda.calcularTotalInventario();
            panelPantallas.mostrarTablaInventario(titulos, reporte);
            panelPantallas.setVisible(true);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
        }
    }

    /**
     * Metodo que genera el reporte de ventas.
     */
    public void generarReporteVentas() {
        JOptionPane.showMessageDialog(this, "Creando el informe del reporte de Ventas...");
        Object[] titulos = {"Tipo #ID", "Nombre", "#Cuenta", "Tipo", "TotalComision", "Total Celulares"};
        try {
            List<ReporteVendedorDTO> reporte = nuevaTienda.generarReporteVentas();
            panelPantallas.mostrarTablaVendedor(titulos, reporte);
            panelPantallas.setVisible(true);
        } catch (IllegalArgumentException e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
        }
    }

    /**
     * Metodo que genera el reporte de marca y linea mas vendidas.
     */
    public void generarReporteMasVendidos() {
        JOptionPane.showMessageDialog(this, "Creando el informe del reporte de marca y linea mas vendidas...");
        try {
            Object[] titulos = {"Marca +Vendida", "Total Ventas +Marca", "Linea +Vendida", "Total Ventas +Linea"};
            ReporteMasVendidoDTO linea = nuevaTienda.reporteMasVendidoLinea();
            ReporteMasVendidoDTO marca = nuevaTienda.reporteMasVendidoMarca();
            panelPantallas.mostrarTablaMasVendido(titulos, linea, marca);
            panelPantallas.setVisible(true);
        } catch (IllegalArgumentException e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
        }
    }

    /**
     * Metodo que genera el reporte de impuestos.
     */
    public void generarReporteIVA() {
        JOptionPane.showMessageDialog(this, "Creando el informe de impuestos...");
        try {
            ReporteIvaDTO reporte;
            Object[] titulos = {"Impuesto", "Total Bases Gravables", "Impuestos"};
            reporte = nuevaTienda.reportIVA();
            panelPantallas.mostrarTablaImpuestos(titulos, reporte);
            panelPantallas.setVisible(true);
        } catch (IllegalArgumentException e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
        }
    }

    /**
     * Metodo para salir de la aplicación.
     */
    public void salir() {
        System.exit(0);
    }
}
