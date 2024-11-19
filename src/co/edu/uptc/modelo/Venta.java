package co.edu.uptc.modelo;

public class Venta {

    private String codVendedor;
    private String codCelular;
    private int cantidad;

    public Venta(String codSeller, String codCell, int amount) {
        this.codVendedor = codSeller;
        this.codCelular = codCell;
        this.cantidad = amount;
    }

    public Venta() {}

    public String getCodVendedor() {
        return codVendedor;
    }

    public void setCodVendedor(String codVendedor) {
        this.codVendedor = codVendedor;
    }

    public String getCodCelular() {
        return codCelular;
    }

    public void setCodCelular(String codCelular) {
        this.codCelular = codCelular;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad += cantidad;
    }

}
