package sia.operador.impl;

import sia.modelo.Individuo;
import sia.operador.Seleccion;

import java.util.List;
import java.util.Random;

/*
 * los individuos con mayor aptitud (fitness) tienen una mayor probabilidad de ser seleccionados,
 * similar a cómo en una ruleta las casillas más grandes tienen más probabilidad de ser seleccionadas.
 * */

public class SeleccionRuleta implements Seleccion {
    private Random rand = new Random();

    @Override
    public Individuo seleccionar(List<Individuo> poblacion, int tamTorneo) {
        return seleccionarRuleta(poblacion);
    }

    @Override
    public Individuo seleccionarRuleta(List<Individuo> poblacion) {
        double totalFitness = 0.0;
        // Sumar la aptitud directamente (no su inversa)
        for (Individuo ind : poblacion) {
            totalFitness += ind.calcularAptitud();
        }
        double randVal = rand.nextDouble() * totalFitness;
        double sum = 0;
        for (Individuo ind : poblacion) {
            sum += ind.calcularAptitud();
            if (sum >= randVal) {
                return ind;
            }
        }
        return poblacion.get(poblacion.size() - 1);
    }
}

