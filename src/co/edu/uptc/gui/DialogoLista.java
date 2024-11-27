package co.edu.uptc.gui;

import co.edu.uptc.dto.ReporteInventarioDTO;
import co.edu.uptc.dto.ReporteIvaDTO;
import co.edu.uptc.dto.ReporteMasVendidoDTO;
import co.edu.uptc.dto.ReporteVendedorDTO;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.text.NumberFormat;
import java.util.List;

public class DialogoLista extends JDialog {

    private DefaultTableModel tabla;
    private NumberFormat format;

    public DialogoLista() {}

    public JScrollPane crearTablaInventario(Object[] titulos, List<ReporteInventarioDTO> inventario) {
        tabla = new DefaultTableModel();
        format = NumberFormat.getCurrencyInstance();
        format.setMinimumFractionDigits(0);

        for (Object titulo : titulos) {
            tabla.addColumn(titulo.toString());
        }

        format = NumberFormat.getCurrencyInstance();
        format.setMinimumFractionDigits(0);
        for (ReporteInventarioDTO cel : inventario) {
            tabla.addRow(new Object[]{cel.getTotalProductos(), format.format(cel.getPrecioBase()), format.format(cel.getTotalPrecioVenta()), format.format(cel.getTotalImpuesto()), format.format(cel.getTotalComisiones()), format.format(cel.getTotalGanancias())});
        }

        JTable table = new JTable(tabla);
        table.setPreferredScrollableViewportSize(new Dimension(700, 800));
        return new JScrollPane(table);
    }

    public JScrollPane crearTablaVendedor(Object[] titulos, List<ReporteVendedorDTO> vendedor) {
        tabla = new DefaultTableModel();
        format = NumberFormat.getCurrencyInstance();
        format.setMinimumFractionDigits(0);

        for (Object titulo : titulos) {
            tabla.addColumn(titulo.toString());
        }

        for (ReporteVendedorDTO vende : vendedor) {
            tabla.addRow(new Object[] {(vende.getTipoID() + " : " + vende.getNumeroID()), vende.getNombres() + " " + vende.getApellidos(), vende.getNumeroCuentaBanc(), vende.getTipoCuentaBanc(), format.format(vende.getComision()), vende.getCelularesVendidos()});
        }

        JTable table = new JTable(tabla);
        table.setPreferredScrollableViewportSize(new Dimension(700, table.getRowHeight() * table.getRowCount()));
        return new JScrollPane(table);
    }

    public JScrollPane crearTablaMasVendidos(Object[] titulos, ReporteMasVendidoDTO linea, ReporteMasVendidoDTO marca) {
        tabla = new DefaultTableModel();
        format = NumberFormat.getCurrencyInstance();
        format.setMinimumFractionDigits(0);

        for (Object titulo : titulos) {
            tabla.addColumn(titulo.toString());
        }

        tabla.addRow(new Object[] {marca.getMarca(), format.format(marca.getVentasMarca()), linea.getLinea(), format.format(linea.getVentasMarca())});

        JTable table = new JTable(tabla);
        table.setPreferredScrollableViewportSize(new Dimension(700, table.getRowHeight() * table.getRowCount()));
        return new JScrollPane(table);
    }

    public JScrollPane crearTablaIVA(Object[] titulos, ReporteIvaDTO reporte) {
        tabla = new DefaultTableModel();
        format = NumberFormat.getCurrencyInstance();
        format.setMinimumFractionDigits(0);
        for (Object titulo : titulos) {
            tabla.addColumn(titulo.toString());
        }

        tabla.addRow(new Object[] {"5%", format.format(reporte.getTotalBasesGravablesMenor()), format.format(reporte.getIvaMenor())});
        tabla.addRow(new Object[] {"19%", format.format(reporte.getTotalBasesGravablesMayor()), format.format(reporte.getIvaMayor())});
        tabla.addRow(new Object[] {"Total", format.format(reporte.getTotalBasesGravablesMenor() + reporte.getTotalBasesGravablesMayor()), format.format(reporte.getIvaMenor() + reporte.getIvaMayor())});

        JTable table = new JTable(tabla);
        table.setPreferredScrollableViewportSize(new Dimension(500, table.getRowHeight() * table.getRowCount()));
        return new JScrollPane(table);
    }
}
