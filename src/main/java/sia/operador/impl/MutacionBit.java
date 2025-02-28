package sia.operador.impl;

import sia.Individuo;
import sia.operador.Mutacion;

import java.util.Random;

/*
 * introduce cambios aleatorios en los genes de un individuo para mantener la diversidad genética
 * en la población y evitar la convergencia prematura hacia soluciones subóptimas
 * */

public class MutacionBit implements Mutacion {
    @Override
    public void mutar(Individuo individuo) {
        Random rand = new Random();
        if (!individuo.productos.isEmpty()) {
            int idx = rand.nextInt(individuo.productos.size());
            individuo.productos.remove(idx);
        }
    }
}
