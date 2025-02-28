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

        // Usamos la longitud mínima para evitar índices fuera de rango
        int minSize = Math.min(productosPadre.size(), productosMadre.size());
        if (minSize == 0) {
            // Si alguno de los padres no tiene genes, retornamos una copia del padre
            return new Individuo(new ArrayList<>(productosPadre));
        }

        // Se permite el cruce en cualquier punto entre 0 y minSize (incluyendo los extremos)
        int punto = rand.nextInt(minSize + 1);  // +1 para permitir cruce al inicio o al final

        List<Producto> hijoProductos = new ArrayList<>();

        // Copiamos desde el padre desde el inicio hasta el punto de cruce (excluido)
        for (int i = 0; i < punto; i++) {
            hijoProductos.add(productosPadre.get(i));
        }

        // Copiamos desde la madre desde el punto de cruce hasta el final (hasta minSize)
        for (int i = punto; i < minSize; i++) {
            hijoProductos.add(productosMadre.get(i));
        }

        return new Individuo(hijoProductos);
    }
}