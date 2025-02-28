package sia.operador;

import sia.modelo.Individuo;

import java.util.List;

public interface Seleccion {
    Individuo seleccionar(List<Individuo> poblacion, int tamTorneo);
    default Individuo seleccionarRuleta(List<Individuo> poblacion) {
        return seleccionar(poblacion, 0); // método por defecto, se sobreescribe en SeleccionRuleta
    }
}