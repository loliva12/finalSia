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

public class ChartPanelWrapper  extends JPanel {
    private ChartPanel chartPanel; // de JFreeChart

    public ChartPanelWrapper() {
        setLayout(new BorderLayout());
        // Podrías inicializar un chart vacío al inicio
        JFreeChart emptyChart = ChartFactory.createXYLineChart(
                "Evolución de la Aptitud",
                "Generación",
                "Aptitud",
                new XYSeriesCollection(),
                PlotOrientation.VERTICAL,
                true, true, false
        );
        chartPanel = new ChartPanel(emptyChart);
        add(chartPanel, BorderLayout.CENTER);
    }

    public void updateChart(List<Double> aptitudes) {
        if (aptitudes.isEmpty()) return;

        XYSeries serie = new XYSeries("Mejor Aptitud por Generación");
        for (int i = 0; i < aptitudes.size(); i++) {
            serie.add(i, aptitudes.get(i));
        }
        XYSeriesCollection dataset = new XYSeriesCollection();
        dataset.addSeries(serie);

        JFreeChart chart = ChartFactory.createXYLineChart(
                "Evolución de la Aptitud",
                "Generación",
                "Aptitud",
                dataset,
                PlotOrientation.VERTICAL,
                true,  // Leyenda
                true,  // Tooltips
                false  // URLs
        );

        // Ajustar eje Y para que vaya de 0..1.1, si tu aptitud está en [0..1]
        NumberAxis rangeAxis = (NumberAxis) chart.getXYPlot().getRangeAxis();
        rangeAxis.setRange(0.0, 1.1);

        chartPanel.setChart(chart);
    }
}
