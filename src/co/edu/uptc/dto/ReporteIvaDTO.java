package co.edu.uptc.dto;

public class ReporteIvaDTO {
    private double ivaMenor;
    private double totalBasesGravablesMayor;
    private double totalBasesGravablesMenor;
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

    public double getTotalBasesGravablesMayor() {
        return totalBasesGravablesMayor;
    }

    public void setTotalBasesGravablesMayor(double totalBasesGravablesMayor) {
        this.totalBasesGravablesMayor += totalBasesGravablesMayor;
    }

    public double getTotalBasesGravablesMenor() {
        return totalBasesGravablesMenor;
    }

    public void setTotalBasesGravablesMenor(double totalBasesGravablesMenor) {
        this.totalBasesGravablesMenor += totalBasesGravablesMenor;
    }
}
