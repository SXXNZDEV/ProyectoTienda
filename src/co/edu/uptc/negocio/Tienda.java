package co.edu.uptc.negocio;

import java.text.NumberFormat;
import java.util.*;

import co.edu.uptc.dto.ReporteInventarioDTO;
import co.edu.uptc.dto.ReporteIvaDTO;
import co.edu.uptc.dto.ReporteMasVendidoDTO;
import co.edu.uptc.dto.ReporteVendedorDTO;
import co.edu.uptc.modelo.Inventario;
import co.edu.uptc.modelo.Vendedor;
import co.edu.uptc.modelo.Venta;

public class Tienda {

    private Map<String, Vendedor> listaVendedores;
    private ArrayList<Inventario> listaInvetario;
    private int cod;

    public Tienda() {
        super();
        listaInvetario = new ArrayList<>();
        listaVendedores = new TreeMap<>();
        cod = 1;
    }

    // Metodos para cargar informacion
    public void cargarInventario(String infoInventario) throws ArrayIndexOutOfBoundsException, IllegalArgumentException {
        // TODO desarrollar metodo que cargue el invetario
        // lista de invetarios listaInvetario
        if (infoInventario.isEmpty()) {
            throw new IllegalArgumentException("No hay datos en el campo de Inventario");
        }
        String[] arregloLineas;
        arregloLineas = separarLineas(infoInventario);
        for (String datosInv : arregloLineas) {
            Inventario nuevo = crearInventario(datosInv);
            if (validarExistenciaCelular(nuevo.getCodigo())) {
                aumentarCantidadCelular(nuevo);
                continue;
            }
            listaInvetario.add(nuevo);
        }
    }

    public void cargarVendedor(String infoVendedor) throws NumberFormatException, ArrayIndexOutOfBoundsException, IllegalArgumentException {
        StringBuilder exception = new StringBuilder();
        if (infoVendedor.isEmpty()) {
            throw new IllegalArgumentException("No hay datos en el campo de Vendedor");
        }
        String[] arregloLineas = separarLineas(infoVendedor);
        for (String datosVendedor : arregloLineas) {
            Vendedor vendedor = crearVendedor(datosVendedor);
            if (!validarTipoID(vendedor.getTipoCuentaBanc())) {
                exception.append("El tipo de cuenta bancaria no es válido, por lo cual no se agrego a la lista.\n");
                cod--;
                continue;
            }
            if (vendedor.getTipoCuentaBanc().equalsIgnoreCase("Corriente") || vendedor.getTipoCuentaBanc().equalsIgnoreCase("Ahorros")) {
                exception.append("El vendedor con ID ").append(vendedor.getNumeroID()).append(" no es válido, por lo cual no se agrego a la lista.\n");
                cod--;
            }
            if (validarExistenciaVendedor(vendedor.getNumeroID())) {
                listaVendedores.put(vendedor.getCodigo(), vendedor);
            } else {
                exception.append("El vendedor con ID ").append(vendedor.getNumeroID()).append(" ya existe en la lista, por lo cual no se agrego a la lista.\n");
                cod--;
            }
        }
        if (!exception.toString().isEmpty()) {
            throw new IllegalArgumentException(exception.toString());
        }
    }

    public void cargarVentas(String infoVentas) throws ArrayIndexOutOfBoundsException, IllegalArgumentException {
        if (!validarExistenciaDatos()) {
            throw new IllegalArgumentException("No hay datos en el campo de Ventas");
        }
        String[] arreglo = separarLineas(infoVentas);
        StringBuilder sb = new StringBuilder();
        for (String datosVenta : arreglo) {
            Venta venta = crearVenta(datosVenta);
            if (validarExistenciaCelular(venta.getCodCelular())) {
                sb.append("El celular ").append(venta.getCodCelular()).append(" no existe en la base de datos, por lo cual no sera registrada esta venta \n");
                continue;
            } else if (listaVendedores.get(venta.getCodVendedor()) == null) {
                sb.append("El vendedor ").append(venta.getCodVendedor()).append(" no existe en la base de datos, por lo cual no sera registrada esta venta\n");
                continue;
            }
            listaVendedores.get(venta.getCodVendedor()).getListaVentas().add(venta);
            listaVendedores.get(venta.getCodVendedor()).setSalesCells(venta.getCantidad());
            listaVendedores.get(venta.getCodVendedor()).setComision(calcularComision(buscarPrecioBase(venta.getCodCelular()), venta.getCantidad()));
            disminuirCantidadCelular(venta.getCodCelular(), venta.getCantidad());
        }

        if (!sb.isEmpty()) {
            throw new IllegalArgumentException(sb.toString());
        }
    }

