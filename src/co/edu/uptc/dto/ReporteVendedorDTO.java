package co.edu.uptc.dto;

public class ReporteVendedorDTO {
    /**
     * Tipo de identificacion del vendedor
     */
    private String tipoID;

    /**
     * Numero de identificacion del vendedor
     */
    private long numeroID;

    /**
     * Nombres del vendedor
     */
    private String nombres;

    /**
     * Apellidos del vendedor
     */
    private String apellidos;

    /**
     * Comision del vendedor
     */
    private long comision;

    /**
     * Numero de cuenta bancaria del vendedor
     */
    private long numeroCuentaBanc;

    /**
     * Tipo de cuenta bancaria del vendedor
     */
    private String tipoCuentaBanc;

    /**
     * Cantidad de celulares vendidos
     */
    private int celularesVendidos;

    public ReporteVendedorDTO() {}

    public String getTipoID() {
        return tipoID;
    }

    public void setTipoID(String tipoID) {
        this.tipoID = tipoID;
    }

    public long getNumeroID() {
        return numeroID;
    }

    public void setNumeroID(long numeroID) {
        this.numeroID = numeroID;
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

    public long getComision() {
        return comision;
    }

    public void setComision(long comision) {
        this.comision = comision;
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

    public int getCelularesVendidos() {
        return celularesVendidos;
    }

    public void setCelularesVendidos(int celularesVendidos) {
        this.celularesVendidos = celularesVendidos;
    }
}
