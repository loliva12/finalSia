package sia.cerebro;

import javax.swing.*;

import sia.modelo.Individuo;
import sia.modelo.Producto;
import sia.operador.Cruza;
import sia.operador.Mutacion;
import sia.operador.Seleccion;
import sia.operador.impl.*;

import java.util.*;

public class AlgoritmoGenetico {
    private static final List<Producto> productos = new ArrayList<Producto>() {{
        add(new Producto("Notebook", 2.1));
        add(new Producto("Tablet", 0.6));
        add(new Producto("Parlante Bluetooth", 3.6));
        add(new Producto("Smart TV", 5.0));
        add(new Producto("Smartphone", 0.25));
        add(new Producto("Impresora laser", 10.0));
        add(new Producto("Ventilador 15\"", 6.0));
        add(new Producto("Cámara GoPro", 0.16));
        add(new Producto("Router wifi", 0.55));
        add(new Producto("Aro luz 18\"", 2.0));
    }};

    public static List<Producto> getProductos() {
        return productos;
    }

    // Genera población inicial. Para cada producto se elige aleatoriamente entre 0 y 3 copias.
    public static List<Individuo> generarPoblacionInicial(int tamPoblacion) {
        List<Individuo> poblacion = new ArrayList<>();
        Random rand = new Random();
        for (int i = 0; i < tamPoblacion; i++) {
            List<Producto> seleccion = new ArrayList<>();
            for (Producto p : productos) {
                int cantidad = rand.nextInt(4); // 0 a 3 copias
                for (int j = 0; j < cantidad; j++) {
                    seleccion.add(p);
                }
            }
            poblacion.add(new Individuo(seleccion));
        }
        return poblacion;
    }

    public static List<Double> ejecutarAlgoritmo(int generaciones, int tamPoblacion, double probCruce, double probMutacion,
                                                 String opSeleccion, String opCruza, String opMutacion, JTextArea outputArea) {
        // Genera la población inicial
        List<Individuo> poblacion = generarPoblacionInicial(tamPoblacion);
        List<Double> historialAptitud = new ArrayList<>();
        Random rand = new Random();

        // Aquí seleccionas tus operadores según parámetros (no se muestra la parte completa)
        Seleccion seleccion = (opSeleccion.equalsIgnoreCase("Torneo"))
                ? new SeleccionTorneo()
                : new SeleccionRuleta();
        Cruza cruza = (opCruza.equalsIgnoreCase("Punto"))
                ? new CruzaPunto()
                : new CruzaUniforme();
        Mutacion mutacion = (opMutacion.equalsIgnoreCase("Bit"))
                ? new MutacionBit()
                : new MutacionSwap();

        for (int gen = 0; gen < generaciones; gen++) {
            List<Individuo> nuevaPoblacion = new ArrayList<>();
            while (nuevaPoblacion.size() < tamPoblacion) {
                Individuo padre = seleccion.seleccionar(poblacion, 3);
                Individuo madre = seleccion.seleccionarRuleta(poblacion);
                if (madre == null) {
                    madre = padre;
                }
                Individuo hijo;
                if (rand.nextDouble() < probCruce) {
                    hijo = (rand.nextBoolean()) ? cruza.cruzar(padre, madre) : cruza.cruzar(madre, padre);
                } else {
                    hijo = new Individuo(madre.getProductos());
                }
                if (rand.nextDouble() < probMutacion) {
                    mutacion.mutar(hijo);
                }
                nuevaPoblacion.add(hijo);
            }
            poblacion = nuevaPoblacion;

            // Seleccionamos el mejor individuo (mayor aptitud es mejor)
            double mejorAptitud = 0;
            for (Individuo ind : poblacion) {
                double apt = ind.calcularAptitud();
                if (apt > mejorAptitud) {
                    mejorAptitud = apt;
                }
            }
            historialAptitud.add(mejorAptitud);
            outputArea.append("Generación " + gen + " - Mejor aptitud: " + mejorAptitud + "\n");
        }
        return historialAptitud;
    }


}


