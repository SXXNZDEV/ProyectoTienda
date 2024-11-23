package co.edu.uptc.dto;

public class ReporteInventarioDTO {

	private int totalProductos;
	private double precioBase;
	private double totalPrecioVenta;
	private double totalImpuesto;
	private double totalComisiones;
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
