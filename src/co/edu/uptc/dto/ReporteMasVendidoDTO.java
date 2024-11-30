package co.edu.uptc.dto;

public class ReporteMasVendidoDTO {

    /**
     * Marca del celular
     */
    private String marca;

    /**
     * Linea del celular
     */
    private String linea;

    /**
     * Codigo del celular
     */
    private String codigo;

    /**
     * Ventas de la marca
     */
    private double ventasMarca;

    public ReporteMasVendidoDTO(String marca, String linea,String codigo, double ventasMarca) {
        this.marca = marca;
        this.linea = linea;
        this.codigo = codigo;
        this.ventasMarca = ventasMarca;
    }

    public ReporteMasVendidoDTO() {}

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

    public double getVentasMarca() {
        return ventasMarca;
    }

    /**
     * Incrementa la cantidad de ventas de la marca.
     * @param ventasMarca cantidad de ventas de la marca.
     */
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
