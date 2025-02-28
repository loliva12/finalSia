package sia.operador.impl;

import sia.cerebro.AlgoritmoGenetico;
import sia.modelo.Individuo;
import sia.modelo.Producto;
import sia.operador.Mutacion;

import java.util.List;
import java.util.Random;

/*
 * introduce cambios aleatorios en los genes de un individuo para mantener la diversidad genética
 * en la población y evitar la convergencia prematura hacia soluciones subóptimas
 * */

public class MutacionBit implements Mutacion {

    private Random rand = new Random();

    @Override
    public void mutar(Individuo ind) {
        List<Producto> productos = ind.getProductos();
        if (productos.isEmpty() || rand.nextBoolean()) {
            // Agrega un producto aleatorio de la lista completa
            List<Producto> allProducts = AlgoritmoGenetico.getProductos();
            Producto randomProduct = allProducts.get(rand.nextInt(allProducts.size()));
            productos.add(randomProduct);
        } else {
            // Remueve un producto aleatorio
            int index = rand.nextInt(productos.size());
            productos.remove(index);
        }
        // Actualiza el peso total luego de la mutación
        ind.recalcularPeso();
    }
}
