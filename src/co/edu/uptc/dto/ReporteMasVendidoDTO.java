package co.edu.uptc.dto;

public class ReporteMasVendidoDTO {
    private String Marca;
    private String Linea;
    private String codigo;
    private double ventasMarca;

    public ReporteMasVendidoDTO(String marca, String linea,String codigo, double ventasMarca) {
        this.Marca = marca;
        this.Linea = linea;
        this.codigo = codigo;
        this.ventasMarca = ventasMarca;
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

    public double getVentasMarca() {
        return ventasMarca;
    }

    public void setVentasMarca(double ventasMarca) {
        this.ventasMarca += ventasMarca;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }
}
