package co.edu.uptc.modelo;

import java.util.ArrayList;
import java.util.List;

public class Vendedor {

	private String nombres;
	private String apellidos;
	private long telefono;
	private long numeroID;
	private String tipoID;
	private long numeroCuentaBanc;
	private String tipoCuentaBanc;
	private long comision;
	private String codigo;
	private int salesCells;
	private List<Venta> listaVentas;

	public Vendedor(String nombres, String apellidos, long telefono, long numeroID, String tipoID, long numeroCuentaBanc, String tipoCuentaBanc, long comision, String codigo, int salesCells) {
		this.nombres = nombres;
		this.apellidos = apellidos;
		this.telefono = telefono;
		this.numeroID = numeroID;
		this.tipoID = tipoID;
		this.numeroCuentaBanc = numeroCuentaBanc;
		this.tipoCuentaBanc = tipoCuentaBanc;
		this.comision = comision;
		this.codigo = codigo;
		this.salesCells = salesCells;
	}

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
