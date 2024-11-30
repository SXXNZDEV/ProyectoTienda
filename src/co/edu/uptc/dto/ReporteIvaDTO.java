package co.edu.uptc.dto;

public class ReporteIvaDTO {
    /**
     * Impuesto menor
     */
    private double ivaMenor;

    /**
     * Total de bases gravables de celulares mayor a 600000
     */
    private double totalBasesGravablesMayor;

    /**
     * Total de bases gravables de celulares menor a 600000
     */
    private double totalBasesGravablesMenor;

    /**
     * Impuesto mayor
     */
    private double ivaMayor;

    public ReporteIvaDTO() {}

    public double getIvaMenor() {
        return ivaMenor;
    }

    /**
     * Incrementa el valor del impuesto menor.
     * @param ivaMenor valor de impuesto menor.
     */
    public void setIvaMenor(double ivaMenor) {
        this.ivaMenor += ivaMenor;
    }

    public double getIvaMayor() {
        return ivaMayor;
    }

    /**
     * Incrementa el valor del impuesto mayor.
     * @param ivaMayor valor de impuesto mayor.
     */
    public void setIvaMayor(double ivaMayor) {
        this.ivaMayor += ivaMayor;
    }

    public double getTotalBasesGravablesMayor() {
        return totalBasesGravablesMayor;
    }

    /**
     * Incrementa el valor de bases gravables mayor.
     * @param totalBasesGravablesMayor valor de bases gravables mayor.
     */
    public void setTotalBasesGravablesMayor(double totalBasesGravablesMayor) {
        this.totalBasesGravablesMayor += totalBasesGravablesMayor;
    }

    public double getTotalBasesGravablesMenor() {
        return totalBasesGravablesMenor;
    }

    /**
     * Incrementa el valor de bases gravables menor.
     * @param totalBasesGravablesMenor valor de bases gravables menor.
     */
    public void setTotalBasesGravablesMenor(double totalBasesGravablesMenor) {
        this.totalBasesGravablesMenor += totalBasesGravablesMenor;
    }
}
