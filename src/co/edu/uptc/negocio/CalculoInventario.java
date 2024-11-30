package co.edu.uptc.negocio;

import co.edu.uptc.modelo.Venta;

import java.util.List;

public class CalculoInventario {

    /**
     * Constructor de la clase CalculoInventario
     */
    public CalculoInventario() {}

    /**
     * Calcula el total de celulares registrados.
     * @param ventas
     * @return
     */
    public int calcularCellVendidos(List<Venta> ventas) {
        int celulares = 0;
        for (Venta venta : ventas) {
            celulares += venta.getCantidad();
        }
        return celulares;
    }
}
