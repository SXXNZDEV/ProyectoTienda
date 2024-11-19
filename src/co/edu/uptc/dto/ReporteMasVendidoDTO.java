package co.edu.uptc.dto;

public class ReporteMasVendidoDTO {
    private String Marca;
    private String Linea;
    private String codigo;
    private int ventas;

    public ReporteMasVendidoDTO(String marca, String linea,String codigo, int ventas) {
        this.Marca = marca;
        this.Linea = linea;
        this.codigo = codigo;
        this.ventas = ventas;
    }

    public ReporteMasVendidoDTO() {}

    public String getMarca() {
        return Marca;
    }

    public void setMarca(String marca) {
        Marca = marca;
    }

    public String getLinea() {
        return Linea;
    }

    public void setLinea(String linea) {
        Linea = linea;
    }

    public int getVentas() {
        return ventas;
    }

    public void setVentas(int ventas) {
        this.ventas += ventas;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }
}
