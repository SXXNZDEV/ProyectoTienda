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
        listaInvetario = new ArrayList<>();
        listaVendedores = new TreeMap<>();
        cod = 1;
    }

    // Metodos para cargar informacion
    public void cargarInventario(String infoInventario) throws ArrayIndexOutOfBoundsException, IllegalArgumentException {
        StringBuilder exception = new StringBuilder();
        if (infoInventario.isBlank()) {
            throw new IllegalArgumentException("No hay datos en el campo de Inventario");
        }
        String[] arregloLineas = separarLineas(infoInventario);
        for (String datosInv : arregloLineas) {
            try {
                if (datosInv.isBlank()) {
                    continue;
                }
                Inventario nuevo = crearInventario(datosInv);
                if (validarExistenciaCelular(nuevo.getCodigo())) {
                    aumentarCantidadCelular(nuevo);
                    continue;
                }
                listaInvetario.add(nuevo);
            } catch (IllegalArgumentException e) {
                exception.append(e.getMessage());
            }
        }
        if (!exception.toString().isEmpty()) {
            throw new IllegalArgumentException(exception.toString());
        }
    }

    public void cargarVendedor(String infoVendedor) throws ArrayIndexOutOfBoundsException, IllegalArgumentException {
        StringBuilder exception = new StringBuilder();
        if (infoVendedor.isBlank()) {
            throw new IllegalArgumentException("No hay datos en el campo de Vendedor");
        }
        String[] arregloLineas = separarLineas(infoVendedor);
        for (String datosVendedor : arregloLineas) {
            try {
                if (!datosVendedor.isBlank()) {

                    Vendedor vendedor = crearVendedor(datosVendedor);
                    if (!(vendedor.getTipoCuentaBanc().equalsIgnoreCase("Corriente") || vendedor.getTipoCuentaBanc().equalsIgnoreCase("Ahorros"))) {
                        exception.append("El vendedor no tiene la cuenta debida para registrarlo ");
                        cod--;
                    } else if (validarExistenciaVendedor(vendedor.getNumeroID())) {
                        listaVendedores.put(vendedor.getCodigo(), vendedor);
                    } else {
                        exception.append("El vendedor con ID ").append(vendedor.getNumeroID()).append(" ya existe, por lo cual no se agrego a la lista.\n");
                        cod--;
                    }
                }
            } catch (IllegalArgumentException | ArrayIndexOutOfBoundsException e) {
                exception.append(e.getMessage());
            }
        }
        if (!exception.toString().isEmpty()) {
            throw new IllegalArgumentException(exception.toString());
        }
    }

    public void cargarVentas(String infoVentas) throws ArrayIndexOutOfBoundsException, IllegalArgumentException {
        if (infoVentas.isBlank()) {
            throw new IllegalArgumentException("No hay datos en el campo de Ventas");
        }
        String[] arreglo = separarLineas(infoVentas);
        StringBuilder sb = new StringBuilder();
        for (String datosVenta : arreglo) {
            try {
                Venta venta = crearVenta(datosVenta);
                if (!datosVenta.isBlank()) {
                    if (!validarExistenciaCelular(venta.getCodCelular())) {
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
            } catch (ArrayIndexOutOfBoundsException e) {
                sb.append("Faltan datos en la linea: '").append(datosVenta).append("' por lo que no esta no sera registrada\n");
            }

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

    public void validarExistenciaDatos() throws IllegalArgumentException {
        if (validarInventarioExisten() && validarVendedoresExisten()) {
            throw new IllegalArgumentException("No hay Inventario ni Vendedores registrados");
        } else if (validarInventarioExisten()) {
            throw new IllegalArgumentException("No hay Inventario registrados");
        } else if (validarVendedoresExisten()) {
            throw new IllegalArgumentException("No hay Vendedores registrados");
        }
    }

    public boolean validarNumPositivos(int num) throws IllegalArgumentException {
        return num > 0;
    }

    public String[] separarLineas(String cadenaInventarios) {
        return cadenaInventarios.split("\n");
    }

    public boolean verificarEspaciosBlancos(String[] arreglo) throws IllegalArgumentException {
        for (String dato : arreglo) {
            if (dato.isBlank()) {
                return false;
            }
        }
        return true;
    }

    //Creacion de ventas, Vendedores e Inventario
    public Venta crearVenta(String datosVenta) throws NumberFormatException {
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
        StringBuilder exception = new StringBuilder();
        if (!verificarEspaciosBlancos(arreglo)) {
            throw new IllegalArgumentException("Hay espacios en blanco en la linea '" + datos + "' por lo que no sera registrada\n");
        }
        try {
            vendedor.setNombres(arreglo[0].trim());
            vendedor.setApellidos(arreglo[1].trim());
            vendedor.setTelefono(Long.parseLong(arreglo[2].trim()));
            vendedor.setNumeroID(Long.parseLong(arreglo[3].trim()));
            vendedor.setTipoID(arreglo[4].trim());
            vendedor.setNumeroCuentaBanc(Long.parseLong(arreglo[5].trim()));
            vendedor.setTipoCuentaBanc(arreglo[6].trim());
            vendedor.setCodigo(codVendedor());
            if (!validarTipoID(vendedor.getTipoID())) {
                exception.append("El tipo identificación no es válido, no se agrego a la lista este vendedor.\n");
                cod--;
            }
        } catch (NumberFormatException e) {
            exception.append("El numero de telefono :").append(vendedor.getTelefono()).append(" o el numero de cuenta ").append(vendedor.getNumeroCuentaBanc()).append(" o el ID ").append(vendedor.getNumeroID()).append(" no es valido, por lo cual no se agrego a la lista.\n");
        } catch (ArrayIndexOutOfBoundsException e) {
            exception.append("Faltan datos en la linea: '").append(datos).append("' por lo que no esta no sera registrada\n");
        }
        if (!exception.toString().isEmpty()) {
            throw new IllegalArgumentException(exception.toString());
        }
        return vendedor;
    }

    public Inventario crearInventario(String linea) throws IllegalArgumentException, ArrayIndexOutOfBoundsException {
        String[] arreglo = linea.split(";");
        Inventario nuevo = new Inventario();
        StringBuilder exception = new StringBuilder();
        if (!verificarEspaciosBlancos(arreglo)) {
            throw new IllegalArgumentException("Hay espacios en blanco en la linea " + linea + " por lo que no sera registrada\n");
        }
        try {
            nuevo.setMarca(arreglo[0]);
            nuevo.setLinea(arreglo[1]);
            nuevo.setCodigo(arreglo[2]);
            if (!validarNumPositivos(Integer.parseInt(arreglo[3].trim())) || !validarNumPositivos(Integer.parseInt(arreglo[4].trim()))) {
                throw new IllegalArgumentException("El precio base y la cantidad deben ser un numero positivo, verifique en la linea: '" + linea + "'\n");
            }
            nuevo.setPrecioBase(Integer.parseInt(arreglo[3].trim()));
            nuevo.aumentarCantidad(Integer.parseInt(arreglo[4].trim()));

        } catch (NumberFormatException e) {
            exception.append("El precio base").append(arreglo[3].trim()).append(" y la cantidad ").append(arreglo[4].trim()).append(" deben ser un numero, por lo cual este no sera registrado\n");
        } catch (ArrayIndexOutOfBoundsException e) {
            exception.append("Faltan datos en la linea: '").append(linea).append("' por lo que no esta no sera registrada\n");
        }

        if (!exception.toString().isEmpty()) {
            throw new IllegalArgumentException(exception.toString());
        }
        return nuevo;
    }


    // Creador de Codigo unico de Venta (Codigo de vendedor)
    public String codVendedor() {
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
    public List<ReporteInventarioDTO> calcularTotalInventario() {
        List<ReporteInventarioDTO> lista = new ArrayList<>();
        ReporteInventarioDTO reporte = new ReporteInventarioDTO();
        CalculoVendedor calcular = new CalculoVendedor();

        reporte.setTotalProductos(calcular.calculateTotalCell(listaInvetario));
        reporte.setTotalGanancias(calcular.calculateProfits(listaInvetario));
        reporte.setTotalComisiones(calcular.calculateCommissions(listaInvetario));
        reporte.setTotalImpuesto(calcular.calcularIVAMayor(listaInvetario) + calcular.calcularIVAMenor(listaInvetario));
        reporte.setPrecioBase(calcular.calcularPrecioBase(listaInvetario));
        reporte.setTotalPrecioVenta(calcular.calcularPrecioVenta(listaInvetario));
        lista.add(reporte);
        return lista;
    }

    public List<ReporteVendedorDTO> generarReporteVentas() throws IllegalArgumentException {
        validarExistenciaDatos();
        List<ReporteVendedorDTO> reporte = new ArrayList<>();
        CalculoVendedor calculo = new CalculoVendedor();
        for (Vendedor vendedor : listaVendedores.values()) {
            reporte.add(calculo.crearReporteVendedor(vendedor));
        }
        return reporte;
    }

    public ReporteIvaDTO reportIVA() throws IllegalArgumentException {
        validarExistenciaDatos();
        CalculoIVA calculo = new CalculoIVA();
        NumberFormat format = NumberFormat.getCurrencyInstance();
        format.setMinimumFractionDigits(0);
        ReporteIvaDTO reporte = calculo.calcularIVA(listaVendedores, listaInvetario);
        return reporte;
    }

    public ReporteMasVendidoDTO reporteMasVendidoLinea() throws IllegalArgumentException {
        if (validarVendedoresExisten() || validarInventarioExisten()) {
            throw new IllegalArgumentException("No hay Inventario ni Vendedores registrados");
        }
        MasVendido masVendidos = new MasVendido();
        ReporteMasVendidoDTO vendido = masVendidos.lineaMasVendida(listaVendedores, listaInvetario);
        return vendido;
    }

    public ReporteMasVendidoDTO reporteMasVendidoMarca() throws IllegalArgumentException {
        if (validarVendedoresExisten() && validarInventarioExisten()) {
            throw new IllegalArgumentException("No hay Inventario ni Vendedores registrados");
        }
        MasVendido masVendidos = new MasVendido();
        ReporteMasVendidoDTO vendido = masVendidos.marcaMasVendida(listaVendedores, listaInvetario);
        long precio = buscarPrecioBase(vendido.getCodigo());
        vendido.setVentasMarca(precio * vendido.getVentasMarca());

        return vendido;
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
