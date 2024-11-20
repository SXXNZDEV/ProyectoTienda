package co.edu.uptc.negocio;

import co.edu.uptc.dto.ReporteMasVendidoDTO;
import co.edu.uptc.modelo.Inventario;
import co.edu.uptc.modelo.Vendedor;
import co.edu.uptc.modelo.Venta;

import java.lang.reflect.Array;
import java.util.*;

public class MasVendido {

    public MasVendido() {
    }

    public ReporteMasVendidoDTO lineaMasVendida(Map<String, Vendedor> listaVendedores, ArrayList<Inventario> listaInventario) {
        // Mapa para almacenar los datos acumulados por código de celular
        Map<String, ReporteMasVendidoDTO> reporteMap = new HashMap<>();

        // Iteramos sobre los vendedores y sus ventas
        for (Vendedor vendedor : listaVendedores.values()) {
            for (Venta venta : vendedor.getListaVentas()) {
                // Verificamos si ya existe un registro para esta línea
                String codCelular = venta.getCodCelular();
                if (reporteMap.containsKey(codCelular)) {
                    // Si existe, acumulamos las ventas
                    ReporteMasVendidoDTO reporteExistente = reporteMap.get(codCelular);
                    reporteExistente.setVentas(venta.getCantidad());
                } else {
                    // Si no existe, creamos un nuevo registro
                    ReporteMasVendidoDTO nuevoReporte = new ReporteMasVendidoDTO(buscarMarca(codCelular, listaInventario), buscarLinea(codCelular, listaInventario), codCelular, venta.getCantidad());
                    reporteMap.put(codCelular, nuevoReporte);
                }
            }
        }
        // Encontrar el reporte con la mayor cantidad de ventas
        return Collections.max(reporteMap.values(), Comparator.comparing(ReporteMasVendidoDTO::getVentas));
    }

    public ReporteMasVendidoDTO marcaMasVendida(Map<String, Vendedor> listaVendedores, ArrayList<Inventario> listaInventario) {
        Map<String, ReporteMasVendidoDTO> reporte = new HashMap<>();

        for (Vendedor vendedor : listaVendedores.values()) {
            for (Venta venta : vendedor.getListaVentas()) {

                String marca = buscarMarca(venta.getCodCelular(), listaInventario);
                if (reporte.containsKey(marca)) {
                    ReporteMasVendidoDTO marcaMasVendida = reporte.get(marca);
                    marcaMasVendida.setVentas(venta.getCantidad());
                } else {
                    ReporteMasVendidoDTO nuevoReporte = new ReporteMasVendidoDTO();
                    nuevoReporte.setMarca(marca);
                    nuevoReporte.setVentas(venta.getCantidad());
                    nuevoReporte.setCodigo(vendedor.getCodigo());
                    reporte.put(marca, nuevoReporte);
                }
            }
        }
        return Collections.max(reporte.values(), Comparator.comparing(ReporteMasVendidoDTO::getVentas));
    }

    public String buscarMarca(String codigo, ArrayList<Inventario> listaInventario) {
        for (Inventario celular : listaInventario) {
            if (celular.getCodigo().equals(codigo)) {
                return celular.getMarca();
            }
        }
        return "";
    }

    public String buscarLinea(String codigo, ArrayList<Inventario> listaInventario) {
        for (Inventario celular : listaInventario) {
            if (celular.getCodigo().equals(codigo)) {
                return celular.getLinea();
            }
        }
        return "";
    }

}
