package sia.operador.impl;

import sia.Individuo;
import sia.operador.Seleccion;

import java.util.List;
import java.util.Random;

/*
 * Realizar varios torneos escogidos al azar de la población.
 * El ganador del torneo (mayor aptitud) es seleccionado para el cruzamiento.
 */
public class SeleccionTorneo implements Seleccion { // Agregado "public"
    @Override
    public Individuo seleccionarRuleta(List<Individuo> poblacion) {
        int tamTorneo = 3;
        return seleccionar(poblacion, tamTorneo);
    }

    @Override
    public Individuo seleccionar(List<Individuo> poblacion, int tamTorneo) {
        Random random = new Random();
        Individuo ganador = null;
        for (int i = 0; i < tamTorneo; i++) {
            Individuo candidato = poblacion.get(random.nextInt(poblacion.size()));
            if (ganador == null || candidato.calcularAptitud() < ganador.calcularAptitud()) {
                ganador = candidato;
            }
        }
        return ganador;
    }
}
