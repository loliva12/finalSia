package sia.ui;

import sia.cerebro.AlgoritmoGenetico;
import sia.modelo.ExecutionListener;
import sia.modelo.ExecutionParameters;
import sia.modelo.Producto;

import javax.swing.*;
import java.awt.*;

public class ConfigurationPanel extends JPanel {
    private JTextField txtGeneraciones, txtTamPoblacion, txtProbCruce, txtProbMutacion, txtMaxPeso;
    private JComboBox<String> cbSeleccion, cbCruza, cbMutacion;
    private DefaultListModel<Producto> productosModel;
    private JList<Producto> listaProductos;
    private JTextField txtNombreProducto, txtPesoProducto;
    private JButton btnAgregarProducto, btnEliminarProducto, btnEjecutar;
    private ExecutionListener executionListener;

    public ConfigurationPanel() {
        setLayout(new BorderLayout());
        add(createInputPanel(), BorderLayout.CENTER);
    }

    public void setExecutionListener(ExecutionListener listener) {
        this.executionListener = listener;
    }

    private JPanel createInputPanel() {
        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        int row = 0;

        // Generaciones
        gbc.gridx = 0; gbc.gridy = row;
        panel.add(new JLabel("Generaciones:"), gbc);
        gbc.gridx = 1;
        txtGeneraciones = new JTextField("100", 10);
        txtGeneraciones.setToolTipText("Número de iteraciones del algoritmo.");
        panel.add(txtGeneraciones, gbc);
        row++;

        // Tamaño de Población
        gbc.gridx = 0; gbc.gridy = row;
        panel.add(new JLabel("Tamaño de Población:"), gbc);
        gbc.gridx = 1;
        txtTamPoblacion = new JTextField("50", 10);
        txtTamPoblacion.setToolTipText("Número de individuos en cada generación.");
        panel.add(txtTamPoblacion, gbc);
        row++;

        // Probabilidad de Cruce
        gbc.gridx = 0; gbc.gridy = row;
        panel.add(new JLabel("Probabilidad de Cruce:"), gbc);
        gbc.gridx = 1;
        txtProbCruce = new JTextField("0.7", 10);
        txtProbCruce.setToolTipText("Ejemplo: 0.7 para 70% de cruce.");
        panel.add(txtProbCruce, gbc);
        row++;

        // Probabilidad de Mutación
        gbc.gridx = 0; gbc.gridy = row;
        panel.add(new JLabel("Probabilidad de Mutación:"), gbc);
        gbc.gridx = 1;
        txtProbMutacion = new JTextField("0.1", 10);
        txtProbMutacion.setToolTipText("Ejemplo: 0.1 para 10% de mutación.");
        panel.add(txtProbMutacion, gbc);
        row++;

        // Capacidad Máxima del Dron
        gbc.gridx = 0; gbc.gridy = row;
        panel.add(new JLabel("Capacidad del Dron (Kg):"), gbc);
        gbc.gridx = 1;
        txtMaxPeso = new JTextField("17", 10);
        txtMaxPeso.setToolTipText("Peso máximo que puede cargar el dron.");
        panel.add(txtMaxPeso, gbc);
        row++;

        // Operador de Selección
        gbc.gridx = 0; gbc.gridy = row;
        panel.add(new JLabel("Operador de Selección:"), gbc);
        gbc.gridx = 1;
        cbSeleccion = new JComboBox<>(new String[]{"Torneo", "Ruleta"});
        cbSeleccion.setToolTipText("Seleccione el método de selección.");
        panel.add(cbSeleccion, gbc);
        row++;

        // Operador de Cruce
        gbc.gridx = 0; gbc.gridy = row;
        panel.add(new JLabel("Operador de Cruce:"), gbc);
        gbc.gridx = 1;
        cbCruza = new JComboBox<>(new String[]{"Punto", "Uniforme"});
        cbCruza.setToolTipText("Seleccione el método de cruce.");
        panel.add(cbCruza, gbc);
        row++;

        // Operador de Mutación
        gbc.gridx = 0; gbc.gridy = row;
        panel.add(new JLabel("Operador de Mutación:"), gbc);
        gbc.gridx = 1;
        cbMutacion = new JComboBox<>(new String[]{"Bit", "Swap"});
        cbMutacion.setToolTipText("Seleccione el método de mutación.");
        panel.add(cbMutacion, gbc);
        row++;

        // Lista de Productos
        gbc.gridx = 0; gbc.gridy = row; gbc.gridwidth = 2;
        panel.add(new JLabel("Productos Disponibles:"), gbc);
        row++;

        productosModel = new DefaultListModel<>();
        listaProductos = new JList<>(productosModel);
        // Inicializa con los productos globales de AlgoritmoGenetico
        for (Producto p : AlgoritmoGenetico.getProductos()) {
            productosModel.addElement(p);
        }
        JScrollPane scrollProductos = new JScrollPane(listaProductos);
        scrollProductos.setPreferredSize(new Dimension(250, 100));
        gbc.gridx = 0; gbc.gridy = row; gbc.gridwidth = 2;
        panel.add(scrollProductos, gbc);
        row++;

        // Panel para agregar/eliminar productos
        JPanel productPanel = new JPanel(new FlowLayout());
        productPanel.add(new JLabel("Nombre:"));
        txtNombreProducto = new JTextField(10);
        productPanel.add(txtNombreProducto);
        productPanel.add(new JLabel("Peso (Kg):"));
        txtPesoProducto = new JTextField(5);
        productPanel.add(txtPesoProducto);
        btnAgregarProducto = new JButton("Agregar");
        btnEliminarProducto = new JButton("Eliminar");
        productPanel.add(btnAgregarProducto);
        productPanel.add(btnEliminarProducto);
        gbc.gridx = 0; gbc.gridy = row; gbc.gridwidth = 2;
        panel.add(productPanel, gbc);
        row++;

        // Botón para ejecutar el algoritmo
        btnEjecutar = new JButton("Ejecutar Algoritmo");
        gbc.gridx = 0; gbc.gridy = row; gbc.gridwidth = 2;
        panel.add(btnEjecutar, gbc);

        // ActionListeners
        btnAgregarProducto.addActionListener(e -> agregarProducto());
        btnEliminarProducto.addActionListener(e -> eliminarProducto());
        btnEjecutar.addActionListener(e -> ejecutar());

        return panel;
    }

