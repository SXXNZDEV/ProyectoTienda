package co.edu.uptc.negocio;

import co.edu.uptc.dto.ReporteVendedorDTO;
import co.edu.uptc.modelo.Inventario;
import co.edu.uptc.modelo.Vendedor;

import java.util.List;

public class CalculoVendedor {

    /**
     * Constructor de la clase CalculoVendedor
     */
    public CalculoVendedor() {}

    /**
     * Calcula el precio base de los celulares registrados.
     *
     * @param inventario lista de inventario.
     * @return precio base de los celulares.
     */
    public long calcularPrecioBase(List<Inventario> inventario) {
        long basePrice = 0;
        for (Inventario celular : inventario) {
            basePrice += celular.getCantidad() * celular.getPrecioBase();
        }
        return basePrice;
    }

    /**
     * Calcula el precio de venta de los celulares registrados.
     *
     * @param inventario lista de inventario.
     * @return precio de venta de los celulares.
     */
    public double calcularPrecioVenta(List<Inventario> inventario) {
        double salesPrice = 0;
        for (Inventario celular : inventario) {
            double precio = calcularGanancia(celular.getPrecioBase());
            if (precio > 600000) {
                salesPrice += celular.getCantidad() * precio + (1 + (celular.getCantidad() * precio * 0.19));
            } else {
                salesPrice += celular.getCantidad() * precio + (1 + (celular.getCantidad() * precio * 0.05));
            }
        }
        return salesPrice;
    }

    /**
     * Calcula el valor del IVA del 19% de los celulares con precio superior a 600000.
     *
     * @param inventario lista de inventario.
     * @return valor del IVA del 19% de los celulares registrados.
     */
    public double calcularIVAMayor(List<Inventario> inventario) {
        double worth = 0;
        for (Inventario celular : inventario) {
            double precio = calcularGanancia(celular.getPrecioBase());
            if (precio > 600000) {
                worth += precio * celular.getCantidad() * 0.19;
            }
        }
        return worth;
    }

    /**
     * Calcula el valor del IVA del 5% de los celulares con precio inferior a 600000.
     *
     * @param inventario lista de inventario.
     * @return valor del IVA del 5% de los celulares registrados.
     */
    public double calcularIVAMenor(List<Inventario> inventario) {
        double worth = 0;
        for (Inventario celular : inventario) {
            double precio = calcularGanancia(celular.getPrecioBase());
            if (precio <= 600000) {
                worth += precio * celular.getCantidad() * 0.05;
            }
        }
        return worth;
    }

    /**
     * Calcula la comision de los celulares registrados.
     * @param inventario lista del inventario.
     * @return comision de los celulares registrados.
     */
    public double calculateCommissions(List<Inventario> inventario) {
        double commission = 0;
        for (Inventario celular : inventario) {
            commission += celular.getCantidad() * celular.getPrecioBase() * 0.05;
        }
        return commission;
    }

    /**
     * Calcula el total de celulares registrados.
     *
     * @param inventario lista de inventario.
     * @return total de celulares registrados.
     */
    public int calcularTotalCelulares(List<Inventario> inventario) {
        int totalCellPhones = 0;
        for (Inventario celular : inventario) {
            totalCellPhones += celular.getCantidad();
        }
        return totalCellPhones;
    }

    /**
     * Calcula el valor de ganancias de los celulares registrados.
     * @param inventario lista de inventario.
     * @return valor de ganancias de los ccelulares registrados.
     */
    public double calcularPrecioGanancia(List<Inventario> inventario) {
        return precioGanancias(inventario) - calcularPrecioBase(inventario) - calculateCommissions(inventario);
    }

    /**
     * Crea el reporte de un vendedor.
     * @param vendedor vendedor a crear el reporte.
     * @return vendedor a crear el reporte.
     * @throws IllegalArgumentException si algun dato no es valido.
     */
    public ReporteVendedorDTO crearReporteVendedor(Vendedor vendedor) throws IllegalArgumentException {
        CalculoInventario calculo = new CalculoInventario();
        ReporteVendedorDTO reporte = new ReporteVendedorDTO();
        reporte.setTipoID(vendedor.getTipoID());
        reporte.setNumeroID(vendedor.getNumeroID());
        reporte.setNombres(vendedor.getNombres());
        reporte.setApellidos(vendedor.getApellidos());
        reporte.setComision(vendedor.getComision());
        reporte.setNumeroCuentaBanc(vendedor.getNumeroCuentaBanc());
        reporte.setTipoCuentaBanc(vendedor.getTipoCuentaBanc());
        reporte.setCelularesVendidos(calculo.calcularCellVendidos(vendedor.getListaVentas()));
        return reporte;
    }

    /**
     * Calcula el precio de ganancia de un celular.
     * @param precio precio base del celular.
     * @return precio de ganancia del celular.
     */
    public double calcularGanancia(double precio) {
        return precio * (1 + ((double) 25 / 100));
    }

    /**
     * Calcula el precio de ganancias de todos los celulares registrados.
     * @param inventario lista de inventario.
     * @return precio de ganancias de todos los celulares registrados.
     */
    public double precioGanancias(List<Inventario> inventario) {
        double ganancias = 0.0;
        for (Inventario celular : inventario) {
            double precio = calcularGanancia(celular.getPrecioBase());
            ganancias += precio * celular.getCantidad();
        }
        return ganancias;
    }
}
