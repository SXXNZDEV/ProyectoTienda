package co.edu.uptc.modelo;

import java.util.ArrayList;
import java.util.List;

public class Vendedor {

	/**
	 * Nombres del vendedor
	 */
	private String nombres;

	/**
	 * Apellidos Vendedor
	 */
	private String apellidos;

	/**
	 * Telefono del Vendedor
	 */
	private long telefono;

	/**
	 * Numero de Identificacion del Vendedor
	 */
	private long numeroID;

	/**
	 * Tipo de Identificacion del Vendedor
	 */
	private String tipoID;

	/**
	 * Numero de cuenta bancaria del Vendedor
	 */
	private long numeroCuentaBanc;

	/**
	 * Tipo de cuenta bancaria del Vendedor
	 */
	private String tipoCuentaBanc;

	/**
	 * Comision del vendedor
	 */
	private long comision;

	/**
	 * Codigo unico del vendedor
	 */
	private String codigo;

	/**
	 * Cantidad de celulares vendidos
	 */
	private int salesCells;

	/**
	 * Lista de ventas
	 */
	private List<Venta> listaVentas;

	public Vendedor() {
		listaVentas = new ArrayList<>();
	}

	public String getNombres() {
		return nombres;
	}

	public void setNombres(String nombres) {
		this.nombres = nombres;
	}

	public String getApellidos() {
		return apellidos;
	}

	public void setApellidos(String apellidos) {
		this.apellidos = apellidos;
	}

	public long getTelefono() {
		return telefono;
	}

	public void setTelefono(long telefono) {
		this.telefono = telefono;
	}

	public long getNumeroID() {
		return numeroID;
	}

	public void setNumeroID(long numeroID) {
		this.numeroID = numeroID;
	}

	public String getTipoID() {
		return tipoID;
	}

	public void setTipoID(String tipoID) {
		this.tipoID = tipoID;
	}

	public long getNumeroCuentaBanc() {
		return numeroCuentaBanc;
	}

	public void setNumeroCuentaBanc(long numeroCuentaBanc) {
		this.numeroCuentaBanc = numeroCuentaBanc;
	}

	public String getTipoCuentaBanc() {
		return tipoCuentaBanc;
	}

	public void setTipoCuentaBanc(String tipoCuentaBanc) {
		this.tipoCuentaBanc = tipoCuentaBanc;
	}

	public long getComision() {
		return comision;
	}

	public void setComision(long comision) {
		this.comision += comision;
	}

	public String getCodigo() {
		return codigo;
	}

	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}

	public int getSalesCells() {
		return salesCells;
	}

	/**
	 * Incrementa la cantidad de celulares vendidos.
	 * @param salesCells cantidad de celulares vendidos.
	 */
	public void setSalesCells(int salesCells) {
		this.salesCells += salesCells;
	}

	public List<Venta> getListaVentas() {
		return listaVentas;
	}

	public void setListaVentas(List<Venta> listaVentas) {
		this.listaVentas = listaVentas;
	}
}
