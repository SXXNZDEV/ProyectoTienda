package co.edu.uptc.negocio;

import co.edu.uptc.dto.ReporteVendedorDTO;
import co.edu.uptc.modelo.Inventario;
import co.edu.uptc.modelo.Vendedor;

import java.util.List;

public class CalculoVendedor {

    public CalculoVendedor() {
    }

    public long calcularPrecioBase(List<Inventario> inventario) {
        long basePrice = 0;
        for (Inventario celular : inventario) {
            basePrice += celular.getCantidad() * celular.getPrecioBase();
        }
        return basePrice;
    }

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

    public double calculateCommissions(List<Inventario> inventario) {
        double commission = 0;
        for (Inventario celular : inventario) {
            commission += celular.getCantidad() * celular.getPrecioBase() * 0.05;
        }
        return commission;
    }

    public int calculateTotalCell(List<Inventario> inventario) {
        int totalCellPhones = 0;
        for (Inventario celular : inventario) {
            totalCellPhones += celular.getCantidad();
        }
        return totalCellPhones;
    }

    public double calculateProfits(List<Inventario> inventario) {
        return precioGanancias(inventario) - calcularPrecioBase(inventario) - calculateCommissions(inventario);
    }

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

    public double calcularGanancia(double precio) {
        return precio * (1 + ((double) 25 / 100));
    }

    public double precioGanancias(List<Inventario> inventario) {
        double ganancias = 0.0;
        for (Inventario celular : inventario) {
            double precio = calcularGanancia(celular.getPrecioBase());
            ganancias += precio * celular.getCantidad();
        }
        return ganancias;
    }
}
