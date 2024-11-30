package co.edu.uptc.modelo;

public class Inventario {

	/**
	 * Marca del celular
	 */
	private String marca;

	/**
	 * Linea del celular
	 */
	private String linea;

	/**
	 * Codigo del celular
	 */
	private String codigo;

	/**
	 * Precio base del celular
	 */
	private long precioBase;

	/**
	 * Cantidad de celulares
	 */
	private int cantidad;

	public String getMarca() {
		return marca;
	}

	public void setMarca(String marca) {
		this.marca = marca;
	}

	public String getLinea() {
		return linea;
	}

	public void setLinea(String linea) {
		this.linea = linea;
	}

	public String getCodigo() {
		return codigo;
	}

	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}

	public long getPrecioBase() {
		return precioBase;
	}

	public void setPrecioBase(long precioBase) {
		this.precioBase = precioBase;
	}

	public int getCantidad() {
		return cantidad;
	}

	/**
	 * Incrementa la cantidad de celulares por un numero dado.
	 * @param cantidad cantidad a aumentar.
	 */
	public void aumentarCantidad(int cantidad) {
		this.cantidad += cantidad;
	}

	public void disminuirCantidad(int cantidad) {
		this.cantidad -= cantidad;
	}
}
