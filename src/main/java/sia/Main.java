package sia;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.*;
import java.util.List;
import javax.swing.*;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;
import sia.operador.Cruza;
import sia.operador.Mutacion;
import sia.operador.Seleccion;
import sia.operador.impl.*;

import static sia.AlgoritmoGenetico.*;

public class Main {
    private static JTextArea outputArea;
    private static JTextField txtGeneraciones, txtTamPoblacion, txtProbCruce, txtProbMutacion;
    private static DefaultListModel<Producto> productosModel;
    private static JList<Producto> listaProductos;
    private static JTextField txtNombreProducto, txtPesoProducto;

    public static void main(String[] args) {
        SwingUtilities.invokeLater(Main::crearInterfaz);
    }

    private static void crearInterfaz() {
        JFrame frame = new JFrame("Configuración Algoritmo Genético");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(600, 600);
        frame.setLayout(new BorderLayout());

        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.gridx = 0;
        gbc.gridy = 0;

        panel.add(new JLabel("Generaciones (Cantidad de iteraciones):"), gbc);
        gbc.gridx = 1;
        txtGeneraciones = new JTextField("100", 10);
        panel.add(txtGeneraciones, gbc);

        gbc.gridx = 0;
        gbc.gridy++;
        panel.add(new JLabel("Tamaño de Población (Número de individuos en cada generación):"), gbc);
        gbc.gridx = 1;
        txtTamPoblacion = new JTextField("50", 10);
        panel.add(txtTamPoblacion, gbc);

        gbc.gridx = 0;
        gbc.gridy++;
        panel.add(new JLabel("Probabilidad de Cruce (Ej: 0.7 para 70% de cruce):"), gbc);
        gbc.gridx = 1;
        txtProbCruce = new JTextField("0.7", 10);
        panel.add(txtProbCruce, gbc);

        gbc.gridx = 0;
        gbc.gridy++;
        panel.add(new JLabel("Probabilidad de Mutación (Ej: 0.1 para 10% de mutación):"), gbc);
        gbc.gridx = 1;
        txtProbMutacion = new JTextField("0.1", 10);
        panel.add(txtProbMutacion, gbc);

        gbc.gridx = 0;
        gbc.gridy++;
        gbc.gridwidth = 2;
        panel.add(new JLabel("Productos disponibles:"), gbc);

        productosModel = new DefaultListModel<>();
        listaProductos = new JList<>(productosModel);

        for (Producto p : AlgoritmoGenetico.productos) {
            productosModel.addElement(p);
        }

        JScrollPane scrollProductos = new JScrollPane(listaProductos);
        scrollProductos.setPreferredSize(new Dimension(200, 100));
        gbc.gridy++;
        panel.add(scrollProductos, gbc);

        JPanel panelProducto = new JPanel(new FlowLayout());
        panelProducto.add(new JLabel("Nombre del Producto:"));
        txtNombreProducto = new JTextField(10);
        panelProducto.add(txtNombreProducto);

        panelProducto.add(new JLabel("Peso del Producto (kg):"));
        txtPesoProducto = new JTextField(5);
        panelProducto.add(txtPesoProducto);

        JButton btnAgregarProducto = new JButton("Agregar");
        JButton btnEliminarProducto = new JButton("Eliminar");
        JButton btnVerProductos = new JButton("Ver Productos");
        panelProducto.add(btnAgregarProducto);
        panelProducto.add(btnEliminarProducto);
        panelProducto.add(btnVerProductos);
        panelProducto.add(btnVerProductos);
        gbc.gridy++;
        panel.add(panelProducto, gbc);

        JButton btnEjecutar = new JButton("Ejecutar");
        gbc.gridy++;
        panel.add(btnEjecutar, gbc);

        outputArea = new JTextArea(10, 40);
        outputArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(outputArea);
        frame.add(panel, BorderLayout.NORTH);
        frame.add(scrollPane, BorderLayout.CENTER);

        btnAgregarProducto.addActionListener(e -> agregarProducto());
        btnEliminarProducto.addActionListener(e -> eliminarProducto());
        btnVerProductos.addActionListener(e -> verProductos());
        btnEjecutar.addActionListener(e -> ejecutarAlgoritmo());

        frame.setVisible(true);
    }


