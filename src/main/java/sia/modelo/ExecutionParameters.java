package sia.modelo;

public class ExecutionParameters {
    private int generaciones;
    private int tamPoblacion;
    private double probCruce;
    private double probMutacion;
    private int maxPeso;
    private String opSeleccion;
    private String opCruza;
    private String opMutacion;

    public ExecutionParameters(int generaciones, int tamPoblacion, double probCruce,
                               double probMutacion, int maxPeso, String opSeleccion,
                               String opCruza, String opMutacion) {
        this.generaciones = generaciones;
        this.tamPoblacion = tamPoblacion;
        this.probCruce = probCruce;
        this.probMutacion = probMutacion;
        this.maxPeso = maxPeso;
        this.opSeleccion = opSeleccion;
        this.opCruza = opCruza;
        this.opMutacion = opMutacion;
    }

    public int getGeneraciones() {
        return generaciones;
    }

    public int getTamPoblacion() {
        return tamPoblacion;
    }

    public double getProbCruce() {
        return probCruce;
    }

    public double getProbMutacion() {
        return probMutacion;
    }

    public int getMaxPeso() {
        return maxPeso;
    }

    public String getOpSeleccion() {
        return opSeleccion;
    }

    public String getOpCruza() {
        return opCruza;
    }

    public String getOpMutacion() {
        return opMutacion;
    }
}
