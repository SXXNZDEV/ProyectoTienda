package co.edu.uptc.dto;

public class ReporteIvaDTO {
    private double ivaMenor;
    private long totalBasesGravablesMayor;
    private long totalBasesGravablesMenor;
    private double ivaMayor;

    public ReporteIvaDTO(double ivaMenor, double ivaMayor) {
        this.ivaMenor = ivaMenor;
        this.ivaMayor = ivaMayor;
    }

    public ReporteIvaDTO() {}

    public double getIvaMenor() {
        return ivaMenor;
    }

    public void setIvaMenor(double ivaMenor) {
        this.ivaMenor += ivaMenor;
    }

    public double getIvaMayor() {
        return ivaMayor;
    }

    public void setIvaMayor(double ivaMayor) {
        this.ivaMayor += ivaMayor;
    }

    public long getTotalBasesGravablesMayor() {
        return totalBasesGravablesMayor;
    }

    public void setTotalBasesGravablesMayor(long totalBasesGravablesMayor) {
        this.totalBasesGravablesMayor += totalBasesGravablesMayor;
    }

    public long getTotalBasesGravablesMenor() {
        return totalBasesGravablesMenor;
    }

    public void setTotalBasesGravablesMenor(long totalBasesGravablesMenor) {
        this.totalBasesGravablesMenor += totalBasesGravablesMenor;
    }
}
