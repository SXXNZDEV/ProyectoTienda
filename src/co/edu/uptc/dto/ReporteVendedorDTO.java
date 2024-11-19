package co.edu.uptc.dto;

public class ReporteVendedorDTO {
    private String tipoID;
    private long numeroID;
    private String nombres;
    private String apellidos;
    private long comision;
    private long numeroCuentaBanc;
    private String tipoCuentaBanc;
    private int celularesVendidos;

    public ReporteVendedorDTO(String tipoID, long numeroID, String nombres, String apellidos, long comision, long numeroCuentaBanc, String tipoCuentaBanc, int celularesVendidos) {
        this.tipoID = tipoID;
        this.numeroID = numeroID;
        this.nombres = nombres;
        this.apellidos = apellidos;
        this.comision = comision;
        this.numeroCuentaBanc = numeroCuentaBanc;
        this.tipoCuentaBanc = tipoCuentaBanc;
        this.celularesVendidos = celularesVendidos;
    }

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
