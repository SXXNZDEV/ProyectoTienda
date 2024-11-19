package co.edu.uptc.modelo;

public class Inventario {

	private String marca;
	private String linea;
	private String codigo;
	private long precioBase;
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

	public void aumentarCantidad(int cantidad) {
		this.cantidad += cantidad;
	}

	public void disminuirCantidad(int cantidad) {
		this.cantidad -= cantidad;
	}
}
