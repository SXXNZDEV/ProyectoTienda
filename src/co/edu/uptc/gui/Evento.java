package co.edu.uptc.gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Evento implements ActionListener {

    public static final String VENTAS = "VENTAS";

    public static final String STOCK = "STOCK";

    public static final String MAS_VENDIDO = "+ VENDIDO";

    public static final String IMPUESTOS = "IMPUESTOS";

    public static final String CARGAR = "Cargar Inventario";

    public static final String CARGAR_PERSONAS = "Cargar Vendedor";

    public static final String CARGAR_VENTAS = "Cargar Ventas";

    public static final String SALIR = "SALIR";

    public static final String INGRESAR = "INGRESAR";

    private PanelPantallas panelPantallas;
    VentanaPrincipal ventanaPrincipal;

    public Evento(VentanaPrincipal ventanaPrincipal) {
        this.ventanaPrincipal = ventanaPrincipal;
        panelPantallas = new PanelPantallas(ventanaPrincipal, this);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String evento = e.getActionCommand();
        ventanaPrincipal = panelPantallas.getVentana();
        if (evento.equals(CARGAR)) {
            ventanaPrincipal.cargarInfoInventario();
        } else if (evento.equals(CARGAR_VENTAS)) {
            ventanaPrincipal.cargarVentas();
        } else if (evento.equals(CARGAR_PERSONAS)) {
            ventanaPrincipal.cargarInfoVendedor();
        } else if (evento.equals(STOCK)) {
            ventanaPrincipal.generarInformeInventario();
        } else if (evento.equals(VENTAS)) {
            ventanaPrincipal.generarReporteVentas();
        } else if (evento.equals(MAS_VENDIDO)) {
            ventanaPrincipal.generarReporteMasVendidos();
        } else if (evento.equals(IMPUESTOS)) {
            ventanaPrincipal.generarReporteIVA();
        } else if (evento.equals(SALIR)) {
            ventanaPrincipal.salir();
        } else if (evento.equals(INGRESAR)) {
            ventanaPrincipal.pantallaPrincipal();
        }
    }

}
