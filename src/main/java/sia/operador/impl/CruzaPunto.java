package sia.operador.impl;

import sia.modelo.Individuo;
import sia.modelo.Producto;
import sia.operador.Cruza;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/*
 * combinar la información genética de dos individuos (padres) y generar uno o más descendientes
 * se elige un punto de corte para dividir los cromosomas
 * */

public class CruzaPunto implements Cruza {
    private Random rand = new Random();

    @Override
    public Individuo cruzar(Individuo padre, Individuo madre) {
        List<Producto> productosPadre = padre.getProductos();
        List<Producto> productosMadre = madre.getProductos();
        int size = Math.min(productosPadre.size(), productosMadre.size());
        if (size == 0) return padre;
        int punto = rand.nextInt(size);
        List<Producto> hijoProductos = new ArrayList<>();
        for (int i = 0; i < punto; i++) {
            hijoProductos.add(productosPadre.get(i));
        }
        for (int i = punto; i < productosMadre.size(); i++) {
            hijoProductos.add(productosMadre.get(i));
        }
        return new Individuo(hijoProductos);
    }
}