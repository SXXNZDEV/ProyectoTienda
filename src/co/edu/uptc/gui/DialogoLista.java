package co.edu.uptc.gui;

import co.edu.uptc.dto.ReporteInventarioDTO;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import java.awt.*;
import java.text.NumberFormat;

public class DialogoLista extends JDialog {

    private JTextArea textArea;
    private JScrollPane scrollPane;
    private JFrame frame;
    private JPanel panel;
    private DefaultTableModel tabla;

    public DialogoLista() {
        frame = new JFrame("Ventana Información");
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
        //panel.add(scrollPane, BorderLayout.CENTER);
        frame.add(scrollPane, BorderLayout.CENTER);

        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
        /*setLayout(new FlowLayout());
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
        frame.setVisible(true);
        textArea.append(texto);
        textArea.append("\n");
        textArea.setFont(new Font("Monospaced", Font.PLAIN, 13));
        textArea.setEditable(false);
    }

    public void tablaInventario(ReporteInventarioDTO datos) {
        tabla = new DefaultTableModel();
        frame = new JFrame("Tabla Inventario");
        tabla.addColumn("Total Celulares");
        tabla.addColumn("Precio Base");
        tabla.addColumn("Precio Venta");
        tabla.addColumn("Impuestos");
        tabla.addColumn("Comisiones");
        tabla.addColumn("Ganancias");
        NumberFormat format = NumberFormat.getCurrencyInstance();
        format.setMinimumFractionDigits(0);
        tabla.addRow(new Object[]{datos.getTotalProductos(), format.format(datos.getPrecioBase()), format.format(datos.getTotalPrecioVenta()), format.format(datos.getTotalImpuesto()), format.format(datos.getTotalComisiones()), format.format(datos.getTotalGanancias())});

        JTable table = new JTable(tabla);
        JScrollPane scroll = new JScrollPane(table);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setSize(800, 500);
        frame.add(scroll);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);

        table.setFont(new Font("Monospaced", Font.PLAIN, 15));
    }

    /*public void agregrarTexto(String texto) {
        txaLista.append(texto);
        txaLista.append("\n");
        txaLista.setFont(new Font("Monospaced", Font.PLAIN, 13));
        txaLista.setEditable(false);
    }*/
}
