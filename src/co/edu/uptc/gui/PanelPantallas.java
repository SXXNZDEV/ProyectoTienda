package co.edu.uptc.gui;

import co.edu.uptc.dto.ReporteInventarioDTO;
import co.edu.uptc.dto.ReporteIvaDTO;
import co.edu.uptc.dto.ReporteMasVendidoDTO;
import co.edu.uptc.dto.ReporteVendedorDTO;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class PanelPantallas extends JPanel {

    /**
     * CardLayout
     */
    private CardLayout cardLayout;

    /**
     * Instancia de Panel de Inventario
     */
    private PanelInventario panelInventario;

    /**
     * Instancia de Panel de Ventas
     */
    private PanelVentas panelVentas;

    /**
     * Instancia de Panel de Persona
     */
    private PanelPersona panelPersona;

    /**
     * Evento que se ejecutará cuando se ingrese un dato.
     */
    private Evento evento;

    /**
     * Instancia de Ventana principal
     */
    private VentanaPrincipal ventanaPrincipal;

    /**
     * Instancia de PanelBotones
     */
    private PanelBotones botones;

    private GridLayout gridLayout;

    public PanelInventario getPanelInventario() {
        return panelInventario;
    }

    public PanelVentas getPanelVentas() {
        return panelVentas;
    }

    public PanelPersona getPanelPersona() {
        return panelPersona;
    }

    public VentanaPrincipal getVentana() {
        return ventanaPrincipal;
    }

    /**
     * Constructor de la clase PanelPantallas
     * @param ventana instancia de Ventana principal
     * @param evento evento que se ejecutara cuando ingrese datos o se presione el boton correspondiente.
     */
    public PanelPantallas (VentanaPrincipal ventana, Evento evento) {
        this.ventanaPrincipal = ventana;
        gridLayout = new GridLayout();
        cardLayout = new CardLayout();
        this.evento = evento;
    }

    /**
     * Metodo que muestra la pantalla principal de ingreso de datos.
     */
    public void pantalla() {
        JPanel panel = new JPanel(new BorderLayout());
        setLayout(cardLayout);

        // Crea los paneles
        panelInventario = new PanelInventario(evento);
        panelVentas = new PanelVentas(evento);
        panelPersona = new PanelPersona(evento);
        botones = new PanelBotones(evento);
        panel.add(panelInventario, BorderLayout.WEST);
        panel.add(panelVentas, BorderLayout.EAST);
        panel.add(panelPersona, BorderLayout.CENTER);
        panel.add(botones, BorderLayout.SOUTH);

        //Agrega los paneles al CardLayout
        add(panel, BorderLayout.CENTER);
        add(botones, BorderLayout.SOUTH);
        this.removeAll();
        this.add(panel);
        revalidate();
        repaint();
    }

    /**
     * Metodo que muestra la tabla de inventario.
     * @param titulos titulos de las columnas.
     * @param inventario lista de reportes de inventario.
     */
    public void mostrarTablaInventario(Object[] titulos, List<ReporteInventarioDTO> inventario) {
        // Crear un objeto DialogoLista
        DialogoLista dialogo = new DialogoLista();

        // Crear la tabla de inventario pasando los datos y los títulos
        JScrollPane tablaInventario = dialogo.crearTablaInventario(titulos,inventario);

        // Mostrar la tabla en el JFrame
        // Crear un panel para mostrar la tabla
        JPanel panelTabla = new JPanel(new BorderLayout());
        panelTabla.add(tablaInventario, BorderLayout.CENTER);

        // Añadir el panel con la tabla al JFrame
        this.removeAll();  // Limpiar cualquier componente anterior
        this.add(panelTabla, BorderLayout.CENTER);
        revalidate();  // Asegurarse de que el JFrame se actualice
        repaint();  // Redibujar la ventana
    }

    /**
     * Metodo que muestra la tabla de reporte de vendedor.
     * @param titulos titulos de las columnas.
     * @param vendedor lista de reportes de vendedor.
     */
    public void mostrarTablaVendedor(Object[] titulos, List<ReporteVendedorDTO> vendedor) {
        DialogoLista dialogo = new DialogoLista();
        JScrollPane tablaInventario = dialogo.crearTablaVendedor(titulos,vendedor);
        crearPanel(tablaInventario);
    }

    /**
     * Metodo que muestra la tabla de reporte de marca y linea mas vendidas.
     * @param titulos titulos de las columnas.
     * @param linea lista de reportes de linea.
     * @param marca lista de reportes de marca.
     */
    public void mostrarTablaMasVendido(Object[] titulos, ReporteMasVendidoDTO linea, ReporteMasVendidoDTO marca) {
        DialogoLista dialogo = new DialogoLista();
        JScrollPane tablaInventario = dialogo.crearTablaMasVendidos(titulos, linea, marca);
        crearPanel(tablaInventario);
    }

    /**
     * Metodo que muestra la tabla de reporte de impuestos.
     * @param titulos titulos de las columnas.
     * @param reporte lista de reportes de impuestos.
     */
    public void mostrarTablaImpuestos(Object[] titulos, ReporteIvaDTO reporte) {
        DialogoLista dialogo = new DialogoLista();
        JScrollPane tablaInventario = dialogo.crearTablaIVA(titulos, reporte);
        crearPanel(tablaInventario);
    }

    /**
     * Metodo que crea el panel con la tabla.
     * @param scrollPane objeto JScrollPane que contiene la tabla.
     */
    public void crearPanel(JScrollPane scrollPane) {
        JPanel panelTabla = new JPanel(new BorderLayout());
        panelTabla.add(scrollPane, BorderLayout.CENTER);
        this.removeAll();
        this.add(panelTabla, BorderLayout.CENTER);
        revalidate();
        repaint();
    }
}
