package sia.ui;


import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
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
        outputArea = new JTextArea(10, 40);
        outputArea.setEditable(false);
        add(new JScrollPane(outputArea), BorderLayout.CENTER);
        chartContainer = new JPanel(new BorderLayout());
        chartContainer.setPreferredSize(new Dimension(400, 250));
        add(chartContainer, BorderLayout.SOUTH);
    }

    public void appendOutput(String text) {
        outputArea.append(text);
    }

    public void clearOutput() {
        outputArea.setText("");
    }

    public JTextArea getOutputArea() {
        return outputArea;
    }

    public void updateChart(List<Double> aptitudes) {
        if (aptitudes.isEmpty()) {
            System.out.println("No hay datos para graficar.");
            return;
        }

        // Definimos un valor máximo para la visualización (ajústalo según lo que esperes)
        double maxVisual = 100.0;

        XYSeries serie = new XYSeries("Mejor Aptitud por Generación");
        for (int i = 0; i < aptitudes.size(); i++) {
            double valor = aptitudes.get(i);
            // Si el valor no es finito o es mayor al umbral, lo recortamos
            if (!Double.isFinite(valor) || valor > maxVisual) {
                valor = maxVisual;
            }
            serie.add(i, valor);
        }

        XYSeriesCollection dataset = new XYSeriesCollection();
        dataset.addSeries(serie);

        JFreeChart chart = ChartFactory.createXYLineChart(
                "Evolución de la Aptitud",
                "Generación",
                "Mejor Aptitud",
                dataset,
                PlotOrientation.VERTICAL,
                true, true, false
        );

        // Fijar manualmente el rango del eje Y para evitar valores extremos
        org.jfree.chart.axis.NumberAxis rangeAxis = (org.jfree.chart.axis.NumberAxis) chart.getXYPlot().getRangeAxis();
        rangeAxis.setRange(0, maxVisual + 10); // Por ejemplo, de 0 a 110

        ChartPanel cp = new ChartPanel(chart);
        chartContainer.removeAll();
        chartContainer.setLayout(new BorderLayout());
        chartContainer.add(cp, BorderLayout.CENTER);
        chartContainer.revalidate();
        chartContainer.repaint();
    }
}