    //Metodos utiles para los metodos de cargar

    public void aumentarCantidadCelular(Inventario producto) {
        for (Inventario celular : listaInvetario) {
            if (celular.getCodigo().equals(producto.getCodigo())) {
                celular.aumentarCantidad(producto.getCantidad());
                return;
            }
        }
    }

    public void disminuirCantidadCelular(String codigo, int cantidad) {
        for (Inventario celular : listaInvetario) {
            if (celular.getCodigo().equals(codigo)) {
                celular.disminuirCantidad(cantidad);
                return;
            }
        }
    }

    public boolean validarExistenciaCelular(String codigo) {
        for (Inventario celular : listaInvetario) {
            if (celular.getCodigo().equals(codigo)) {
                return true;
            }
        }
        return false;
    }

    public boolean validarTipoID(String ID) {
        return ID.equalsIgnoreCase("Cedula de Ciudadania") || ID.equalsIgnoreCase("CC") || ID.equalsIgnoreCase("Cedula de Extranjeria") || ID.equalsIgnoreCase("CE") || ID.equalsIgnoreCase("Pasaporte") || ID.equalsIgnoreCase("PA") || ID.equalsIgnoreCase("Carnet Diplomatico") || ID.equalsIgnoreCase("CD");
    }

    public boolean validarExistenciaVendedor(long numeroID) {
        for (Vendedor vendedor : listaVendedores.values()) {
            if (vendedor.getNumeroID() == numeroID) {
                return false;
            }
        }
        return true;
    }

    public boolean validarExistenciaDatos() throws IllegalArgumentException {
        if (validarInventarioExisten() && validarVendedoresExisten()) {
            throw new IllegalArgumentException("No hay Inventario ni Vendedores registrados");
        } else if (validarInventarioExisten()) {
            throw new IllegalArgumentException("No hay Inventario registrados");
        } else if (validarVendedoresExisten()) {
            throw new IllegalArgumentException("No hay Vendedores registrados");
        }
        return true;
    }

    public boolean validarNumPositivos(int num) {
        return num > 0;
    }

    public String[] separarLineas(String cadenaInventarios) {
        return cadenaInventarios.split("\n");
    }

    public boolean verificarEspaciosBlancos(String[] arreglo) throws IllegalArgumentException {
        for (String dato : arreglo) {
            if (dato.isBlank()) {
                throw new IllegalArgumentException("Verifique los datos ingresados, hay datos en blanco...");
            }
        }
        return true;
    }

    //Creacion de ventas, Vendedores e Inventario
    public Venta crearVenta(String datosVenta) throws NumberFormatException{
        String[] arreglo = datosVenta.split(";");
        Venta venta = new Venta();
        if (verificarEspaciosBlancos(arreglo)) {
            venta.setCodVendedor(arreglo[0]);
            venta.setCodCelular(arreglo[1]);
            try {
                if (validarNumPositivos(Integer.parseInt(arreglo[2].trim()))) {
                    venta.setCantidad(Integer.parseInt(arreglo[2].trim()));
                }
            } catch (NumberFormatException e) {
                throw new NumberFormatException("Debe ingresar una cantidad válida...");
            }
        }
        return venta;
    }

