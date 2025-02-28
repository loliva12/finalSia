package sia.operador.impl;

import sia.modelo.Individuo;
import sia.modelo.Producto;
import sia.operador.Mutacion;

import java.util.Collections;
import java.util.List;
import java.util.Random;

/*
 * intercambia dos elementos en la secuencia de un individuo, introduciendo diversidad genética en la población.
 * */

public class MutacionSwap implements Mutacion {

    @Override
    public void mutar(Individuo individuo) {
        List<Producto> productos = individuo.getProductos();
        Random rand = new Random();
        if (productos.size() < 2) return;
        int idx1 = rand.nextInt(productos.size());
        int idx2 = rand.nextInt(productos.size());
        Collections.swap(productos, idx1, idx2);
    }
}