package sia.operador.impl;

import sia.modelo.Individuo;
import sia.operador.Seleccion;

import java.util.List;
import java.util.Random;

/*
 * Realizar varios torneos escogidos al azar de la población.
 * El ganador del torneo (mayor aptitud) es seleccionado para el cruzamiento.
 */
public class SeleccionTorneo implements Seleccion { // Agregado "public"
    private Random rand = new Random();

    @Override
    public Individuo seleccionar(List<Individuo> poblacion, int tamTorneo) {
        int n = poblacion.size();
        Individuo best = null;
        for (int i = 0; i < tamTorneo; i++) {
            int idx = rand.nextInt(n);
            Individuo cand = poblacion.get(idx);
            if (best == null || cand.calcularAptitud() < best.calcularAptitud()) {
                best = cand;
            }
        }
        return best;
    }
}
