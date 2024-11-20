package co.edu.uptc.negocio;

import co.edu.uptc.modelo.Venta;

import java.util.List;

public class CalculoInventario {

    public CalculoInventario() {}

    public int calcularCellVendidos(List<Venta> ventas) {
        int celulares = 0;
        for (Venta venta : ventas) {
            celulares += venta.getCantidad();
        }
        return celulares;
    }
}
