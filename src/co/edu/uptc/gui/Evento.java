package co.edu.uptc.gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Evento implements ActionListener {

    /**
     * Titulo de boton de ventas
     */
    public static final String VENTAS = "VENTAS";

    /**
     * Titulo de boton de stock
     */
    public static final String STOCK = "STOCK";

    /**
     * Titulo de boton de mas vendidos
     */
    public static final String MAS_VENDIDO = "+ VENDIDO";

    /**
     * Titulo de boton de impuestos
     */
    public static final String IMPUESTOS = "IMPUESTOS";

    /**
     * Titulo de boton de cargar Inventario
     */
    public static final String CARGAR = "Cargar Inventario";

    /**
     * Titulo de boton de cargar Vendedor
     */
    public static final String CARGAR_PERSONAS = "Cargar Vendedor";

    /**
     * Titulo de boton de cargar Ventas
     */
    public static final String CARGAR_VENTAS = "Cargar Ventas";

    /**
     * Titulo de boton de salir
     */
    public static final String SALIR = "SALIR";

    /**
     * Titulo de boton de ingresar
     */
    public static final String INGRESAR = "INGRESAR";

    /**
     * Instancia de PanelPantallas
     */
    private PanelPantallas panelPantallas;

    /**
     * Instancia de Ventana principal
     */
    VentanaPrincipal ventanaPrincipal;

    /**
     * Constructor de la clase Evento
     * @param ventanaPrincipal instancia de VentanaPrincipal
     */
    public Evento(VentanaPrincipal ventanaPrincipal) {
        this.ventanaPrincipal = ventanaPrincipal;
        panelPantallas = new PanelPantallas(ventanaPrincipal, this);
    }

    /**
     * Metodo que ejecuta cada accion correspondiente al boton presionado.
     * @param e evento que se ejecutará cuando se presione el boton correspondiente.
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        String evento = e.getActionCommand();
        ventanaPrincipal = panelPantallas.getVentana();
        switch (evento) {
            case CARGAR -> ventanaPrincipal.cargarInfoInventario();
            case CARGAR_VENTAS -> ventanaPrincipal.cargarVentas();
            case CARGAR_PERSONAS -> ventanaPrincipal.cargarInfoVendedor();
            case STOCK -> ventanaPrincipal.generarInformeInventario();
            case VENTAS -> ventanaPrincipal.generarReporteVentas();
            case MAS_VENDIDO -> ventanaPrincipal.generarReporteMasVendidos();
            case IMPUESTOS -> ventanaPrincipal.generarReporteIVA();
            case SALIR -> ventanaPrincipal.salir();
            case INGRESAR -> ventanaPrincipal.pantallaPrincipal();
        }
    }

}
