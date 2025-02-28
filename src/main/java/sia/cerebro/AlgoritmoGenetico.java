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
        // 1. Genera la población inicial
        List<Individuo> poblacion = generarPoblacionInicial(tamPoblacion);
        List<Double> historialAptitud = new ArrayList<>();
        Random rand = new Random();

        // 2. Elige los operadores según los parámetros
        Seleccion seleccion = (opSeleccion.equalsIgnoreCase("Torneo"))
                ? new SeleccionTorneo()
                : new SeleccionRuleta();
        Cruza cruza = (opCruza.equalsIgnoreCase("Punto"))
                ? new CruzaPunto()
                : new CruzaUniforme();
        Mutacion mutacion = (opMutacion.equalsIgnoreCase("Bit"))
                ? new MutacionBit()
                : new MutacionSwap();

        // 3. Itera por cada generación
        for (int gen = 0; gen < generaciones; gen++) {

            // 3.1 Encuentra el mejor individuo (mayor aptitud) de la población actual
            Individuo mejorIndividuo = Collections.max(
                    poblacion,
                    Comparator.comparingDouble(Individuo::calcularAptitud)
            );

            // 3.2 Crea la nueva población e incluye al mejor como élite
            List<Individuo> nuevaPoblacion = new ArrayList<>();
            // Clonamos al mejor para no modificar el objeto original
            Individuo clonMejor = new Individuo(mejorIndividuo.getProductos());
            nuevaPoblacion.add(clonMejor);

            // 3.3 Genera el resto de la nueva población mediante selección, cruza y mutación
            while (nuevaPoblacion.size() < tamPoblacion) {
                // Selección: padre y madre (tú usas Torneo + Ruleta)
                Individuo padre = seleccion.seleccionar(poblacion, 3);
                Individuo madre = seleccion.seleccionarRuleta(poblacion);
                if (madre == null) {
                    madre = padre;
                }

                // Cruza con probabilidad probCruce
                Individuo hijo;
                if (rand.nextDouble() < probCruce) {
                    // Alternas quién es "padre" y quién es "madre" en la cruza
                    hijo = (rand.nextBoolean())
                            ? cruza.cruzar(padre, madre)
                            : cruza.cruzar(madre, padre);
                } else {
                    // Si no hay cruza, se clona a la madre (o padre)
                    hijo = new Individuo(madre.getProductos());
                }

                // Mutación con probMutacion
                if (rand.nextDouble() < probMutacion) {
                    mutacion.mutar(hijo);
                }

                nuevaPoblacion.add(hijo);
            }

            // 3.4 Reemplazamos la población anterior por la nueva
            poblacion = nuevaPoblacion;

            // 3.5 Seleccionamos el mejor individuo de la nueva población (para registrar su aptitud)
            double mejorAptitud = 0;
            for (Individuo ind : poblacion) {
                double apt = ind.calcularAptitud();
                if (apt > mejorAptitud) {
                    mejorAptitud = apt;
                }
            }
            historialAptitud.add(mejorAptitud);

            // 3.6 Imprimimos en el JTextArea
            outputArea.append("Generación " + gen + " - Mejor aptitud: " + mejorAptitud + "\n");
        }

        // 4. Retornamos el historial de aptitudes
        return historialAptitud;
    }


}


