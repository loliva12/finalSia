package sia.operador.impl;

import sia.Individuo;
import sia.Producto;
import sia.operador.Cruza;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/*
 * combinar la información genética de dos individuos (padres) y generar uno o más descendientes
 * se elige un punto de corte para dividir los cromosomas
 * */

public class CruzaPunto implements Cruza {
    @Override
    public Individuo cruzar(Individuo padre, Individuo madre) {
        Random random = new Random();
        int maxPunto = Math.min(padre.productos.size(), madre.productos.size());

        if (maxPunto == 0) {
            return new Individuo(new ArrayList<>());
        }

        int punto = random.nextInt(maxPunto);
        List<Producto> hijos = new ArrayList<>(padre.productos.subList(0, punto));
        hijos.addAll(madre.productos.subList(punto, madre.productos.size()));
        return new Individuo(hijos);
    }
}