    private void agregarProducto() {
        String nombre = txtNombreProducto.getText().trim();
        if (nombre.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Ingrese el nombre del producto.");
            return;
        }
        double peso;
        try {
            peso = Double.parseDouble(txtPesoProducto.getText());
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Peso inválido.");
            return;
        }
        Producto nuevoProducto = new Producto(nombre, peso);
        productosModel.addElement(nuevoProducto);
        AlgoritmoGenetico.getProductos().add(nuevoProducto);
        txtNombreProducto.setText("");
        txtPesoProducto.setText("");
    }

    private void eliminarProducto() {
        int index = listaProductos.getSelectedIndex();
        if (index != -1) {
            Producto p = productosModel.getElementAt(index);
            productosModel.remove(index);
            AlgoritmoGenetico.getProductos().remove(p);
        } else {
            JOptionPane.showMessageDialog(this, "Seleccione un producto para eliminar.");
        }
    }

    private void ejecutar() {
        try {
            int generaciones = Integer.parseInt(txtGeneraciones.getText());
            int tamPoblacion = Integer.parseInt(txtTamPoblacion.getText());
            double probCruce = Double.parseDouble(txtProbCruce.getText());
            double probMutacion = Double.parseDouble(txtProbMutacion.getText());
            int maxPeso = Integer.parseInt(txtMaxPeso.getText());
            String opSeleccion = (String) cbSeleccion.getSelectedItem();
            String opCruza = (String) cbCruza.getSelectedItem();
            String opMutacion = (String) cbMutacion.getSelectedItem();

            ExecutionParameters params = new ExecutionParameters(
                    generaciones, tamPoblacion, probCruce, probMutacion, maxPeso,
                    opSeleccion, opCruza, opMutacion
            );
            if (executionListener != null) {
                executionListener.execute(params);
            }
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Error en la conversión de parámetros numéricos.");
        }
    }
}
