package co.edu.uptc.modelo;

public class Venta {

    /**
     * Codigo del vendedor
     */
    private String codVendedor;

    /**
     * Codigo del celular
     */
    private String codCelular;

    /**
     * Cantidad de celulares
     */
    private int cantidad;

    /**
     * Constructor de la clase Venta
     */
    public Venta() {
    }

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

    /**
     * Aumenta la cantidad de celulares por un numero dado.
     * @param cantidad cantidad a aumentar.
     */
    public void setCantidad(int cantidad) {
        this.cantidad += cantidad;
    }

}
