package sia.modelo;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

// representa un cromosoma
// representa un subconjunto de productos
public class Individuo {
    // Lista de productos que forman parte de este individuo.
    private List<Producto> productos;
    private double pesoTotal;

    public Individuo(List<Producto> productos) {
        // Se hace una copia para evitar efectos colaterales.
        this.productos = new ArrayList<>(productos);
        this.pesoTotal = calcularPesoTotal();
    }

    private double calcularPesoTotal() {
        double suma = 0;
        for (Producto p : productos) {
            suma += p.getPeso();
        }
        return suma;
    }

    /**
     * Calcula la aptitud del individuo.
     * Se define el error como la diferencia absoluta entre 17 y el peso total,
     * y la aptitud se transforma en un valor entre 0 y 1 usando:
     *    aptitud = 1 / (1 + error)
     *
     * @return aptitud, donde 1 es óptimo (error 0) y valores menores son peores.
     */
    public double calcularAptitud() {
        final double capacidad = 17.0;
        double error = Math.abs(capacidad - pesoTotal);
        return 1.0 / (1.0 + error);
    }

    public List<Producto> getProductos() {
        return productos;
    }

    @Override
    public String toString() {
        return "Individuo: " + productos.toString() + " - Peso total: " + pesoTotal + " kg - Aptitud: " + calcularAptitud();
    }
}
