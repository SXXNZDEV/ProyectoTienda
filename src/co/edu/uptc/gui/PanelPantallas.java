package co.edu.uptc.gui;

import co.edu.uptc.dto.ReporteInventarioDTO;
import co.edu.uptc.dto.ReporteIvaDTO;
import co.edu.uptc.dto.ReporteMasVendidoDTO;
import co.edu.uptc.dto.ReporteVendedorDTO;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class PanelPantallas extends JPanel {

    private CardLayout cardLayout;
    private PanelInventario panelInventario;
    private PanelVentas panelVentas;
    private PanelPersona panelPersona;
    private Evento evento;
    private VentanaPrincipal ventanaPrincipal;
    private PanelBotones botones;

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

    public PanelPantallas (VentanaPrincipal ventana, Evento evento) {
        this.ventanaPrincipal = ventana;
        cardLayout = new CardLayout();
        this.evento = evento;
    }

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

        // Agrega los paneles al CardLayout
        add(panel, BorderLayout.CENTER);
        add(botones, BorderLayout.SOUTH);
        this.removeAll();
        this.add(panel);
        revalidate();
        repaint();
    }

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

    public void mostrarTablaVendedor(Object[] titulos, List<ReporteVendedorDTO> vendedor) {
        DialogoLista dialogo = new DialogoLista();
        JScrollPane tablaInventario = dialogo.crearTablaVendedor(titulos,vendedor);
        crearTabla(tablaInventario);
    }

    public void mostrarTablaMasVendido(Object[] titulos, ReporteMasVendidoDTO linea, ReporteMasVendidoDTO marca) {
        DialogoLista dialogo = new DialogoLista();
        JScrollPane tablaInventario = dialogo.crearTablaMasVendidos(titulos, linea, marca);
        crearTabla(tablaInventario);
    }

    public void mostrarTablaImpuestos(Object[] titulos, ReporteIvaDTO reporte) {
        DialogoLista dialogo = new DialogoLista();
        JScrollPane tablaInventario = dialogo.crearTablaIVA(titulos, reporte);
        crearTabla(tablaInventario);
    }

    public void crearTabla(JScrollPane scrollPane) {
        JPanel panelTabla = new JPanel(new BorderLayout());
        panelTabla.add(scrollPane, BorderLayout.CENTER);
        this.removeAll();
        this.add(panelTabla, BorderLayout.CENTER);
        revalidate();
        repaint();
    }
}
