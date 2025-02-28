package sia.modelo;

import java.util.List;

public class ResultadoEjecucion {

    private List<Double> fitnessHistory;
    private Individuo mejorIndividuoFinal;

    public ResultadoEjecucion(List<Double> fitnessHistory, Individuo mejorIndividuoFinal) {
        this.fitnessHistory = fitnessHistory;
        this.mejorIndividuoFinal = mejorIndividuoFinal;
    }

    public List<Double> getFitnessHistory() {
        return fitnessHistory;
    }

    public Individuo getMejorIndividuoFinal() {
        return mejorIndividuoFinal;
    }
}
