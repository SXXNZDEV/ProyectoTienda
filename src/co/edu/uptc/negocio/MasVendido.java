package co.edu.uptc.negocio;

import co.edu.uptc.dto.ReporteMasVendidoDTO;
import co.edu.uptc.modelo.Inventario;
import co.edu.uptc.modelo.Vendedor;
import co.edu.uptc.modelo.Venta;

import java.util.*;

public class MasVendido {

    /**
     * Constructor de la clase MasVendido
     */
    public MasVendido() {}

    /**
     * Busca la linea de celulares que mas ventas tiene.
     * @param listaVendedores mapa de vendedores.
     * @param listaInventario liste de inventario.
     * @return reporte de la linea de celulares mas vendidos.
     */
    public ReporteMasVendidoDTO buscarLineaMasVendida(Map<String, Vendedor> listaVendedores, ArrayList<Inventario> listaInventario) {
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

    /**
     * Calcula el precio de venta de un celular.
     * @param cod codigo del celular.
     * @param cantidad cantidad de celulares.
     * @param inventario lista de inventario.
     * @return precio de venta del celular.
     */
    public double calcularPrecioVenta(String cod, double cantidad, List<Inventario> inventario) {
        double precio = buscarPrecioBase(cod, inventario) * (1 + ((double) 25 / 100));
        if (precio > 600000) {
            return precio * (1.19) * cantidad;
        } else {
            return precio * (1.05) * cantidad;
        }
    }

    /**
     * Busca el precio base de un celular.
     * @param codigo codigo del celular.
     * @param inventario lista de inventario.
     * @return precio base del celular.
     */
    public long buscarPrecioBase (String codigo, List<Inventario> inventario) {
        for (Inventario invent : inventario) {
            if (invent.getCodigo().equalsIgnoreCase(codigo)) {
                return invent.getPrecioBase();
            }
        }
        return 0;
    }

    /**
     * Busca la marca de un celular que mas ventas tiene.
     * @param listaVendedores lista de vendedores.
     * @param listaInventario lista de inventario
     * @return reporte de la marca mas vendida.
     */
    public ReporteMasVendidoDTO marcaMasVendida(Map<String, Vendedor> listaVendedores, ArrayList<Inventario> listaInventario)  {
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

    /**
     * Busca la marca de un celular.
     * @param codigo codigo del celular.
     * @param listaInventario lista de inventario.
     * @return marca del celular.
     */
    public String buscarMarca(String codigo, ArrayList<Inventario> listaInventario) {
        for (Inventario celular : listaInventario) {
            if (celular.getCodigo().equals(codigo)) {
                return celular.getMarca();
            }
        }
        return "";
    }

    /**
     * Busca la linea de un celular.
     * @param codigo codigo del celular.
     * @param listaInventario lista de inventario.
     * @return linea del celular.
     */
    public String buscarLinea(String codigo, ArrayList<Inventario> listaInventario) {
        for (Inventario celular : listaInventario) {
            if (celular.getCodigo().equals(codigo)) {
                return celular.getLinea();
            }
        }
        return "";
    }

}