    public Vendedor crearVendedor(String datos) throws ArrayIndexOutOfBoundsException, IllegalArgumentException {
        String[] arreglo = datos.split(";");
        Vendedor vendedor = new Vendedor();
        if (verificarEspaciosBlancos(arreglo)) {
            vendedor.setNombres(arreglo[0].trim());
            vendedor.setApellidos(arreglo[1].trim());
            try {
                vendedor.setTelefono(Long.parseLong(arreglo[2].trim()));
                vendedor.setNumeroID(Long.parseLong(arreglo[3].trim()));
                vendedor.setNumeroCuentaBanc(Long.parseLong(arreglo[5].trim()));
            } catch (NumberFormatException e) {
                throw new NumberFormatException("Revise el formato del telefono o el numero de ID: ");
            } catch (ArrayIndexOutOfBoundsException e) {
                throw new ArrayIndexOutOfBoundsException("Los datos ingresados son invalidos, verifiquelos...");
            }
            vendedor.setTipoID(arreglo[4].trim());
            vendedor.setTipoCuentaBanc(arreglo[6].trim());
            vendedor.setCodigo(codSellers());
        }
        return vendedor;
    }

    public Inventario crearInventario(String linea) throws IllegalArgumentException, ArrayIndexOutOfBoundsException {
        String[] arreglo = linea.split(";");
        Inventario nuevo = new Inventario();
        if (verificarEspaciosBlancos(arreglo)) {
            nuevo.setMarca(arreglo[0]);
            nuevo.setLinea(arreglo[1]);
            nuevo.setCodigo(arreglo[2]);
            try {
                if (validarNumPositivos(Integer.parseInt(arreglo[3].trim())) && validarNumPositivos(Integer.parseInt(arreglo[4].trim()))) {
                    nuevo.setPrecioBase(Integer.parseInt(arreglo[3].trim()));
                    nuevo.aumentarCantidad(Integer.parseInt(arreglo[4].trim()));
                }
            } catch (NumberFormatException e) {
                throw new NumberFormatException("El !!precio base y la cantidad deben ser un numero");
            } catch (ArrayIndexOutOfBoundsException e) {
                throw new ArrayIndexOutOfBoundsException("Los datos ingresados son invalidos, verifiquelos...");
            }
        }
        return nuevo;
    }


    // Creador de Codigo unico de Venta (Codigo de vendedor)
    public String codSellers() {
        if (cod < 10) {
            return "VEN00" + (cod++);
        } else if (cod < 100) {
            return "VEN0" + (cod++);
        } else if (cod < 1000) {
            return "VEN" + (cod++);
        } else if (cod < 10000) {
            return "VEN" + (cod++);
        }
        return "";
    }


    //Metodos encargados de crear los reportes requeridos
    public ReporteInventarioDTO calcularTotalInventario() {
        ReporteInventarioDTO reporte = new ReporteInventarioDTO();
        CalculoVendedor calcular = new CalculoVendedor();
        //TODO desarrollar logica de total de inventario
        reporte.setTotalProductos(calcular.calculateTotalCell(listaInvetario));
        reporte.setTotalGanancias(calcular.calculateProfits(listaInvetario));
        reporte.setTotalComisiones(calcular.calculateCommissions(listaInvetario));
        reporte.setTotalImpuesto(calcular.calcularIVAMayor(listaInvetario) + calcular.calcularIVAMenor(listaInvetario));
        reporte.setPrecioBase(calcular.calcularPrecioBase(listaInvetario));
        reporte.setTotalPrecioVenta(calcular.calcularPrecioVenta(listaInvetario));
        return reporte;
    }

