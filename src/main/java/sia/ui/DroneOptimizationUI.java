package sia.ui;

import sia.cerebro.AlgoritmoGenetico;
import sia.modelo.ExecutionListener;
import sia.modelo.ExecutionParameters;
import sia.modelo.Individuo;
import sia.modelo.ResultadoEjecucion;

import javax.swing.*;
import java.awt.*;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class DroneOptimizationUI {
    private JFrame frame;
    private ConfigurationPanel configPanel;
    private LogPanel logPanel;
    private ChartPanelWrapper chartPanel;

    public DroneOptimizationUI() {
        configPanel = new ConfigurationPanel();
        logPanel = new LogPanel();
        chartPanel = new ChartPanelWrapper();

        // Configuramos el listener de ejecución
        configPanel.setExecutionListener(params -> {
            // Limpiar log antes de iniciar
            logPanel.clearLog();

            // Mostrar parámetros de ejecución
            logPanel.appendLog("Ejecutando AG con:\n");
            logPanel.appendLog("Generaciones: " + params.getGeneraciones() + "\n");
            logPanel.appendLog("Población: " + params.getTamPoblacion() + "\n");
            logPanel.appendLog("Prob. Cruce: " + params.getProbCruce() + "\n");
            logPanel.appendLog("Prob. Mutación: " + params.getProbMutacion() + "\n");
            logPanel.appendLog("Capacidad Dron: " + params.getMaxPeso() + " Kg\n");
            logPanel.appendLog("Operador Selección: " + params.getOpSeleccion() + "\n");
            logPanel.appendLog("Operador Cruce: " + params.getOpCruza() + "\n");
            logPanel.appendLog("Operador Mutación: " + params.getOpMutacion() + "\n\n");

            // Ejecutar el algoritmo en un hilo aparte
            new Thread(() -> {
                ResultadoEjecucion resultado = AlgoritmoGenetico.ejecutarAlgoritmo(
                        params.getGeneraciones(),
                        params.getTamPoblacion(),
                        params.getProbCruce(),
                        params.getProbMutacion(),
                        params.getOpSeleccion(),
                        params.getOpCruza(),
                        params.getOpMutacion(),
                        logPanel.getLogArea()  // Para ir imprimiendo en el log
                );

                // Imprimir detalles del mejor individuo final
                Individuo mejor = resultado.getMejorIndividuoFinal();
                logPanel.appendLog("\nMejor Individual Final:\n");
                logPanel.appendLog("Aptitud: " + mejor.calcularAptitud() + "\n");
                logPanel.appendLog("Peso Total: " + mejor.getPesoTotal() + "\n");
                logPanel.appendLog("Productos: " + mejor.getProductos().toString() + "\n");

                // Actualizamos el gráfico en el hilo EDT
                SwingUtilities.invokeLater(() -> {
                    chartPanel.updateChart(resultado.getFitnessHistory());
                });
            }).start();
        });
    }

    public void createAndShowUI() {
        frame = new JFrame("Optimización de Envío de Pedidos - Algoritmo Genético");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(900, 600);
        frame.setLocationRelativeTo(null);

        JTabbedPane tabbedPane = new JTabbedPane();
        tabbedPane.addTab("Configuración", configPanel);
        tabbedPane.addTab("Log", logPanel);
        tabbedPane.addTab("Gráfico", chartPanel);

        frame.add(tabbedPane, BorderLayout.CENTER);
        frame.setVisible(true);
    }
}

