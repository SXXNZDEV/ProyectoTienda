package co.edu.uptc.gui;

import co.edu.uptc.dto.ReporteInventarioDTO;
import co.edu.uptc.dto.ReporteMasVendidoDTO;
import co.edu.uptc.dto.ReporteVendedorDTO;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.text.NumberFormat;
import java.util.List;

public class DialogoLista extends JDialog {

    private JTextArea textArea;
    private JScrollPane scrollPane;
    private JFrame frame;
    private JPanel panel;
    private DefaultTableModel tabla;
    private NumberFormat format;

    public DialogoLista() {
        /*frame = new JFrame("Ventana Información");
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setSize(800, 500);
        frame.setLayout(new BorderLayout());

        panel = new JPanel();
        panel.setLayout(new BorderLayout());

        textArea = new JTextArea();
        textArea.setLineWrap(true);
        textArea.setWrapStyleWord(true);


        scrollPane = new JScrollPane(textArea);
        scrollPane.setPreferredSize(new Dimension(300, 150));
        panel.add(scrollPane, BorderLayout.CENTER);
        frame.add(scrollPane, BorderLayout.CENTER);

        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
        setLayout(new FlowLayout());
        txaLista = new JTextArea();
        scrollPane = new JScrollPane(txaLista);
        scrollPane.setPreferredSize(new Dimension(300, 150));
        txaLista.setLocation(50, 10);
        //poner el set size que se ajuste automaticamente al texto
        txaLista.setWrapStyleWord(true);

        setSize(1500, 500);
        add(txaLista);
         */
    }

    public void mostrar(String texto) {
        /*frame.setVisible(true);
        textArea.append(texto);
        textArea.append("\n");
        textArea.setFont(new Font("Arial", Font.PLAIN, 11));
        textArea.setEditable(false);*/
    }

    public void crearTablaInventario(Object[] titulos, List<ReporteInventarioDTO> inventario) {
        tabla = new DefaultTableModel();
        frame = new JFrame("Tabla Vendedores");
        format = NumberFormat.getCurrencyInstance();
        format.setMinimumFractionDigits(0);

        for (Object titulo : titulos) {
            tabla.addColumn(titulo.toString());
        }

        format = NumberFormat.getCurrencyInstance();
        format.setMinimumFractionDigits(0);
        for (ReporteInventarioDTO cel : inventario) {
            tabla.addRow(new Object[] {cel.getTotalProductos(), format.format(cel.getPrecioBase()), format.format(cel.getTotalPrecioVenta()), format.format(cel.getTotalImpuesto()), format.format(cel.getTotalComisiones()), format.format(cel.getTotalGanancias())});
        }

        JTable table = new JTable(tabla);
        table.setPreferredScrollableViewportSize(new Dimension(700, table.getRowHeight() * table.getRowCount()));
        JScrollPane scroll = new JScrollPane(table);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.add(scroll);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setFont(new Font("Arial", Font.PLAIN, 12));
        frame.setVisible(true);
    }

    public void crearTablaVendedor(Object[] titulos, List<ReporteVendedorDTO> vendedor) {
        tabla = new DefaultTableModel();
        frame = new JFrame("Tabla Vendedores");
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
        JScrollPane scroll = new JScrollPane(table);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.add(scroll);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setFont(new Font("Arial", Font.PLAIN, 12));
        frame.setVisible(true);
    }

    public void crearTablaMasVendidos(Object[] titulos, ReporteMasVendidoDTO linea, ReporteMasVendidoDTO marca) {
        tabla = new DefaultTableModel();
        frame = new JFrame("Tabla Vendedores");
        format = NumberFormat.getCurrencyInstance();
        format.setMinimumFractionDigits(0);

        for (Object titulo : titulos) {
            tabla.addColumn(titulo.toString());
        }

        tabla.addRow(new Object[] {marca.getMarca(), format.format(marca.getVentasMarca()), linea.getLinea(), format.format(linea.getVentasMarca())});

        JTable table = new JTable(tabla);
        table.setPreferredScrollableViewportSize(new Dimension(700, table.getRowHeight() * table.getRowCount()));
        JScrollPane scroll = new JScrollPane(table);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.add(scroll);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setFont(new Font("Arial", Font.PLAIN, 12));
        frame.setVisible(true);
    }

    /*public void agregrarTexto(String texto) {
        txaLista.append(texto);
        txaLista.append("\n");
        txaLista.setFont(new Font("Monospaced", Font.PLAIN, 13));
        txaLista.setEditable(false);
    }*/
}
