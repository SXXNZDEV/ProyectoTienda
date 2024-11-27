package co.edu.uptc.negocio;

import co.edu.uptc.dto.ReporteIvaDTO;
import co.edu.uptc.modelo.Inventario;
import co.edu.uptc.modelo.Vendedor;
import co.edu.uptc.modelo.Venta;

import java.util.ArrayList;
import java.util.Map;

public class CalculoIVA {

    public CalculoIVA() {}

    public ReporteIvaDTO calcularIVA(Map<String, Vendedor> listaVendedores, ArrayList<Inventario> listaInventario) {
        ReporteIvaDTO reporte = new ReporteIvaDTO();
        for (Vendedor vendedor : listaVendedores.values()) {
            for (Venta venta : vendedor.getListaVentas()) {
                long precioBase = buscarPrecioBase(venta.getCodCelular(), listaInventario);
                double precioGanancia = precioBase * 1.25;
                double precioTotal;
                if (precioGanancia > 600000) {
                    precioTotal = precioGanancia * 1.19;
                    reporte.setIvaMayor(precioTotal * venta.getCantidad() - (venta.getCantidad() * precioGanancia));
                    reporte.setTotalBasesGravablesMayor(precioGanancia * venta.getCantidad());
                } else {
                    precioTotal = precioGanancia * 1.05;
                    reporte.setIvaMenor(precioTotal * venta.getCantidad() - (venta.getCantidad() * precioGanancia));
                    reporte.setTotalBasesGravablesMenor(precioGanancia * venta.getCantidad());
                }
            }
        }
        return reporte;
    }

    public long buscarPrecioBase(String codigo, ArrayList<Inventario> listaInvetario) {
        for (Inventario celular : listaInvetario) {
            if (celular.getCodigo().equals(codigo)) {
                return celular.getPrecioBase();
            }
        }
        return 0;
    }
}
