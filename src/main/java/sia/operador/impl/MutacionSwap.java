package sia.operador.impl;

import sia.Individuo;
import sia.operador.Mutacion;

import java.util.Collections;
import java.util.Random;

/*
 * intercambia dos elementos en la secuencia de un individuo, introduciendo diversidad genética en la población.
 * */

public class MutacionSwap implements Mutacion {
    @Override
    public void mutar(Individuo individuo) {
        Random rand = new Random();
        if (individuo.productos.size() > 1) {
            int idx1 = rand.nextInt(individuo.productos.size());
            int idx2 = rand.nextInt(individuo.productos.size());
            Collections.swap(individuo.productos, idx1, idx2);
        }
    }
}