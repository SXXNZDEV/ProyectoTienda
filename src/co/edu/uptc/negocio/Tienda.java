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

    public Venta crearVenta(String datosVenta) {
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

    public String codSellers() {//generar codigo de los vendedores con la estructura VENOO1, VENOO2, etc
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

    public boolean validarNumPositivos(int num) {
        return num > 0;
    }

    public boolean verificarEspaciosBlancos(String[] arreglo) throws IllegalArgumentException {
        for (String dato : arreglo) {
            if (dato.isBlank()) {
                throw new IllegalArgumentException("Verifique los datos ingresados, hay datos en blanco...");
            }
        }
        return true;
    }


    public String[] separarLineas(String cadenaInventarios) {
        return cadenaInventarios.split("\n");
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
        // TODO crear logica que separe la linea en los diferentes
        // atributos de inventario y asignarselos al objeto
        return nuevo;
    }

    public ReporteInventarioDTO calcularTotalInventario() {
        ReporteInventarioDTO reporte = new ReporteInventarioDTO();
        //TODO desarrollar logica de total de inventario
        reporte.setTotalProductos(calculateTotalCell(listaInvetario));
        reporte.setTotalGanancias(calculateProfits(listaInvetario));
        reporte.setTotalComisiones(calculateCommissions(listaInvetario));
        reporte.setTotalImpuesto(calcularIVAMayor(listaInvetario) + calcularIVAMenor(listaInvetario));
        reporte.setPrecioBase(calcularPrecioBase(listaInvetario));
        reporte.setTotalPrecioVenta(calcularPrecioVenta(listaInvetario));
        return reporte;
    }

    public ReporteVendedorDTO crearReporteVendedor(Vendedor vendedor) throws IllegalArgumentException {
        ReporteVendedorDTO reporte = new ReporteVendedorDTO();
        reporte.setTipoID(vendedor.getTipoID());
        reporte.setNumeroID(vendedor.getNumeroID());
        reporte.setNombres(vendedor.getNombres());
        reporte.setApellidos(vendedor.getApellidos());
        reporte.setComision(vendedor.getComision());
        reporte.setNumeroCuentaBanc(vendedor.getNumeroCuentaBanc());
        reporte.setTipoCuentaBanc(vendedor.getTipoCuentaBanc());
        reporte.setCelularesVendidos(calcularCellVendidos(vendedor.getListaVentas()));
        return reporte;
    }

    public String generarReporteVentas() throws IllegalArgumentException {
        if (validarVendedoresExisten() && validarInventarioExisten()) {
            throw new IllegalArgumentException("No hay Inventario ni Vendedores registrados");
        }
        NumberFormat format = NumberFormat.getCurrencyInstance();
        format.setMinimumFractionDigits(0);
        StringBuilder sb = new StringBuilder();
        sb.append(String.format("%-25s | %-20s | %-22s | %-25s | %-22s | %-5s\n", "Tipo y Numero Documento", "Nombre Vendedor", "Total Comision Ventas ", "Numero de cuenta Bancaria", "Tipo cuenta Bancaria", "Celulares vendidos"));
        for (Vendedor vendedor : listaVendedores.values()) {
            ReporteVendedorDTO reporte = crearReporteVendedor(vendedor);
            sb.append(String.format("%-25s | %-20s | %-22s | %-25s | %-22s | %-5s\n", reporte.getTipoID() + " " + reporte.getNumeroID(), reporte.getNombres() + " " + reporte.getApellidos(), format.format(reporte.getComision()), reporte.getNumeroCuentaBanc(), reporte.getTipoCuentaBanc(), reporte.getCelularesVendidos()));
        }
        return sb.toString();
    }

    public String generarReporteMasVendidos() throws IllegalArgumentException {
        if (validarVendedoresExisten() && validarInventarioExisten()) {
            throw new IllegalArgumentException("No hay Inventario ni Vendedores registrados");
        }
        ReporteMasVendidoDTO linea = lineaMasVendida();
        ReporteMasVendidoDTO marca = marcaMasVendida();

        StringBuilder sb = new StringBuilder();
        sb.append(String.format("%-40s | %-10s\n", "Concepto", "Valor"));
        sb.append(String.format("%-40s | %-10s\n", "Marca de Celular más Vendida", marca.getMarca()));
        sb.append(String.format("%-40s | %-10s\n", "Total ventas marca", marca.getVentas()));
        sb.append(String.format("%-40s | %-10s\n", "Linea de Celular más Vendida", linea.getLinea()));
        sb.append(String.format("%-40s | %-10s\n", "Total ventas linea", linea.getVentas()));
        return sb.toString();
    }


    public ReporteMasVendidoDTO lineaMasVendida(ArrayList<ReporteMasVendidoDTO> lista) {
        return Collections.max(lista, Comparator.comparing(ReporteMasVendidoDTO::getLinea));
    }

    public Map<String, Vendedor> getListaVendedores() {
        return listaVendedores;
    }

    public void setListaVendedores(Map<String, Vendedor> listaVendedores) {
        this.listaVendedores = listaVendedores;
    }

    public List<Inventario> getListaInvetario() {
        return listaInvetario;
    }

    public void setListaInvetario(ArrayList<Inventario> listaInvetario) {
        this.listaInvetario = listaInvetario;
    }

    //TODO crear clase para que calcule unicamente la info de ReporteVendedor en negocio
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
//Hasta aquí

    //TODO crear clase para que calcule unicamente la info de ReporteVendedor en negocio
    public long calcularComision(long precioBase, int cantidad) {
        return (long) (cantidad * precioBase * 0.05);
    }

    public long buscarPrecioBase(String codigo) {
        for (Inventario celular : listaInvetario) {
            if (celular.getCodigo().equals(codigo)) {
                return celular.getPrecioBase();
            }
        }
        return 0;
    }

    public int calcularCellVendidos(List<Venta> ventas) {
        int celulares = 0;
        for (Venta venta : ventas) {
            celulares += venta.getCantidad();
        }
        return celulares;
    }

    public boolean validarVendedoresExisten() {
        return listaVendedores.isEmpty();
    }

    public boolean validarInventarioExisten() {
        return listaInvetario.isEmpty();
    }

    public boolean validarVentaExiste(List<Venta> ventas) {
        return ventas == null;
    }

    public ReporteMasVendidoDTO lineaMasVendida() {
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
                    ReporteMasVendidoDTO nuevoReporte = new ReporteMasVendidoDTO(buscarMarca(codCelular), buscarLinea(codCelular), codCelular, venta.getCantidad());
                    reporteMap.put(codCelular, nuevoReporte);
                }
            }
        }
        // Encontrar el reporte con la mayor cantidad de ventas
        return Collections.max(reporteMap.values(), Comparator.comparing(ReporteMasVendidoDTO::getVentas));
    }

    public ReporteMasVendidoDTO marcaMasVendida() {
        Map<String, ReporteMasVendidoDTO> reporte = new HashMap<>();

        for (Vendedor vendedor : listaVendedores.values()) {
            for (Venta venta : vendedor.getListaVentas()) {

                String marca = buscarMarca(venta.getCodCelular());
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

    public String buscarMarca(String codigo) {
        for (Inventario celular : listaInvetario) {
            if (celular.getCodigo().equals(codigo)) {
                return celular.getMarca();
            }
        }
        return "";
    }

    public String buscarLinea(String codigo) {
        for (Inventario celular : listaInvetario) {
            if (celular.getCodigo().equals(codigo)) {
                return celular.getLinea();
            }
        }
        return "";
    }

    // Generar reporte de IVA
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
}
