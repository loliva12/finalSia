package sia.operador;

import sia.Individuo;

import java.util.List;

public interface Seleccion {
    Individuo seleccionar(List<Individuo> poblacion, int tamPoblacion);
    Individuo seleccionarRuleta(List<Individuo> poblacion);
}