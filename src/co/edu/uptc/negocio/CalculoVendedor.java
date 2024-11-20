package co.edu.uptc.negocio;

import co.edu.uptc.dto.ReporteVendedorDTO;
import co.edu.uptc.modelo.Inventario;
import co.edu.uptc.modelo.Vendedor;

import java.util.List;

public class CalculoVendedor {

    public CalculoVendedor() {}

    public long calcularPrecioBase(List<Inventario> inventario) {
        long basePrice = 0;
        for (Inventario celular : inventario) {
            basePrice += celular.getCantidad() * celular.getPrecioBase();
        }
        return basePrice;
    }

    public long calcularPrecioVenta(List<Inventario> inventario) {
        long salesPrice = 0;
        for (Inventario celular : inventario) {
            if (celular.getPrecioBase() > 600000) {
                salesPrice += (long) (celular.getCantidad() * celular.getPrecioBase() + (celular.getCantidad() * celular.getPrecioBase() * 0.19));
            } else {
                salesPrice += (long) (celular.getCantidad() + (celular.getPrecioBase() * 0.05));
            }
        }
        return salesPrice;
    }

    public long calcularIVAMayor(List<Inventario> inventario) {
        long worth = 0;
        for (Inventario celular : inventario) {
            if (celular.getPrecioBase() > 600000) {
                worth += (long) (celular.getCantidad() * celular.getPrecioBase() * 0.19);
            }
        }
        return worth;
    }

    public long calcularIVAMenor(List<Inventario> inventario) {
        long worth = 0;
        for (Inventario celular : inventario) {
            if (celular.getPrecioBase() <= 600000 && celular.getPrecioBase() > 0) {
                worth += (long) (celular.getCantidad() * celular.getPrecioBase() * 0.05);
            }
        }
        return worth;
    }

    public int calculateCommissions(List<Inventario> inventario) {
        int commission = 0;
        for (Inventario celular : inventario) {
            commission += (int) (celular.getCantidad() * celular.getPrecioBase() * 0.05);
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

    public long calculateProfits(List<Inventario> inventario) {
        return (calcularPrecioBase(inventario) - calculateCommissions(inventario));
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
}
