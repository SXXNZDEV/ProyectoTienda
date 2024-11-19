package co.edu.uptc.dto;

public class ReporteInventarioDTO {

	private int totalProductos;
	private double precioBase;
	private long totalPrecioVenta;
	private long totalImpuesto;
	private int totalComisiones;
	private long totalGanancias;

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

	public long getTotalPrecioVenta() {
		return totalPrecioVenta;
	}

	public void setTotalPrecioVenta(long totalPrecioVenta) {
		this.totalPrecioVenta = totalPrecioVenta;
	}

	public long getTotalImpuesto() {
		return totalImpuesto;
	}

	public void setTotalImpuesto(long totalImpuesto) {
		this.totalImpuesto = totalImpuesto;
	}

	public int getTotalComisiones() {
		return totalComisiones;
	}

	public void setTotalComisiones(int totalComisiones) {
		this.totalComisiones = totalComisiones;
	}

	public long getTotalGanancias() {
		return totalGanancias;
	}

	public void setTotalGanancias(long totalGanancias) {
		this.totalGanancias = totalGanancias;
	}
}
