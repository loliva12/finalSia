package sia.operador.impl;

import sia.Individuo;
import sia.operador.Seleccion;

import java.util.List;

/*
 * los individuos con mayor aptitud (fitness) tienen una mayor probabilidad de ser seleccionados,
 * similar a cómo en una ruleta las casillas más grandes tienen más probabilidad de ser seleccionadas.
 * */

public class SeleccionRuleta implements Seleccion {

    @Override
    public Individuo seleccionar(List<Individuo> poblacion, int tamPoblacion) {
        return seleccionarRuleta(poblacion);
    }
    @Override
    public Individuo seleccionarRuleta(List<Individuo> poblacion) {
        double sumaAptitud = poblacion.stream().mapToDouble(Individuo::calcularAptitud).sum();
        double valorSeleccion = Math.random() * sumaAptitud;
        double count = 0;
        for (Individuo i : poblacion) {
            count += i.calcularAptitud();
            if (count >= valorSeleccion) {
                return i;
            }
        }
        return poblacion.get(poblacion.size() - 1);
    }
}

