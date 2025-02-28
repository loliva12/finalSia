package sia.operador;

import sia.Individuo;

public interface Cruza {
    Individuo cruzar(Individuo padre, Individuo madre);
}