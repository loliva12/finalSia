package sia.ui;

import sia.cerebro.AlgoritmoGenetico;
import sia.modelo.ExecutionListener;
import sia.modelo.ExecutionParameters;

import javax.swing.*;
import java.util.List;

public class DroneOptimizationUI {
    private JFrame frame;
    private ConfigurationPanel configPanel;
    private ResultsPanel resultsPanel;

    public DroneOptimizationUI() {
        configPanel = new ConfigurationPanel();
        resultsPanel = new ResultsPanel();

        // Se asigna el listener para ejecutar el algoritmo
        configPanel.setExecutionListener(new ExecutionListener() {
            @Override
            public void execute(ExecutionParameters params) {
                resultsPanel.clearOutput();
                resultsPanel.appendOutput("Ejecutando AG con:\n");
                resultsPanel.appendOutput("Generaciones: " + params.getGeneraciones() + "\n");
                resultsPanel.appendOutput("Población: " + params.getTamPoblacion() + "\n");
                resultsPanel.appendOutput("Prob. Cruce: " + params.getProbCruce() + "\n");
                resultsPanel.appendOutput("Prob. Mutación: " + params.getProbMutacion() + "\n");
                resultsPanel.appendOutput("Capacidad Dron: " + params.getMaxPeso() + " Kg\n");
                resultsPanel.appendOutput("Operador Selección: " + params.getOpSeleccion() + "\n");
                resultsPanel.appendOutput("Operador Cruce: " + params.getOpCruza() + "\n");
                resultsPanel.appendOutput("Operador Mutación: " + params.getOpMutacion() + "\n\n");

                // Se ejecuta el algoritmo en un hilo aparte para no bloquear la UI
                new Thread(() -> {
                    java.util.List<Double> fitnessHistory = AlgoritmoGenetico.ejecutarAlgoritmo(
                            params.getGeneraciones(),
                            params.getTamPoblacion(),
                            params.getProbCruce(),
                            params.getProbMutacion(),
                            params.getOpSeleccion(),
                            params.getOpCruza(),
                            params.getOpMutacion(),
                            resultsPanel.getOutputArea()
                    );
                    SwingUtilities.invokeLater(() -> {
                        resultsPanel.updateChart(fitnessHistory);
                    });
                }).start();
            }
        });
    }

    public void createAndShowUI() {
        frame = new JFrame("Optimización de Envío de Pedidos - Algoritmo Genético");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(800, 600);
        frame.setLocationRelativeTo(null);

        JTabbedPane tabbedPane = new JTabbedPane();
        tabbedPane.addTab("Configuración", configPanel);
        tabbedPane.addTab("Resultados", resultsPanel);

        frame.add(tabbedPane);
        frame.setVisible(true);
    }
}

