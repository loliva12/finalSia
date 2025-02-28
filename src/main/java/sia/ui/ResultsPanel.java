package sia.ui;


import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class ResultsPanel extends JPanel {
    private JTextArea outputArea;
    private JPanel chartContainer;

    public ResultsPanel() {
        setLayout(new BorderLayout());

        // Parte de texto (log)
        outputArea = new JTextArea();
        outputArea.setEditable(false);
        JScrollPane scroll = new JScrollPane(outputArea);
        scroll.setPreferredSize(new Dimension(400, 150));

        // Parte para el gráfico
        chartContainer = new JPanel(new BorderLayout());

        // Usamos un JSplitPane para dividir la zona de texto y la zona de gráfico
        JSplitPane splitPane = new JSplitPane(
                JSplitPane.VERTICAL_SPLIT,
                scroll,
                chartContainer
        );
        // Ajustamos cuánto ocupa inicialmente el log (30%) y el gráfico (70%)
        splitPane.setResizeWeight(0.3);

        add(splitPane, BorderLayout.CENTER);
    }

    // Limpiar el área de texto
    public void clearOutput() {
        outputArea.setText("");
    }

    // Agregar texto al log
    public void appendOutput(String text) {
        outputArea.append(text);
    }

    // Si quieres manipular directamente el JTextArea (por ejemplo, desde AlgoritmoGenetico)
    public JTextArea getOutputArea() {
        return outputArea;
    }

    // Actualiza el gráfico con la lista de aptitudes
    public void updateChart(java.util.List<Double> aptitudes) {
        if (aptitudes.isEmpty()) {
            System.out.println("No hay datos para graficar.");
            return;
        }

        XYSeries serie = new XYSeries("Mejor Aptitud por Generación");
        for (int i = 0; i < aptitudes.size(); i++) {
            double valor = aptitudes.get(i);
            serie.add(i, valor);
        }

        XYSeriesCollection dataset = new XYSeriesCollection();
        dataset.addSeries(serie);

        // Construimos el chart
        JFreeChart chart = ChartFactory.createXYLineChart(
                "Evolución de la Aptitud",
                "Generación",
                "Mejor Aptitud",
                dataset,
                PlotOrientation.VERTICAL,
                true,  // Leyenda
                true,  // Tooltips
                false  // URLs
        );

        // Ajustar el rango del eje Y, si tu aptitud va de 0 a 1
        NumberAxis rangeAxis = (NumberAxis) chart.getXYPlot().getRangeAxis();
        rangeAxis.setRange(0.0, 1.1);

        // Ponemos el chart en un ChartPanel y lo metemos en chartContainer
        ChartPanel cp = new ChartPanel(chart);
        chartContainer.removeAll();
        chartContainer.add(cp, BorderLayout.CENTER);
        chartContainer.revalidate();
        chartContainer.repaint();
    }
}
