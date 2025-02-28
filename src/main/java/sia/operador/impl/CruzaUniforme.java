package sia.operador.impl;

import sia.Individuo;
import sia.Producto;
import sia.operador.Cruza;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/*
 * cada gen del descendiente se elige aleatoriamente de uno de los dos padres.
 * */

public class CruzaUniforme implements Cruza {
    @Override
    public Individuo cruzar(Individuo padre, Individuo madre) {
        Random random = new Random();
        List<Producto> hijos = new ArrayList<>();
        int maxSize = Math.min(padre.productos.size(), madre.productos.size());

        for (int i = 0; i < maxSize; i++) {
            if (random.nextBoolean()) {
                hijos.add(padre.productos.get(i));
            } else {
                hijos.add(madre.productos.get(i));
            }
        }

        return new Individuo(hijos);
    }
}