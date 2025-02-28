package sia.operador.impl;

import sia.modelo.Individuo;
import sia.modelo.Producto;
import sia.operador.Cruza;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/*
 * cada gen del descendiente se elige aleatoriamente de uno de los dos padres.
 * */

public class CruzaUniforme implements Cruza {
    private Random rand = new Random();

    @Override
    public Individuo cruzar(Individuo padre, Individuo madre) {
        List<Producto> productosPadre = padre.getProductos();
        List<Producto> productosMadre = madre.getProductos();
        // Definir el tamaño máximo entre ambos padres
        int size = Math.max(productosPadre.size(), productosMadre.size());
        List<Producto> hijoProductos = new ArrayList<>();

        for (int i = 0; i < size; i++) {
            // Decide aleatoriamente de qué padre tomar el gen
            boolean elegirPadre = rand.nextBoolean();
            if (elegirPadre) {
                if (i < productosPadre.size()) {
                    hijoProductos.add(productosPadre.get(i));
                } else if (i < productosMadre.size()) {
                    // Si el padre no tiene gen en esta posición, toma el de la madre
                    hijoProductos.add(productosMadre.get(i));
                }
            } else {
                if (i < productosMadre.size()) {
                    hijoProductos.add(productosMadre.get(i));
                } else if (i < productosPadre.size()) {
                    // Si la madre no tiene gen en esta posición, toma el del padre
                    hijoProductos.add(productosPadre.get(i));
                }
            }
        }
        return new Individuo(hijoProductos);
    }
}