    private static void eliminarProducto() {
        int selectedIndex = listaProductos.getSelectedIndex();
        if (selectedIndex != -1) {
            productosModel.remove(selectedIndex);
        } else {
            JOptionPane.showMessageDialog(null, "Seleccione un producto para eliminar", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private static void verProductos() {
        if (productosModel.isEmpty()) {
            JOptionPane.showMessageDialog(null, "No hay productos en la lista", "Información", JOptionPane.INFORMATION_MESSAGE);
            return;
        }
        StringBuilder productos = new StringBuilder("Lista de Productos:\n");
        for (int i = 0; i < productosModel.getSize(); i++) {
            productos.append(productosModel.getElementAt(i).toString()).append("\n");
        }
        JOptionPane.showMessageDialog(null, productos.toString(), "Productos", JOptionPane.INFORMATION_MESSAGE);
    }

    private static void agregarProducto() {
        String nombre = txtNombreProducto.getText();
        double peso;
        try {
            peso = Double.parseDouble(txtPesoProducto.getText());
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(null, "Peso inválido", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        productosModel.addElement(new Producto(nombre, peso));
    }

    private static void ejecutarAlgoritmo() {
        int generaciones = Integer.parseInt(txtGeneraciones.getText());
        int tamPoblacion = Integer.parseInt(txtTamPoblacion.getText());
        double probCruce = Double.parseDouble(txtProbCruce.getText());
        double probMutacion = Double.parseDouble(txtProbMutacion.getText());

        List<Individuo> poblacion = generarPoblacionInicial(tamPoblacion);
        List<Double> historialAptitud = new ArrayList<>();

        Seleccion seleccionTorneo = new SeleccionTorneo();
        Seleccion seleccionRuleta = new SeleccionRuleta();
        Cruza cruzaPunto = new CruzaPunto();
        Cruza cruzaUniforme = new CruzaUniforme();
        Mutacion mutacionBit = new MutacionBit();
        Mutacion mutacionSwap = new MutacionSwap();

        for (int gen = 0; gen < generaciones; gen++) {
            List<Individuo> nuevaPoblacion = new ArrayList<>();
            while (nuevaPoblacion.size() < tamPoblacion) {
                int tamTorneo = 3;
                Individuo padre = seleccionTorneo.seleccionar(poblacion, tamTorneo);
                Individuo madre = seleccionRuleta.seleccionarRuleta(poblacion);

                if (Math.random() < probCruce) {
                    Individuo hijo;
                    if (Math.random() < 0.5) {
                        hijo = cruzaPunto.cruzar(padre, madre);
                    } else {
                        hijo = cruzaUniforme.cruzar(padre, madre);
                    }
                    nuevaPoblacion.add(hijo);
                } else {
                    nuevaPoblacion.add(madre);
                }

                if (Math.random() < probMutacion) {
                    if (Math.random() < 0.5) {
                        mutacionBit.mutar(nuevaPoblacion.get(nuevaPoblacion.size() - 1));
                    } else {
                        mutacionSwap.mutar(nuevaPoblacion.get(nuevaPoblacion.size() - 1));
                    }
                }
            }

            poblacion = nuevaPoblacion;
            double mejorAptitud = poblacion.stream()
                    .mapToDouble(Individuo::calcularAptitud)
                    .min()
                    .orElse(Double.MAX_VALUE);

            historialAptitud.add(mejorAptitud);
            outputArea.append("Generación " + gen + " - Mejor aptitud: " + mejorAptitud + "\n");
        }
        mostrarGrafico(historialAptitud);
    }



    static List<Individuo> generarPoblacionInicial(int tamPoblacion) {
        List<Individuo> poblacion = new ArrayList<>();
        Random rand = new Random();
        for (int i = 0; i < tamPoblacion; i++) {
            List<Producto> seleccion = new ArrayList<>();
            for (Producto p : productos) {
                if (rand.nextBoolean()) seleccion.add(p);
            }
            poblacion.add(new Individuo(seleccion));
        }
        return poblacion;
    }

    static void mostrarGrafico(List<Double> aptitudes) {
        if (aptitudes.isEmpty()) {
            System.out.println("No hay datos para graficar.");
            return;
        }

        XYSeries serie = new XYSeries("Mejor Aptitud por Generación");
        for (int i = 0; i < aptitudes.size(); i++) {
            serie.add(i, aptitudes.get(i));
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

        JFrame frame = new JFrame("Gráfico de Aptitud");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.add(new ChartPanel(chart));
        frame.pack();
        frame.setVisible(true);
    }
}
