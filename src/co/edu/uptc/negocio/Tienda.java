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

    /**
     * lista de vendedores
     * Este mapa se encarga de almacenar los vendedores registrados
     */
    private Map<String, Vendedor> listaVendedores;


    /**
     * Lista de Inventario
     * Esta lista se encarga de almacenar los celulares registrados
     */
    private ArrayList<Inventario> listaInvetario;


    /**
     * Codigo de venta
     * Este codigo se utiliza para generar un codigo unico para cada venta
     */
    private int cod;

    public Tienda() {
        listaInvetario = new ArrayList<>();
        listaVendedores = new TreeMap<>();
        cod = 1;
    }


    /**
     * @param infoInventario cadena de texto con la información de Inventario
     * @throws ArrayIndexOutOfBoundsException si la cadena de texto no tiene la información necesaria
     * @throws IllegalArgumentException       Si encuentra alguna anomalía en la información de Inventario
     */
    public void cargarInventario(String infoInventario) throws ArrayIndexOutOfBoundsException, IllegalArgumentException {
        StringBuilder exception = new StringBuilder();
        if (infoInventario.isBlank()) {
            throw new IllegalArgumentException("No hay datos en el campo de Inventario");
        }
        String[] arregloLineas = separarLineas(infoInventario);
        for (String linea : arregloLineas) {
            try {
                if (linea.isBlank()) {
                    continue;
                }
                Inventario nuevo = crearInventario(linea);
                if (validarExistenciaCelular(nuevo.getCodigo())) {
                    aumentarCantidadCelular(nuevo);
                } else {
                    listaInvetario.add(nuevo);
                }
            } catch (IllegalArgumentException | ArrayIndexOutOfBoundsException e) {
                exception.append(e.getMessage());
            }
        }
        if (!exception.toString().isEmpty()) {
            throw new IllegalArgumentException(exception.toString());
        }
    }


    /**
     * @param infoVendedor cadena de texto con la información de Vendedor
     * @throws ArrayIndexOutOfBoundsException si la cadena de texto no tiene la información necesaria
     * @throws IllegalArgumentException       Si encuentra alguna anomalía en la información de vendedor
     */
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
                    if (!validarExistenciaVendedor(vendedor.getNumeroID())) {
                        listaVendedores.put(vendedor.getCodigo(), vendedor);
                    } else {
                        exception.append("El vendedor con ID ").append(vendedor.getNumeroID()).append(" ya existe, por lo cual no se agrego a la lista.\n");
                        cod--;
                    }
                    if (!(vendedor.getTipoCuentaBanc().equalsIgnoreCase("Corriente") || vendedor.getTipoCuentaBanc().equalsIgnoreCase("Ahorros"))) {
                        exception.append("El vendedor no tiene la cuenta debida para registrarlo ");
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


    /**
     * @param infoVentas cadena de texto con la información de Ventas.
     * @throws ArrayIndexOutOfBoundsException si la cadena de texto no tiene la información necesaria.
     * @throws IllegalArgumentException       si encuentra alguna anomalía en la información de Ventas.
     */
    public void cargarVentas(String infoVentas) throws ArrayIndexOutOfBoundsException, IllegalArgumentException {
        validarCondiciones(infoVentas);
        String[] arreglo = separarLineas(infoVentas);
        StringBuilder sb = new StringBuilder();
        for (String datosVenta : arreglo) {
            try {
                procesarVentas(datosVenta, sb);
            } catch (ArrayIndexOutOfBoundsException e) {
                sb.append("Faltan datos en la linea: '").append(datosVenta).append("' por lo que no esta no sera registrada\n");
            }
        }
        if (!sb.isEmpty()) {
            throw new IllegalArgumentException(sb.toString());
        }
    }


    /**
     * @param infoVentas cadena de texto con la información de Ventas.
     * @throws IllegalArgumentException si informacion de ventas, lista de vendedores o lista de inventario estan vacios genera la excepcion.
     */
    public void validarCondiciones(String infoVentas) throws IllegalArgumentException {
        if (infoVentas.isBlank()) {
            throw new IllegalArgumentException("No hay datos en el campo de Ventas");
        }
        if (listaVendedores.isEmpty()) {
            throw new IllegalArgumentException("No hay Vendedores registrados");
        }
        if (listaInvetario.isEmpty()) {
            throw new IllegalArgumentException("No hay Inventario registrados");
        }
    }


    /**
     * Procesa una venta y la guarda en el sistema.
     * @param datosVenta cadena de texto con la información de Ventas.
     * @param errores cadena de caracteres que se encarga de guardar los errores.
     */
    public void procesarVentas(String datosVenta, StringBuilder errores) {
        if (datosVenta.isBlank()) {
            return;
        }
        Venta venta = crearVenta(datosVenta);
        if (!datosVenta.isBlank()) {
            if (!validarExistenciaCelular(venta.getCodCelular())) {
                errores.append("El celular ").append(venta.getCodCelular()).append(" no existe en la base de datos, por lo cual no sera registrada esta venta \n");
                return;
            }
            if (listaVendedores.get(venta.getCodVendedor()) == null) {
                errores.append("El vendedor ").append(venta.getCodVendedor()).append(" no existe en la base de datos, por lo cual no sera registrada esta venta\n");
                return;
            }
            registrarVentas(venta);
        }
    }

    /**
     * Registra una venta en el sistema.
     * @param venta venta a registrar.
     */
    public void registrarVentas(Venta venta) {
        Vendedor vendedor = listaVendedores.get(venta.getCodVendedor());
        vendedor.getListaVentas().add(venta);
        vendedor.setSalesCells(venta.getCantidad());
        vendedor.setComision(calcularComision(buscarPrecioBase(venta.getCodCelular()), venta.getCantidad()));
        disminuirCantidadCelular(venta.getCodCelular(), venta.getCantidad());
    }

    /**
     * Aumenta la cantidad de un celular en la base de datos
     * @param producto producto a aumentar.
     */
    public void aumentarCantidadCelular(Inventario producto) {
        for (Inventario celular : listaInvetario) {
            if (celular.getCodigo().equals(producto.getCodigo())) {
                celular.aumentarCantidad(producto.getCantidad());
                return;
            }
        }
    }

    /**
     * Disminuye la cantidad de celulares en la base de datos al realizar una venta.
     * @param codigo codigo del celular.
     * @param cantidad cantidad de celulares a disminuir.
     */
    public void disminuirCantidadCelular(String codigo, int cantidad) {
        for (Inventario celular : listaInvetario) {
            if (celular.getCodigo().equals(codigo)) {
                celular.disminuirCantidad(cantidad);
                return;
            }
        }
    }

    /**
     * Valida si existe un celular en la base de datos.
     * @param codigo codigo del celular.
     * @return true si existe, false si no existe.
     */
    public boolean validarExistenciaCelular(String codigo) {
        for (Inventario celular : listaInvetario) {
            if (celular.getCodigo().equals(codigo)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Valida si el tipo de identificacion es valido.
     * @param tipoID tipo de identificacion.
     * @return true si es valido, false si no coincide.
     */
    public boolean validarTipoID(String tipoID) {
        return tipoID.equalsIgnoreCase("Cedula de Ciudadania") || tipoID.equalsIgnoreCase("CC") || tipoID.equalsIgnoreCase("Cedula de Extranjeria") || tipoID.equalsIgnoreCase("CE") || tipoID.equalsIgnoreCase("Pasaporte") || tipoID.equalsIgnoreCase("PA") || tipoID.equalsIgnoreCase("Carnet Diplomatico") || tipoID.equalsIgnoreCase("CD");
    }

    /**
     * Valida si existe un vendedor en la base de datos.
     * @param numeroID numero de identificación del vendedor.
     * @return true si existe, false si no existe.
     */
    public boolean validarExistenciaVendedor(long numeroID) {
        for (Vendedor vendedor : listaVendedores.values()) {
            if (vendedor.getNumeroID() == numeroID) {
                return true;
            }
        }
        return false;
    }

    /**
     * Valida si un numero es positivo.
     * @param num numero a validar.
     * @return true si es positivo, false si no es positivo.
     * @throws IllegalArgumentException si el numero no es valido.
     */
    public boolean validarNumPositivos(int num) throws IllegalArgumentException {
        return num > 0;
    }

    /**
     * Separa cada linea que contenga salto de línea en un array de cadenas.
     * @param cadena cadena de texto con la información a separar.
     * @return retorna un array de cadenas.
     */
    public String[] separarLineas(String cadena) {
        return cadena.split("\n");
    }

    /**
     * Verifica si todos los datos de un array de cadenas son validos.
     * @param arreglo array de cadenas.
     * @return true si todos los datos son validos, false si son validos.
     * @throws IllegalArgumentException si algun dato no es valido.
     */
    public boolean verificarEspaciosBlancos(String[] arreglo) throws IllegalArgumentException {
        for (String dato : arreglo) {
            if (dato.isBlank()) {
                return false;
            }
        }
        return true;
    }

    /**
     * Crea una venta a partir de una cadena de texto.
     * @param datosVenta cadena de texto con la información de una venta.
     * @return venta creada.
     * @throws NumberFormatException si algun dato no es valido.
     */
    public Venta crearVenta(String datosVenta) throws NumberFormatException {
        String[] arreglo = datosVenta.split(";");
        Venta venta = new Venta();
        if (verificarEspaciosBlancos(arreglo)) {
            venta.setCodVendedor(arreglo[0].toUpperCase());
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

    /**
     * Crea un vendedor a partir de una cadena de texto.
     * @param datos cadena de texto con la información de un vendedor.
     * @return vendedor creado.
     * @throws ArrayIndexOutOfBoundsException si la cadena de texto no tiene la información necesaria.
     * @throws IllegalArgumentException si algun dato no es valido.
     */
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
            vendedor.setTelefono(arreglarFormatoNumero(arreglo[2]));
            vendedor.setNumeroID(arreglarFormatoNumero(arreglo[3]));
            vendedor.setTipoID(arreglo[4].trim());
            vendedor.setNumeroCuentaBanc(arreglarFormatoNumero(arreglo[5]));
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

    /**
     * Crea un inventario a partir de una cadena de texto.
     * @param linea cadena de texto con la información de un inventario.
     * @return inventario creado.
     * @throws IllegalArgumentException si algund dato no es valido.
     * @throws ArrayIndexOutOfBoundsException si la cadena de texto no tiene la información necesaria.
     */
    public Inventario crearInventario(String linea) throws IllegalArgumentException, ArrayIndexOutOfBoundsException {
        String[] arreglo = linea.split(";");
        if (arreglo.length != 5) {
            throw new IllegalArgumentException("La linea '" + linea + "' no tieen 5 datos, por lo que no sera registrada\n");
        }
        if (!verificarEspaciosBlancos(arreglo)) {
            throw new IllegalArgumentException("Hay espacios en blanco en la linea '" + linea + "' por lo que no sera registrada\n");
        }
        Inventario nuevo = new Inventario();
        try {
            nuevo.setMarca(arreglo[0].trim());
            nuevo.setLinea(arreglo[1].trim());
            nuevo.setCodigo(arreglo[2].trim());
            long precioBase = arreglarFormatoNumero(arreglo[3].trim());
            long cantidad = arreglarFormatoNumero(arreglo[4].trim());
            if (!validarNumPositivos((int) precioBase) || !validarNumPositivos((int) cantidad)) {
                throw new IllegalArgumentException("El precio base y la cantidad deben ser un numero positivos en la linea '" + linea + "'.\n");
            }
            nuevo.setPrecioBase(precioBase);
            nuevo.aumentarCantidad((int) cantidad);
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("El precio base o la cantidad estan mal formados en la linea '" + linea + "'.\n");
        }
        return nuevo;
    }

    /**
     * Arregla el formato de un numero.
     * @param numero numero a arreglar.
     * @return numero arreglado.
     */
    public long arreglarFormatoNumero(String numero) {
        return Long.parseLong(numero.trim().replaceAll(",", "").replaceAll("\\.", "").replaceAll("\\s", ""));
    }

    /**
     * Creador de codigo unico de venta (codigo de vendedor).
     * @return codigo unico de venta.
     */
    public String codVendedor() {
        return cod < 1000 ? String.format("VEN%03d", cod++) : String.format("VEN%04d", cod++);
    }


    /**
     * Crea el reporte de Inventario
     * @return lista de reportes de Inventario.
     */
    public List<ReporteInventarioDTO> calcularTotalInventario() {
        List<ReporteInventarioDTO> lista = new ArrayList<>();
        ReporteInventarioDTO reporte = new ReporteInventarioDTO();
        CalculoVendedor calcular = new CalculoVendedor();

        reporte.setTotalProductos(calcular.calcularTotalCelulares(listaInvetario));
        reporte.setTotalGanancias(calcular.calcularPrecioGanancia(listaInvetario));
        reporte.setTotalComisiones(calcular.calculateCommissions(listaInvetario));
        reporte.setTotalImpuesto(calcular.calcularIVAMayor(listaInvetario) + calcular.calcularIVAMenor(listaInvetario));
        reporte.setPrecioBase(calcular.calcularPrecioBase(listaInvetario));
        reporte.setTotalPrecioVenta(calcular.calcularPrecioVenta(listaInvetario));
        lista.add(reporte);
        return lista;
    }

    /**
     * Crea el reporte de Ventas
     * @return lista de reportes de Ventas.
     * @throws IllegalArgumentException si no hay vendedores registrados.
     */
    public List<ReporteVendedorDTO> generarReporteVentas() throws IllegalArgumentException {
        if (listaVendedores.isEmpty()) {
            throw new IllegalArgumentException("No hay Vendedores registrados");
        }
        List<ReporteVendedorDTO> reporte = new ArrayList<>();
        CalculoVendedor calculo = new CalculoVendedor();
        for (Vendedor vendedor : listaVendedores.values()) {
            reporte.add(calculo.crearReporteVendedor(vendedor));
        }
        return reporte;
    }

    /**
     * Crea el reporte de IVA.
     * @return reporte de IVA.
     * @throws IllegalArgumentException si no hay vendedores registrados.
     */
    public ReporteIvaDTO reportIVA() throws IllegalArgumentException {
        if (listaVendedores.isEmpty()) {
            throw new IllegalArgumentException("No hay Vendedores registrados");
        }
        CalculoIVA calculo = new CalculoIVA();
        NumberFormat format = NumberFormat.getCurrencyInstance();
        format.setMinimumFractionDigits(0);
        return calculo.calcularIVA(listaVendedores, listaInvetario);
    }

    /**
     * Crea el reporte de Ventas por Linea de Celulares.
     * @return reporte de ventas por linea de ceulares
     * @throws IllegalArgumentException si no hay vendedores o inventario registrados.
     */
    public ReporteMasVendidoDTO reporteMasVendidoLinea() throws IllegalArgumentException {
        if (listaVendedores.isEmpty() || listaInvetario.isEmpty()) {
            throw new IllegalArgumentException("No hay Inventario ni Vendedores registrados");
        }
        MasVendido masVendidos = new MasVendido();
        return masVendidos.buscarLineaMasVendida(listaVendedores, listaInvetario);
    }

    /**
     * Crea el reporte con la marca mas vendida.
     * @return reporte de la marca mas vendida.
     * @throws IllegalArgumentException si no hay vendedores o inventario registrados.
     */
    public ReporteMasVendidoDTO reporteMasVendidoMarca() throws IllegalArgumentException {
        if (listaVendedores.isEmpty() || listaInvetario.isEmpty()) {
            throw new IllegalArgumentException("No hay Inventario ni Vendedores registrados");
        }
        MasVendido masVendidos = new MasVendido();
        ReporteMasVendidoDTO vendido = masVendidos.marcaMasVendida(listaVendedores, listaInvetario);
        long precio = buscarPrecioBase(vendido.getCodigo());
        vendido.setVentasMarca(precio * vendido.getVentasMarca());
        return vendido;
    }

    /**
     * Calcula la comision del vendedor cuando ingresa la venta.
     * @param precioBase precio base del celular.
     * @param cantidad cantidad de celulares.
     * @return comision del vendedor.
     */
    public long calcularComision(long precioBase, int cantidad) {
        return (long) (cantidad * precioBase * 0.05);
    }

    /**
     * Busca el precio base de un celular.
     * @param codigo codigo del celular.
     * @return precio base del celular.
     */
    public long buscarPrecioBase(String codigo) {
        for (Inventario celular : listaInvetario) {
            if (celular.getCodigo().equals(codigo)) {
                return celular.getPrecioBase();
            }
        }
        return 0;
    }
}
