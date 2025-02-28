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

        // Creamos la serie con los datos
        XYSeries serie = new XYSeries("Mejor Aptitud por Generación");
        for (int i = 0; i < aptitudes.size(); i++) {
            double valor = aptitudes.get(i); // Aquí NO escalamos
            serie.add(i, valor);
        }

        // Armamos el dataset
        XYSeriesCollection dataset = new XYSeriesCollection();
        dataset.addSeries(serie);

        // Construimos el chart
        JFreeChart chart = ChartFactory.createXYLineChart(
                "Evolución de la Aptitud",
                "Generación",
                "Mejor Aptitud",
                dataset,
                PlotOrientation.VERTICAL,
                true,  // leyenda
                true,  // tooltips
                false  // URLs
        );

        // Ajustamos el rango del eje Y para que se vea de 0 a 1 (o 1.1)
        NumberAxis rangeAxis = (NumberAxis) chart.getXYPlot().getRangeAxis();
        rangeAxis.setRange(0.0, 1.1);

        // Agregamos el chart al panel
        ChartPanel cp = new ChartPanel(chart);
        chartContainer.removeAll();
        chartContainer.setLayout(new BorderLayout());
        chartContainer.add(cp, BorderLayout.CENTER);
        chartContainer.revalidate();
        chartContainer.repaint();
    }
}