    public String generarReporteVentas() throws IllegalArgumentException {
        if (validarVendedoresExisten() && validarInventarioExisten()) {
            throw new IllegalArgumentException("No hay Inventario ni Vendedores registrados");
        }
        CalculoVendedor calculo = new CalculoVendedor();
        NumberFormat format = NumberFormat.getCurrencyInstance();
        format.setMinimumFractionDigits(0);
        StringBuilder sb = new StringBuilder();
        sb.append(String.format("%-25s | %-20s | %-22s | %-25s | %-22s | %-5s\n", "Tipo y Numero Documento", "Nombre Vendedor", "Total Comision Ventas ", "Numero de cuenta Bancaria", "Tipo cuenta Bancaria", "Celulares vendidos"));
        for (Vendedor vendedor : listaVendedores.values()) {
            ReporteVendedorDTO reporte = calculo.crearReporteVendedor(vendedor);
            sb.append(String.format("%-25s | %-20s | %-22s | %-25s | %-22s | %-5s\n", reporte.getTipoID() + " " + reporte.getNumeroID(), reporte.getNombres() + " " + reporte.getApellidos(), format.format(reporte.getComision()), reporte.getNumeroCuentaBanc(), reporte.getTipoCuentaBanc(), reporte.getCelularesVendidos()));
        }
        return sb.toString();
    }

    public String reportIVA() throws IllegalArgumentException {
        if (validarVendedoresExisten() && validarInventarioExisten()) {
            throw new IllegalArgumentException("No hay Inventario ni Vendedores registrados");
        }
        CalculoIVA calculo = new CalculoIVA();
        NumberFormat format = NumberFormat.getCurrencyInstance();
        format.setMinimumFractionDigits(0);
        ReporteIvaDTO reporte = calculo.calcularIVA(listaVendedores, listaInvetario);

        StringBuilder sb = new StringBuilder(String.format("%-20s | %-20s | %-15s \n", "Impuestos", "Total Bases Gravables", "Total Impuestos"));
        sb.append(String.format("%-20s | %-20s | %-15s \n", "Impuesto del 5%", format.format(reporte.getTotalBasesGravablesMenor()), format.format(reporte.getIvaMenor())));
        sb.append(String.format("%-20s | %-20s | %-15s \n", "Impuesto del 19%", format.format(reporte.getTotalBasesGravablesMayor()), format.format(reporte.getIvaMayor())));
        sb.append(String.format("%-20s | %-20s | %-15s \n", "Total", format.format(reporte.getTotalBasesGravablesMenor() + reporte.getTotalBasesGravablesMayor()), format.format(reporte.getIvaMenor() + reporte.getIvaMayor())));
        return sb.toString();

    }

    public String generarReporteMasVendidos() throws IllegalArgumentException {
        if (validarVendedoresExisten() && validarInventarioExisten()) {
            throw new IllegalArgumentException("No hay Inventario ni Vendedores registrados");
        }
        MasVendido masVendido = new MasVendido();
        ReporteMasVendidoDTO linea = masVendido.lineaMasVendida(listaVendedores, listaInvetario);
        ReporteMasVendidoDTO marca = masVendido.marcaMasVendida(listaVendedores, listaInvetario);

        StringBuilder sb = new StringBuilder();
        sb.append(String.format("%-40s | %-10s\n", "Concepto", "Valor"));
        sb.append(String.format("%-40s | %-10s\n", "Marca de Celular más Vendida", marca.getMarca()));
        sb.append(String.format("%-40s | %-10s\n", "Total ventas marca", marca.getVentas()));
        sb.append(String.format("%-40s | %-10s\n", "Linea de Celular más Vendida", linea.getLinea()));
        sb.append(String.format("%-40s | %-10s\n", "Total ventas linea", linea.getVentas()));
        return sb.toString();
    }

    //Metodo encargado de calcular la comisión del vendedor cuando ingresa la venta
    public long calcularComision(long precioBase, int cantidad) {
        return (long) (cantidad * precioBase * 0.05);
    }

    //Metodo encargado de buscar el precio base
    public long buscarPrecioBase(String codigo) {
        for (Inventario celular : listaInvetario) {
            if (celular.getCodigo().equals(codigo)) {
                return celular.getPrecioBase();
            }
        }
        return 0;
    }

    public boolean validarVendedoresExisten() {
        return listaVendedores.isEmpty();
    }

    public boolean validarInventarioExisten() {
        return listaInvetario.isEmpty();
    }
}
