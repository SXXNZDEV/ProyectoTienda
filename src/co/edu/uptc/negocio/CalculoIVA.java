package co.edu.uptc.negocio;

import co.edu.uptc.dto.ReporteIvaDTO;
import co.edu.uptc.modelo.Inventario;
import co.edu.uptc.modelo.Vendedor;
import co.edu.uptc.modelo.Venta;

import java.util.ArrayList;
import java.util.Map;

public class CalculoIVA {

    public ReporteIvaDTO calcularIVA(Map<String, Vendedor> listaVendedores, ArrayList<Inventario> listaInventario) {
        ReporteIvaDTO reporte = new ReporteIvaDTO();
        for (Vendedor vendedor : listaVendedores.values()) {
            for (Venta venta : vendedor.getListaVentas()) {
                long precioBase = buscarPrecioBase(venta.getCodCelular(), listaInventario);
                if (precioBase > 600000) {
                    reporte.setIvaMayor(precioBase * venta.getCantidad() * 0.19);
                    reporte.setTotalBasesGravablesMayor(precioBase * venta.getCantidad());
                } else {
                    reporte.setIvaMenor(precioBase * venta.getCantidad() * 0.05);
                    reporte.setTotalBasesGravablesMenor(precioBase * venta.getCantidad());
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
