package sia.operador;

import sia.modelo.Individuo;

public interface Cruza {
    Individuo cruzar(Individuo padre, Individuo madre);
}