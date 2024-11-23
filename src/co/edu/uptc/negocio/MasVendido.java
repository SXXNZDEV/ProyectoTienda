package co.edu.uptc.negocio;

import co.edu.uptc.dto.ReporteMasVendidoDTO;
import co.edu.uptc.modelo.Inventario;
import co.edu.uptc.modelo.Vendedor;
import co.edu.uptc.modelo.Venta;

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

                    ReporteMasVendidoDTO reporteExistente = reporteMap.get(codCelular);
                    reporteExistente.setVentasMarca((long)(calcularPrecioVenta(venta.getCodCelular(), venta.getCantidad(), listaInventario)));
                } else {
                    ReporteMasVendidoDTO nuevoReporte = new ReporteMasVendidoDTO(buscarMarca(codCelular, listaInventario), buscarLinea(codCelular, listaInventario), codCelular, calcularPrecioVenta(venta.getCodCelular(), venta.getCantidad(), listaInventario));
                    reporteMap.put(codCelular, nuevoReporte);
                }
            }
        }
        // Encontrar el reporte con la mayor cantidad de ventas
        return Collections.max(reporteMap.values(), Comparator.comparing(ReporteMasVendidoDTO::getVentasMarca));
    }

    public double calcularPrecioVenta(String cod, double cantidad, List<Inventario> inventario) {
        double precio = buscarPrecioBase(cod, inventario) * (1 + ((double) 25 / 100));
        if (precio > 600000) {
            return precio * (1.19) * cantidad;
        } else {
            return precio * (1.05) * cantidad;
        }
    }

    public long buscarPrecioBase (String codigo, List<Inventario> inventario) {
        for (Inventario invent : inventario) {
            if (invent.getCodigo().equalsIgnoreCase(codigo)) {
                return invent.getPrecioBase();
            }
        }
        return 0;
    }

    public ReporteMasVendidoDTO marcaMasVendida(Map<String, Vendedor> listaVendedores, ArrayList<Inventario> listaInventario) {
        Map<String, ReporteMasVendidoDTO> reporte = new HashMap<>();

        for (Vendedor vendedor : listaVendedores.values()) {
            for (Venta venta : vendedor.getListaVentas()) {

                String marca = buscarMarca(venta.getCodCelular(), listaInventario);
                if (reporte.containsKey(marca)) {
                    ReporteMasVendidoDTO marcaMasVendida = reporte.get(marca);
                    marcaMasVendida.setVentasMarca(calcularPrecioVenta(venta.getCodCelular(), venta.getCantidad(), listaInventario));
                } else {
                    ReporteMasVendidoDTO nuevoReporte = new ReporteMasVendidoDTO();
                    nuevoReporte.setMarca(marca);
                    nuevoReporte.setVentasMarca(calcularPrecioVenta(venta.getCodCelular(), venta.getCantidad(), listaInventario));
                    nuevoReporte.setCodigo(vendedor.getCodigo());
                    reporte.put(marca, nuevoReporte);
                }
            }
        }
        return Collections.max(reporte.values(), Comparator.comparing(ReporteMasVendidoDTO::getVentasMarca));
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
