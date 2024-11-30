package co.edu.uptc.dto;

public class ReporteInventarioDTO {

	/**
	 * Cantidad de celulares registrados
	 */
	private int totalProductos;

	/**
	 * Precio base total de los celulares
	 */
	private double precioBase;

	/**
	 * Precio total de ventas de los celulares
	 */
	private double totalPrecioVenta;

	/**
	 * Total de impuestos de los celulares
	 */
	private double totalImpuesto;

	/**
	 * Total de comisiones de los celulares
	 */
	private double totalComisiones;

	/**
	 * Total de ganancias de los celulares
	 */
	private double totalGanancias;

	public ReporteInventarioDTO() {}

	public int getTotalProductos() {
		return totalProductos;
	}

	public void setTotalProductos(int totalProductos) {
		this.totalProductos = totalProductos;
	}

	public double getPrecioBase() {
		return precioBase;
	}

	public void setPrecioBase(double precioBase) {
		this.precioBase = precioBase;
	}

	public double getTotalPrecioVenta() {
		return totalPrecioVenta;
	}

	public void setTotalPrecioVenta(double totalPrecioVenta) {
		this.totalPrecioVenta = totalPrecioVenta;
	}

	public double getTotalImpuesto() {
		return totalImpuesto;
	}

	public void setTotalImpuesto(double totalImpuesto) {
		this.totalImpuesto = totalImpuesto;
	}

	public double getTotalComisiones() {
		return totalComisiones;
	}

	public void setTotalComisiones(double totalComisiones) {
		this.totalComisiones = totalComisiones;
	}

	public double getTotalGanancias() {
		return totalGanancias;
	}

	public void setTotalGanancias(double totalGanancias) {
		this.totalGanancias = totalGanancias;
	}
}
